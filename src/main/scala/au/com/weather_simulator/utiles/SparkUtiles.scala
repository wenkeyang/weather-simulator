package au.com.weather_simulator.utiles

import scala.util.Random
import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}
import au.com.weather_simulator.typing.{SparkReadException, SparkWriteException}

object SparkUtiles extends LoggingSupport {

  private val rand = new Random(System.currentTimeMillis)

  private def random(min: Int, max: Int) = min + rand.nextInt(max - min)

  def readCSV(filePath: String)(implicit sparksession: SparkSession): DataFrame = {

    log.info("Reading files from " + filePath)
    try {
      sparksession
        .read
        .option("header", "true")
        .option("delimiter", "|")
        .option("maxCharsPerColumn", "50000000")
        .csv(filePath)
    } catch {
      case e: Exception => throw SparkReadException(s"Error while reading CSV $filePath: " + e.getMessage, e)
    }
  }

  def writeCSV[T](dataset: Dataset[T], filePath: String, saveMode: SaveMode = SaveMode.Overwrite): String = {
    log.info(s"Writing CSV files at $filePath with saveMode=$saveMode")
    try {
      dataset
        .repartition(1)
        .write
        .option("delimiter", "|")
        .option("emptyValue", "")
        .option("quote", "")
        .mode(saveMode)
        .csv(filePath)
      filePath
    } catch {
      case e: Exception => throw SparkWriteException(s"Error while writing CSV $filePath with saveMode=$saveMode => ${e.getMessage}", e)
    }
  }

  def getSparkSession(app_name: String): SparkSession = {
    SparkSession
      .builder()
      .appName(getClass.getName)
      .master("local[*]")
      .config("spark.rdd.compress", true)
      .config("spark.ui.port", random(4000, 5000))
      .config("spark.history.ui.port", random(18000, 19000))
      .config("spark.sql.crossJoin.enabled", true)
      .getOrCreate()
  }
}

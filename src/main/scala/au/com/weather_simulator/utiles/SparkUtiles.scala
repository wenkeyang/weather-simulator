package au.com.weather_simulator.utiles

import org.apache.spark.sql.{DataFrame, Dataset, SaveMode, SparkSession}

object SparkUtiles extends LoggingSupport {

  case class SparkReadException(message: String, cause: Throwable) extends Exception(message, cause)

  case class SparkWriteException(message: String, cause: Throwable) extends Exception(message, cause)

  def readCSV(filePath: String)(implicit sparkSession: SparkSession): DataFrame = {

    log.info("Reading files from " + filePath)
    try {
      sparkSession
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
        .write
        .option("header", "true")
        .option("delimiter", "|")
        .option("emptyValue", "")
        .mode(saveMode)
        .csv(filePath)
      filePath
    } catch {
      case e: Exception => throw SparkWriteException(s"Error while writing CSV $filePath with saveMode=$saveMode => ${e.getMessage}", e)
    }
  }
}

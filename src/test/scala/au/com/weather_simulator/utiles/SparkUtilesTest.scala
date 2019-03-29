package au.com.weather_simulator.utiles

import au.com.weather_simulator.testutils.TestUtils
import au.com.weather_simulator.typing.{SparkReadException, SparkWriteException}
import org.specs2.mock.Mockito

class SparkUtilesTest extends TestUtils with Mockito {
  sequential

  "function readCSV " should {
    "return an exception when the path does not exists" in {
      SparkUtiles.readCSV("src/test/resources/readCSVInput1BAD") must throwA[SparkReadException]
    }
    "return dataset with 1 record when reading readCSVInput1" in {
      SparkUtiles.readCSV("src/test/resources/readCSVInput1").count must_== 1
    }
  }

  "function writeCSV " should {
    "return an exception when the path does not exists" in {
      import sparkSession.implicits._

      val dataset = Seq("test").toDF("test")
      val filePath = "XXXX://src/test/resources/writeCSVOutput"
      SparkUtiles.writeCSV(dataset, filePath) must throwA[SparkWriteException]
    }
    "write a CSV formatted file to specified path" in {
      import sparkSession.implicits._

      val dataset = Seq("test1", "test2").toDF("test")
      val filePath = "src/test/resources/writeCSVOutput"
      val outputPath = SparkUtiles.writeCSV(dataset, filePath)

      SparkUtiles.readCSV(filePath).count() must_== 1
      outputPath must_== filePath
    }
  }

  "function getSparkSession" should {
    "return sparkSession config" in {
      val sparkSession = SparkUtiles.getSparkSession("testcase")
      sparkSession.conf.get("spark.rdd.compress") must_== "true"
      sparkSession.conf.get("spark.sql.crossJoin.enabled") must_== "true"
    }
  }
}

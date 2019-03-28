package au.com.weather_simulator

import au.com.weather_simulator.utiles.SparkUtiles
import utiles.SparkUtiles._

object Runtest {
  def main(args: Array[String]): Unit = {
    println("Hello world")

    implicit val spark = getSparkSession("localTest")

    val sydney = SparkUtiles.readCSV("src/main/resources/sydney.csv")
    sydney.show()

    spark.close()
  }
}

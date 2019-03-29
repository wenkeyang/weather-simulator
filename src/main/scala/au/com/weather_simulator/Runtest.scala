package au.com.weather_simulator

import java.util.Random

import au.com.weather_simulator.operations.ConditionsFormat
import au.com.weather_simulator.utiles.SparkUtiles
import utiles.SparkUtiles._
import org.apache.spark.sql.functions.{col, row_number, udf}
import au.com.weather_simulator.operations.UdfBuilders._
object Runtest {
  def main(args: Array[String]): Unit = {
    println("Hello world")


    implicit val spark = getSparkSession("localTest")

    spark.udf.register("rand_temp", generateRandomTemp)

    val sydney = SparkUtiles.readCSV("src/main/resources/sydney.csv")
    sydney.printSchema()
    sydney.createOrReplaceTempView("sydney")


    spark.sql(
      """
        |select monthday, maxtemp, mintemp ,
        |rand_temp(mintemp,maxtemp) as emutemp
        | from sydney
      """.stripMargin).show


    spark.close()




    //    val rangeMin = 2.2
    //    val rangeMax = 3.5
    //    val r = new Random()
    //    var randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble()
    //
    //    for (x <- 1 until 100)
    //      println(rangeMin + (rangeMax - rangeMin) * r.nextDouble())

  }
}

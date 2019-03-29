package au.com.weather_simulator

import java.util.Random

import au.com.weather_simulator.operations.ConditionsFormat
import au.com.weather_simulator.utiles.SparkUtiles
import utiles.SparkUtiles._
import org.apache.spark.sql.functions.{col, row_number, udf}
import au.com.weather_simulator.operations.UdfBuilders._

object Run {
  def main(args: Array[String]): Unit = {


    implicit val spark = getSparkSession("weather-simulator")

    spark.udf.register("get_temp", generateRandomTemp)
    spark.udf.register("get_station", generateStation)
    spark.udf.register("get_condition", generateCondition)
    spark.udf.register("get_pressure", generateRandomPressure)
    spark.udf.register("get_humidity", generateRandomHumidity)
    spark.udf.register("get_timeStamp", generateTimeStamp)


    val sydney = SparkUtiles.readCSV("src/main/resources/bomstatis/")
    sydney.printSchema()
    sydney.createOrReplaceTempView("sydney")

    spark.sql(
      """
        |select get_station(location) as station,
        |       get_timestamp(monthday,location) as whole_time ,
        |       get_temp(mintemp,maxtemp) as temprature,
        |       get_pressure() as pressure,
        |       get_humidity() as humidy
        |from sydney
      """.stripMargin).show(100, false)


    val set2 = spark.sql(
      """
        |with tab as (
        |select
        |       get_station(location) as station,
        |       get_timestamp(monthday,location) as whole_time ,
        |       get_temp(mintemp,maxtemp) as temprature,
        |       get_pressure() as pressure,
        |       get_humidity() as humidy
        |from sydney)
        |select
        |split(station, "[|]")[0] as station_name,
        |split(station, "[|]")[1] as geoloc,
        |whole_time,
        |get_condition (cast(replace(temprature,'+','') as double), cast(humidy as int) ) as Condition,
        |temprature,
        |pressure,
        |humidy
        |from tab
      """.stripMargin)

    set2.show(20, false)

    SparkUtiles.writeCSV(set2, "src\\main\\resources\\output")


    //    spark.sql(
    //      """
    //        |select monthday, maxtemp, mintemp ,
    //        |get_temp(mintemp,maxtemp) as emutemp
    //        | from sydney
    //      """.stripMargin).show
    //
    //    spark.sql(
    //      """
    //        |select get_Station("Sydney")
    //      """.stripMargin).show(100, false)

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

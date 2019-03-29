package au.com.weather_simulator

import au.com.weather_simulator.utiles.SparkUtiles._
import au.com.weather_simulator.operations.UdfBuilders._
import au.com.weather_simulator.operations.LocationsFormat
import au.com.weather_simulator.utiles.BomUtiles.extractLocationStatis

object Run {
  def main(args: Array[String]): Unit = {

    /*    extractLocationStatis(LocationsFormat.Sydney.toString, "066062", "2018-01-01", "2018-02-03")
        extractLocationStatis(LocationsFormat.Melbourne.toString, "086038", "2018-06-07", "2018-09-13")
        extractLocationStatis(LocationsFormat.Adelaide.toString, "023000", "2018-11-02", "2018-12-13")*/

    implicit val spark = getSparkSession("weather-simulator")

    spark.udf.register("get_temp", generateRandomTemp)
    spark.udf.register("get_station", generateStation)
    spark.udf.register("get_condition", generateCondition)
    spark.udf.register("get_pressure", generateRandomPressure)
    spark.udf.register("get_humidity", generateRandomHumidity)
    spark.udf.register("get_timeStamp", generateTimeStamp)


    val sydney = readCSV("src/main/resources/bomstatis/")
    sydney.printSchema()
    sydney.createOrReplaceTempView("bomstatis")

    val set2 = spark.sql(
      """
        |with outtab as (
        |select
        |       get_station(location) as station,
        |       get_timestamp(monthday,location) as whole_time ,
        |       get_temp(mintemp,maxtemp) as temprature,
        |       get_pressure() as pressure,
        |       get_humidity() as humidy
        |from bomstatis)
        |select
        |       split(station, "[|]")[0] as station_name,
        |       split(station, "[|]")[1] as geoloc,
        |       whole_time,
        |       get_condition (cast(replace(temprature,'+','') as double), cast(humidy as int) ) as Condition,
        |       temprature,
        |       pressure,
        |       humidy
        |from outtab
      """.stripMargin)

    set2.show(20, false)

    writeCSV(set2, "src\\main\\resources\\output")

    spark.close()
  }
}

package au.com.weather_simulator

import au.com.weather_simulator.utiles.SparkUtiles._
import au.com.weather_simulator.operations.UdfBuilders._
import au.com.weather_simulator.operations.LocationsFormat
import au.com.weather_simulator.utiles.BomUtiles.extractLocationStatis

object Run {
  def main(args: Array[String]): Unit = {

    //extract real statis from bom website
    /*    extractLocationStatis(LocationsFormat.Sydney.toString, "066062", "2018-01-01", "2018-02-03")
        extractLocationStatis(LocationsFormat.Melbourne.toString, "086038", "2018-06-07", "2018-09-13")
        extractLocationStatis(LocationsFormat.Adelaide.toString, "023000", "2018-11-02", "2018-12-13")*/

    implicit val spark = getSparkSession("weather-simulator")

    //register UDFs
    spark.udf.register("get_temp", generateRandomTemp)
    spark.udf.register("get_station", generateStation)
    spark.udf.register("get_condition", generateCondition)
    spark.udf.register("get_pressure", generateRandomPressure)
    spark.udf.register("get_humidity", generateRandomHumidity)
    spark.udf.register("get_timeStamp", generateTimeStamp)

    //loading real statis
    val bomstatis = readCSV("src/main/resources/bomstatis/")
    bomstatis.printSchema()
    bomstatis.createOrReplaceTempView("bomstatis")

    //generate emulated data output
    val emulated = spark.sql(
      """
        |with outtab as (
        | select
        |        get_station(location) as station,
        |        get_timestamp(monthday,location) as whole_time ,
        |        get_temp(mintemp,maxtemp) as temprature,
        |        get_pressure() as pressure,
        |        get_humidity() as humidy
        | from bomstatis)
        |select
        |       split(station, "[|]")[0] as Location,
        |       split(station, "[|]")[1] as Position,
        |       whole_time as Local_Time,
        |       get_condition (cast(replace(temprature,'+','') as double), cast(humidy as int) ) as Conditions,
        |       temprature as Temprature,
        |       pressure as Pressure,
        |       humidy as Humidity
        |from outtab
      """.stripMargin)
    emulated.show(20, false)
    writeCSV(emulated, "src/main/resources/emulatedData")

    val reload = readCSV("src/main/resources/emulatedData")
    reload.createOrReplaceTempView("emulated")

    spark.close()
  }
}

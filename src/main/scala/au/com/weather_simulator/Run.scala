package au.com.weather_simulator

import au.com.weather_simulator.utiles.SparkUtiles._
import au.com.weather_simulator.operations.UdfBuilders._
import au.com.weather_simulator.operations.LocationsFormat
import au.com.weather_simulator.utiles.BomUtiles.extractLocationStatis
import org.apache.spark.sql.SparkSession

object Run {
  def main(args: Array[String]): Unit = {

    //extract real statis from bom website
    extractLocationStatis(LocationsFormat.Sydney.toString, "066062", "2018-01-01", "2018-12-31")
    extractLocationStatis(LocationsFormat.Melbourne.toString, "086038", "2018-01-01", "2018-12-31")
    extractLocationStatis(LocationsFormat.Adelaide.toString, "023000", "2018-01-01", "2018-12-31")

    implicit val spark = getSparkSession("weather-simulator")

    regisUDFs()

    //loading real statis
    val bomstatis = readCSV("src/main/resources/bomstatis/")
    bomstatis.printSchema()
    bomstatis.createOrReplaceTempView("bomstatis")

    //generate emulated data output
    val emulated =
      spark.sql("""
          |WITH outtab AS
          |(
          |       SELECT Get_station(location)            AS station,
          |              Get_timestamp(monthday,location) AS whole_time ,
          |              Get_temp(mintemp,maxtemp)        AS temprature,
          |              Get_pressure()                   AS pressure,
          |              Get_humidity()                   AS humidy
          |       FROM   bomstatis)
          |SELECT Split(station, "[|]")[0]                                                         as location,
          |       split(station, "[|]")[1]                                                         AS position,
          |       whole_time                                                                       AS local_time,
          |       get_condition (cast(replace(temprature,'+','') AS DOUBLE), cast(humidy AS int) ) AS conditions,
          |       temprature                                                                       AS temperature,
          |       pressure                                                                         AS pressure,
          |       humidy                                                                           AS humidity
          |FROM   outtab
        """.stripMargin)
    emulated.show(20, false)
    writeCSV(emulated, "src/main/resources/emulatedData")

    //generate verify data output
    emulated.createOrReplaceTempView("emulated")
    val verify = spark.sql("""
        |SELECT bs.location,
        |       bs.monthday,
        |       bs.maxtemp,
        |       bs.mintemp,
        |       Replace(em.temprature, '+', '') AS tempra
        |FROM   bomstatis AS bs
        |       JOIN emulated AS em
        |         ON To_date(bs.monthday) = To_date(em.local_time)
        |            AND bs.location = em.location
      """.stripMargin)
    verify.show(20, false)
    writeCSV(verify, "src/main/resources/verify")

    spark.close()
  }

  //register UDFs
  def regisUDFs()(implicit spark: SparkSession): Unit = {
    spark.udf.register("get_temp", generateRandomTemp)
    spark.udf.register("get_station", generateStation)
    spark.udf.register("get_condition", generateCondition)
    spark.udf.register("get_pressure", generateRandomPressure)
    spark.udf.register("get_humidity", generateRandomHumidity)
    spark.udf.register("get_timeStamp", generateTimeStamp)
  }
}

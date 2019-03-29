package au.com.weather_simulator.operations

import java.util.Random
import au.com.weather_simulator.utiles.LoggingSupport

object UdfBuilders extends LoggingSupport {

  val generateStation: (String) => String = (station_name: String) => {
    val station = StationsList.stationslist(station_name)
    s"${station.station_name}|${station.station_location},${station.station_elevation}"
  }

  val generateRandomTemp: (Double, Double) => String = (rangeMin: Double, rangeMax: Double) => {
    val tempNumber = rangeMin + (rangeMax - rangeMin) * new Random().nextDouble()

    if (tempNumber >= 0)
      "+%.1f".format(tempNumber)
    else
      "%.1f".format(tempNumber)
  }

  val generateCondition: (Double, Int) => String = (temp: Double, humidity: Int) => (temp, humidity) match {
    case (temp, humidity) if (temp < 8 && humidity > 45 && humidity < 60) => ConditionsFormat.Snow.toString
    case (temp, humidity) if (humidity > 88) => ConditionsFormat.Rain.toString
    case _ => ConditionsFormat.Sunny.toString
  }

  //generate Pressure range from 800-1100
  val generateRandomPressure: String = {
    "%.1f".format(800 + 300 * new Random().nextDouble())
  }

  //generate humidity in percentage
  val generateRandomHumidity: Int = {
    new Random().nextInt(100) + 1
  }

  val generateTimeStamp: (String) => String = (datetime: String) => {
    datetime + " " + randomInt(12) + ":" + randomInt(60) + ":" + randomInt(60)
  }

  private def randomInt(bound: Int): String = {
    (new Random().nextInt(bound) + 1).toString
  }

  def main(args: Array[String]): Unit = {
    val x = generateTimeStamp("2018-01-01")
    println(x)
  }
}

package au.com.weather_simulator.utiles

import au.com.weather_simulator.testutils.TestUtils
import au.com.weather_simulator.typing.{BomCalendar, WeatherAverageStatis}

class BomUtilesTest extends TestUtils {
  "function getweatherAverage" should {
    "return give 2018-01-01 statis for Sydney station" in {
      val output = BomUtiles.getweatherAverage("066062", BomCalendar("2018-01-01", "01", "01"))
      /*
      * http://www.bom.gov.au/jsp/ncc/cdio/calendar/climate-calendar?stn_num=066062&month=01&day=01
      * Averages
      * Maximum temperature	25.7 °C
      * Minimum temperature	18.4 °C
      * Rainfall	2.6 mm
      * */
      output must_== WeatherAverageStatis("2018-01-01", 25.7, 18.4, 2.6)
    }
    "return 0,0,0 statis for not exit station" in {
      val output = BomUtiles.getweatherAverage("notexist", BomCalendar("2018-01-01", "01", "01"))
      output must_== WeatherAverageStatis("2018-01-01", 0.0, 0.0, 0.0)
    }
  }

  "function weatherAverageWash" should {
    "return give 2018-01-01 statis for Sydney station sample html" in {
      import scala.io.Source
      val data = Source.fromFile("src/test/resources/test.html").mkString
      BomUtiles.weatherAverageWash(data) must_==(25.7, 18.4, 2.6)
    }
  }
}


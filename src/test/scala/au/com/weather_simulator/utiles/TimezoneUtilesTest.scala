package au.com.weather_simulator.utiles

import au.com.weather_simulator.testutils.TestUtils
import au.com.weather_simulator.typing.BomCalendar

class TimezoneUtilesTest extends TestUtils {
  "function generateBOMcalendar" should {
    "generate 31 days list from 2018-01-01 to 2018-01-31" in {
      val dlist = TimezoneUtiles.generateBOMcalendar("2018-01-01", "2018-01-31")
      dlist.size must_== 31
    }

    "generate list of BomCalendar from 2019-02-01 to 2019-02-08" in {
      val dlist = TimezoneUtiles.generateBOMcalendar("2019-02-01", "2019-02-03")
      val expectedlist = List(
        BomCalendar("2019-02-01", "02", "01"),
        BomCalendar("2019-02-02", "02", "02"),
        BomCalendar("2019-02-03", "02", "03"))
      dlist must_== expectedlist
    }
  }
}

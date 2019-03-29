package au.com.weather_simulator.utiles

import java.time.LocalDate
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.{Calendar, TimeZone}
import au.com.weather_simulator.typing.BomCalendar

object TimezoneUtiles extends LoggingSupport {

  val standardPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"

  val timeZone_Sydney = "Australia/Sydney"
  val timeZone_Melbourne = "Australia/Melbourne"
  val timeZone_Adelaide = "Australia/Adelaide"

  def generateTimeZoneTime(timezone: String): String = {
    val formatFile = new SimpleDateFormat(standardPattern)
    formatFile.setTimeZone(TimeZone.getTimeZone(timezone))
    formatFile.format(Calendar.getInstance().getTime)
  }

  def generateBOMcalendar(start_date: String, end_date: String): List[BomCalendar] = {
    val start = LocalDate.parse(start_date)
    val end = LocalDate.parse(end_date)
    val mlist = for (a <- 0 until (start.until(end, ChronoUnit.DAYS) + 1).toInt)
      yield {
        val temp = start.plusDays(a).toString
        BomCalendar(temp, temp.split("-")(1), temp.split("-")(2))
      }
    mlist.toList
  }

  def main(args: Array[String]): Unit = {

    val x = generateBOMcalendar("2018-01-02", "2018-03-01")

    x.foreach(println)
  }
}

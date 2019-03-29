package au.com.weather_simulator.utiles

import java.time.LocalDate
import java.text.SimpleDateFormat
import java.time.temporal.ChronoUnit
import java.util.{Calendar, TimeZone}
import au.com.weather_simulator.typing.BomCalendar

object TimezoneUtiles extends LoggingSupport {

  val standardPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
  val randomTimePattern = "yyyy-MM-dd HH:mm:ss"

  val timeZone = Map("Sydney" -> "Australia/Sydney",
    "Melbourne" -> "Australia/Melbourne",
    "Adelaide" -> "Australia/Adelaide")

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
}

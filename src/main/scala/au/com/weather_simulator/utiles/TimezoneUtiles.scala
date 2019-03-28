package au.com.weather_simulator.utiles

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.{Calendar, TimeZone}

object TimezoneUtiles extends LoggingSupport {

  case class bomCalendar(cmonth: String, cday: String)

  def generateTimeForSydney(): String = {
    val formatFile = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatFile.setTimeZone(TimeZone.getTimeZone("Australia/Sydney"))
    formatFile.format(Calendar.getInstance().getTime)
  }

  def generateTimeForMelbourne(): String = {
    val formatFile = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatFile.setTimeZone(TimeZone.getTimeZone("Australia/Melbourne"))
    formatFile.format(Calendar.getInstance().getTime)
  }

  def generateTimeForAdelaide(): String = {
    val formatFile = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatFile.setTimeZone(TimeZone.getTimeZone("Australia/Adelaide"))
    formatFile.format(Calendar.getInstance().getTime)
  }

  def generateBOMcalendar(): List[bomCalendar] = {
    val start = LocalDate.parse("2018-01-01")
    val end = LocalDate.parse("2018-12-31")
    val mlist = for (a <- 0 until (start.until(end, ChronoUnit.DAYS) + 1).toInt)
      yield {
        val temp = start.plusDays(a).toString
        bomCalendar(temp.split("-")(1), temp.split("-")(2))
      }
    mlist.toList
  }

  def main(args: Array[String]): Unit = {
    for (elem <- generateBOMcalendar) {
      print(elem)
    }

  }
}

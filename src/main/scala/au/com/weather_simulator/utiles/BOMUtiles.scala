package au.com.weather_simulator.utiles

import scala.util.Try
import scala.sys.process._

object BOMUtiles extends LoggingSupport {

  case class weatherAverage(monthday: String, maxtemp: Double, mintemp: Double, rainfall: Double)

  def getweatherAverage(site_id: String, mmonth: String, mday: String): weatherAverage = {
    val baseURL = s"http://www.bom.gov.au/jsp/ncc/cdio/calendar/climate-calendar?stn_num=${site_id}&month=${mmonth}&day=${mday}"
    val data = "curl " + baseURL !!
    val cleaned = weatherAverageWash(data)
    weatherAverage(s"$mmonth-$mday", cleaned._1, cleaned._2, cleaned._3)
  }

  def weatherAverageWash(data: String): (Double, Double, Double) = {
    val point1 = data.indexOf("""<table class="table-basic" id="typical1" summary="">""")
    val point2 = data.indexOf("""</table>""", point1) + "</table>".length
    val subst = data.substring(point1, point2)
      .replaceAll("<span> &deg;C</span>", "")
      .replaceAll("<span> mm</span>", "")
    val xmlcon = scala.xml.XML.loadString(subst)
    val td = xmlcon \ "tbody" \ "tr" \ "td"
    val textList = for (elem <- td.theSeq.toIterator)
      yield elem.text.replaceAll("\n", "").replaceAll(" +", " ")
    val temp = textList.toList
    (getDoublevalue(temp(1)), getDoublevalue(temp(3)), getDoublevalue(temp(5)))
  }

  def getDoublevalue(value: String): Double = {
    Try(value.toDouble).getOrElse(0)
  }

  def main(args: Array[String]): Unit = {
    val test=getweatherAverage("066062","01","01")
    println(test.maxtemp)
    println(test.mintemp)
    println(test.monthday)
    println(test.rainfall)
  }

  def temp = {
    import scala.sys.process._
    val data = "curl http://www.bom.gov.au/jsp/ncc/cdio/calendar/climate-calendar?stn_num=066062&month=05&day=01" !!
    //    println(data)
    val point1 = data.indexOf("""<table class="table-basic" id="typical1" summary="">""")
    val point2 = data.indexOf("""</table>""", point1) + "</table>".length

    val tab = data.substring(point1, point2).replaceAll("<span> &deg;C</span>", "").replaceAll("<span> mm</span>", "")
    //println(tab)
    val xmlcon = scala.xml.XML.loadString(tab)
    val td = xmlcon \ "tbody" \ "tr" \ "td"

    val finallist1 = for (elem <- td.theSeq.toIterator) yield
      elem.text.replaceAll("\n", "").replaceAll(" +", " ")
    val finallist = finallist1.toList
    println(finallist(0) + "  " + finallist(1) + "  " + finallist(2) + "  " + finallist(3) + "  " + finallist(4) + "  " + finallist(5) + "  ")
  }

}

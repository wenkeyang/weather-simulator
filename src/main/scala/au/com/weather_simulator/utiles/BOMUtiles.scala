package au.com.weather_simulator.utiles

import java.io._
import scala.util.Try
import scala.sys.process._
import au.com.weather_simulator.typing.weatherAverage
import au.com.weather_simulator.utiles.TimezoneUtiles.generateBOMcalendar

object BOMUtiles extends LoggingSupport {


  def getweatherAverage(site_id: String, mmonth: String, mday: String): weatherAverage = {
    log.info(s"Extracing statis for stn_num=${site_id}&month=${mmonth}&day=${mday}")
    val baseURL = s"http://www.bom.gov.au/jsp/ncc/cdio/calendar/climate-calendar?stn_num=${site_id}&month=${mmonth}&day=${mday}"
    val data = "curl " + baseURL !!
    val cleaned = weatherAverageWash(data)
    weatherAverage(s"$mmonth-$mday", cleaned._1, cleaned._2, cleaned._3)
  }

  private def weatherAverageWash(data: String): (Double, Double, Double) = {
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

  private def getDoublevalue(value: String): Double = {
    Try(value.toDouble).getOrElse(0)
  }

  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  private def getDatafromHttp(url: String,
                              connectTimeout: Int = 5000,
                              readTimeout: Int = 5000,
                              requestMethod: String = "GET") = {
    import java.net.{HttpURLConnection, URL}
    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    val inputStream = connection.getInputStream
    val content = scala.io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content
  }

  def extractLocationStatis(filename: String, site_code: String, start_date: String = "2018-01-01", end_date: String = "2018-12-31") = {
    val finaldataset = for (elem <- generateBOMcalendar(start_date, end_date)) yield
      getweatherAverage(site_code, elem.cmonth, elem.cday)

    val filewriter = new BufferedWriter(new FileWriter("src\\main\\resources\\" + filename + ".csv"))
    filewriter.write("monthday|maxtemp|mintemp|rainfall\n")
    finaldataset.foreach(row => filewriter.write(s"${row.monthday}|${row.maxtemp}|${row.mintemp}|${row.rainfall}\n"))
    filewriter.close()
  }


  def main(args: Array[String]): Unit = {
    //066062  sydney
    //086038  melbourne
    //023000  adelaide
    extractLocationStatis("sydney", "066062")
    extractLocationStatis("melbourne", "086038")
    extractLocationStatis("adelaide", "023000")

  }
}

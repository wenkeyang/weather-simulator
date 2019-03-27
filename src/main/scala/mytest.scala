import java.net.URL

import org.htmlcleaner.HtmlCleaner

import scala.collection.mutable.ListBuffer

object mytest {
  val weather_APPID = "4de065fa58a4680f5ada222fecc20859"

  //  val IATA_api_key="ea86a3ed-082c-42f6-95bb-cb510ef72bf6"

  //  val url_weather = "http://api.openweathermap.org/data/2.5/weather?id=" + city_id + "&APPID=" + weather_APPID


  case class weather_data(location: String, position: String, local_time: String, conditions: String, temperature: String, pressure: String, humidity: String)

  @throws(classOf[java.io.IOException])
  @throws(classOf[java.net.SocketTimeoutException])
  def get(url: String,
          connectTimeout: Int = 5000,
          readTimeout: Int = 5000,
          requestMethod: String = "GET") = {
    import java.net.{URL, HttpURLConnection}
    val connection = (new URL(url)).openConnection.asInstanceOf[HttpURLConnection]
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)
    val inputStream = connection.getInputStream
    val content = io.Source.fromInputStream(inputStream).mkString
    if (inputStream != null) inputStream.close
    content
  }

  def main(args: Array[String]): Unit = {

    val data = get("http://www.bom.gov.au/jsp/ncc/cdio/calendar/climate-calendar?stn_num=066062&month=05&day=01")
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

    //    println(xmlcon)
  }
}

package au.com.weather_simulator.utiles

import au.com.weather_simulator.utiles.TimezoneUtiles.generateBOMcalendar
import org.apache.spark.sql.SparkSession
import java.io._

import scala.sys.process._
import scala.util.Try

object BOMUtiles extends LoggingSupport {

  case class weatherAverage(monthday: String, maxtemp: Double, mintemp: Double, rainfall: Double)

  def getweatherAverage(site_id: String, mmonth: String, mday: String): weatherAverage = {
    println(s"Extracing statis for stn_num=${site_id}&month=${mmonth}&day=${mday}")
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

  def extractLocationStatis(filename: String, site_code: String) = {
    val finaldataset = for (elem <- generateBOMcalendar) yield
      getweatherAverage(site_code, elem.cmonth, elem.cday)

    val filewriter = new BufferedWriter(new FileWriter("src\\main\\resources\\" + filename + ".csv"))
    filewriter.write("monthday|maxtemp|mintemp|rainfall\n")
    finaldataset.foreach(row => filewriter.write(s"${row.monthday}|${row.maxtemp}|${row.mintemp}|${row.rainfall}\n"))
    filewriter.close()
  }


  def main(args: Array[String]): Unit = {
    //066062  sydney
    //086038  melbourne
    //023000 adelaide
    extractLocationStatis("sydney", "066062")
    extractLocationStatis("melbourne", "086038")
    extractLocationStatis("adelaide", "023000")


    //    val spark = SparkSession
    //      .builder()
    //      .appName(getClass.getName)
    //      .master("local[*]")
    //      .getOrCreate()
    //    import spark.implicits._
    //    val dataset=spark.sparkContext.parallelize(finaldataset)
    //    SparkUtiles.writeCSV(dataset.toDF(), "C:\\temp\\mytest")


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

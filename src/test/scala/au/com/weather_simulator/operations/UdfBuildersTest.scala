package au.com.weather_simulator.operations

import au.com.weather_simulator.testutils.TestUtils

class UdfBuildersTest extends TestUtils {

  import sparkSession.implicits._

  "function generateStation present" should {
    " match station info for Sydney" in {
      val row = ("Sydney")
      val df = Seq(row).toDF("station_name")
      val result = df.select(UdfBuilders.generateStation('station_name))
      result.take(1)(0)(0) must_== "Sydney|-33.86,151.2,39"
    }
    " match station info for Melbourne" in {
      val row = ("Melbourne")
      val df = Seq(row).toDF("station_name")
      val result = df.select(UdfBuilders.generateStation('station_name))
      result.take(1)(0)(0) must_== "Melbourne|-37.73,144.91,78"
    }
    " match station info for Adelaide" in {
      val row = ("Adelaide")
      val df = Seq(row).toDF("station_name")
      val result = df.select(UdfBuilders.generateStation('station_name))
      result.take(1)(0)(0) must_== "Adelaide|-34.93,138.58,29"
    }
  }

  "function generateRandomTemp" should {
    " get figure between (3.2, 6.5)" in {
      val row = (3.2, 6.5)
      val df = Seq(row).toDF("minr", "maxr")
      val result = df.select(UdfBuilders.generateRandomTemp('minr, 'maxr))
      val resultfigure = result.take(1)(0)(0).toString.replace("+", "").toDouble
      val output = if (resultfigure >= 3.2 && resultfigure <= 6.5) true else false
      output must_== true
    }
    " get figure between (-5.1, -1.1)" in {
      val row = (-5.1, -1.1)
      val df = Seq(row).toDF("minr", "maxr")
      val result = df.select(UdfBuilders.generateRandomTemp('minr, 'maxr))
      val resultfigure = result.take(1)(0)(0).toString.replace("+", "").toDouble
      val output = if (resultfigure >= -5.1 && resultfigure <= -1.1) true else false
      output must_== true
    }
    " get figure between (-1, 1)" in {
      val row = (-1, 1)
      val df = Seq(row).toDF("minr", "maxr")
      val result = df.select(UdfBuilders.generateRandomTemp('minr, 'maxr))
      val resultfigure = result.take(1)(0)(0).toString.replace("+", "").toDouble
      val output = if (resultfigure >= -1 && resultfigure <= 1) true else false
      output must_== true
    }
  }

  "function generateCondition " should {
    " get condition Snow with temp 6 and humid 55 " in {
      val row = (6, 55)
      val df = Seq(row).toDF("temp", "humidity")
      val result = df.select(UdfBuilders.generateCondition('temp, 'humidity))
      result.take(1)(0)(0) must_== "Snow"
    }
    " get condition Rain with temp 7 and humid 90 " in {
      val row = (7, 90)
      val df = Seq(row).toDF("temp", "humidity")
      val result = df.select(UdfBuilders.generateCondition('temp, 'humidity))
      result.take(1)(0)(0) must_== "Rain"
    }
    " get condition Sunny with temp 12 and humid 28 " in {
      val row = (12, 28)
      val df = Seq(row).toDF("temp", "humidity")
      val result = df.select(UdfBuilders.generateCondition('temp, 'humidity))
      result.take(1)(0)(0) must_== "Sunny"
    }
  }


  "function generateRandomPressure" should {
    "get pressure between 800 and 1100" in {
      val row = (6, 55)
      val df = Seq(row).toDF()
      val result = df.select(UdfBuilders.generateRandomPressure())
      val resultfigure = result.take(1)(0)(0).toString.replace("+", "").toDouble
      val output = if (resultfigure >= 800 && resultfigure <= 1100) true else false
      output must_== true
    }
  }

  "function generateRandomHumidity" should {
    "get Humidity between 1 and 100" in {
      val row = (6, 55)
      val df = Seq(row).toDF()
      val result = df.select(UdfBuilders.generateRandomHumidity())
      val resultfigure = result.take(1)(0)(0).toString.replace("+", "").toDouble
      val output = if (resultfigure >= 1 && resultfigure <= 100) true else false
      output must_== true
    }
  }
}
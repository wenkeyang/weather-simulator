package au.com.weather_simulator.typing

import au.com.weather_simulator.testutils.TestUtils

import scala.util.Try

class NotEmptyStringTest extends TestUtils {
  "NotEmptyString" should {
    "match not empty string" in {
      val testst1 = "test"
      val testst2 = ""

      val output1 = testst1 match {
        case NotEmptyString(testst1) => testst1
        case _ => None
      }

      val output2 = testst2 match {
        case NotEmptyString(testst2) => testst2
        case _ => None
      }

      output1 must_== "test"
      output2 must_== None
    }
  }

  private def stringTo[A](number: String, func: String => A): Option[A] = {
    number match {
      case NotEmptyString(number) => Try(func(number)).toOption
      case _ => None
    }
  }
}

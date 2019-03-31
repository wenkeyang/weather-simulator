package au.com.weather_simulator.utiles

import au.com.weather_simulator.testutils.TestUtils
import au.com.weather_simulator.typing.{ExtractBomSite, SystemConfig}

class AppConfigTest extends TestUtils {
  "object AppConfig" should {
    "return system config" in {
      val appconfig = AppConfig.init("src/test/resources/testconfig.conf")
      val systemconfig = appconfig.systemConfig()
      systemconfig must_== SystemConfig("test1", "test2", "test3", "test4")
    }
    "return extractBomSites list config" in {
      val appconfig = AppConfig.init("src/test/resources/testconfig.conf")
      val sitelist = appconfig.extractBomSites()

      sitelist must_== List(ExtractBomSite("Sydney", "a", "b", "c"),
        ExtractBomSite("Melbourne", "a", "b", "c"),
        ExtractBomSite("Adelaide", "a", "b", "c"))
    }
  }

}

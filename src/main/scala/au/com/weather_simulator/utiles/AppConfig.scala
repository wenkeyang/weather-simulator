package au.com.weather_simulator.utiles

import au.com.weather_simulator.typing.{ExtractBomSite, SystemConfig}
import com.typesafe.config.{Config, ConfigFactory}

import scala.util.Try

object AppConfig {
  def init(configPath: String) = new AppConfig(configPath)
}

class AppConfig(configPath: String) {
  private val config: Config = ConfigFactory.parseString(scala.io.Source.fromFile(configPath).mkString)
  private val site_names = List("Sydney", "Melbourne", "Adelaide")

  val systemConfig: () => SystemConfig = () => {
    val sparkAppName = Try(config.getString("run.sparkAppName"))
    val bomStatisPath = Try(config.getString("run.bomStatisPath"))
    val emulatedOutputPath = Try(config.getString("run.emulatedOutputPath"))
    val verifyOutputPath = Try(config.getString("run.verifyOutputPath"))
    SystemConfig(sparkAppName.getOrElse(""),
                 bomStatisPath.getOrElse(""),
                 emulatedOutputPath.getOrElse(""),
                 verifyOutputPath.getOrElse(""))
  }

  val extractBomSites: () => List[ExtractBomSite] = () => {
    for (site_name <- site_names) yield {
      if (config.hasPath("run.sources")) {}
      val site_code = Try(config.getString(s"run.sources.$site_name.siteCode"))
      val start_date = Try(config.getString(s"run.sources.$site_name.startDate"))
      val end_date = Try(config.getString(s"run.sources.$site_name.endDate"))
      ExtractBomSite(site_name, site_code.getOrElse(""), start_date.getOrElse(""), end_date.getOrElse(""))
    }
  }
}

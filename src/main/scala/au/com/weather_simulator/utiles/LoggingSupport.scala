package au.com.weather_simulator.utiles

trait LoggingSupport {
  protected lazy val log = org.apache.log4j.Logger.getLogger(getClass)
}

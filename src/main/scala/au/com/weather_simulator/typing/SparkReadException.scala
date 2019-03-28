package au.com.weather_simulator.typing

case class SparkReadException(message: String, cause: Throwable) extends Exception(message, cause)

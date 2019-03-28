package au.com.weather_simulator.typing

case class SparkWriteException(message: String, cause: Throwable) extends Exception(message, cause)

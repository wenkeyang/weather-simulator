package au.com.weather_simulator.utiles

class ConditionsFormat extends Enumeration {
  type Format = Value
  val Rain, Snow, Sunny = Value

  def fromString(name: String): Value =
    values.find(_.toString.toLowerCase == name.toLowerCase()).getOrElse(Sunny)
}

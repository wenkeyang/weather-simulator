package au.com.weather_simulator.utiles

object NotEmptyString {
  def unapply(s: String): Option[String] =
    if (s != null && !s.isEmpty) Some(s) else None
}

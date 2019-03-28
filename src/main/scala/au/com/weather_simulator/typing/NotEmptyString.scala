package au.com.weather_simulator.typing

object NotEmptyString {
  def unapply(s: String): Option[String] =
    if (s != null && !s.isEmpty) Some(s) else None
}

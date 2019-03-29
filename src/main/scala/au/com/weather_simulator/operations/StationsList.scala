package au.com.weather_simulator.operations

import au.com.weather_simulator.typing.ObserveStation

object StationsList {
  val stationslist =
    Map("Sydney" -> ObserveStation("066062", "Sydney", "-33.86,151.2", "39"),
      "Melbourne" -> ObserveStation("86038", "Melbourne", "-37.73, 144.91", "78"),
      "Adelaide" -> ObserveStation("023000", "Adelaide", "-34.93, 138.58", "29"))
}


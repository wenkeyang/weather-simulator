package au.com.weather_simulator.operations

import au.com.weather_simulator.testutils.TestUtils
import au.com.weather_simulator.typing.ObserveStation

class StationsListTest extends TestUtils {
  "stationslist map" should {
    "return observe stations info" in {
      StationsList.stationslist(LocationsFormat.Sydney.toString) must_== ObserveStation("066062", "Sydney", "-33.86,151.2", "39")
      StationsList.stationslist(LocationsFormat.Melbourne.toString) must_== ObserveStation("86038", "Melbourne", "-37.73,144.91", "78")
      StationsList.stationslist(LocationsFormat.Adelaide.toString) must_== ObserveStation("023000", "Adelaide", "-34.93,138.58", "29")
    }
  }
}

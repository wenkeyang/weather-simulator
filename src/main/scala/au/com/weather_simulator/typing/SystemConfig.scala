package au.com.weather_simulator.typing

case class SystemConfig(sparkAppName: String,
                        bomStatisPath: String,
                        emulatedOutputPath: String,
                        verifyOutputPath: String) {
  override def toString: String =
    s"""
       |sparkAppName: $sparkAppName
       |bomStatisPath: $bomStatisPath
       |emulatedOutputPath: $emulatedOutputPath
       |verifyOutputPath: $verifyOutputPath
     """.stripMargin
}

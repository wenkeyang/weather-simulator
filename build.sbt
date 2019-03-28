import aether.AetherKeys._

logLevel in aetherDeploy := Level.Info

name := "weather-simulator"
version := "0.0.1-SNAPSHOT"
organization := "au.com.weather_simulator"
val specs2Version = "4.0.1"
parallelExecution in Test := false

scalaVersion := "2.12.8"
val sparkVersion = sys.env.getOrElse("SPARK_VERSION", "2.4.0")
val hadoopVersion = sys.env.getOrElse("HADOOP_VERSION", "2.8.5")

unmanagedBase := baseDirectory.value / "src/main/scala/au/com/weather_simulator/libs"
resolvers ++= Seq(
  "edi-snapshots" at "https://artifacts.edi-pilot.ampaws.com.au/nexus/content/repositories/snapshots/",
  "edi-releases" at "https://artifacts.edi-pilot.ampaws.com.au/nexus/content/repositories/releases/",
  "edi-public" at "https://artifacts.edi-pilot.ampaws.com.au/nexus/content/repositories/public/",
  "atlassian" at "https://packages.atlassian.com/maven/repository/public",
  "talend" at "https://talend-update.talend.com/nexus/content/repositories/libraries"
)

addArtifact(artifact in(Compile, assembly), assembly)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-sql" % sparkVersion % Provided,
  "org.apache.spark" %% "spark-hive" % sparkVersion % Provided,
  "org.apache.hadoop" % "hadoop-aws" % hadoopVersion % Provided,
  "org.apache.hadoop" % "hadoop-client" % hadoopVersion % Provided,

  "com.typesafe" % "config" % "1.3.1",
  "org.joda" % "joda-convert" % "1.9.2",
  "com.hubspot.jinjava" % "jinjava" % "2.4.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.1.1",
  "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.22",

  "org.specs2" %% "specs2-core" % specs2Version % Test,
  "org.specs2" %% "specs2-matcher-extra" % specs2Version % Test,
  "org.specs2" %% "specs2-mock" % specs2Version % Test

)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.first
}
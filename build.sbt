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
  "atlassian" at "https://packages.atlassian.com/maven/repository/public",
  "talend" at "https://talend-update.talend.com/nexus/content/repositories/libraries"
)

addArtifact(artifact in(Compile, assembly), assembly)

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.apache.hadoop" % "hadoop-aws" % hadoopVersion,
  "org.apache.hadoop" % "hadoop-client" % hadoopVersion,

  "com.typesafe" % "config" % "1.3.1",
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
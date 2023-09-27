
ThisBuild / organization := "ch.iwerk"
ThisBuild / version      := "RECIPE"

val flinkVersion = "1.17.1"

val jacksonVersion = "2.14.1"
val kafkaVersion = "3.2.2"
val log4jVersion = "2.19.0"
val jUnitJupiterVersion = "5.9.2"

//conflictManager := ConflictManager.strict
//This / scalaVersion := "3.3.0"
val scala3Version = "3.3.1"

//crossScalaVersions := Seq( "2.13.10", "3.3.1")

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}

val flinkDependencies = Seq(


  "org.flinkextended" %% "flink-scala-api" % "1.17.1_1.0.0",
  //"org.flinkextended" %% "flink-scala-api" % "1.16.2_1.0.0",

  "org.apache.flink" % "flink-clients" % flinkVersion % "provided",

    //"org.apache.flink" % "flink-streaming-java" % flinkVersion % "provided",
    //"org.apache.flink" % "flink-clients" % flinkVersion % "provided",
    "org.apache.flink" % "flink-runtime-web" % flinkVersion % Test,

    "org.apache.flink" % "flink-test-utils" % flinkVersion % Test,


    //"org.apache.flink" % "flink-table-api-java-bridge" % flinkVersion % "provided",
    //"org.apache.flink" % "flink-table-planner-loader" % flinkVersion  % "provided",
    //"org.apache.flink" % "flink-table-runtime" % flinkVersion % "provided"
)


val jacksonDependencies = Seq(
  //"com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion,
  //"com.fasterxml.jackson.core" % "jackson-annotations" % jacksonVersion,
  //"com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % jacksonVersion
)

val logging = Seq(
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % log4jVersion % Test,
  "org.apache.logging.log4j" % "log4j-api" % log4jVersion % Runtime,
  "org.apache.logging.log4j" % "log4j-core" % log4jVersion
  //"ch.qos.logback" % "logback-core" % logbackVersion,
  //"ch.qos.logback" % "logback-classic" % logbackVersion


)


val testing = Seq(
  //"net.mguenther.kafka" % "kafka-junit" % "3.2.2" % Test,
  //"net.datafaker" %% "datafaker" % "1.6.0" % Test,
  //"org.junit.jupiter" %% "junit-jupiter-engine" % jUnitJupiterVersion % Test,
  //"org.junit.jupiter" %% "junit-jupiter-api" % jUnitJupiterVersion % Test
  //dependency Problem mit net.mguenther
  //"com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.14.1"




)


val flinkConnectors = Seq(
    "org.apache.flink" % "flink-connector-kafka" % flinkVersion,
    //"org.apache.flink" % "flink-connector-base" % flinkVersion,
    //"org.apache.flink" % "flink-json" % flinkVersion,
    "org.apache.flink" % "flink-connector-files" % flinkVersion,
    "org.apache.flink" % "flink-csv" % flinkVersion,
    //"org.apache.kafka" % "kafka-clients" % kafkaVersion


)

val generalLibs = Seq(
  //https://www.scala-lang.org/blog/2021/04/08/scala-3-in-sbt.html
  //("com.lihaoyi" %% "upickle" % "3.1.3").cross(CrossVersion.for3Use2_13)
  //("com.lihaoyi" %% "upickle" % "2.0.0").cross(CrossVersion.for3Use2_13)

  "com.lihaoyi" %% "upickle" % "2.0.0"
  //"com.lihaoyi" %% "upickle" % "3.1.3"
)
//https://github.com/awslabs/deequ/issues/336
/*
s. auch
https://tersesystems.com/blog/2021/07/19/adding-scala-3-support-to-blindsight/
https://github.com/indoorvivants/sbt-commandmatrix


https://users.scala-lang.org/t/question-about-scala-3-2-13-library-compat-mode-and-sbt-cross-version-conflicts/7456/8
https://www.scala-lang.org/blog/2021/04/08/scala-3-in-sbt.html
https://lightrun.com/answers/awslabs-deequ-modules-were-resolved-with-conflicting-cross-version-suffixes

https://www.scala-lang.org/2019/10/17/dependency-management.html

 */




lazy val scala_latest_transaction = (project in file("."))
  .settings(
    name := "scala-contiuous-file-reading",
    scalaVersion:= "2.13.10",
    //scalaVersion:= scala3Version,
    assembly / assemblyJarName := "scala-continuous-file-reading.jar",
    assembly / mainClass := Some("com.immerok.cookbook.ContinuousFileReading"),
    libraryDependencies ++= flinkDependencies ++ flinkConnectors ++ logging ++ jacksonDependencies  ++ testing
      ++ generalLibs


  )




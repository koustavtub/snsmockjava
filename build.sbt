organization := "io.github.koustavtub"
name := "snsmockjava"

homepage := Some(url("https://github.com/koustavtub/snsmockjava"))
scmInfo := Some(ScmInfo(url("https://github.com/koustavtub/snsmockjava"), "git@github.com:koustavtub/snsmockjava.git"))
licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0"))

developers := List(
  Developer("koustavtub", "Koustav Ray", "koustavtub@gmail.com", url("https://github.com/koustavtub"))
)


publishMavenStyle := true


credentials += Credentials(
  "Sonatype Nexus Repository Manager",
  "oss.sonatype.org",
  "koustavtub",
  "{yourpsasword}"
)

publishConfiguration := publishConfiguration.value.withOverwrite(true)

// Add sonatype repository settings
publishTo := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)


version := "0.4.1.0"

scalaVersion := "2.12.4"

// sbt-assembly
assemblyJarName in assembly := s"sns-${version.value}.jar"
test in assembly := {}

val akkaVersion = "2.5.6"
val akkaHttpVersion = "10.0.10"
val camelVersion = "2.19.4"

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,

    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,

    "org.slf4j" % "slf4j-api" % "1.7.2",
    "ch.qos.logback" % "logback-classic" % "1.0.7",
    "com.typesafe.akka" %% "akka-camel" % akkaVersion,
    "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.228",
    "org.apache.camel" % "camel-aws" % camelVersion
      excludeAll ExclusionRule(organization = "com.amazonaws"),
    "org.apache.camel" % "camel-http" % camelVersion,
    "org.apache.camel" % "camel-rabbitmq" % camelVersion,
    "org.apache.camel" % "camel-slack" % camelVersion,
    "junit" % "junit" % "4.12" % Test,

    "org.scalatest" %% "scalatest" % "3.0.4" % Test
  )
}


dependencyOverrides += "com.typesafe.akka" %% "akka-actor" % akkaVersion
dependencyOverrides += "com.typesafe.akka" %% "akka-stream" % akkaVersion



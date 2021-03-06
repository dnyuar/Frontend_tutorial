name := """Frontend_tutorial"""
organization := "com.hmrc"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += ws


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.hmrc.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.hmrc.binders._"
libraryDependencies += "org.scalamock" %% "scalamock" % "4.4.0" % Test

//libraryDependencies += "com.github.tomakehurst" % "wiremock" % "2.21.0" % Test
libraryDependencies += "com.github.tomakehurst" % "wiremock-jre8" % "2.22.0" % Test
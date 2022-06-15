name := """impaqts-be"""
organization := "it.drwolf"

version := "dist"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.3"

libraryDependencies += guice
// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.12.0"

PlayKeys.externalizeResources := false

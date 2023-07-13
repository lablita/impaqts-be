name := """impaqts-be"""
organization := "it.drwolf"

version := "dist"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.3"

libraryDependencies += guice
// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.12.0"
// https://mvnrepository.com/artifact/net.lingala.zip4j/zip4j
libraryDependencies += "net.lingala.zip4j" % "zip4j" % "2.11.2"
// https://mvnrepository.com/artifact/commons-io/commons-io
libraryDependencies += "commons-io" % "commons-io" % "2.11.0"
// https://mvnrepository.com/artifact/com.auth0/auth0
libraryDependencies += "com.auth0" % "auth0" % "1.17.0"
// https://mvnrepository.com/artifact/com.nimbusds/nimbus-jose-jwt
libraryDependencies += "com.nimbusds" % "nimbus-jose-jwt" % "9.25.6"
// https://mvnrepository.com/artifact/org.apache.commons/commons-text
libraryDependencies += "org.apache.commons" % "commons-text" % "1.10.0"
// https://mvnrepository.com/artifact/com.opencsv/opencsv
libraryDependencies += "com.opencsv" % "opencsv" % "3.7"

PlayKeys.externalizeResources := false

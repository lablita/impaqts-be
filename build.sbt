name := """impaqts-be"""
organization := "it.drwolf"

version := "dist"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.3"

libraryDependencies += guice

PlayKeys.externalizeResources := false
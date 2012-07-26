name := "Tutorial Example 5a - Dictonary Service Client with Service Tracker - Loop"

organization := "org.apache.felix"

version := "0.2"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.apache.felix" % "org.osgi.core" % "1.4.0",
  "org.apache.felix" % "org.osgi.compendium" % "1.4.0",
  "org.apache.felix" % "tutorial-example-2---dictonary-service_2.9.1" % "0.1"
)

seq(osgiSettings: _*)

OsgiKeys.bundleActivator := Some("tutorial.example5.Activator")

OsgiKeys.exportPackage ++= Seq(
  "tutorial.example5"
)

OsgiKeys.importPackage ++= Seq(
  "tutorial.example2.service"
)
name := "Tutorial Example 4 - Dictonary Service Client 2"

organization := "org.apache.felix"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "com.weiglewilczek.slf4s" % "slf4s_2.9.1" % "1.0.7",
  "org.slf4j" % "slf4j-log4j12" % "1.6.4",
  "org.apache.felix" % "org.osgi.core" % "1.4.0",
  "org.apache.felix" % "tutorial-example-2---dictonary-service_2.9.1" % "0.1"
)

seq(osgiSettings: _*)

OsgiKeys.bundleActivator := Some("tutorial.example4.Activator")

OsgiKeys.exportPackage ++= Seq(
  "tutorial.example4"
)

OsgiKeys.importPackage ++= Seq(
  "tutorial.example2.service"
)

name := "Tutorial Example 3 - Dictonary Service Client"

organization := "org.apache.felix"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.apache.felix" % "org.osgi.core" % "1.4.0",
  "org.apache.felix" % "tutorial-example-2---dictonary-service_2.9.1" % "0.1"
)

seq(osgiSettings: _*)

OsgiKeys.bundleActivator := Some("tutorial.example3.Activator")

OsgiKeys.exportPackage ++= Seq(
  "tutorial.example3"
)

OsgiKeys.importPackage ++= Seq(
  "tutorial.example2.service"
)

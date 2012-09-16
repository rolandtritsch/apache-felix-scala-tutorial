name := "Tutorial Example 7 - Spell Checker Client"

organization := "org.apache.felix"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.apache.felix" % "org.osgi.core" % "1.4.0",
  "org.apache.felix" % "org.osgi.compendium" % "1.4.0",
  "org.apache.felix" % "tutorial-example-6---spell-checker-service_2.9.1" % "0.1"
)

seq(osgiSettings: _*)

OsgiKeys.bundleActivator := Some("tutorial.example7.Activator")

OsgiKeys.exportPackage ++= Seq(
  "tutorial.example7"
)

OsgiKeys.importPackage ++= Seq(
  "tutorial.example6.service"
)

name := "Tutorial Example 2 - Dictonary Service"

organization := "org.apache.felix"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.apache.felix" % "org.osgi.core" % "1.4.0"
)

seq(osgiSettings: _*)

OsgiKeys.bundleActivator := Some("tutorial.example2.Activator")

OsgiKeys.exportPackage ++= Seq(
  "tutorial.example2",
  "tutorial.example2.service"
)

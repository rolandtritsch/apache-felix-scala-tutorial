name := "Tutorial Example 1 - Service Listener"

organization := "org.apache.felix"

version := "0.1"

scalaVersion := "2.9.1"

libraryDependencies ++= Seq(
  "org.apache.felix" % "org.osgi.core" % "1.4.0"
)

seq(osgiSettings: _*)

OsgiKeys.bundleActivator := Some("tutorial.example1.Activator")

OsgiKeys.exportPackage := Seq("tutorial.example1")

import sbt._
import Keys._

object XSDK extends Build {
  lazy val buildSettings = Seq(
      organization := "com.timesprint",
      scalaVersion := "2.10.1",
      scalacOptions := Seq("-feature", "-deprecation", "-unchecked", "-Xlint", "-Yrangepos", "-encoding", "utf8"),
      scalacOptions in (console) += "-Yrangepos"
  )

  lazy val commonSettings = Defaults.defaultSettings ++ buildSettings

  lazy val root = Project(id = "asn1kit", base = file("."))
    .aggregate(asn1ast).settings(commonSettings: _*)
    .aggregate(asn1core).settings(commonSettings: _*)
    .aggregate(asn1gen).settings(commonSettings: _*)
    .aggregate(asn1jrt).settings(commonSettings: _*)
    .aggregate(asn1parse).settings(commonSettings: _*)
    .aggregate(asn1runtime).settings(commonSettings: _*)
    .aggregate(asn1util).settings(commonSettings: _*)
    .aggregate(model).settings(commonSettings: _*)
    .aggregate(rough).settings(commonSettings: _*)

  lazy val asn1ast = Project(id = "asn1ast", base = file("asn1ast"))
    .settings(commonSettings: _*)

  lazy val asn1core = Project(id = "asn1core", base = file("asn1core"))
    .settings(commonSettings: _*)

  lazy val asn1gen = Project(id = "asn1gen", base = file("asn1gen"))
    .settings(commonSettings: _*)

  lazy val asn1jrt = Project(id = "asn1jrt", base = file("asn1jrt"))
    .settings(commonSettings: _*)

  lazy val asn1parse = Project(id = "asn1parse", base = file("asn1parse"))
    .settings(commonSettings: _*)

  lazy val asn1runtime = Project(id = "asn1runtime", base = file("asn1runtime"))
    .settings(commonSettings: _*)

  lazy val asn1util = Project(id = "asn1util", base = file("asn1util"))
    .settings(commonSettings: _*)

  lazy val model = Project(id = "model", base = file("model"))
    .settings(commonSettings: _*)

  lazy val rough = Project(id = "rough", base = file("rough"))
    .settings(commonSettings: _*)
}


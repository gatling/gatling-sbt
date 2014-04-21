package io.gatling.sbt

import sbt._
import sbt.Keys._
import sbt.Tests.{Argument, Group}

import io.gatling.sbt.GatlingTasks._

object GatlingPlugin extends Plugin {

  val Gatling = config("gatling") extend Test

  val gatlingTestFramework = TestFramework("io.gatling.sbt.GatlingFramework")

  lazy val gatlingSettings = inConfig(Gatling)(gatlingBaseSettings)

  lazy val gatlingBaseSettings = Defaults.testSettings ++ Seq(
    testFrameworks in Gatling := Seq(gatlingTestFramework),
    target in Gatling := target.value / "gatling",
    testOptions in Gatling += Argument("-m", "-rf", (target in Gatling).value.getPath),
    sourceDirectory in Gatling := (sourceDirectory in Test).value,
    parallelExecution in Gatling := false,
    fork in Gatling := true,
    testGrouping in Gatling := (testGrouping in Gatling).value flatMap singleTestGroup,
    startRecorder in Gatling := recorderRunner((dependencyClasspath in Test).value.map(_.data), organization.value, (scalaSource in Gatling).value),
    lastReport in Gatling := openLastReport((target in Gatling).value))

  def singleTestGroup(group: Group) = group.tests map (test => Group(test.name, Seq(test), group.runPolicy))

}

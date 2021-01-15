import _root_.io.gatling.build.license._

ThisBuild / Keys.useCoursier := false

enablePlugins(SbtPlugin)
disablePlugins(SonatypeReleasePlugin, MavenPublishPlugin, Sonatype)

name := "gatling-sbt"
headerLicense := ApacheV2License
scalaVersion := "2.12.12"
sbtPlugin := true
publishMavenStyle := false
bintrayRepository := "sbt-plugins"
bintrayOrganization in bintray := None

libraryDependencies += "org.scalatest"      %% "scalatest" % "3.2.2" % "test"
libraryDependencies += "org.zeroturnaround" % "zt-zip"     % "1.14"

scriptedLaunchOpts := {
  scriptedLaunchOpts.value ++
    Seq(
      "-Xmx512m",
      "-Dgatling.http.enableGA=false",
      "-Dplugin.version=" + version.value
    )
}

scriptedBufferLog := false

pluginCrossBuild / sbtVersion := "1.4.3"

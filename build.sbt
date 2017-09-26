name := """TypeClassPlayGround"""

version := "1.0"

scalaVersion := "2.12.1"

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  Resolver.sonatypeRepo("snapshots")
)

// Change this to another test framework if you prefer
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.typelevel" %% "cats" % "0.9.0"

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"
addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies += "com.github.mpilquist" %% "simulacrum" % "0.10.0"

libraryDependencies ++= Seq(
  "com.chuusai" %% "shapeless" % "2.3.2"
)
val circeVersion = "0.7.0"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

// add to show implicit conversions
//scalacOptions in Compile += "-Xlog-implicits"

libraryDependencies += "com.github.alexarchambault" %% "scalacheck-shapeless_1.13" % "1.1.3"

scalacOptions in Compile ++= Seq("-feature","-language:implicitConversions")

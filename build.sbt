
import sbtrelease._
import ReleaseStateTransformations._
import AmiBundleBuild._

name := "ami-bundle"

organization := "ohnosequences"

version := "0.1.1-SNAPSHOT"

scalaVersion := "2.10.0"

publishMavenStyle := false

publishPrivate := false

publishTo <<= (version, publishPrivate) { (v: String, p: Boolean) =>
  Some(s3resolver(isSnapshot = v.trim.endsWith("SNAPSHOT"), isPrivate = p, publisher = true))
}

resolvers ++= Seq (
                    "Typesafe Releases"   at "http://repo.typesafe.com/typesafe/releases"
                  , "Sonatype Releases"   at "https://oss.sonatype.org/content/repositories/releases"
                  , "Sonatype Snapshots"  at "https://oss.sonatype.org/content/repositories/snapshots"
                  , "Era7 Releases"       at "http://releases.era7.com.s3.amazonaws.com"
                  , "Era7 Snapshots"      at "http://snapshots.era7.com.s3.amazonaws.com"
                  )

resolvers ++= s3resolvers

libraryDependencies ++= Seq (
                              "com.chuusai" %% "shapeless" % "1.2.3"
                            , "ohnosequences" %% "statika" % "0.7.0"
                            )

 

scalacOptions ++= Seq("-feature"
                    , "-language:higherKinds"
                    , "-language:implicitConversions"
                    , "-deprecation"
                    , "-unchecked"
                    )

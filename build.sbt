
import sbtrelease._
import ReleaseStateTransformations._
import ReleasePlugin._
import ReleaseKeys._

name := "ami-bundle"

organization := "ohnosequences"

scalaVersion := "2.10.0"

publishMavenStyle := false

publishTo <<= (isSnapshot, s3resolver) { 
                (snapshot,   resolver) => 
  val prefix = if (snapshot) "snapshots" else "releases"
  resolver( "Statika "+prefix+" S3 publishing bucket"
          , "s3://"+prefix+".statika.ohnosequences.com")
}

resolvers ++= Seq ( Resolver.typesafeRepo("releases")
                  , Resolver.sonatypeRepo("releases")
                  , Resolver.sonatypeRepo("snapshots")
                  , "Era7 Releases"  at "http://releases.era7.com.s3.amazonaws.com"
                  , "Era7 Snapshots" at "http://snapshots.era7.com.s3.amazonaws.com"
                  )

resolvers <++= s3resolver { s3 => Seq(
    s3("Era7 ivy releases", "s3://releases.era7.com")
  , s3("Era7 ivy snapshots", "s3://snapshots.era7.com")
  ).flatten }

libraryDependencies ++= Seq ( "com.chuusai" %% "shapeless" % "1.2.3"
                            , "ohnosequences" %% "statika" % "0.8.2"
                            )

scalacOptions ++= Seq("-feature"
                    , "-language:higherKinds"
                    , "-language:implicitConversions"
                    , "-deprecation"
                    , "-unchecked"
                    )

// sbt-release settings

releaseSettings

releaseProcess <<= thisProjectRef apply { ref =>
  Seq[ReleaseStep](
    checkSnapshotDependencies
  , inquireVersions
  , runTest
  , setReleaseVersion
  , commitReleaseVersion
  , tagRelease
  , publishArtifacts
  , setNextVersion
  , commitNextVersion
  , pushChanges
  )
}

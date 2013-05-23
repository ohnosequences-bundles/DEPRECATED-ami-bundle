import sbtrelease._
import ReleaseStateTransformations._
import AmiBundleBuild._

name := "ami-bundle"

organization := "ohnosequences"

scalaVersion := "2.10.0"

s3credentialsFile := Some("AwsCredentials.properties")

publishMavenStyle := false

publishPrivate := false

publishTo <<= (s3credentials, version, publishPrivate)(s3publisher(statikaPrefix)) 

resolvers ++= Seq ( Resolver.typesafeRepo("releases")
                  , Resolver.sonatypeRepo("releases")
                  , Resolver.sonatypeRepo("snapshots")
                  , "Era7 Releases"  at "http://releases.era7.com.s3.amazonaws.com"
                  , "Era7 Snapshots" at "http://snapshots.era7.com.s3.amazonaws.com"
                  )

libraryDependencies ++= Seq ( "com.chuusai" %% "shapeless" % "1.2.3"
                            , "ohnosequences" %% "statika" % "0.8.1"
                            )

 

scalacOptions ++= Seq("-feature"
                    , "-language:higherKinds"
                    , "-language:implicitConversions"
                    , "-deprecation"
                    , "-unchecked"
                    )

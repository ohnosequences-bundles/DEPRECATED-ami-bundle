resolvers ++= Seq (
  Resolver.url("Era7 Ivy Snapshots", url("http://snapshots.era7.com.s3.amazonaws.com"))(
    Patterns("[organisation]/[module]/[revision]/[type]s/[artifact].[ext]"))
, Resolver.url("Era7 Ivy Releases", url("http://releases.era7.com.s3.amazonaws.com"))(
    Patterns("[organisation]/[module]/[revision]/[type]s/[artifact].[ext]"))
)

addSbtPlugin("ohnosequences" % "sbt-statika" % "0.2.3")

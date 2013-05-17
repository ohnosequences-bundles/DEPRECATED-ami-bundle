package ohnosequences.statika

import shapeless._
import ohnosequences.statika.General._
import scala.sys.process._

abstract class AmiBundle(val id: String, val amiVersion: String, version: String) extends 
  Bundle( name = id.split("""\W""").map(_.capitalize).mkString +
                 "_" + amiVersion.replaceAll("\\.","_")
        , version = version
        , dependencies = HNil: HNil) {

  override def install = {
    val ami = "wget -q -O - http://169.254.169.254/latest/meta-data/ami-id".!!.replaceAll("\n","")
    if (ami == id)
      success("Checked that Amazon Machine Image is " + id)
    else
      failure("AMI should be "+ id +", instead of "+ ami)
  }

  def userScript[B <: BundleAux](b: B, artifact: String)
      (implicit s: Selector[b.Deps, this.type]): String
}

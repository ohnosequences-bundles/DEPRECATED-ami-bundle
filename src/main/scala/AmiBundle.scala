package ohnosequences.statika

import shapeless._
import ohnosequences.statika.General._
import ohnosequences.statika.TypeLookupHList._
import scala.sys.process._

object Ami {

  abstract class AmiBundle(val id: String, val amiVersion: String)
    (implicit meta: BundleMetaData) extends BundleWithMetaData(data = meta) {

    override def install = {
      val ami = "wget -q -O - http://169.254.169.254/latest/meta-data/ami-id".!!.replaceAll("\n","")
      if (ami == id)
        success("Checked that Amazon Machine Image is " + id)
      else
        failure("AMI should be "+ id +", instead of "+ ami)
    }

    def userScript[B <: BundleAux](b: B)
        (implicit s: Selector[b.Deps, this.type]): String
  }

  implicit class GetBundlesAmi[B <: BundleAux](b: B) {
    def ami(implicit a: Contains[b.Deps, AmiBundle]): a.Out = a(b.dependencies) 
  }
 
}

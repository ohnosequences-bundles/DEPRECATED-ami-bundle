package ohnosequences.statika

import shapeless._
import ohnosequences.statika.General._
import scala.sys.process._

abstract class AmiBundle(id: String, amiVersion: String, version: String) extends 
  Bundle(id+"_"+amiVersion, version, dependencies = HNil: HNil) {

  override val install = {
    val ami = "ec2-metadata -a".!!
    if (ami == "ami-id: " + id)
    	success("Checked that Amazon Machine Image is " + this)
    else
      failure("AMI should be "+ id +", whereas 'ec2-metadata -a' gives: "+ ami)
  }
}

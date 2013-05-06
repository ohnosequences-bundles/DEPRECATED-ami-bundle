package ohnosequences.statika

import shapeless._
import ohnosequences.statika.General._
import scala.sys.process._

abstract class CredentialsBundle(name: String, version: String) extends 
  Bundle(name, version, dependencies = HNil: HNil) {

  override val install = {
    val ami = "ec2-metadata -a".!
    if (ami == "ami-id: " + name)
    	success("Checked that Amazon Machine Image is " + this)
    else
      failure("AMI should be "+ name +", whereas 'ec2-metadata -a' gives: "+ ami)
  }
}

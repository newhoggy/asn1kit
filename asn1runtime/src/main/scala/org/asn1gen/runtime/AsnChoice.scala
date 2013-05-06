package org.asn1gen.runtime

abstract class AsnChoice extends AsnType {
  override def _desc: meta.AsnChoice = meta.AsnChoice
  
  def _raw: this.type = this
  
  def _choiceName: String
  
  def _element: Any
}

object AsnChoice {
}

package org.asn1gen.runtime

import org.asn1gen.extra.ExtraListOfByte
import org.asn1gen.extra.Extras._

class AsnOctetString(val value: List[Byte]) extends AsnType {
  override def _desc: meta.AsnOctetString = meta.AsnOctetString
  
  def _raw = value

  def copy(value: List[Byte] = this.value) = new AsnOctetString(value)

  override def equals(that: Any): Boolean = that match {
    case that: AsnOctetString => this.value == that.value
    case _ => false
  }

  override def hashCode(): Int = this.value.hashCode

  def value(f: (List[Byte] => List[Byte])): AsnOctetString = this.copy(value = f(this.value))

  def string: String = this.value.string
  
  def length: Int = this.value.length
}

object AsnOctetString extends AsnOctetString(Nil) {
  def apply(value: List[Byte]): AsnOctetString = new AsnOctetString(value)
  
  def apply(value: String): AsnOctetString = AsnOctetString(value.getBytes.toList)

  def unapply(value: AsnOctetString) = Some(value.value)
}

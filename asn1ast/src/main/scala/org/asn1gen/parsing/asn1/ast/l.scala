package org.asn1gen.parsing.asn1.ast

case class Literal(
  word: Option[Word]
) extends Node with RequiredToken with DefinedSyntaxToken {
}

case class LowerEndPoint(
    exclusive: Boolean,
    lowerEndValue: LowerEndValue
) extends Node {
}

trait LowerEndValue {
}


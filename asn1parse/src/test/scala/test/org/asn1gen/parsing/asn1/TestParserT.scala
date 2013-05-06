import _root_.org.junit._
import _root_.org.junit.Assert._
import _root_.junit.framework.TestCase
import _root_.org.asn1gen.parsing.asn1.Asn1Parser
import _root_.org.asn1gen.parsing.asn1.ast._
import _root_.scala.util.parsing.input._

package test.org.asn1gen.parsing.asn1 {
  class TestParserT extends TestCase {
    
    object TheParser extends Asn1Parser {
      def parse[N](root: Parser[N], input: String) =
        phrase(root)(new lexical.Scanner(input))
    }
    
    import TheParser._
    
    @Test def test_type_1() {
      parse(_type, "INTEGER { a(1), b(2), c(3) }") match {
        case Success(
          result@Type(_, _),
          _) =>
        case x => fail("Parse '_type' failure: " + x)
      }
    }
    
    @Test def test_type_2() {
      parse(_type, "CHOICE { choice1 [0] INTEGER, choice2 [1] INTEGER }") match {
        case Success(
          result@Type(
            ChoiceType(
              AlternativeTypeLists(
                RootAlternativeTypeList(
                  AlternativeTypeList(
                    List(
                      NamedType(
                        Identifier("choice1"),
                        Type(
                          TaggedType(
                            Tag(Empty, Number("0")),
                            _,
                            Type(
                              INTEGER(None), _)),
                          _)),
                      NamedType(
                        Identifier("choice2"),
                        Type(
                          TaggedType(
                            Tag(Empty, Number("1")),
                            _,
                            Type(INTEGER(None), _)),
                          _))))),
                  None,None,None)),
            _),
          _) => println(result)
        case x => fail("Parse '_type' failure: " + x)
      }
    }
    
    @Test def test_typeAssignment_1() {
      parse(typeAssignment, "MyChoice ::= CHOICE { choice1 [0] INTEGER, choice2 [1] INTEGER }") match {
        case Success(
          result,
          _) =>
        case x => fail("Parse 'type' failure: " + x)
      }
    }
    
    // 9.1.1
    @Test def test_typeAssignment_2() {
      parse(typeAssignment, "TypeReference ::= CHOICE { integer INTEGER, boolean BOOLEAN }") match {
        case Success(
          TypeAssignment(
            TypeReference("TypeReference"),
            Type(ChoiceType(AlternativeTypeLists(
              RootAlternativeTypeList(
                AlternativeTypeList(
                  List(
                    NamedType(Identifier("integer"), Type(INTEGER(None), _)),
                    NamedType(Identifier("boolean"), Type(BOOLEAN, _))))),
              None,
              None,
              None)),
              _)),
          _) =>
        case x => fail("Parse 'type' failure: " + x)
      }
    }
    
    // 9.1.1
    @Test def test_typeAssignment_3() {
      parse(typeAssignment, "Pair ::= SEQUENCE { x INTEGER, y INTEGER }") match {
        case Success(
          result@TypeAssignment(TypeReference("Pair"), Type(SequenceType(_), _)),
          _) =>
        case x => fail("Parse 'type' failure: " + x)
      }
    }
  }
}

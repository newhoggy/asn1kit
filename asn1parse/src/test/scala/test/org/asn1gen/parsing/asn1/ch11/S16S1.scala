import _root_.org.junit._
import _root_.org.junit.Assert._
import _root_.junit.framework.TestCase
import _root_.org.asn1gen.parsing.asn1.Asn1Parser
import _root_.org.asn1gen.parsing.asn1.ast._
import _root_.scala.util.parsing.input._

package test.org.asn1gen.parsing.asn1.ch11 {
  class TestS16S1 extends TestCase {
    
    object TheParser extends Asn1Parser {
      def parse[N](root: Parser[N], input: String) =
        phrase(root)(new lexical.Scanner(input))
    }
    
    import TheParser._
    
    @Test def test_1() {
      val text = """
          -- local hour:
          now GeneralizedTime ::= "19980528142905.1"
          now GeneralizedTime ::= "19980528142905,1"
          -- UTC hour:
          utc-time GeneralizedTime ::= "1998052814Z"
          utc-time GeneralizedTime ::= "199805281429Z"
          -- ahead of UTC:
          ahead-time GeneralizedTime ::= "199805281629+0200"
          """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_2() {
      val text = """
          KerberosTime ::= GeneralizedTime
          -- specify a UTC time at a second precision
          """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_3() {
      val text = """
          DeleteResult ::= SEQUENCE {
            managedObjectClass ObjectClass OPTIONAL,
            managedObjectInstance ObjectInstance OPTIONAL,
            currentTime [5] IMPLICIT GeneralizedTime OPTIONAL
          }
          """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
  }
}

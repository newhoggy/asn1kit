import _root_.org.junit._
import _root_.org.junit.Assert._
import _root_.junit.framework.TestCase
import _root_.org.asn1gen.parsing.asn1.Asn1Parser
import _root_.org.asn1gen.parsing.asn1.ast._
import _root_.scala.util.parsing.input._

package test.org.asn1gen.parsing.asn1.ch12 {
  class TestS9S1 extends TestCase {
    
    object TheParser extends Asn1Parser {
      def parse[N](root: Parser[N], input: String) =
        phrase(root)(new lexical.Scanner(input))
    }
    
    import TheParser._
    
    // Not supported
    def not_test_01() {
      val text = """
        AlgorithmIdentifier ::= SEQUENCE {
          algorithm OBJECT IDENTIFIER,
          parameters ANY DEFINED BY algorithm OPTIONAL
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02() {
      val text = """
        State ::= ENUMERATED {
          on, off, out-of-order, ...
        }
        Description ::= SEQUENCE {
          surname IA5String,
          first-name IA5String,
          age INTEGER,
          ...
        }
        Dimensions ::= SET {
          x INTEGER,
          y INTEGER,
          ...
        }
        Afters ::= CHOICE {
          cheese IA5String,
          dessert IA5String,
          ...
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_1() {
      val text = """
        State ::= ENUMERATED {
          on, off, out-of-order, ...
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_2() {
      val text = """
        Description ::= SEQUENCE {
          surname IA5String,
          first-name IA5String,
          age INTEGER,
          ...
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_2_1() {
      val text = """
        SEQUENCE {
          surname IA5String,
          first-name IA5String,
          age INTEGER,
          ...
        }
      """
      parse(_type, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_2_1_1() {
      val text = """
        SEQUENCE {
          surname IA5String,
          first-name IA5String,
          age INTEGER,
          ...
        }
      """
      parse(sequenceType, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_2_1_1_1() {
      val text = """
        surname IA5String,
        first-name IA5String,
        age INTEGER,
        ...
      """
      parse(componentTypeLists, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_2_1_1_1_1() {
      val text = """
        surname IA5String,
        first-name IA5String,
        age INTEGER,
        ...
      """
      parse(componentTypeLists, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_3() {
      val text = """
        Dimensions ::= SET {
          x INTEGER,
          y INTEGER,
          ...
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_4() {
      val text = """
        Afters ::= CHOICE {
          cheese IA5String,
          dessert IA5String,
          ...
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_4_1() {
      val text = """
        CHOICE {
          cheese IA5String,
          dessert IA5String,
          ...
        }
      """
      parse(_type, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_4_1_1() {
      val text = """
        CHOICE {
          cheese IA5String,
          dessert IA5String,
          ...
        }
      """
      parse(choiceType, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_02_4_1_1_1() {
      val text = """
        CHOICE {
          cheese IA5String,
          dessert IA5String,
          ...
        }
      """
      parse(choiceType, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_03() {
      val text = """
        State ::= ENUMERATED {
          on, off, out-of-order, ..., stand-by
        }
        Dimensions ::= SET {
          x INTEGER,
          y INTEGER,
          ...,
          z INTEGER
        }
        Afters ::= CHOICE {
          cheese IA5String,
          dessert IA5String,
          ...,
          coffee NULL
        }
        Afters ::= CHOICE {
          cheese IA5String,
          dessert IA5String,
          ...,
          coffee NULL,
          cognac IA5String
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_04() {
      val text = """
        Description ::= SEQUENCE {
          surname IA5String,
          first-name IA5String,
          age INTEGER,
          ...!extended-description
        }
        extended-description INTEGER ::= 1
        Dimensions ::= SET {
          x INTEGER,
          y INTEGER,
          ... !IA5String:"dimension error"
        }
        Afters ::= CHOICE {
          cheese IA5String,
          dessert IA5String,
          ...!ExtensionPb:greedy,
          coffee NULL,
          cognac IA5String
        }
        ExtensionPb::= ENUMERATED {greedy, ...}
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_05() {
      val text = """
        T ::= SEQUENCE { a  A, b B, ..., ..., c C}
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_06() {
      val text = """
        Afters ::= CHOICE {
          cheese  IA5String,
          dessert IA5String,
          ...!ExtensionPb:greedy,
          [[coffee NULL ]], -- version 2
          [[cognac IA5String]] -- version 3
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_07() {
      val text = """
        T ::= SEQUENCE {
          a A, b B,
          ...,
          [[d D, e E]],
          ...,
          c C
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_07_1() {
      val text = """
        SEQUENCE {
          a A, b B,
          ...,
          [[d D, e E]],
          ...,
          c C
        }
      """
      parse(_type, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_07_1_1() {
      val text = """
        SEQUENCE {
          a A, b B,
          ...,
          [[d D, e E]],
          ...,
          c C
        }
      """
      parse(sequenceType, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_07_1_1_1() {
      val text = """
        a A, b B,
        ...,
        [[d D, e E]],
        ...,
        c C
      """
      parse(componentTypeLists, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_07_1_1_1_1() {
      val text = """
        ...,
        [[d D, e E]],
        ...
      """
      parse(componentTypeListsExtension, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_07_1_1_1_1_1() {
      val text = """
        ,
        [[d D, e E]]
      """
      parse(extensionsAdditions, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_07_1_1_1_1_1_1() {
      val text = """
        [[d D, e E]]
      """
      parse(extensionAdditionList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_07_1_1_1_1_1_1_1() {
      val text = """
        [[d D, e E]]
      """
      parse(extensionAddition, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_08() {
      val text = """
        Person ::= SET {
          surname [0] IA5String,
          first-name [1] IA5String,
          contact CHOICE {
            phone-number [2] NumericString,
            e-mail-address [3] NumericString,
            ...
          },
          info CHOICE {
            age [4] INTEGER,
            ...
          }
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_08_1() {
      val text = """
        SET {
          surname [0] IA5String,
          first-name [1] IA5String,
          contact CHOICE {
            phone-number [2] NumericString,
            e-mail-address [3] NumericString,
            ...
          },
          info CHOICE {
            age [4] INTEGER,
            ...
          }
        }
      """
      parse(_type, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_09() {
      val text = """
        MasterSlaveDeterminationReject ::= SEQUENCE {
          cause CHOICE {
            identicalNumbers NULL,
            ...
          },
          ...
        }
      """
      parse(assignmentList, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
    
    @Test def test_10() {
      val text = """
        ModuleName DEFINITIONS AUTOMATIC TAGS EXTENSIBILITY IMPLIED ::=
          BEGIN
            I ::= INTEGER
          END
      """
      parse(moduleDefinition, text) match {
        case Success(_, _) => ()
        case x => fail("Parse failure: " + x)
      }
    }
  }
}

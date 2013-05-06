import _root_.org.junit._
import _root_.org.junit.Assert._
import _root_.junit.framework.TestCase
import _root_.org.asn1gen.parsing.asn1._
import _root_.org.asn1gen.parsing.asn1.ast._
import _root_.scala.util.parsing.input._
import _root_.org.asn1gen.gen._
import _root_.org.asn1gen.gen.scala._
import _root_.org.asn1gen.io.IndentWriter

package test.org.asn1gen.gen {
  class TestGenScala extends TestCase {
    import Asn1._
    
    @Test def test1() {
      val text = """
        ModuleName DEFINITIONS ::= BEGIN
          MyEnum ::= ENUMERATED {
            value0, value1, value2, value3
          }
        END
        """
      Asn1.parse(text) match {
        case Asn1.Success(moduleDefinition, _) => {
          val genScala = new GenScala("test.asn1.genruntime", new IndentWriter(System.out))
          val module = Module.from(moduleDefinition)
          genScala.generate(module)
        }
        case x => fail("Parse failed: " + x)
      }
    }

    @Test def test2() {
      val text = """
        ModuleName DEFINITIONS ::= BEGIN
          MyChoice ::= CHOICE {
            choice0 [0] Empty,
            choice1 [1] INTEGER,
            choice2 [2] REAL
          }
        END
        """
      Asn1.parse(text) match {
        case Asn1.Success(moduleDefinition, _) => {
          val genScala = new GenScala("test.asn1.genruntime", new IndentWriter(System.out))
          val module = Module.from(moduleDefinition)
          genScala.generate(module)
        }
        case x => fail("Parse failed: " + x)
      }
    }
    
    @Test def test3() {
      val text = """
        ModuleName DEFINITIONS ::= BEGIN
        	MySequence ::= SEQUENCE {
            field1 [0] INTEGER OPTIONAL,
            field2 [1] REAL,
            field3 [2] PrintableString,
            field4 [3] MyChoice
          }
        END
        """
      Asn1.parse(text) match {
        case Asn1.Success(moduleDefinition, _) => {
          val genScala = new GenScala("test.asn1.genruntime", new IndentWriter(System.out))
          val module = Module.from(moduleDefinition)
          genScala.generate(module)
        }
        case x => fail("Parse failed: " + x)
      }
    }
    @Test def test4() {
      val text = """
        ModuleName DEFINITIONS ::= BEGIN
          Empty ::= SEQUENCE {
          }
        END
        """
      Asn1.parse(text) match {
        case Asn1.Success(moduleDefinition, _) => {
          val genScala = new GenScala("test.asn1.genruntime", new IndentWriter(System.out))
          val module = Module.from(moduleDefinition)
          genScala.generate(module)
        }
        case x => fail("Parse failed: " + x)
      }
    }
  }
}

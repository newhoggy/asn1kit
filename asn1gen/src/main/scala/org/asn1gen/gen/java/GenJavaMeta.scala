package org.asn1gen.gen.java

import java.io.PrintWriter
import org.asn1gen.parsing.asn1.{ast => ast}
import org.asn1gen.io._
import scala.collection.immutable.Set
import org.asn1gen.gen.java.NameOf._

class GenJavaMeta(packageName: String, out: IndentWriter) {
  val keywords = Set("yield", "type", "null")
  
  def generate(module: Module): Unit = {
    out.println("/* This file was generated by asn1gen */")
    out.println()
    out.println("package " + packageName + ".meta")
    out.println()
    out.println("import org.asn1gen.runtime.{meta => _meta_}")
    out.println()
    out.println("object " + safeId(module.name) + " {")
    out.indent(2) {
      module.imports foreach { symbolsFromModule =>
        out.println("import " + symbolsFromModule.module + "._")
      }
      out.println()
      module.types.foreach { case (_, namedType: NamedType) => generate(namedType) }
    }
    out.println("}")
  }
  
  def generate(namedType: NamedType): Unit = {
    namedType._type match {
      case ast.Type(builtinType: ast.BuiltinType, _) => {
        generate(builtinType, namedType.name)
      }
      case t@ast.Type(referencedType: ast.ReferencedType, _) => {
        referencedType match {
          case ast.TypeReference(name) => {
            val safeAlias = safeId(namedType.name)
            val safeReferent = safeId(name)
            out.println("trait " + safeAlias + " extends " + safeReferent + "{")
            out.indent(2) {
              out.println("override def name: String = \"" + safeAlias + "\"")
            }
            out.println("}")
            out.println()
            out.println("object " + safeAlias + " extends " + safeAlias)
          }
          case _ => {
            out.println("/* referencedType")
            out.println(referencedType)
            out.println("*/")
          }
        }
      }
      case t@ast.Type(_, _) => {
        out.println("/* unknown: " + namedType.name)
        out.println(t)
        out.println("*/")
      }
    }
  }
  
  def generate(builtinType: ast.BuiltinType, assignmentName: String): Unit = {
    val safeAssignmentName = safeId(assignmentName)
    builtinType match {
      case ast.ChoiceType(
        ast.AlternativeTypeLists(rootAlternativeTypeList, _, _, _))
      => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnChoice {")
        out.indent(2) {
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
          out.println()
          out.println("override def children: Map[String, _meta_.AsnMember] = Map(")
          out.indent(2) {
            var firstItem = true
            rootAlternativeTypeList match {
              case ast.RootAlternativeTypeList(ast.AlternativeTypeList(namedTypes)) => {
                namedTypes foreach { case ast.NamedType(ast.Identifier(identifier), _type) =>
                  val safeIdentifier = safeId(identifier)
                  val safeType = safeId(typeNameOf(_type))
                  if (!firstItem) {
                    out.println(",")
                  }
                  out.print(
                      "\"" + safeIdentifier + "\" -> _meta_.AsnChoiceMember(\"" +
                      safeIdentifier + "\", " + safeType + ")")
                  firstItem = false
                }
              }
            }
            out.println(")")
          }
        }
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case ast.SequenceType(ast.Empty) => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnSequence {")
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case ast.SequenceType(ast.ComponentTypeLists(list1, extension, list2)) => {
        val list = (list1.toList:::list2.toList).map { componentTypeList =>
          componentTypeList.componentTypes
        }.flatten
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnSequence {")
        out.indent(2) {
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
          out.println()
          out.println("override def children: Map[String, _meta_.AsnMember] = Map(")
          out.indent(2) {
            var firstItem = true
            list.map {
              case ast.NamedComponentType(
                ast.NamedType(ast.Identifier(identifier), _type), optionalValue)
              => {
                val safeIdentifier = safeId(identifier)
                val safeType = safeId(typeNameOf(_type))
                if (!firstItem) {
                  out.println(",")
                }
                out.print(
                    "\"" + safeIdentifier + "\" -> _meta_.AsnSequenceMember(\"" +
                    safeIdentifier + "\", " +
                    safeType + ", " +
                    (optionalValue == ast.Optional) + ")")
                firstItem = false
              }
            }
            out.println(")")
          }
        }
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case ast.EnumeratedType(enumerations)
      => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnEnumeration {")
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case setOfType: ast.SetOfType => {
        generate(assignmentName, setOfType)
      }
      case bitStringType: ast.BitStringType => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnBitString {")
        out.indent(2) {
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
        }
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case ast.INTEGER(None) => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnInteger {")
        out.indent(2) {
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
        }
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case ast.BOOLEAN => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnBoolean {")
        out.indent(2) {
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
        }
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case ast.OctetStringType => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnOctetString {")
        out.indent(2) {
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
        }
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case ast.PrintableString => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnPrintableString {")
        out.indent(2) {
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
        }
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case ast.REAL => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnReal {")
        out.indent(2) {
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
        }
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case ast.UTF8String => {
        out.println("trait " + safeAssignmentName + " extends _meta_.AsnUtf8String {")
        out.indent(2) {
          out.println("override def name: String = \"" + safeAssignmentName + "\"")
        }
        out.println("}")
        out.println()
        out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
      }
      case unmatched => {
        out.println("// Unmatched " + safeAssignmentName + ": " + unmatched)
      }
    }
  }
  
  def generate(assignmentName: String, setOfType: ast.SetOfType): Unit = {
    val safeAssignmentName = safeId(assignmentName)
    setOfType match {
      case ast.SetOfType(ast.Type(elementType, _)) => {
        elementType match {
          case ast.TypeReference(referencedType) => {
            out.println("trait " + safeAssignmentName + " extends _meta_.AsnList {")
            out.indent(2) {
              out.println("override def name: String = \"" + safeAssignmentName + "\"")
              out.println()
              out.println("override def children: Map[String, _meta_.AsnMember] = Map.empty")
            }
            out.println("}")
            out.println()
            out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
          }
          case sequenceType: ast.SequenceType => {
            assert(false)
          }
          case builtinType: ast.BuiltinType => {
            out.println("trait " + safeAssignmentName + " extends _meta_.AsnList {")
            out.indent(2) {
              out.println("override def name: String = \"" + safeAssignmentName + "\"")
              out.println()
              out.println("override def children: Map[String, _meta_.AsnMember] = Map.empty")
            }
            out.println("}")
            out.println()
            out.println("object " + safeAssignmentName + " extends " + safeAssignmentName)
          }
        }
      }
    }
  }
  
  def generate(assignmentName: String, enumerations: ast.Enumerations): Unit = {
    enumerations match {
      case ast.Enumerations(ast.RootEnumeration(ast.Enumeration(items)), extension)
      => {
        var index = 0
        items foreach {
          case ast.Identifier(item) => {
            out.println(
              "def " + safeId(item) + ": " + safeId(assignmentName) +
              " = " + safeId(assignmentName) + "(" + index + ")")
            index = index + 1
          }
          case v@_ => {
            out.println("/* unknown enumeration:")
            out.println(v)
            out.println("*/")
          }
        }
        extension match {
          case None => {}
          case _ => out.println(extension)
        }
      }
    }
  }
  
  def generateSequenceFieldDefines(
      sequenceName: String, list: List[ast.ComponentType]): Unit = {
    var firstTime = true
    list foreach {
      case ast.NamedComponentType(
        ast.NamedType(ast.Identifier(identifier), _type),
        value)
      => {
        if (!firstTime) {
          out.println(",")
        }
        out.print("val " + safeId(identifier) + ": " + safeId(typeNameOf(_type, value)))
        firstTime = false
      }
    }
  }
  
  def generateSequenceFieldParameters(
      sequenceName: String, list: List[ast.ComponentType]): Unit = {
    var firstTime = true
    list foreach {
      case ast.NamedComponentType(
        ast.NamedType(ast.Identifier(identifier), _type),
        value)
      => {
        if (!firstTime) {
          out.println(",")
        }
        out.print(safeId(identifier) + ": " + safeId(typeNameOf(_type, value)))
        firstTime = false
      }
    }
  }
  
  def generateSequenceFieldValues(
      sequenceName: String, list: List[ast.ComponentType]): Unit = {
    var firstTime = true
    list foreach {
      case ast.NamedComponentType(
        ast.NamedType(ast.Identifier(identifier), _type),
        value)
      => {
        if (!firstTime) {
          out.println(",")
        }
        out.print(safeId(identifier))
        firstTime = false
      }
    }
  }
  
  def generateSequenceCopyParameters(
      list: List[ast.ComponentType]): Unit = {
    var firstTime = true
    list foreach {
      case ast.NamedComponentType(
        ast.NamedType(ast.Identifier(identifier), _type),
        value)
      => {
        if (!firstTime) {
          out.println(",")
        }
        out.print(safeId(identifier) + ": " + safeId(typeNameOf(_type, value)) + " = this." + safeId(identifier))
        firstTime = false
      }
    }
  }
}

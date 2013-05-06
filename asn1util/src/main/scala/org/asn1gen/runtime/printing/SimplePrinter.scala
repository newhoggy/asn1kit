package org.asn1gen.runtime.printing

import org.asn1gen.{runtime => _rt_}
import org.asn1gen.runtime.{meta => _meta_}

import java.io.PrintWriter
import org.asn1gen.extra.Extras
import org.asn1gen.io.IndentWriter

object SimplePrinter extends Extras {
  def print(out: IndentWriter, value: Any): Unit = {
    value match {
      case _rt_.AsnInteger(value) => {
        out.print("AsnInteger")
        out.print("(")
        out.print(value)
        out.print(")")
      }
      case _rt_.AsnReal(value) => {
        out.print("AsnReal")
        out.print("(")
        out.print(value)
        out.print(")")
      }
      case asnChoice: _rt_.AsnChoice => {
        val line = out.line
        out.print(asnChoice._desc.name)
        out.println
        out.indent {
          out.print(".")
          out.print(asnChoice._choiceName)
          out.print(" { _ => ")
          asnChoice._element match {
            case element: _rt_.AsnType => {
              print(out, element)
            }
            case Some(element: _rt_.AsnType) => {
              out.print("Some apply ")
              print(out, element)
            }
          }
          if (line != out.line) {
            out.break()
          } else {
            out.print(" ")
          }
          out.print("}")
        }
      }
      case asnCharacterString: _rt_.AsnCharacterString => {
        out.print(asnCharacterString._desc.name)
        if (asnCharacterString.length != 0) {
          out.print("(" + asnCharacterString.value.inspect() + ")")
        }
      }
      case _rt_.AsnOctetString(bytes) => {
        out.print("AsnOctetString")
        if (bytes.length != 0) {
          out.print("(" + bytes.string.inspect() + ")")
        }
      }
      case asnSequence: _rt_.AsnSequence => {
        val desc = asnSequence._desc
        out.print(desc.name)
        out.indent {
          desc.children.foreach { case (name, _: _meta_.AsnSequenceMember) =>
            out.break()
            val child = asnSequence._child(name)
            child match {
              case None =>
              case Some(subValue: _rt_.AsnSequence) => {
                out.print(".")
                out.print(name)
                out.print(" { _ => Some apply ")
                val line = out.line
                this.print(out, subValue)
                if (line != out.line) {
                  out.break()
                  out.print("}")
                } else {
                  out.print(" }")
                }
              }
              case Some(subValue: _rt_.AsnType) => {
                out.print(".")
                out.print(name)
                out.print(" { _ => Some apply ")
                val line = out.line
                this.print(out, subValue)
                if (line != out.line) {
                  out.break()
                } else {
                  out.print(" ")
                }
                out.print("}")
              }
              case Some(x) => {
                out.print(".")
                out.print(name)
                out.print(" { _ => Some(")
                print(out, x)
                out.print(") }")
              }
              case subValue: _rt_.AsnType => {
                out.print(".")
                out.print(name)
                out.print(" { _ => ")
                val line = out.line
                this.print(out, subValue)
                if (line != out.line) {
                  out.break()
                  out.print("}")
                } else {
                  out.print(" }")
                }
              }
              case subValue: Long => {
                out.print(".")
                out.print(name)
                out.print(" { _ => ")
                out.print(subValue)
                out.print(" }")
              }
              case subValue: Double => {
                out.print(".")
                out.print(name)
                out.print(" { _ => ")
                out.print(subValue)
                out.print(" }")
              }
              case subValue: String => {
                out.print(".")
                out.print(name)
                out.print(" { _ => ")
                out.print(subValue.inspect)
                out.print(" }")
              }
              case subValue: Boolean => {
                out.print(".")
                out.print(name)
                out.print(" { _ => ")
                out.print(subValue.inspect)
                out.print(" }")
              }
              case null => {
                out.print(".")
                out.print(name)
                out.print(" { _ => null }")
              }
            }
          }
        }
      }
      case _rt_.AsnBoolean(value) => {
        if (value) {
          out.print("AsnTrue")
        } else {
          out.print("AsnFalse")
        }
      }
      case list: _rt_.AsnList => {
        out.print(list._desc.name)
        if (list.items.length != 0) {
          out.println("(")
          out.indent {
            var firstItem = true
            list.items.foreach { item =>
              if (!firstItem) {
                out.println(",")
              }
              print(out, item)
              firstItem = false
            }
          }
          out.println
          out.print(")")
        }
      }
      case enumeration: _rt_.AsnEnumeration => {
        out.print(enumeration._desc.name)
        enumeration._shortName match {
          case Some(shortName) => {
            out.print(".")
            out.print(shortName)
          }
          case _ => {
            out.print("(")
            out.print(enumeration._value)
            out.print(")")
          }
        }
      }
      case value: Long => {
        out.print(value)
      }
      case value: Boolean => {
        out.print(value)
      }
      case value: Integer => {
        out.print(value)
      }
      case value: String => {
        out.print(value.inspect)
      }
      case _ => {
        out.print("/**")
        out.print(value)
        out.print("**/")
      }
    }
  }
}

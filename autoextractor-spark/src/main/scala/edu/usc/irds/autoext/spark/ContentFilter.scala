package edu.usc.irds.autoext.spark

import java.lang.Boolean

import edu.usc.irds.lang.Function

/**
  * Creates a filter based substring presence
  */
@SerialVersionUID(100L)
class ContentFilter(subString:String)
  extends Function[String, Boolean]
  with scala.Serializable {

  override def apply(t: String): Boolean = t.contains(subString)
}

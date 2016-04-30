/*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package gnieh.regex
package util

sealed trait ReNode {
  def acceptEmpty: Boolean
}

case object Empty extends ReNode {
  val acceptEmpty = true
  override def toString = ""
}

case object AnyChar extends ReNode {
  val acceptEmpty = false
  override def toString = "."
}

final case class SomeChar(c: Char) extends ReNode {
  val acceptEmpty = false
  override def toString = c.toString
}

final case class Concat(n1: ReNode, n2: ReNode) extends ReNode {
  val acceptEmpty = n1.acceptEmpty && n2.acceptEmpty
  override def toString = s"$n1$n2"
}

final case class Alt(n1: ReNode, n2: ReNode) extends ReNode {
  val acceptEmpty = n1.acceptEmpty || n2.acceptEmpty
  override def toString = s"$n1|$n2"
}

final case class Star(n: ReNode, greedy: Boolean) extends ReNode {
  val acceptEmpty = true
  override def toString = s"$n*"
}

final case class Plus(n: ReNode, greedy: Boolean) extends ReNode {
  val acceptEmpty = n.acceptEmpty
  override def toString = s"$n+"
}

final case class Opt(n: ReNode, greedy: Boolean) extends ReNode {
  val acceptEmpty = true
  override def toString = s"$n?"
}

final case class CharSet(chars: CharRangeSet) extends ReNode {
  val acceptEmpty = chars.isEmpty
  override def toString = s"$chars"
}

final case class Capture(n: ReNode) extends ReNode {
  val acceptEmpty = n.acceptEmpty
  override def toString = s"($n)"
}

/* A temporary node that is pushed onto the parsing stack and serves as marker
 * Typically, this is an opening parenthesis or bracket. */
private[regex] sealed trait Temporary extends ReNode {
  val offset: Int
}
private[regex] final case class CapturingStart(level: Int, offset: Int) extends Temporary {
  val acceptEmpty = false
}
private[regex] final case class CharSetStart(level: Int, offset: Int) extends Temporary {
  val acceptEmpty = false
}
private[regex] final case class Alternative(offset: Int) extends Temporary {
  val acceptEmpty = false
}


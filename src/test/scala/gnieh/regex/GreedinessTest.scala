/*
* This file is part of the regex project.
*
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

import bytecode._

import org.scalatest._

/** Tests that (non-)greediness is respected.
 *
 *  @author Lucas Satabin
 */
class GreedinessTest extends FlatSpec with Matchers {

  "Greedy operator" should "match as many characters as possible for star" in {

    val re = "(a*)(.*)".re

    "aaaaaaaaaabaaa" match {
      case re(as, rest) =>
        as should be("aaaaaaaaaa")
        rest should be("baaa")
      case _ =>
        fail("input should match")
    }

  }

  it should "match as many characters as possible for plus" in {

    val re = "(a+)(.*)".re

    "aaaaaaaaaabaaa" match {
      case re(as, rest) =>
        as should be("aaaaaaaaaa")
        rest should be("baaa")
      case _ =>
        fail("input should match")
    }

  }

  it should "match as many characters as possible for option" in {

    val re = "(a?)(.*)".re

    "aaaaaaaaaabaaa" match {
      case re(as, rest) =>
        as should be("a")
        rest should be("aaaaaaaaabaaa")
      case _ =>
        fail("input should match")
    }

  }

  "Non greedy operator" should "match as few characters as possible for star" in {

    val re = "(a*?)(.*)".re

    "aaaaaaaaaabaaa" match {
      case re(as, rest) =>
        as should be("")
        rest should be("aaaaaaaaaabaaa")
      case _ =>
        fail("input should match")
    }

  }

  it should "match as few characters as possible for plus" in {

    val re = "(a+?)(.*)".re

    "aaaaaaaaaabaaa" match {
      case re(as, rest) =>
        as should be("a")
        rest should be("aaaaaaaaabaaa")
      case _ =>
        fail("input should match")
    }

  }

  it should "match as few characters as possible for option" in {

    val re = "(a??)(.*)".re

    "aaaaaaaaaabaaa" match {
      case re(as, rest) =>
        as should be("")
        rest should be("aaaaaaaaaabaaa")
      case _ =>
        fail("input should match")
    }

  }

}


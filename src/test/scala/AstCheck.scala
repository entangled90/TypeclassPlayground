package com.example

import org.scalacheck.Shapeless._
import org.scalacheck._
import org.scalatest.{FunSuite, MustMatchers, PropSpecLike, WordSpecLike}
import org.scalatest.prop.{Checkers, PropertyChecks}
import org.scalacheck.Arbitrary._
import org.scalacheck.util.Pretty.Params
import org.scalatest.prop.Checkers._

import scala.util.control.NonFatal
import scala.util.{Failure, Success, Try}






class AstCheck extends PropertyChecks with Checkers with MustMatchers with WordSpecLike  {

  def notMaching(disney : Disney) = disney match {
    case x : Pippo =>
      ()
    case p : Pluto =>
      ()
  }

  val im = implicitly[Arbitrary[Disney]]


  def checkException(implicit arbitrary: Arbitrary[Disney]) = {
//    implicitly[Arbitrary[Scemo.type]]
    check(Prop.forAll{
      (d : Disney) => {
        Try{
          println(d)
          notMaching(d)
        }.recoverWith{
          case err : MatchError =>
            Failure(err)
          case _ =>
            Success(())
        }.isSuccess
        //          d.isInstanceOf[Pippo]
      }
    }, minSuccessful(200))
  }

  "test " should {
    "be ok " in {
      checkException
    }
  }

}




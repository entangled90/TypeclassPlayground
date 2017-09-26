//
//
import io.circe.Encoder
import shapeless._
//
//
sealed trait Disney
case class Pippo(name : String) extends Disney
case class Pluto(name : String) extends Disney
//
//
//def getDisney : Disney = Pippo("ciao")
//getDisney match {
//  case el : Disney =>
//    el.getClass.getCanonicalName
//
//}


type CP = String :+: Boolean :+: CNil
import io.circe.syntax._
//import  io.circe.generic.semiauto._

//implicit val disneyEncoder = deriveEncoder[CP]

val gen = LabelledGeneric[Disney]

gen

implicit def cnilToJson: Encoder[CNil] =
  Encoder[CNil](_ => null)


implicit def coproductEncoder[H,T<:Coproduct](implicit tEnc : Encoder[T], hEncoder : Encoder[H]) : Encoder[H :+: T] = {
  Encoder[H :+: T](t => t.eliminate( h => hEncoder.apply(h), t => tEnc.apply(t)))
}

val ciao : CP = Coproduct[CP]("ciao")
ciao.asJson
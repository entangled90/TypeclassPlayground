package json

import io.circe.Json
import shapeless.labelled.{FieldBuilder, FieldType}
import shapeless._
import simulacrum.{op, typeclass}

package object decoders {

//  sealed trait ValidationError
//  case class KeyMissing(key: String) extends ValidationError
//  case class CantParse(key : String, value : Json) extends ValidationError

  @typeclass
  trait JSReader[T] {
    @op("to") def parse(json: Json): Option[T]
  }

  implicit def intReader = JSReader(json => json.asNumber.flatMap(_.toInt))
  implicit def doubleReader = JSReader(json => json.asNumber.map(_.toDouble))
  implicit def stringReader = JSReader(json => json.asString)

  //TODO Ignores unparsable
  implicit def seqReader[T](
      implicit jSReader: JSReader[T]): JSReader[Seq[T]] =
    JSReader[Seq[T]](json => json.asArray.map(_.flatMap(jSReader.parse)))

  implicit def toLabelledJson[T, Rep](
      implicit lgen: LabelledGeneric.Aux[T, Rep],
      jSReader: Lazy[JSReader[Rep]]): JSReader[T] =
    JSReader(json => jSReader.value.parse(json).map(lgen.from))

  implicit def labelledJsReader[Key <: Symbol, Head, Tail <: HList](
      implicit key: Witness.Aux[Key],
      headReader: Lazy[JSReader[Head]],
      tailReader: Lazy[JSReader[Tail]]) : JSReader[FieldType[Key, Head] :: Tail] = JSReader[FieldType[Key,Head] :: Tail]{ json =>
      val hRes = (json \\ key.value.name).headOption.flatMap(headReader.value.parse)
      val tRes = tailReader.value.parse(json)
      for {
        h <- hRes
        t <- tRes
      } yield (new FieldBuilder[Key])(h) :: t
  }

  implicit def cnilReader : JSReader[HNil] = JSReader(_ => Some(HNil))


}

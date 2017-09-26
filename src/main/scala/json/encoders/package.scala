package json

import io.circe.Json
import shapeless.{
  :+:,
  ::,
  CNil,
  Coproduct,
  HList,
  HNil,
  LabelledGeneric,
  Lazy,
  Witness
}
import shapeless.labelled.FieldType
import simulacrum.{op, typeclass}

package object encoders {

  @typeclass
  trait JSWriter[-T] {
    @op("toJson") def write(t: T): Json
  }

  implicit val intWriter: JSWriter[Int] = JSWriter[Int](Json.fromInt)
  implicit val doubleWriter: JSWriter[Double] =
    JSWriter[Double](Json.fromDoubleOrNull)
  implicit val stringWriter: JSWriter[String] =
    JSWriter[String](Json.fromString)
  implicit def optionWriter[T](implicit wt: JSWriter[T]): JSWriter[Option[T]] =
    JSWriter[Option[T]](el => el.fold(Json.Null)(wt.write))

  implicit def seqWriter[T](implicit wt: JSWriter[T]): JSWriter[Seq[T]] =
    JSWriter[Seq[T]](seq => Json.fromValues(seq.map(wt.write(_))))

  implicit class ToJsonOps[T](t: T)(implicit jsonWrites: JSWriter[T]) {
    def toJson: Json = jsonWrites.write(t)
  }

  implicit def hnilToJson: JSWriter[HNil] = JSWriter[HNil](_ => Json.obj())

  implicit def hconsToJson[Key <: Symbol, Head, Tail <: HList](
      implicit key: Witness.Aux[Key],
      headWrites: JSWriter[Head],
      tailWrites: Lazy[JSWriter[Tail]]
  ): JSWriter[FieldType[Key, Head] :: Tail] =
    JSWriter[FieldType[Key, Head] :: Tail] { hlist =>
      val tWrites = tailWrites.value.write(hlist.tail)
      Json
        .obj(key.value.name -> headWrites.write(hlist.head))
        .deepMerge(tWrites)
    }

  implicit def cnilToJson: JSWriter[CNil] =
    JSWriter[CNil](_ => throw new RuntimeException("Arrived at Cnil"))

  implicit def copToJson[Key <: Symbol, Head, Tail <: Coproduct](
      implicit key: Witness.Aux[Key],
      headWrites: JSWriter[Head],
      tailWrites: Lazy[JSWriter[Tail]]
  ): JSWriter[FieldType[Key, Head] :+: Tail] =
    JSWriter[FieldType[Key, Head] :+: Tail](_.eliminate(head => {
      headWrites
        .write(head)
        .deepMerge(Json.obj("type" -> Json.fromString(key.value.name)))
    }, tail => tailWrites.value.write(tail)))

  implicit def lgenToJson[T, Repr](
      implicit lgen: LabelledGeneric.Aux[T, Repr],
      reprWrites: Lazy[JSWriter[Repr]]): JSWriter[T] =
    JSWriter[T](t => reprWrites.value.write(lgen.to(t)))

}

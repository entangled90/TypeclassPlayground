package csvencoder

import shapeless._

/**
  * Created by carlo on 01/03/17.
  */
package object generic {

  implicit def anyValEncoder[T <: AnyVal]: CsvEncoder[T] =
    CsvEncoder.createEncoder((t: T) => List(t.toString))

  implicit def stringEncoder: CsvEncoder[String] =
    CsvEncoder.createEncoder((s : String) =>  List(s))

  implicit val hnilEncoder: CsvEncoder[HNil] =
    CsvEncoder.createEncoder(_ => Nil)

//  implicit lazy val floatEncoder : CsvEncoder[Float] = CsvEncoder.createEncoder(f => List(f.toString))

  implicit def hlistEncoder[H, T <: HList](
      implicit hEncoder: CsvEncoder[H],
      tEncoder: CsvEncoder[T]): CsvEncoder[H :: T] =
    CsvEncoder.createEncoder {
      case h :: t =>
        hEncoder.encode(h) ++ tEncoder.encode(t)
    }

  implicit def genericEncoder[T, R](implicit gen: Generic.Aux[T, R],
                                    encoder: CsvEncoder[R]): CsvEncoder[T] = {
    CsvEncoder.createEncoder { t: T =>
      encoder.encode(gen.to(t))
    }
  }

}

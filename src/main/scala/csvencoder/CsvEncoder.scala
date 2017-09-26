package csvencoder

import simulacrum.{op, typeclass}
import scala.collection.immutable.Seq
/**
  * Created by carlo on 01/03/17.
  */
@typeclass
trait CsvEncoder[T] {
  @op("encode") def encode(t: T): Seq[String]
}

object CsvEncoder {
  def instance[T](implicit encoder: CsvEncoder[T]): CsvEncoder[T] = encoder

  def createEncoder[T](f: T => Seq[String]) = new CsvEncoder[T] {
    override def encode(t: T): Seq[String] = f(t)
  }
}

package csvencoder

/**
  * Created by carlo on 01/03/17.
  */
object CsvWriter {

  def write[T](t : Seq[T])(implicit csvEncoder: CsvEncoder[T]): Seq[String] = {
    t.map(el => csvEncoder.encode(el).mkString("\t"))
  }
}

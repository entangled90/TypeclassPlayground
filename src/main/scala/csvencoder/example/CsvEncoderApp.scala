package csvencoder.example

import scala.util.Random
import csvencoder.generic._
import csvencoder.{CsvEncoder, CsvWriter}
import shapeless._

/**
  * Created by carlo on 01/03/17.
  */
object CsvEncoderApp extends App{

  val rand = new Random()
  case class Geo(latitude : Float, longitude : Float)
  case class GeoName( city : String, nation : String)
  def createRandomGeo(n : Int)  = {
    for {
      _ <- 1 to n
    } yield Geo(rand.nextFloat(),rand.nextFloat())
  }
  def createRandomGeoName(n : Int)  = {
    for {
      _ <- 1 to n
    } yield GeoName(rand.nextString(5),rand.nextString(5))
  }
  val coordinates = createRandomGeoName(10)
  val names = createRandomGeoName(10)


  println(CsvWriter.write(coordinates).mkString("\n"))

  println(CsvWriter.write(names).mkString("\n"))
}

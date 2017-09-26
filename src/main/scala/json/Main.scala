package json

import io.circe.Json
import shapeless.{:+:, CNil, Coproduct, LabelledGeneric}

object Main extends App{

  import io.circe.parser._
  import encoders._
  import decoders._

  sealed trait Buildings
  case class House(street : String , number : Int, surname : Option[String]= None) extends Buildings
  case class Facility(name : String, houses : Seq[House])


  val houseJson = parse(House("via",11).toJson.noSpaces).right.get
  val facilityJson = parse(Facility("Ciao",List(House("via",11),House("pasoida",123))).toJson.noSpaces).right.get
//  implicitly[JSReader[House]].parse(houseJson)
  println(implicitly[JSWriter[Buildings]].write(House("v",1)))
  type CP = String :+: Boolean :+: CNil

  val generic = LabelledGeneric[Buildings]
  println(generic)
  val ciao : CP = Coproduct[CP]("ciao")
//  implicitly[JSWriter[CP]].write(ciao)



  val a = Option(1) match {
    case Some(i) => i


  }

  println(a)

  //  println(implicitly[JSReader[House]].parse(Json.fromValues(Seq(1,2,3).map(Json.fromInt))))




}

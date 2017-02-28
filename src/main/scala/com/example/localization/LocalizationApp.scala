package com.example.localization
import scala.language.implicitConversions

/**
  * Created by carlo on 28/02/17.
  */
object LocalizationApp extends App{


  import Implicits._

  val facility = Facility("Facility")
  val building = Building("Bulding 1",facility)
  val floor = Floor("Floor 1", building)
  val room1 = Room("Room 1", floor)
  val room2 = Room("Room 2", floor)

  println(floor.tree)
  println(room1.tree)
  println(room2.tree)
}

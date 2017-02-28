package com.example.localization

import com.example.localization.domain.building._
import com.example.localization.domain.building.details._

/**
  * Created by carlo on 28/02/17.
  */
object LocalizationApp extends App{

//  import Implicits._
  import Loc.ops._

  val facility = Facility("Facility")
  val building = Building("Bulding 1",facility)
  val floor = Floor("Floor 1", building)
  val room1 = Room("Room 1", floor)
  val room2 = Room("Room 2", floor)
  val cubicle = Cubicle(1,room1)
  val desk = Desk(1,cubicle)

  println(desk.loc)

}

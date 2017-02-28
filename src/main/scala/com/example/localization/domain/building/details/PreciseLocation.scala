package com.example.localization.domain.building.details

import com.example.localization.domain.building.Room

/**
  * Created by carlo on 28/02/17.
  */
sealed trait PreciseLocation {
  type Parent
  def name : String
  def parent : Parent
}

case class Cubicle(id : Long, parent : Room) extends PreciseLocation {
  override type Parent = Room
  def name = id.toString
}

case class Desk(id : Long, parent : Cubicle) extends PreciseLocation{
  override type Parent = Cubicle
  def name = id.toString
}



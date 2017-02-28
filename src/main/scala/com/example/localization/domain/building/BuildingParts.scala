package com.example.localization.domain.building

/**
  * Created by carlo on 28/02/17.
  */
sealed trait BuildingParts{
  type Parent
  def name: String
  def parent: Parent
}
case class Building(name: String, parent: Facility)
  extends BuildingParts{
  type Parent = Facility
}
case class Floor(name: String, parent: Building)
  extends BuildingParts{
  override type Parent = Building
}
case class Room(name: String, parent: Floor)
  extends BuildingParts{
  override type Parent = Floor
}

case class Facility(city: String)


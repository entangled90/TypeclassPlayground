package com.example.localization

import cats.data.NonEmptyList
import shapeless.Lazy

/**
  * Created by carlo on 28/02/17.
  */
case class Location(name: String)

trait Localizable[T] {
  def locationTree(t: T): NonEmptyList[Location]
}

sealed trait PartOfBuilding[Parent] {
  def name: String
  def parent: Parent
}
case class Building(name: String, parent: Facility)
    extends PartOfBuilding[Facility]
case class Floor(name: String, parent: Building)
    extends PartOfBuilding[Building]
case class Room(name: String, parent: Floor)
    extends PartOfBuilding[Floor]

case class Facility(city: String)

object Implicits {

  import shapeless.Lazy._

  implicit object facilityLocalizable extends Localizable[Facility] {
    override def locationTree(t: Facility) =
      NonEmptyList(Location(t.city), Nil)
  }

  implicit def buildingLocalizable[P, T <: PartOfBuilding[P]](implicit localizableParent : Lazy[Localizable[P]])
    : Localizable[T] = { (t: T) => {
      val parentTree =
        localizableParent.value.locationTree(t.parent).toList
      NonEmptyList(Location(t.name), parentTree)
    }
  }

  trait LocalizableOps[T] {
    val value: T
    val typeclass: Localizable[T]
    def tree: NonEmptyList[Location] = typeclass.locationTree(value)
  }

  implicit def localizableOps[T](t: T)(implicit localizable: Lazy[Localizable[T]]): LocalizableOps[T] = {
    new LocalizableOps[T] {
      val value: T = t
      override val typeclass: Localizable[T] = localizable.value
    }
  }

}

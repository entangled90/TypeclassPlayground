package com.example.localization

import cats.data.NonEmptyList
import com.example.localization.domain.building.{BuildingParts, Facility}

/**
  * Created by carlo on 28/02/17.
  */
case class Location(name: String)

trait Localizable[T] {
  def locationTree(t: T): NonEmptyList[Location]
}
object Implicits {

  implicit object facilityLocalizable extends Localizable[Facility] {
    override def locationTree(t: Facility) =
      NonEmptyList(Location(t.city), Nil)
  }

  // Not an implicit conversion!
  implicit def buildingPartLocalizable[_, T <: BuildingParts](implicit localizableParent : Localizable[T#Parent])
    : Localizable[T] = { (t: T) => {
      val parentTree =
        localizableParent.locationTree(t.parent).toList
      NonEmptyList(Location(t.name), parentTree)
    }
  }

  trait LocalizableOps[T] {
    val value: T
    val typeclass: Localizable[T]
    def tree: NonEmptyList[Location] = typeclass.locationTree(value)
  }

  implicit def localizableOps[T](t: T)(implicit localizable: Localizable[T]): LocalizableOps[T] = {
    new LocalizableOps[T] {
      val value: T = t
      override val typeclass: Localizable[T] = localizable
    }
  }

}

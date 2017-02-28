package com.example.localization.domain

import cats.data.NonEmptyList
import com.example.localization.Loc

/**
  * Created by carlo on 28/02/17.
  */
package object building {

  implicit object facilityLocalizable extends Loc[Facility] {
    override def loc(t: Facility) =
      NonEmptyList(t.city, Nil)
  }

  implicit def buildingPartLocalizable[_, T <: BuildingParts](
      implicit localizableParent: Loc[T#Parent]): Loc[T] = {
    (t: T) =>
      {
        val parentTree =
          localizableParent.loc(t.parent).toList
        NonEmptyList(t.name, parentTree)
      }
  }

}

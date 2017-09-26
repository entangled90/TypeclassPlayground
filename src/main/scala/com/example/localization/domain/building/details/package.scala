package com.example.localization.domain.building

import cats.data.NonEmptyList
import com.example.localization.Loc

/**
  * Created by carlo on 28/02/17.
  */
package object details {

  implicit def preciseLocalizable[_, T <: PreciseLocation](
      implicit localizableParent: Loc[T#Parent]): Loc[T] = {
    (t: T) =>
      {
        val parentTree =
          localizableParent.loc(t.parent).toList
        NonEmptyList(t.name, parentTree)
      }
  }

}

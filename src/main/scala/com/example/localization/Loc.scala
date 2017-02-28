package com.example.localization

import cats.data.NonEmptyList
import simulacrum.{op, typeclass}

/**
  * Created by carlo on 28/02/17.
  */
@typeclass
trait Loc[T] {
  @op("loc") def loc(t: T): NonEmptyList[String]
}
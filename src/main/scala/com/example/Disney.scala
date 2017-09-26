package com.example

sealed trait Disney
case class Pippo(name : String ) extends Disney
case class Pluto( age : Int , rel : Relationship) extends Disney
case object Scemo extends Disney


case class Relationship( name : Some[String])


package services

import models.Location

object CostumerLocation extends CostumerLocation
class CostumerLocation {

  def getLocation(id: Int): Location.Value =
    id match {
      case -1             => Location.everywhere
      case i if isEven(i) => Location.london
      case i if isOdd(i)  => Location.liverpool
    }

  def isEven(number: Int) = number % 2 == 0
  def isOdd(number: Int) = !isEven(number)
}
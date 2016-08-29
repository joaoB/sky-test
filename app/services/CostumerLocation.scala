package services

import models.Location

object CostumerLocation extends CostumerLocation
class CostumerLocation {

  def getLocation(id: Int): Option[Location.Value] =
    id match {
      case i if isEven(i) => Some(Location.london)
      case i if isOdd(i)  => Some(Location.liverpool)
      case _              => None //Failure Exception
    }

  private def isEven(number: Int) = number % 2 == 0
  private def isOdd(number: Int) = !isEven(number)
}
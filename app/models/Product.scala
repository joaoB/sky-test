package models

import play.api.libs.json._

object EnumUtils {
  def enumReads[E <: Enumeration](enum: E): Reads[E#Value] = new Reads[E#Value] {
    def reads(json: JsValue): JsResult[E#Value] = json match {
      case JsString(s) => {
        try {
          JsSuccess(enum.withName(s))
        } catch {
          case _: NoSuchElementException => JsError(s"Enumeration expected of type: '${enum.getClass}', but it does not appear to contain the value: '$s'")
        }
      }
      case _ => JsError("String value expected")
    }
  }
}

object Category extends Enumeration {
  type Category = Value
  val sports = Value("sports")
  val news = Value("news")
  implicit val myEnumReads: Reads[Category.Value] = EnumUtils.enumReads(Category)
}

object Location extends Enumeration {
  type Location = Value
  val london = Value("london")
  val liverpool = Value("liverpool")
  val everywhere = Value("everywhere")
  implicit val myEnumReads: Reads[Location.Value] = EnumUtils.enumReads(Location)
}

case class Product(id: Long, name: String, category: Category.Value, location: Location.Value)

object Product {
  implicit val productFormat = Json.format[Product]
}
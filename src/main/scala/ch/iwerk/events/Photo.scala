package ch.iwerk.events

case class Photo(
  cote: String,
  collection: String,
  support: String,
  dimensions: String,
  technique: String,
  conservation: String,
  condition: String,
  titre: String,
  theme: String,
  description: String,
  pays: String,
  sub_continent: String,
  continent: String,
  region: String,
  population: String,
  datation: String,
  auteur: String,
  copyright: String,
  mention: String,
  date_acq: String,
  source_acq: String,
  type_acq: String,
  lot: String,
  album: String,
  planche: String,
  fusion: String
                   )
{
  import Photo._

  override def toString: String =
    upickle.default.write(this)
}



object Photo {
  implicit val photoRW: upickle.default.ReadWriter[Photo] = upickle.default.macroRW
}

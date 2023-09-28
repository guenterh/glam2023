package ch.iwerk.events

case class ZBReiseberichte(
                          line: String
                          ) {

  import ZBReiseberichte._
  override def toString: String =
    line
}

object ZBReiseberichte {
  //implicit val zBReiseberichte: upickle.default.ReadWriter[ZBReiseberichte] = upickle.default.macroRW
}

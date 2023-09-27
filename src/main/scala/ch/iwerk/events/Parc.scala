package ch.iwerk.events

case class Parc(

               wholeLine: String

               )  {
  override def toString: String =
    //s"""
    //  |the whole line ${wholeLine}
    //  |""".stripMargin
    wholeLine
}

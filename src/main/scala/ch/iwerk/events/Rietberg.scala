package ch.iwerk.events

//"Inv_Nr","Titel","UrheberIn","Datierung","Geografische_Referenz_ALT","Kultur_ALT","Geografische_Bezuge_NEU",
// "Stil_Kultur_NEU","Material","Creditline","Schlagworte","Linked_Objects","Link_to_Foto"

case class Rietberg(Inv_Nr: String,


                    Titel: String,
                    UrheberIn: String,
                    Datierung: String,
                    Geografische_Referenz_ALT: String,
                    Kultur_ALT: String,
                    Geografische_Bezuge_NEU: String,
                    Stil_Kultur_NEU: String,
                    Material: String,
                    Creditline: String,
                    Schlagworte: String,
                    Linked_Objects: String,
                    Link_to_Foto: String

                   ) {
  import Rietberg._


  override def toString: String = {

    /*
    s"""
       |Inv_Nr: $Inv_Nr
       |Titel: $Titel
       |UrheberIn $UrheberIn
       |Datierung: $Datierung
       |GeografischeReferenz Alt: $Geografische_Referenz_ALT
       |Kultur Alt: $Kultur_ALT
       |Geografische Bezuege Neu: $Geografische_Bezuge_NEU
       |Material: $Material
       |CreditLine: $Creditline
       |Schlagworte: $Schlagworte
       |Linked_Objects: $Linked_Objects
       |Link_to_Foto: $Link_to_Foto
       |""".stripMargin
      */
      upickle.default.write(this)

  }
}

object Rietberg {
  implicit val rietbergRW: upickle.default.ReadWriter[Rietberg] = upickle.default.macroRW
}



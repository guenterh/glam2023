package ch.iwerk.events

/*
`audio` (^M
        `cote_concat` varchar (225),^M
        `format` varchar (90),^M
        `continent` varchar (60),^M
        `sub_continent` varchar (225),^M
        `pays` varchar (765),^M
        `autres_pays` varchar (765),^M
        `region` varchar (765),^M
        `localite` varchar (765),^M
        `ethnie` varchar (765),^M
        `titre` varchar (765),^M
        `sous_titre` varchar (765),^M
        `traduction` varchar (765),^M
        `interprete` varchar (765),^M
        `style` varchar (765),^M
        `instrument` varchar (765),^M
        `collection` varchar (765),^M
        `production` varchar (765),^M
        `annee_production` varchar (225),^M
        `lieu` varchar (765),^M
        `auteur` varchar (765),^M
        `langue` varchar (765),^M
        `photos` varchar (90),^M
        `pages` varchar (90),^M
        `collectage` varchar (765),^M
        `ancien_no` varchar (90),^M
        `edition` varchar (765),^M
        `annee_edition` varchar (60),^M
        `no_edition` varchar (765),^M
        `lieu_edition` varchar (765),^M
        `fusion_central` blob ,^M
        `fusion_plages` blob ^M
 */

case class Audio(

                  cote_concat: String,
                  format: String,
                  continent: String,
                  sub_continent: String,
                  pays: String,
                  autres_pays: String,
                  region: String,
                  localite: String,
                  ethnie: String,
                  titre: String,
                  sous_titre: String,
                  traduction: String,
                  interprete: String,
                  style: String,
                  instrument: String,
                  collection: String,
                  production: String,
                  annee_production: String,
                  lieu: String,
                  auteur: String,
                  langue: String,
                  photos: String,
                  pages: String,
                  collectage: String,
                  ancien_no: String,
                  edition: String,
                  annee_edition: String,
                  no_edition: String,
                  lieu_edition: String,
                  fusion_central: String,
                  fusion_plages: String

                ){
  import Audio._

  override def toString: String =
    upickle.default.write(this)
}


object Audio {
  implicit val audioRW: upickle.default.ReadWriter[Audio] = upickle.default.macroRW
}

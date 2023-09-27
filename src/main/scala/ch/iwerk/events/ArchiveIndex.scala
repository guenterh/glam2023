package ch.iwerk.events

//Cote Date No Correspondant Localisation Fonction "No d'Index" "Fichier image" Sens Remarques

case class ArchiveIndex(
                      /*
                       Cote: String = "",
                       Date: String = "",
                       No: String = "",
                       Correspondant: String = "",
                       Localisation: String = "",
                       Fonction: String = "",
                       No_d_Index: String = "",
                       Fichier_image: String = "",
                       Sens: String = "",
                       Remarques: String = ""

                      */
                      line: String
                       ) {
  override def toString: String = line
}

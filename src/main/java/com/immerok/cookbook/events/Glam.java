package com.immerok.cookbook.events;

import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Objects;

@JsonPropertyOrder({"cote", "collection", "support", "dimensions", "technique", "conservation", "condition",
        "titre", "theme", "description", "pays","sub_continent", "continent", "region", "population", "datation", "auteur", "copyright", "mention",
        "date_acq", "source_acq", "type_acq", "lot", "album", "planche", "fusion"})
public class Glam {


    public String cote;
    public String collection;
    public String support;
    public String dimensions;
    public String technique;
    public String conservation;
    public String condition;
    public String titre;
    public String theme;
    public String description;
    public String pays;
    public String sub_continent;
    public String continent;
    public String region;
    public String population;
    public String datation;


    public String auteur;
    public String copyright;
    public String mention;
    public String date_acq;
    public String source_acq;

    public String type_acq;
    public String lot;
    public String album;
    public String planche;
    public String fusion;

    public Glam() {}

     //                                          continent       region  population      datation        auteur  copyright       mention date_acq        source_acq      type_acq        lot     album   planche fusion
    public Glam(
                    final String cote,
                    final String collection,
                    final String support,
                    final String dimensions,
                    final String technique,
                    final String conservation,
                    final String condition,
                    final String titre,
                    final String theme,
                    final String description,
                    final String pays,
                    final String sub_continent,

                    final String continent,
                    final String region,
                    final String population,
                    final String datation,
                    final String auteur,
                    final String copyright,
                    final String mention,
                    final String date_acq,
                    final String source_acq,
                    final String type_acq,
                    final String lot,
                    final String album,
                    final String planche,
                    final String fusion
    ) {
        this.cote = cote;
        this.collection = collection;
        this.support = support;
        this.dimensions = dimensions;
        this.technique = technique;
        this.conservation = conservation;
        this.condition = condition;
        this.titre = titre;
        this.theme = theme;
        this.description = description;
        this.pays = pays;
        this.sub_continent = sub_continent;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.datation = datation;
        this.auteur = auteur;
        this.copyright = copyright;
        this.mention = mention;
        this.date_acq = date_acq;
        this.source_acq = source_acq;
        this.type_acq = type_acq;
        this.lot = lot;
        this.album = album;
        this.planche = planche;
        this.fusion = fusion;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cote, collection, support, dimensions, technique, conservation, condition,
                titre, theme, description, pays,sub_continent, continent, region, population, datation, auteur, copyright, mention,
                date_acq, source_acq, type_acq, lot, album, planche, fusion);
    }

    @Override
    public String toString() {
        return "Glam Event(\n"
                + "cote: " + cote + "\n" +
                 "collection: " + collection + "\n" +
                 "support: " + support + "\n" +
                 "dimensions: " + dimensions + "\n" +
                 "technique: " + technique + "\n" +
                 "conservation: " + conservation + "\n" +
                 "condition: " + condition + "\n" +
                 "titre: " + titre + "\n" +
                 "theme: " + theme + "\n" +


                "description: " + description + "\n" +
                "pays: " + pays + "\n" +
                "sub_continent: " + sub_continent + "\n" +
                "continent: " + continent + "\n" +
                "region: " + region + "\n" +
                "population: " + population + "\n" +
                "datation: " + datation + "\n" +
                "auteur: " + auteur + "\n" +
                "copyright: " + copyright + "\n" +
                "mention: " + mention + "\n" +

                "date_acq: " + date_acq + "\n" +
                "source_acq: " + source_acq + "\n" +
                "type_acq: " + type_acq + "\n" +
                "lot: " + lot + "\n" +
                "album: " + album + "\n" +
                "planche: " + planche + "\n" +
                "fusion: " + fusion + "\n" +
                ")\n";
    }

}

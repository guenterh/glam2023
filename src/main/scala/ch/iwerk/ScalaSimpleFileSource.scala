package ch.iwerk

import ch.iwerk.events.{ArchiveIndex, Audio, Parc, Photo, Rietberg}
import ch.iwerk.serializer.{ArchiveIndexSerializer, AudioSerializer, ParcSerializer, PhotoSerializer, RietbergSerializer}
import ch.iwerk.sink.{CustomKafkaStringSinkBuilder, GenericCustomSinkBuilder}
import com.immerok.cookbook.events.Glam
import org.apache.flink.api._
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.functions.{FlatMapFunction, MapFunction}
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.configuration.Configuration
import org.apache.flink.connector.file.src.impl.StreamFormatAdapter
import org.apache.flink.connector.file.src.reader.{BulkFormat, StreamFormat, TextLineInputFormat}
import org.apache.flink.connector.file.src.{FileSource, FileSourceSplit}
import org.apache.flink.core.fs.{FSDataInputStream, Path}
import org.apache.flink.formats.csv.CsvReaderFormat
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.apache.flink.streaming.api.functions.sink.PrintSink
import org.apache.flink.util.Collector

import _root_.java.time.Duration
import _root_.java.time.temporal.ChronoUnit.SECONDS
import scala.util.matching.Regex
import ujson._

object ScalaSimpleFileSource {


  /*
  given photoSerializer: TypeInformation[Photo] = TypeInformation.of(classOf[Photo])
  given audioSerializer: TypeInformation[Audio] = TypeInformation.of(classOf[Audio])
  given rietBergSerializer: TypeInformation[Rietberg] = TypeInformation.of(classOf[Rietberg])
  given parcSerializer: TypeInformation[Parc] = TypeInformation.of(classOf[Parc])
  given archiveIndexSerializer: TypeInformation[ArchiveIndex] = TypeInformation.of(classOf[ArchiveIndex])

  given stringSerializer: TypeInformation[String] = TypeInformation.of(classOf[String])


   */

  implicit val photoSerializer: TypeInformation[Photo]= TypeInformation.of(classOf[Photo])
  implicit val audioSerializer: TypeInformation[Audio] = TypeInformation.of(classOf[Audio])
  implicit val rietBergSerializer: TypeInformation[Rietberg] = TypeInformation.of(classOf[Rietberg])
  implicit val parcSerializer: TypeInformation[Parc] = TypeInformation.of(classOf[Parc])
  implicit val archiveIndexSerializer: TypeInformation[ArchiveIndex] = TypeInformation.of(classOf[ArchiveIndex])

  implicit val stringSerializer: TypeInformation[String] = TypeInformation.of(classOf[String])


  def runJob(): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.BATCH)

    val photoDataDirectory = new Path("/home/swissbib/environment/code/repositories/guenterh/glam/2023genf/meg/photographic_collections/tsv")
    val audioDataDirectory = new Path("/home/swissbib/environment/code/repositories/guenterh/glam/2023genf/meg/audio_archives/tsv")
    val rietbergDataDirectory = new Path("/home/swissbib/environment/code/repositories/guenterh/glam/2023genf/rietberg/objects")
    val parcDataDirectory = new Path("/home/swissbib/environment/code/repositories/guenterh/glam/2023genf/parc/metadata/jsononeline")
    val archiveIndexDataDirectory = new Path("/home/swissbib/environment/code/repositories/guenterh/glam/2023genf/archive_de_geneve/index_letters/index")

    val sourcePhoto: FileSource [String]  =
      FileSource.forRecordStreamFormat(new TextLineInputFormat, photoDataDirectory)
      //FileSource.forBulkFileFormat(new StringReaderFormat, dataDirectory)
        //.monitorContinuously(Duration.of(5, SECONDS))
        .build()


    val sourceAudio: FileSource[String] =
      FileSource.forRecordStreamFormat(new TextLineInputFormat, audioDataDirectory)
        .build()

    //https://itecnote.com/tecnote/java-custom-implementation-of-inputstream/

    //implicit val glamSerializer: TypeInformation[Glam] = TypeInformation.of(classOf[Glam])
    val dSPhoto = env.fromSource(sourcePhoto, WatermarkStrategy.noWatermarks(), "Photo")
      .map[Photo] {

        str =>
        val splits = str.split("\t")
        val g =   Photo(
            splits(0),
            splits(1),
            splits(2),
            splits(3),
            splits(4),
            splits(5),
            splits(6),
            splits(7),
            splits(8),
            splits(9),
            splits(10),
            splits(11),
            splits(12),
            splits(13),
            splits(14),
            splits(15),
            splits(16),
            splits(17),
            splits(18),
            splits(19),
            splits(20),
            splits(21),
            splits(22),
            splits(23),
            splits(24),
          splits(25)

          )
          g

      }

    //dSPhoto.sinkTo(new PrintSink[Photo]())

    val dsAudio: DataStream[Audio] = env.fromSource(sourceAudio, WatermarkStrategy.noWatermarks(), "Audio")
      .flatMap ((str: String, out: Collector[Audio]) => {
          val splits = str.split("\t")

          val g = Audio(
            splits(0),
            splits(1),
            splits(2),
            splits(3),
            splits(4),
            splits(5),
            splits(6),
            splits(7),
            splits(8),
            splits(9),
            splits(10),
            splits(11),
            splits(12),
            splits(13),
            splits(14),
            splits(15),
            splits(16),
            splits(17),
            splits(18),
            splits(19),
            splits(20),
            splits(21),
            splits(22),
            splits(23),
            splits(24),
            splits(25),
            splits(26),
            splits(27),
            splits(28),
            splits(29),
            splits(29), //hier stimmt der Index noch nicht
          )
          out.collect(g)


      })

    val sourceRietberg: FileSource[String] =
      FileSource.forRecordStreamFormat(new TextLineInputFormat, rietbergDataDirectory)
        .build()


    val rmQuotes : String => String = str => {
      if (str.nonEmpty && str.substring(0,1) == """"""" && str.substring(str.length -1 ) == """"""") {
        str.dropRight(1).drop(1)
      } else {
        str
      }
    }

    val dsRietberg = env.fromSource(sourceRietberg, WatermarkStrategy.noWatermarks(),
        "Rietberg")
      .flatMap(new FlatMapFunction[String, Rietberg] {
      //.flatMap((value: String, out: Collector[Rietberg]) =>  {
        override def flatMap(value: String, out: Collector[Rietberg]): Unit = {
          val reg = """"(.*?)"""".r

          val elements = reg.findAllIn(value)

          elements.toList match {
            case invnr :: titel :: urherberin :: datierung :: geografische_ref_alt :: kultur_alt :: geografische_bezuege_alt ::
              stil_kultur_neu :: material :: creditline :: schlagworte :: linkedobjects :: linktofoto :: Nil
            =>
              out.collect(Rietberg(rmQuotes(invnr), rmQuotes(titel), rmQuotes(urherberin), rmQuotes(datierung),
                rmQuotes(geografische_ref_alt), rmQuotes(kultur_alt), rmQuotes(geografische_bezuege_alt),
                rmQuotes(stil_kultur_neu), rmQuotes(material), rmQuotes(creditline), rmQuotes(schlagworte),
                rmQuotes(linkedobjects), rmQuotes(linktofoto)))


            case _ =>
              //List.empty[Rietberg]
          }
        }
      })
      //.sinkTo(new PrintSink[Rietberg]())


    val sourceParc: FileSource[String] =
      FileSource.forRecordStreamFormat(new TextLineInputFormat, parcDataDirectory)
        .build()

    val dsParc = env.fromSource(sourceParc, WatermarkStrategy.noWatermarks(), "Parc")
      .flatMap((str: String, out: Collector[Parc]) => out.collect(Parc(str)))


    val sourceArchiveIndex: FileSource[String] =
      FileSource.forRecordStreamFormat(new TextLineInputFormat, archiveIndexDataDirectory)
        .build()

    val dsArchiveIndex = env.fromSource(sourceArchiveIndex, WatermarkStrategy.noWatermarks(), "ArchiveIndex")
      .flatMap((str: String, out: Collector[ArchiveIndex]) => {

        //(str: String) =>

          //var splits = str.split("\t")
          //var elements = splits.toList

          /*
          val reg = """"(.*?)"""".r
          val elements = reg.findAllIn(str)

          //val json = ujson.read(str)
          //val key = json.obj.keys.toList.mkString(" ")

          val index = elements.toList match
            case cote :: date :: no :: correspondant :: localisation :: fonction ::
              no_d_index :: fichier_image :: sens :: remarques :: Nil =>
              List(ArchiveIndex())
            case all =>
              List.empty[ArchiveIndex]
          */
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
          //val s = str
          //println(s)

          out.collect(ArchiveIndex(str))
      })


    //dsAudio.sinkTo(new PrintSink[Audio]())
    //dSPhoto.sinkTo(new PrintSink[Photo]())

    //dsRietberg.sinkTo(new PrintSink[Rietberg]())
    //dsParc.sinkTo(CustomKafkaSinkBuilder.apply("parc"))
    dsParc.sinkTo(GenericCustomSinkBuilder("parc_typed")(() => new ParcSerializer))
    //dsArchiveIndex.sinkTo(new PrintSink[ArchiveIndex]())
    dsArchiveIndex.sinkTo(GenericCustomSinkBuilder("archive_index_typed")(() => new ArchiveIndexSerializer))
    dsRietberg.sinkTo(GenericCustomSinkBuilder("rietberg_typed")(() => new RietbergSerializer))
    dsAudio.sinkTo(GenericCustomSinkBuilder("audio_typed")(() => new AudioSerializer))
    dSPhoto.sinkTo(GenericCustomSinkBuilder("photo_typed")(() => new PhotoSerializer))

    //dsParc.sinkTo(new PrintSink[Parc]())
    //dsRietberg.sinkTo(new PrintSink[Rietberg]())


    env.execute()

    ()
  }

  def main(args: Array[String]): Unit = {
    runJob()
  }

}



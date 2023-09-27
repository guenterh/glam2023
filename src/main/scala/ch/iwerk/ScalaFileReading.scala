package ch.iwerk

import com.immerok.cookbook.events.{Glam, OneColumn}
import org.apache.flink.api._
import org.apache.flink.api.common.RuntimeExecutionMode
import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.connector.file.src.{FileSource, FileSourceSplit}
import org.apache.flink.connector.file.src.impl.StreamFormatAdapter
import org.apache.flink.connector.file.src.reader.BulkFormat
import org.apache.flink.core.fs.Path
import org.apache.flink.formats.csv.CsvReaderFormat
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.csv.CsvSchema
import org.apache.flink.streaming.api.functions.sink.PrintSink

import _root_.java.time.Duration
import _root_.java.time.temporal.ChronoUnit.SECONDS

object ScalaFileReading {


  def runJob(): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    //env.setRuntimeMode(RuntimeExecutionMode.BATCH)

    val dataDirectory = new Path("/home/swissbib/environment/code/repositories/guenterh/glam/2023genf/daten/photo")
    val csvFormat: CsvReaderFormat[Glam] = CsvReaderFormat.forSchema(
      CsvSchema.builder()
        .setColumnSeparator('\t')

        //.addColumn(new CsvSchema.Column(0, "column", CsvSchema.ColumnType.STRING))

        .addColumn(new CsvSchema.Column(0, "cote", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(1, "collection", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(2, "support", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(3, "dimensions", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(4, "technique", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(5, "conservation", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(6, "condition", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(7, "titre", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "theme", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "description", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "pays", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "sub_continent", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "continent", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "region", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "population", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "datation", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "auteur", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "copyright", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "mention", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "date_acq", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "source_acq", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "type_acq", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "lot", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "album", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "planche", CsvSchema.ColumnType.STRING))
        .addColumn(new CsvSchema.Column(8, "fusion", CsvSchema.ColumnType.STRING))


        .build(),
      TypeInformation.of(classOf[Glam])
    ).withIgnoreParseErrors();

    val bulkFormat: BulkFormat[Glam, FileSourceSplit ] =
      new StreamFormatAdapter(csvFormat)


    val source: FileSource [Glam]  =
      FileSource.forRecordStreamFormat(csvFormat, dataDirectory)
      //FileSource.forBulkFileFormat(bulkFormat, dataDirectory)
        .monitorContinuously(Duration.of(5, SECONDS))
        .build();

    implicit val columnSerializer: TypeInformation[Glam] = TypeInformation.of(classOf[Glam])
    val dS = env.fromSource(source, WatermarkStrategy.noWatermarks(), "File")
    dS.sinkTo(new PrintSink[Glam]())

    env.execute()

    ()
  }

  def main(args: Array[String]): Unit = {
    runJob()
  }

}

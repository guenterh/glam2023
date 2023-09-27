package com.immerok.cookbook;

import static java.time.temporal.ChronoUnit.SECONDS;

import com.immerok.cookbook.events.Event;
import java.time.Duration;
import java.util.function.Consumer;

import com.immerok.cookbook.events.Glam;
import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.connector.file.src.FileSource;
import org.apache.flink.connector.file.src.FileSourceSplit;
import org.apache.flink.connector.file.src.impl.StreamFormatAdapter;
import org.apache.flink.connector.file.src.reader.BulkFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.formats.csv.CsvReaderFormat;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSink;

public class ContinuousFileReading {

    public static void main(String[] args) throws Exception {
        final ParameterTool parameters = ParameterTool.fromArgs(args);

        //Path inputFolder = new Path(parameters.getRequired("inputFolder"));
        Path inputFolder = new Path("/home/swissbib/environment/code/repositories/guenterh/glam/2023genf/daten/photo");

        runJob(inputFolder);
    }

    static void runJob(Path dataDirectory) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.BATCH);

        defineWorkflow(env, dataDirectory, workflow -> workflow.sinkTo(new PrintSink<>()));
        env.execute();
    }

    static void defineWorkflow(
            StreamExecutionEnvironment env,
            Path dataDirectory,
            Consumer<DataStream<Glam>> sinkApplier) {
        //CsvReaderFormat<Glam> csvFormat = CsvReaderFormat.forPojo(Glam.class);
        CsvReaderFormat<Glam> csvFormat = CsvReaderFormat.forSchema(
                CsvSchema.builder()
                        .setColumnSeparator('\t')

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
                TypeInformation.of(Glam.class)
        ).withIgnoreParseErrors();

       BulkFormat<Glam, FileSourceSplit> bulkFormat =
                new StreamFormatAdapter<>(csvFormat);



        FileSource<Glam> source =
                FileSource.forRecordStreamFormat(csvFormat, dataDirectory)
                //FileSource.forBulkFileFormat(bulkFormat, dataDirectory)
                        .monitorContinuously(Duration.of(5, SECONDS))
                        .build();

        final DataStreamSource<Glam> file =
                env.fromSource(source, WatermarkStrategy.noWatermarks(), "File");

        // additional workflow steps go here

        sinkApplier.accept(file);
    }
}

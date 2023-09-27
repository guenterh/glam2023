package ch.iwerk.sink

import ch.iwerk.events.Parc
import ch.iwerk.serializer.ParcSerializer
import org.apache.flink.connector.kafka.source.KafkaSource
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer

object KafkaSourceParcBuilder {

  def apply() = {
    val source = KafkaSource.builder[Parc]()
      .setBootstrapServers("localhost:9092;localhost:9093;localhost:9094")
      .setGroupId("my-group")
      .setStartingOffsets(OffsetsInitializer.earliest())
      .setValueOnlyDeserializer(new ParcSerializer())
      .setTopics("parc")
      .build()

    source

  }

}

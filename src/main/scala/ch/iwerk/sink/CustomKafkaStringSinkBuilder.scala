package ch.iwerk.sink

import org.apache.flink.api.common.serialization.{DeserializationSchema, SimpleStringSchema}
import org.apache.flink.connector.base.DeliveryGuarantee
import org.apache.flink.connector.kafka.sink.{KafkaRecordSerializationSchema, KafkaSink}

import java.util.Properties

object CustomKafkaStringSinkBuilder {

  def apply (topic: String): KafkaSink[String] = {
    //val s:SimpleStringSchema = ???

    val producerProperties = new Properties()
    producerProperties.setProperty ("transaction.timeout.ms", "60000")


    val serializer = KafkaRecordSerializationSchema.builder[String]()
        .setValueSerializationSchema (new SimpleStringSchema())
        .setTopic (topic)
        .build()


      val  sink = KafkaSink.builder[String]()
        .setBootstrapServers ("localhost:9092;localhost:9093;localhost:9094")
        .setDeliveryGuarantee (DeliveryGuarantee.AT_LEAST_ONCE)
        .setTransactionalIdPrefix ("StringSinkBuilder")
        .setKafkaProducerConfig (producerProperties)
        .setRecordSerializer (serializer)
        .build()

      sink

  }


}

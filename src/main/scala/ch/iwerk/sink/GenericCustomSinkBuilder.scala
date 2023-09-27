package ch.iwerk.sink

import org.apache.flink.api.common.serialization.{DeserializationSchema, SerializationSchema, SimpleStringSchema}
import org.apache.flink.connector.base.DeliveryGuarantee
import org.apache.flink.connector.kafka.sink.{KafkaRecordSerializationSchema, KafkaSink}

import java.util.Properties

object GenericCustomSinkBuilder {

  def apply[T](topic: String)(serializerSchema: () => SerializationSchema[T]): KafkaSink[T] = {
    //val s:SimpleStringSchema = ???

    val producerProperties = new Properties()
    producerProperties.setProperty("transaction.timeout.ms", "60000")


    val serializer = KafkaRecordSerializationSchema.builder[T]()
      .setValueSerializationSchema(serializerSchema())
      .setTopic(topic)
      .build()


    val sink = KafkaSink.builder[T]()
      .setBootstrapServers("localhost:9092;localhost:9093;localhost:9094")
      .setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE)
      .setTransactionalIdPrefix("KafkaExactl")
      .setKafkaProducerConfig(producerProperties)
      .setRecordSerializer(serializer)
      .build()

    sink



  }


}

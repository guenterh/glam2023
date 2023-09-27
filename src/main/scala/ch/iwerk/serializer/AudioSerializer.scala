package ch.iwerk.serializer

import ch.iwerk.events.{Audio, Parc}
import org.apache.flink.api.common.serialization.{DeserializationSchema, SerializationSchema}
import org.apache.flink.api.common.typeinfo.TypeInformation





class AudioSerializer extends SerializationSchema[Audio] with DeserializationSchema[Audio] with Serializable {

  import AudioSerializer._

  override def serialize(element: Audio): Array[Byte] = {
    upickle.default.writeBinary(element)

    /*


    val stream: ByteArrayOutputStream  = new ByteArrayOutputStream()
    val oos = new ObjectOutputStream(stream)
    oos.writeObject(element)
    oos.close()

    val by = stream.toByteArray
    stream.close()
    by
    */
  }

  override def deserialize(message: Array[Byte]): Audio = {

    upickle.default.readBinary(message)

    /*

    val bis = new ByteArrayInputStream(message)
    var in:ObjectInput = null
    var o: AnyRef = null
    try {
      in = new ObjectInputStream(bis)
      o = in.readObject()
    } finally {
      if (in != null) {
        in.close()
      }
    }

    o.asInstanceOf[Parc]


     */
  }

  override def isEndOfStream(nextElement: Audio): Boolean = false

  override def getProducedType: TypeInformation[Audio] = TypeInformation.of(classOf[Audio])
}

object AudioSerializer {
  implicit val audioSerializer: upickle.default.ReadWriter[Audio] = upickle.default.macroRW[Audio]
}

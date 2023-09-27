package ch.iwerk.serializer

import ch.iwerk.events.{Parc, Photo}
import org.apache.flink.api.common.serialization.{DeserializationSchema, SerializationSchema}
import org.apache.flink.api.common.typeinfo.TypeInformation





class PhotoSerializer extends SerializationSchema[Photo] with DeserializationSchema[Photo] with Serializable {

  import PhotoSerializer._

  override def serialize(element: Photo): Array[Byte] = {
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

  override def deserialize(message: Array[Byte]): Photo = {

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

    o.asInstanceOf[Photo]


     */
  }

  override def isEndOfStream(nextElement: Photo): Boolean = false

  override def getProducedType: TypeInformation[Photo] = TypeInformation.of(classOf[Photo])
}

object PhotoSerializer {
  implicit val photoSerializer: upickle.default.ReadWriter[Photo] = upickle.default.macroRW[Photo]
}

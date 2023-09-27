package ch.iwerk.serializer

import ch.iwerk.events.Parc
import org.apache.flink.api.common.serialization.{DeserializationSchema, SerializationSchema}
import org.apache.flink.api.common.typeinfo.TypeInformation

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, IOException, ObjectInput, ObjectInputStream, ObjectOutputStream}





class ParcSerializer extends SerializationSchema[Parc] with DeserializationSchema[Parc] with Serializable {

  import ParcSerializer._

  override def serialize(element: Parc): Array[Byte] = {
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

  override def deserialize(message: Array[Byte]): Parc = {

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

  override def isEndOfStream(nextElement: Parc): Boolean = false

  override def getProducedType: TypeInformation[Parc] = TypeInformation.of(classOf[Parc])
}

object ParcSerializer {
  implicit val parcSerializer: upickle.default.ReadWriter[Parc] = upickle.default.macroRW[Parc]
}

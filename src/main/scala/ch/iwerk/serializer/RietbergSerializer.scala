package ch.iwerk.serializer

import ch.iwerk.events.Rietberg
import org.apache.flink.api.common.serialization.{DeserializationSchema, SerializationSchema}
import org.apache.flink.api.common.typeinfo.TypeInformation





class RietbergSerializer extends SerializationSchema[Rietberg] with DeserializationSchema[Rietberg] with Serializable {

  import RietbergSerializer._

  override def serialize(element: Rietberg): Array[Byte] = {
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

  override def deserialize(message: Array[Byte]): Rietberg = {

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

    o.asInstanceOf[Rietberg]


     */
  }

  override def isEndOfStream(nextElement: Rietberg): Boolean = false

  override def getProducedType: TypeInformation[Rietberg] = TypeInformation.of(classOf[Rietberg])
}

object RietbergSerializer {
  implicit val rietbergSerializer: upickle.default.ReadWriter[Rietberg] = upickle.default.macroRW[Rietberg]
}

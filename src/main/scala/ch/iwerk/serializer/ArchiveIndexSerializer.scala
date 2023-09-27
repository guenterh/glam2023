package ch.iwerk.serializer

import ch.iwerk.events.ArchiveIndex
import org.apache.flink.api.common.serialization.{DeserializationSchema, SerializationSchema}
import org.apache.flink.api.common.typeinfo.TypeInformation





class ArchiveIndexSerializer extends SerializationSchema[ArchiveIndex] with DeserializationSchema[ArchiveIndex] with Serializable {

  import ArchiveIndexSerializer._

  override def serialize(element: ArchiveIndex): Array[Byte] = {
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

  override def deserialize(message: Array[Byte]): ArchiveIndex = {

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

  override def isEndOfStream(nextElement: ArchiveIndex): Boolean = false

  override def getProducedType: TypeInformation[ArchiveIndex] = TypeInformation.of(classOf[ArchiveIndex])
}

object ArchiveIndexSerializer {
  implicit val rchiveIndexSerializer: upickle.default.ReadWriter[ArchiveIndex] = upickle.default.macroRW[ArchiveIndex]
}

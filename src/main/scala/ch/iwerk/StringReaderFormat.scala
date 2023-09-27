package ch.iwerk

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.configuration.Configuration
import org.apache.flink.connector.file.src.reader.{SimpleStreamFormat, StreamFormat}
import org.apache.flink.core.fs.FSDataInputStream
import scala.util.control.Breaks.{break, breakable}
import java.io.BufferedReader

class StringReaderFormat extends SimpleStreamFormat[String]{
  override def createReader(config: Configuration, stream: FSDataInputStream): StreamFormat.Reader[String] = {


    //val bufferedReader = new BufferedReader(stream)
    new StreamFormat.Reader[String] {
      override def read(): String = {
        var s: String = ""
        var c: Char = ' '

        breakable {
          while (c != -1) {
            c = stream.read().asInstanceOf[Char]
            if (c == '\n')
              break()
            else
              s += s"$c"

          }
        }

        if (s.length == 0)
          null
        else
          s



        //new String(stream.readAllBytes())
      }

      override def close(): Unit = println("closing the stream")
    }
  }

  override def getProducedType: TypeInformation[String] = TypeInformation.of(classOf[String])
}

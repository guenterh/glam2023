package ch.iwerk.transformations

import ch.iwerk.events.Photo
import org.apache.flink.api.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.connector.file.src.FileSource

object PhotoTransformations {

  def defineActions[T](env: StreamExecutionEnvironment,
                    source: FileSource[T],
                    workflow: DataStream[Photo] => Unit
                   ): Unit = ???


}

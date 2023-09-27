package ch.iwerk

import ch.iwerk.events.Parc
import ch.iwerk.sink.KafkaSourceParcBuilder
import org.apache.flink.api.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.api.common.{JobExecutionResult, RuntimeExecutionMode}
import org.apache.flink.api.common.eventtime.WatermarkStrategy
import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.functions.sink.PrintSink

object ReadParcEvents {

  implicit val parcSerializer: TypeInformation[Parc] = TypeInformation.of(classOf[Parc])

  def runJob(): JobExecutionResult = {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setRuntimeMode(RuntimeExecutionMode.STREAMING)

    val kafkaStream: DataStream[Parc] = env.fromSource(KafkaSourceParcBuilder(), WatermarkStrategy.noWatermarks(), "Kafka Source");
    kafkaStream.sinkTo(new PrintSink[Parc]())

    env.execute()




  }

  def main(args: Array[String]): Unit = {
    runJob()
  }

}

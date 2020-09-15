package myProject_LSP;

import myProject_LSP.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    @Autowired
    PointRepository pointRepository;
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverOrderCancelled_PointSendCancel(@Payload OrderCancelled orderCancelled){
        //수정
        if(orderCancelled.isMe()){

            System.out.println("##### listener pointCancelUpdate : " + orderCancelled.toJson());
            Optional<Point> pointOptional = pointRepository.findByOrderId(orderCancelled.getId());
            Point point = pointOptional.get();
            if("ORDER : ORDER CANCELED".equals(orderCancelled.getStatus())){
                point.setStatus("point : point SEND CANCELLED BY ORDER CANCEL");

            }



            pointRepository.save(point);

        }
    }

}

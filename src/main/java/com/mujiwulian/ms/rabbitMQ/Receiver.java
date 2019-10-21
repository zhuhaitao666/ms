package com.mujiwulian.ms.rabbitMQ;

import com.mujiwulian.ms.entity.Order;
import com.mujiwulian.ms.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Receiver {

    @Autowired
    private SeckillService seckillService;

    @RabbitListener(queues = Config.MS_QUEUE)
    public void receive(int [] message){
        int userId= message[0];
        int goodsId=message[1];
        //进行数据库操作
        Order order= seckillService.seckill(userId,goodsId);
    }

    @RabbitListener(queues = Config.QUEUE)
    public void receive(String message){
        log.info("receive message:"+message);
    }
    @RabbitListener(queues =Config.TOPIC_QUEUE1)
    public void receiveTopic_queue1(String message){
        log.info("receive topic_queue1_message:"+message);
    }
    @RabbitListener(queues =Config.TOPIC_QUEUE2)
    public void receiveTopic_queue2(String message){
        log.info("receive topic_queue2_message:"+message);
    }
    @RabbitListener(queues =Config.HEADERS_QUEUE)
    public void receiveHeaderQueue(byte[] message){
        log.info("receive header_queue_message:"+new String(message));
    }
}

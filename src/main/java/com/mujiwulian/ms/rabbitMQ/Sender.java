package com.mujiwulian.ms.rabbitMQ;

import com.mujiwulian.ms.service.SeckillService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@Slf4j
public class Sender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private SeckillService seckillService;

    /**异步下单,在队列执行数据库操作*/
    public void sendOrder(Integer userId, Integer goodsId){
        int message[]=new int[2];
        message[0]=userId;message[1]=goodsId;
        amqpTemplate.convertAndSend(Config.MS_QUEUE,message);
    }

    public void send(Object message){
        log.info("send message:"+message);
        amqpTemplate.convertAndSend(Config.QUEUE,message);
    }

    public void sendTopic(Object message){
        log.info("send message:"+message);
        //发送消息到key1上面对应的topic_queue2队列上
        amqpTemplate.convertAndSend(Config.TOPIC_EXCANGE,"topic.key1",message);

        //发送消息到key1上面对应的topic_queue1，topic_queue2队列上
        amqpTemplate.convertAndSend(Config.TOPIC_EXCANGE,"topic.key2",message);
    }

    public void sendFanout(Object message){
        log.info("send message:"+message);
        //没有key 但是必须有三个字符串
        amqpTemplate.convertAndSend(Config.FANOUT_EXCANGE,"",message);
    }
    public void sendHeader(Object message){
        log.info("send message:"+message);
        MessageProperties messageProperties=new MessageProperties();
        messageProperties.setHeader("header1","value1");
        messageProperties.setHeader("header2","value2");
        Message obj=new Message(message.toString().getBytes(),messageProperties);
        amqpTemplate.convertAndSend(Config.HEADERS_QUEUE,"",obj);
    }
}

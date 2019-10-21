package com.mujiwulian.ms.rabbitMQ;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class Config {
    public static final String QUEUE="queue";
    public static final String TOPIC_QUEUE1="topic_queue1";
    public static final String TOPIC_QUEUE2="topic_queue2";
    public static final String HEADERS_QUEUE="headers_queue";

    public static final String MS_QUEUE="ms_quque";

    public static final String TOPIC_EXCANGE="topicExchange";
    public static final String FANOUT_EXCANGE="fanoutExchange";
    public static final String HEADERS_EXCANGE="headersExchange";

    @Bean
    /**ms*/
    public Queue msQuque(){
        //队列名称
        return new Queue(MS_QUEUE,true);
    }

    /**交换机Exchange Direct模式（最简单的模式）*/
    @Bean
    public Queue queue(){
        //队列名称
        return new Queue(QUEUE,true);
    }


    /**topic模式 这种方式通过交换机和队列和key绑定在一起，
        可以通过设置key为通配符的方式发送一条消息到多个队列
     */
    @Bean
    public Queue topic_queue1(){
        return new Queue(TOPIC_QUEUE1,true);
    }
    @Bean
    public Queue topic_queue2(){
        return new Queue(TOPIC_QUEUE2,true);
    }
    /**TopicExchange交换机*/
    @Bean
    public TopicExchange getToPicExchange(){
        return new TopicExchange(TOPIC_EXCANGE);
    }
    /**将交换机和队列绑定起来*/
    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topic_queue1()).
                to(getToPicExchange()).with("topic.key1");
    }
    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topic_queue2()).
                to(getToPicExchange()).with("topic.#");
    }


    /** Fanout广播模式   FanoutExchange交换机*/
    @Bean
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange(FANOUT_EXCANGE);
    }
    /**将交换机和队列绑定起来*/
    @Bean
    public Binding fanoutBinding1(){
        return BindingBuilder.bind(topic_queue1()).to(getFanoutExchange());
    }
    @Bean
    public Binding fanoutBinding2(){
        return BindingBuilder.bind(topic_queue2()).to(getFanoutExchange());
    }
    /** Headers模式   HeaderExchange交换机*/

    @Bean
    public Queue headerQueue(){
        //队列名称
        return new Queue(HEADERS_QUEUE,true);
    }
    @Bean
    public HeadersExchange getHeadersExchange(){
        return new HeadersExchange(HEADERS_EXCANGE);
    }
    @Bean
    public Binding headerBinding(){
        Map<String,Object> map=new HashMap<>();
        map.put("header1","value1");
        map.put("header2","value2");
        return BindingBuilder.bind(headerQueue())
                .to(getHeadersExchange()).whereAll(map).match();
    }
}

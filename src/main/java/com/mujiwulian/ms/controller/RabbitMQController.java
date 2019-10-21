package com.mujiwulian.ms.controller;

import com.mujiwulian.ms.rabbitMQ.Receiver;
import com.mujiwulian.ms.rabbitMQ.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RabbitMQController {

    @Autowired
    private Sender sender;


    @RequestMapping("/testRabbitMessage")
    @ResponseBody
    public String test(){
        sender.send("hello RabbitMQ！");
        return "success";
    }

    @RequestMapping("/testTopic")
    @ResponseBody
    public String testTopic(){
        sender.sendTopic("topicChange");
        return "success";
    }

    @RequestMapping("/testFanout")
    @ResponseBody
    public String testFanout(){
        sender.sendFanout("testFanout");
        return "success";
    }
}

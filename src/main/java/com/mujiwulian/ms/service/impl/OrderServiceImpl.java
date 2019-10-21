package com.mujiwulian.ms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mujiwulian.ms.entity.Order;
import com.mujiwulian.ms.mapper.OrderMapper;
import com.mujiwulian.ms.service.OrderService;
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService{

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public int insertOne(Order order){
       return orderMapper.insertOne(order);
    }
}

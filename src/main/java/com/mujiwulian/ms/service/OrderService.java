package com.mujiwulian.ms.service;

import com.mujiwulian.ms.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;
public interface OrderService extends IService<Order>{
    int insertOne(Order order);
}

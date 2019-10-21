package com.mujiwulian.ms.service;

import com.mujiwulian.ms.entity.Order;

public interface SeckillService {
    public Order seckill(Integer userId, Integer goodsId);
}

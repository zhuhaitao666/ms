package com.mujiwulian.ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mujiwulian.ms.entity.Order;

public interface OrderMapper extends BaseMapper<Order> {
    int insertOne(Order order);
}
package com.mujiwulian.ms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.mujiwulian.ms.entity.Order;
import com.mujiwulian.ms.entity.SecOrder;
import com.mujiwulian.ms.entity.SeckillGoods;
import com.mujiwulian.ms.service.OrderService;
import com.mujiwulian.ms.service.SecOrderService;
import com.mujiwulian.ms.service.SeckillGoodsService;
import com.mujiwulian.ms.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.convert.EntityWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class SeckillServiceImpl  implements SeckillService {

    @Autowired
    private SeckillGoodsService seckillGoodsService;
    @Autowired
    private SecOrderService secOrderService;
    @Autowired
    private OrderService orderService;


    @Override
    @Transactional
    public Order seckill(Integer userId, Integer goodsId) {

        //1.减少库存
        SeckillGoods seckillGoods=seckillGoodsService.getById(goodsId);
        if (seckillGoods!=null){
            if(seckillGoods.getStockCount()>0){
                UpdateWrapper updateWrapper=new UpdateWrapper();
                updateWrapper.ge("id",seckillGoods.getId());
                seckillGoods.setStockCount(seckillGoods.getStockCount()-1);
                seckillGoodsService.update(seckillGoods,updateWrapper);
            }else{
                return null;
            }
        }
        //2.添加订单
        Order order=new Order();
        order.setCreateTime(new Date());
        order.setGoodCount(1);
        order.setGoodsId(goodsId);
        order.setStatus(0);
        order.setUserId(userId);
        orderService.insertOne(order);
        //3.生成秒杀订单
        SecOrder secOrder=new SecOrder();
        secOrder.setGoodsId(goodsId);
        secOrder.setUserId(userId);
        secOrder.setOrderId(order.getId());
        secOrderService.save(secOrder);
        return order;
    }
}

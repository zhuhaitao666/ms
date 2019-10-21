package com.mujiwulian.ms.service.impl;

import com.mujiwulian.ms.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mujiwulian.ms.mapper.SecOrderMapper;
import com.mujiwulian.ms.entity.SecOrder;
import com.mujiwulian.ms.service.SecOrderService;
@Service
public class SecOrderServiceImpl extends ServiceImpl<SecOrderMapper, SecOrder> implements SecOrderService{

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SecOrderMapper secOrderMapper;
    @Override
    public SecOrder selectSecOrderByUserIdAndGoodsId(Integer userId, Integer goodsId){
        SecOrder secOrder= (SecOrder) redisUtil.get("hadBoughtSecOrder:"+userId+":"+goodsId);
        if(secOrder==null){
            secOrder=secOrderMapper.selectSecOrderByUserIdAndGoodsId(userId, goodsId);
        }
        return secOrder;
    }
}

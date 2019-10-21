package com.mujiwulian.ms.service;

import com.mujiwulian.ms.entity.SecOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface SecOrderService extends IService<SecOrder>{
    SecOrder selectSecOrderByUserIdAndGoodsId( Integer userId, Integer goodsId);
}

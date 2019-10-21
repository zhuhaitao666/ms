package com.mujiwulian.ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mujiwulian.ms.entity.SecOrder;
import org.apache.ibatis.annotations.Param;


public interface SecOrderMapper extends BaseMapper<SecOrder> {
    SecOrder selectSecOrderByUserIdAndGoodsId(@Param("userId") Integer userId,
                                              @Param("goodsId") Integer goodsId);
}
package com.mujiwulian.ms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mujiwulian.ms.entity.Goods;
import com.mujiwulian.ms.vo.GoodsVO;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods> {
    List<GoodsVO> selectGoodsList();
    GoodsVO getSecGoodsById(Integer id);
}
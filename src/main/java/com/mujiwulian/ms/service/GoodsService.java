package com.mujiwulian.ms.service;

import com.mujiwulian.ms.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mujiwulian.ms.vo.GoodsVO;

import java.util.List;

public interface GoodsService extends IService<Goods>{

    List<GoodsVO> selectGoodsList();
    GoodsVO getSecGoodsById(Integer id);
}

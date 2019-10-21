package com.mujiwulian.ms.service.impl;

import com.mujiwulian.ms.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mujiwulian.ms.mapper.GoodsMapper;
import com.mujiwulian.ms.entity.Goods;
import com.mujiwulian.ms.service.GoodsService;
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService{

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<GoodsVO> selectGoodsList() {
        return goodsMapper.selectGoodsList();
    }

    @Override
    public GoodsVO getSecGoodsById(Integer id) {
        return goodsMapper.getSecGoodsById(id);
    }

}

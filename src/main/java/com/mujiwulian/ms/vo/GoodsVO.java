package com.mujiwulian.ms.vo;

import com.mujiwulian.ms.entity.Goods;
import lombok.Data;

import java.util.Date;

@Data
public class GoodsVO extends Goods {
    private Long seckilPrice;
    private Integer stockCount;
    private Date startTime;
    private Date endTime;
}

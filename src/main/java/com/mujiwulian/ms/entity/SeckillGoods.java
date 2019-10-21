package com.mujiwulian.ms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "seckill_goods")
public class SeckillGoods implements Serializable {
     @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "goods_id")
    private Integer goodsId;

    @TableField(value = "seckil_price")
    private Long seckilPrice;

    @TableField(value = "stock_count")
    private Integer stockCount;

    @TableField(value = "start_time")
    private Date startTime;

    @TableField(value = "end_time")
    private Date endTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_GOODS_ID = "goods_id";

    public static final String COL_SECKIL_PRICE = "seckil_price";

    public static final String COL_STOCK_COUNT = "stock_count";

    public static final String COL_START_TIME = "start_time";

    public static final String COL_END_TIME = "end_time";
}
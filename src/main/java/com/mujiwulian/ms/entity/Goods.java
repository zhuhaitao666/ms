package com.mujiwulian.ms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName(value = "goods")
public class Goods implements Serializable {
     @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "goods_name")
    private String goodsName;

    @TableField(value = "goods_title")
    private String goodsTitle;

    @TableField(value = "goods_img")
    private String goodsImg;

    @TableField(value = "goods_detail")
    private String goodsDetail;

    @TableField(value = "goods_price")
    private Long goodsPrice;

    @TableField(value = "goods_stock")
    private Integer goodsStock;

    private static final long serialVersionUID = 1L;

    public static final String COL_GOODS_NAME = "goods_name";

    public static final String COL_GOODS_TITLE = "goods_title";

    public static final String COL_GOODS_IMG = "goods_img";

    public static final String COL_GOODS_DETAIL = "goods_detail";

    public static final String COL_GOODS_PRICE = "goods_price";

    public static final String COL_GOODS_STOCK = "goods_stock";
}
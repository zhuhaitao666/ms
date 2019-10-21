package com.mujiwulian.ms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName(value = "user")
public class User implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "name")
    private String name;

    @TableField(value = "age")
    private Integer age;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "password")
    private String password;

    private static final long serialVersionUID = 1L;

    public static final String COL_NAME = "name";

    public static final String COL_AGE = "age";

    public static final String COL_PHONE = "phone";

    public static final String COL_PASSWORD = "password";
}
package org.chuan.woj.pojo.dto.problem;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

/**
 * @Author: chuan-wxy
 * @Date: 2024/8/27 15:48
 * @Description:
 */
public class TagAddDTO implements Serializable {

    /**
     * 标签名字
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

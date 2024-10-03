package org.chuan.woj.pojo.vo.activity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: chuan-wxy
 * @Date: 2024/10/2 21:06
 * @Description:
 */
@Data
public class ActivityContentVO implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     * 名称
     */
    private String title;

    /**
     * 内容
     */
    private String description;

    private static final long serialVersionUID = 1L;
}

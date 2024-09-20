package org.chuan.woj.pojo.dto.problemSubmit;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chuan-wxy
 * @Date: 2024/9/12 12:43
 * @Description:
 */
@Data
public class ProblemSubmitAddDTO implements Serializable {
    /**
     * 代码语言
     */
    private String language;

    /**
     * 提交代码
     */
    private String code;

    /**
     * 题目id
     */
    private Long pid;

    /**
     * 用户id
     */
    private String uid;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

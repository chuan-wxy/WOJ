package org.chuan.woj.pojo.dto.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @Author: chuan-wxy
 * @Date: 2024/8/26 16:35
 * @Description:
 */
@Data
public class ProblemAddDTO {

    /**
     * 问题的自定义ID
     */
    private String problemId;

    /**
     * 题目
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 单位ms
     */
    private Integer timeLimit;

    /**
     * 单位kb
     */
    private Integer memoryLimit;

    /**
     * 单位mb
     */
    private Integer stackLimit;

    /**
     * 描述
     */
    private String description;

    /**
     * 输入描述
     */
    private String input;

    /**
     * 输出描述
     */
    private String output;

    /**
     * 题目来源
     */
    private String source;

    /**
     * 题目难度,0简单，1中等，2困难
     */
    private Integer difficulty;

    /**
     * 默认为1公开，2为私有，3为比赛题目
     */
    private Integer auth;

    /**
     * 题目评测模式,default、spj、interactive
     */
    private String judgeMode;

    /**
     * 特判程序或交互程序代码
     */
    private String spjCode;

    /**
     * 特判程序或交互程序代码的语言
     */
    private String spjLanguage;

    /**
     * 修改题目的管理员用户名
     */
    private String modifiedUser;

    /**
     * 是否是file io自定义输入输出文件模式
     */
    private Integer isFileIo;

    /**
     * 题目指定的file io输入文件的名称
     */
    private String ioReadFileName;

    /**
     * 题目指定的file io输出文件的名称
     */
    private String ioWriteFileName;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}

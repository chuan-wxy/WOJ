package org.chuan.woj.pojo.vo.problem;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.chuan.woj.pojo.dto.problem.ProblemTagDTO;
import org.chuan.woj.pojo.dto.problem.ProblemTitleDTO;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.Tag;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: chuan-wxy
 * @Date: 2024/8/28 14:27
 * @Description:
 */
@Data
public class ProblemTitleVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 问题的自定义ID
     */
    private String problemId;

    /**
     * 题目
     */
    private String title;

    /**
     * 标签
     */
    private List<String> tagList;

    /**
     * 题目来源
     */
    private String source;

    /**
     * 题目难度,0简单，1中等，2困难
     */
    private Integer difficulty;

    private static final long serialVersionUID = 1L;

    /**
     * 对象转包装类
     *
     * @param problem
     * @return
     */
    public static ProblemTitleVO objToVo(Problem problem) {
        if (problem == null) {
            return null;
        }
        ProblemTitleVO problemTitleVO = new ProblemTitleVO();

        BeanUtils.copyProperties(problem, problemTitleVO);

        List<String> tagList = JSONUtil.toList(problem.getTagList(),String.class);
        problemTitleVO.setTagList(tagList);
        return problemTitleVO;
    }
}

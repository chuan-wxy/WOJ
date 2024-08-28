package org.chuan.woj.pojo.vo.problem;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.chuan.woj.pojo.dto.problem.ProblemTagDTO;
import org.chuan.woj.pojo.dto.problem.ProblemTitleDTO;
import org.chuan.woj.pojo.entity.Tag;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: chuan-wxy
 * @Date: 2024/8/28 14:27
 * @Description:
 */
@Data
public class ProblemTitleVO implements Serializable {

    private ProblemTitleDTO problemTitleDTO;

    private List<Tag> tagList;

    private static final long serialVersionUID = 1L;
}

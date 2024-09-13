package org.chuan.woj.pojo.vo.problemSubmit;

import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.chuan.woj.pojo.dto.problemSubmit.JudgeInfo;
import org.chuan.woj.pojo.vo.user.UserVO;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: chuan-wxy
 * @Date: 2024/9/12 12:57
 * @Description:
 */
@Data
public class ProblemSubmitVO  implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 代码语言
     */
    private String language;

    /**
     * 提交代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;
    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 判题结束）
     */
    private Integer state;

    /**
     * 题目id
     */
    private Long pid;

    /**
     * 用户id
     */
    private String uid;

    private UserVO userVO;
//    private QuestionVO questionVO;
//
//
//    private static final long serialVersionUID = 1L;
//
//    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
//        if (questionSubmitVO == null) {
//            return null;
//        }
//        QuestionSubmit questionSubmit = new QuestionSubmit();
//        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
//        JudgeInfo judgeInfo = questionSubmitVO.getJudgeInfo();
//        if(judgeInfo!=null){
//            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
//        }
//        return questionSubmit;
//    }
//
//    /**
//     * 对象转包装类
//     *
//     * @param questionSubmit
//     * @return
//     */
//    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
//        if (questionSubmit == null) {
//            return null;
//        }
//        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
//        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
//
//        String judgeInfoStr = questionSubmit.getJudgeInfo();
//        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr,JudgeInfo.class));
//        return questionSubmitVO;
//    }
}

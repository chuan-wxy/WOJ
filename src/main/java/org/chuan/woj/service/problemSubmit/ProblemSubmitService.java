package org.chuan.woj.service.problemSubmit;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.pojo.dto.problemSubmit.ProblemSubmitAddDTO;
import org.chuan.woj.pojo.entity.ProblemSubmit;
import org.chuan.woj.pojo.entity.User;
import org.chuan.woj.pojo.vo.problemSubmit.ProblemSubmitVO;

/**
* @author lenovo
* @description 针对表【problem_submit】的数据库操作Service
* @createDate 2024-09-12 12:39:38
*/
public interface ProblemSubmitService extends IService<ProblemSubmit> {
    ProblemSubmitVO doQuestionSubmit(ProblemSubmitAddDTO problemSubmitAddDTO, User user) throws StatusFailException, StatusSystemErrorException;

}

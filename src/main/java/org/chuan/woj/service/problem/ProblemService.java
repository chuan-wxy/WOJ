package org.chuan.woj.service.problem;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.Tag;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
* @author lenovo
* @description 针对表【problem】的数据库操作Service
* @createDate 2024-08-26 16:25:01
*/
public interface ProblemService extends IService<Problem> {

    BaseResponse<String> addProblem(ProblemAddDTO ProblemAddDTO, List<Tag> tagList) throws StatusFailException, StatusSystemErrorException;

}

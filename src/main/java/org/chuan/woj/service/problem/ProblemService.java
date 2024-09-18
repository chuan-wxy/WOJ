package org.chuan.woj.service.problem;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.pojo.dto.problem.ProblemAddDTO;
import org.chuan.woj.pojo.dto.problem.TagAddDTO;
import org.chuan.woj.pojo.entity.Problem;
import org.chuan.woj.pojo.entity.Tag;
import org.chuan.woj.pojo.vo.problem.ProblemTitleVO;
import org.chuan.woj.pojo.vo.problem.ProblemVO;
import org.chuan.woj.pojo.vo.problem.TagVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
* @author lenovo
* @description 针对表【problem】的数据库操作Service
* @createDate 2024-08-26 16:25:01
*/
public interface ProblemService extends IService<Problem> {

    BaseResponse<String> addProblem(ProblemAddDTO ProblemAddDTO) throws StatusFailException, StatusSystemErrorException;

    BaseResponse<String> addTag(String tagName) throws StatusFailException;

    BaseResponse<Page<ProblemTitleVO>> getProblemTitle(Integer current, Integer size) throws StatusFailException;

    BaseResponse<ProblemVO> getProblem(Long id) throws StatusFailException;

    BaseResponse<Page<ProblemTitleVO>> searchProblemTitle(Integer current, Integer size, String text) throws StatusFailException;

    BaseResponse<IPage<ProblemTitleVO>> searchProblemTitleTwo(Integer current, Integer size, Long id, String tags, String difficulty, String title) throws StatusFailException;
}

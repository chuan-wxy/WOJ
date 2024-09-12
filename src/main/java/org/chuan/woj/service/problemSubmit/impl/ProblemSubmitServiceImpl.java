package org.chuan.woj.service.problemSubmit.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.exception.StatusSystemErrorException;
import org.chuan.woj.pojo.dto.problemSubmit.ProblemSubmitAddDTO;
import org.chuan.woj.pojo.entity.ProblemSubmit;
import org.chuan.woj.pojo.entity.User;
import org.chuan.woj.pojo.vo.problemSubmit.ProblemSubmitVO;
import org.chuan.woj.service.problemSubmit.ProblemSubmitService;
import org.chuan.woj.mapper.ProblemSubmitMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【problem_submit】的数据库操作Service实现
* @createDate 2024-09-12 12:39:38
*/
@Service
@Slf4j
public class ProblemSubmitServiceImpl extends ServiceImpl<ProblemSubmitMapper, ProblemSubmit>
    implements ProblemSubmitService{

    @Override
    public ProblemSubmitVO doQuestionSubmit(ProblemSubmitAddDTO problemSubmitAddDTO, User user) throws StatusFailException, StatusSystemErrorException {
        if (problemSubmitAddDTO == null || problemSubmitAddDTO.getPid() <= 0) {
            throw new StatusFailException("请求体为空或题目id为空");
        }
        ProblemSubmit problemSubmit = new ProblemSubmit();
        BeanUtils.copyProperties(problemSubmitAddDTO,problemSubmit);

        problemSubmit.setUid(user.getUuid());

        boolean save = this.save(problemSubmit);
        if(!save) {
            log.debug("ProblemSubmitServiceImpl----doQuestionSubmit--->保存problemSubmit失败");
            throw new StatusSystemErrorException("保存失败");
        }


        return null;
    }
}





package org.chuan.woj.service.announcement.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.manager.ActivityManager;
import org.chuan.woj.mapper.AnnouncementMapper;
import org.chuan.woj.pojo.dto.announcement.AnnouncementAddDTO;
import org.chuan.woj.pojo.entity.Activity;
import org.chuan.woj.pojo.entity.Announcement;
import org.chuan.woj.pojo.vo.activity.ActivityContentVO;
import org.chuan.woj.pojo.vo.activity.ActivityTitleVO;
import org.chuan.woj.pojo.vo.announcement.AnnouncementContentVO;
import org.chuan.woj.pojo.vo.announcement.AnnouncementTitleVO;
import org.chuan.woj.service.announcement.AnnouncementService;
import org.chuan.woj.utils.ResultUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author lenovo
* @description 针对表【announcement】的数据库操作Service实现
* @createDate 2024-10-06 19:45:34
*/
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
    implements AnnouncementService {

    @Autowired
    private ActivityManager activityManager;

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public BaseResponse<String> addAnnouncement(AnnouncementAddDTO announcementAddDTO) throws StatusFailException {
        activityManager.announcementAddvalidate(announcementAddDTO);

        Announcement announcement = new Announcement();

        BeanUtils.copyProperties(announcementAddDTO,announcement);

        boolean save = this.save(announcement);

        if(save) {
            return ResultUtils.success("保存成功");
        } else {
            log.error("AnnouncementServiceImpl---->addAnnouncement---保存公告失败");
            return ResultUtils.error("保存失败");
        }
    }


    @Override
    public BaseResponse<List<AnnouncementTitleVO>> getAnnouncementList() {
        List<AnnouncementTitleVO> announcementTitleVOS = announcementMapper.selectAnnouncementTitleList();
        for(int i = 0;i<announcementTitleVOS.size();i++) {
            announcementTitleVOS.get(i).setCreateTime(announcementTitleVOS.get(i).getCreateTime().substring(0, 10));
        }
        return ResultUtils.success(announcementTitleVOS);
    }

    @Override
    public BaseResponse<AnnouncementContentVO> getAnnouncement(Integer id) throws StatusFailException {
        if(id == null) {
            throw new StatusFailException("id为空");
        }
        if(id <= 0) {
            throw new StatusFailException("参数错误");
        }
        return ResultUtils.success(announcementMapper.selectAnnouncementContentVO(id));
    }
}





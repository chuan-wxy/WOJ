package org.chuan.woj.service.announcement;

import com.baomidou.mybatisplus.extension.service.IService;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.announcement.AnnouncementAddDTO;
import org.chuan.woj.pojo.entity.Announcement;
import org.chuan.woj.pojo.vo.activity.ActivityContentVO;
import org.chuan.woj.pojo.vo.activity.ActivityTitleVO;
import org.chuan.woj.pojo.vo.announcement.AnnouncementContentVO;
import org.chuan.woj.pojo.vo.announcement.AnnouncementTitleVO;

import java.util.List;

/**
* @author lenovo
* @description 针对表【announcement】的数据库操作Service
* @createDate 2024-10-06 19:45:34
*/
public interface AnnouncementService extends IService<Announcement> {

    BaseResponse<String> addAnnouncement(AnnouncementAddDTO announcementAddDTO) throws StatusFailException;

    BaseResponse<List<AnnouncementTitleVO>> getAnnouncementList();

    BaseResponse<AnnouncementContentVO> getAnnouncement(Integer id) throws StatusFailException;
}

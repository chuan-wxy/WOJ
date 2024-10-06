package org.chuan.woj.controller.oj;

import lombok.extern.slf4j.Slf4j;
import org.chuan.woj.annotation.AuthCheck;
import org.chuan.woj.common.BaseResponse;
import org.chuan.woj.constant.UserConstant;
import org.chuan.woj.exception.StatusFailException;
import org.chuan.woj.pojo.dto.activity.ActivityAddDTO;
import org.chuan.woj.pojo.dto.announcement.AnnouncementAddDTO;
import org.chuan.woj.pojo.vo.activity.ActivityContentVO;
import org.chuan.woj.pojo.vo.activity.ActivityTitleVO;
import org.chuan.woj.pojo.vo.announcement.AnnouncementContentVO;
import org.chuan.woj.pojo.vo.announcement.AnnouncementTitleVO;
import org.chuan.woj.service.activity.ActivityService;
import org.chuan.woj.service.announcement.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: chuan-wxy
 * @Date: 2024/10/6 19:51
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {
    @Autowired
    AnnouncementService announcementService;

    /**
     * 新增活动
     * @param announcementAddDTO
     * @return
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = UserConstant.AMDIN)
    public BaseResponse<String> addAnnouncement(@RequestBody AnnouncementAddDTO announcementAddDTO) throws StatusFailException {
        return announcementService.addAnnouncement(announcementAddDTO);
    }

    /**
     * 获取活动标题列表
     * @return
     */
    @GetMapping("/get-activity-list")
    public BaseResponse<List<AnnouncementTitleVO>> getAnnouncementList() {
        return announcementService.getAnnouncementList();
    }

    /**
     * 获取单个活动文章
     * @return
     */
    @GetMapping("/get-activity")
    public BaseResponse<AnnouncementContentVO> getAnnouncement(@RequestParam Integer id) throws StatusFailException {
        return announcementService.getAnnouncement(id);
    }
}

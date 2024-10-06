package org.chuan.woj.service.announcement.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Announcement;
import generator.service.AnnouncementService;
import generator.mapper.AnnouncementMapper;
import org.springframework.stereotype.Service;

/**
* @author lenovo
* @description 针对表【announcement】的数据库操作Service实现
* @createDate 2024-10-06 19:45:34
*/
@Service
public class AnnouncementServiceImpl extends ServiceImpl<AnnouncementMapper, Announcement>
    implements AnnouncementService{

}





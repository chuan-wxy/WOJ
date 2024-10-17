package org.chuan.woj;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.PingCmd;
import com.github.dockerjava.core.DockerClientBuilder;
import org.chuan.woj.mapper.ActivityMapper;
import org.chuan.woj.pojo.vo.activity.ActivityTitleVO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试类通用模板
 *
 * @Author: chuan-wxy
 * @Date: 2024/8/16 9:14
 * @Description:
 */
@SpringBootTest
public class TestTemplate {

    @Autowired
    ActivityMapper activityMapper;
    @Test
    public void test(){
    }
}











































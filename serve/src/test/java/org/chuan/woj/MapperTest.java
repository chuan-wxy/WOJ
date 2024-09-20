package org.chuan.woj;

import org.chuan.woj.mapper.RoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 测试类通用模板
 *
 * @Author: chuan-wxy
 * @Date: 2024/8/16 9:14
 * @Description:
 */
@SpringBootTest
public class MapperTest {
    @Autowired
    RoleMapper roleMapper;

    @Test
    public void test(){
        List<String> list = roleMapper.SelectRoleByUserAccount("3153917921@qq.com");
        System.out.println(list);
    }
}

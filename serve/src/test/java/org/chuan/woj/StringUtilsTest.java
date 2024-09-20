package org.chuan.woj;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 测试类通用模板
 *
 * @Author: chuan-wxy
 * @Date: 2024/8/16 9:14
 * @Description:
 */
@SpringBootTest
public class StringUtilsTest {

    @Test
    public void test(){
        String a = "asd";

        String b = "asd";

        System.out.println(StringUtils.isAnyBlank(a, b));
    }
}

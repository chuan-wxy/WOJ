package org.chuan.woj;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文本检测拆分工具测试类
 *
 * @Author: chuan-wxy
 * @Date: 2024/8/16 9:14
 * @Description:
 */
@SpringBootTest
public class DataExtractorTest {
    @Test
    public void test(){
        String text = "深度优先,树::difficulty:困难,复杂;深度,深度优先::tags:深度,有限,遍历;;";
        List<String> keywords = new ArrayList<>();
        List<String> difficulties = new ArrayList<>();
        List<String> tags = new ArrayList<>();

        // 定义正则表达式
        String difficultyPattern = "::difficulty:([^;]+);";
        String tagsPattern = "::tags:([^;]+);"; // 假设标签以分号结束

        // 编译正则表达式
        Pattern difficultyRegex = Pattern.compile(difficultyPattern);
        Pattern tagsRegex = Pattern.compile(tagsPattern);

        // 匹配难度
        Matcher difficultyMatcher = difficultyRegex.matcher(text);
        while (difficultyMatcher.find()) {
            String difficulty = difficultyMatcher.group(1).trim();
            // 难度可能包含多个值，用逗号分隔
            String[] difficultiesArray = difficulty.split(",");
            for (String diff : difficultiesArray) {
                difficulties.add(diff.trim());
            }
        }

        // 匹配标签
        Matcher tagsMatcher = tagsRegex.matcher(text);
        while (tagsMatcher.find()) {
            String tagList = tagsMatcher.group(1).trim();
            // 标签可能包含多个值，用逗号分隔
            String[] tagsArray = tagList.split(",");
            for (String tag : tagsArray) {
                tags.add(tag.trim());
            }
        }

        // 提取关键字
        // 移除难度和标签部分，然后分割剩余字符串为关键字
        // 注意：这里我们假设关键字之间以逗号分隔，并且不会出现在难度或标签标识符内部
        String remainingText = text.replaceAll(difficultyPattern, "").replaceAll(tagsPattern, "").replaceAll(";", "").trim();
        String[] keywordParts = remainingText.split(",");
        for (String part : keywordParts) {
            String keyword = part.trim();
            if (!keyword.isEmpty()) {
                keywords.add(keyword);
            }
        }

        // 打印结果
        System.out.println("Keywords: " + keywords);
        System.out.println("Difficulties: " + difficulties);
        System.out.println("Tags: " + tags);
    }
}

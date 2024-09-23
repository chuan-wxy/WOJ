<template>
  <div class="home">
    <el-timeline style="max-width: 600px">
      <el-timeline-item
        v-for="(activity, index) in activities"
        :key="index"
        :timestamp="activity.timestamp"
        :color="activity.color"
      >
        <el-card>
          <h4>{{ activity.content }}</h4>
          <p>{{ activity.description }}</p>
        </el-card>
      </el-timeline-item>
    </el-timeline>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";

const activities = [
  {
    content: "项目开始",
    timestamp: "2024-07-19",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "库表设计完成",
    timestamp: "2024-07-21",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "controller，service，mapper设计完成",
    timestamp: "2024-07-25",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "用Slf4j完成了对接口的自动化生成",
    timestamp: "2024-07-25",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "前后端开发开始",
    timestamp: "2024-07-26",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "前端页面基本完毕",
    timestamp: "2024-07-27",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "前后端联调成功",
    timestamp: "2024-07-29",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "题库模块完成",
    timestamp: "2024-07-30",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "所有模块的增删改成基本完毕",
    timestamp: "2024-08-01",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "引入教资刷题模块",
    timestamp: "2024-08-02",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "优化用户注册登录页面",
    timestamp: "2024-08-04",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "实现邮箱注册功能",
    timestamp: "2024-08-05",
    description: "后端新建邮箱注册的工具类，实现邮箱注册",
    color: "#0bbd87",
  },
  {
    content: "课程模块初步实现",
    timestamp: "2024-08-09",
    description: "将课程拆分为章、节和小小节",
    color: "#0bbd87",
  },
  {
    content: "课程模块重构",
    timestamp: "2024-08-10",
    description:
      "更新优化了库表设计，用双亲表示法，将个章节、小节和小小节融合成一张表",
    color: "#0bbd87",
  },
  {
    content: "完成头像模块",
    timestamp: "2024-08-10",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "重构前后端",
    timestamp: "2024-08-26",
    description: "",
    color: "#0bbd87",
  },
  {
    content: "代办",
    timestamp: "2024-08-05",
    description: "邮件UI待优化",
    color: "#c04851",
  },
  {
    content: "代办",
    timestamp: "2024-08-09",
    description: "ActivityContentView.vue渲染逻辑待优化",
    color: "#c04851",
  },
  {
    content: "代办",
    timestamp: "2024-08-10",
    description: "刷新页面登录信息消失",
    color: "#c04851",
  },
];
</script>

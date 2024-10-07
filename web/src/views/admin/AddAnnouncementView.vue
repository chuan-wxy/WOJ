<template>
  <div id="add-announcement">
    <div class="panel-body">
      <el-form :model="form" label-width="auto" style="width: 100%">
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="内容">
          <MdEditor :value="form.content" :handle-change="onContentMdchange" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit()">提交 </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { ElMessage } from "element-plus";
import MdEditor from "@/components/MdEditor.vue";
import {
  AnnouncementAddDTO,
  AnnouncementControllerService,
} from "../../../generated";

// 新建课程请求体
const form = ref({
  title: "",
  content: "",
} as AnnouncementAddDTO);

const onContentMdchange = (v: string) => {
  form.value.content = v;
};

const onSubmit = async () => {
  const res = await AnnouncementControllerService.addAnnouncement(form.value);
  if (res.code === 0) {
    ElMessage.success("添加成功");
  } else {
    ElMessage.error("添加失败：" + res.message);
  }
};
</script>

<style scoped>
#add-announcement {
  background: white;
  width: 100%;
  margin: auto;
}

.panel-body {
  padding: 15px;
}
</style>

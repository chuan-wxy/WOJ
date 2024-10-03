<template>
  <div id="add-activity">
    <div class="panel-body">
      <el-form :model="form" label-width="auto" style="width: 100%">
        <el-form-item label="标题">
          <el-input v-model="form.title" />
        </el-form-item>
        <el-form-item label="内容">
          <MdEditor
            :value="form.description"
            :handle-change="onContentMdchange"
          />
        </el-form-item>
        <el-upload
          class="upload-demo"
          drag
          :action="activityAvatarUploadPath"
          :on-success="handleAvatarSuccess"
          :data="avatarData"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将图片上传至此，或<em>点击上传</em></div>
        </el-upload>
        <el-form-item>
          <el-button type="primary" @click="onSubmit()">提交 </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { ElMessage } from "element-plus";
import { ActivityControllerService } from "../../../generated/services/ActivityControllerService";
import MdEditor from "@/components/MdEditor.vue";

const avatarData = ref({
  activityTitle: "",
});

const activityAvatarUploadPath = ref("");

// 新建课程请求体
const form = ref({
  title: "",
  avatar: "",
  description: "",
});

// 图片上传成功，回调函数
const handleAvatarSuccess = (response) => {
  if (response.code === 0) {
    form.value.avatar = response.data;
  } else {
    ElMessage.error("上传失败：{}", response.message);
  }
};

const onContentMdchange = (v: string) => {
  form.value.description = v;
};

const onSubmit = async () => {
  const res = await ActivityControllerService.addActivity(form.value);
  if (res.code === 0) {
    ElMessage.success("添加成功");
  } else {
    ElMessage.error("添加失败：" + res.message);
  }
};

watch(
  () => form.value.title,
  (newValue) => {
    avatarData.value.activityTitle = newValue as string;
  }
);

onMounted(() => {
  activityAvatarUploadPath.value = process.env
    .VUE_APP_ACTIVITY_AVATAR_UPLOAD_PATH as string;
});
</script>

<style scoped>
#add-activity {
  background: white;
  width: 100%;
  margin: auto;
}

.panel-body {
  padding: 15px;
}
</style>

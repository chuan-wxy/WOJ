<template>
  <div id="addCourse">
    <div class="panel-body">
      <el-form :model="form" label-width="auto" style="max-width: 600px">
        <el-form-item label="名称">
          <el-input v-model="form.name" />
        </el-form-item>
        <el-form-item label="层级">
          <el-select v-model="form.level" placeholder="请选择层级">
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value"
              @click="getParent()"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="父级">
          <el-select v-model="parent" placeholder="请选择父级">
            <el-option
              v-for="item in parentList"
              :key="item.value"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="描述">
          <el-input v-model="form.description" type="textarea" />
        </el-form-item>
        <el-upload
          class="upload-demo"
          drag
          headers="POST"
          :action="courseAvatarUploadPath"
          multiple
          :data="avatarData"
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将图片上传至此，或<em>点击上传</em></div>
        </el-upload>
        <el-form-item>
          <el-button
            v-if="isUpdate === false"
            type="primary"
            @click="onSubmit()"
            >提交
          </el-button>
          <el-button v-else @click="updateCourse()">修改</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import { useUserStore } from "@/store/UserStore";
import { ElMessage } from "element-plus";
import { CourseControllerService } from "../../../generated/services/CourseControllerService";

const route = useRoute();
const userStore = useUserStore();
const isUpdate = ref(false);
const avatarData = ref({
  courseName: "",
});

const courseAvatarUploadPath = ref("");

const options = [
  {
    value: 1,
    label: "第一层",
  },
  {
    value: 2,
    label: "第二层",
  },
  {
    value: 3,
    label: "第三层",
  },
];

const form = ref({
  name: "",
  level: 0,
  description: "",
  avatar: "",
});

watch(
  () => form.value.name,
  (newValue) => {
    avatarData.value.courseName = newValue as string;
  }
);

const parent = ref();
const parentList = ref();
const getParent = async () => {
  parentList.value = null;
  parent.value = null;
  const res = await CourseControllerService.getCourseByLevel(
    form.value.level as number
  );
  if (res.code === 0) {
    parentList.value = res.data;
  }
};

const onSubmit = async () => {
  const res = await CourseControllerService.addCourse(form.value);
  if (res.code === 0) {
    ElMessage.success("添加成功");
  } else {
    ElMessage.error("添加失败：" + res.message);
  }
};
const updateCourse = async () => {
  const res = await CourseControllerService.updateProblem(form.value);
  if (res.code === 0) {
    ElMessage.success("修改成功");
  } else {
    ElMessage.error("修改失败：" + res.message);
  }
};
onMounted(() => {
  courseAvatarUploadPath.value = process.env
    .VUE_APP_COURSE_AVATAR_UPLOAD_PATH as string;
});
</script>

<style scoped>
#addCourse {
  background: white;
  width: 100%;
  margin: auto;
}

.panel-body {
  padding: 15px;
}
</style>

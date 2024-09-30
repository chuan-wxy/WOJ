<template>
  <div id="addQuestion">
    <div class="panel-body">
      <a-space style="justify-content: space-between">
        <a-form-item field="title" label="题目">
          <a-input v-model="form.title" placeholder="请输入题目标题" />
        </a-form-item>
        <a-form-item field="problemId" label="自定义ID">
          <a-input v-model="form.problemId" placeholder="请输入自定义id" />
        </a-form-item>
        <a-form-item field="tags" label="标签">
          <a-input-tag v-model="form.tagList" placeholder="请输入题目标签" />
        </a-form-item>
        <a-form-item field="difficulty" label="难度">
          <a-select
            :style="{ width: '100px' }"
            placeholder="请选择"
            v-model="form.difficulty"
          >
            <a-option value="0">简单</a-option>
            <a-option value="1">中等</a-option>
            <a-option value="2">困难</a-option>
          </a-select>
        </a-form-item>
      </a-space>
      <a-form-item field="content" label="内容">
        <MdEditor
          :value="form.description"
          :handle-change="onContentMdchange"
        />
      </a-form-item>
      <a-form-item label="配置" :content-flex="false" :merge-props="false">
        <a-space direction="vertical" fill>
          <a-form-item field="judgeConfig.timeLimit1" label="时间限制">
            <a-input v-model="form.timeLimit" placeholder="时间限制" />
          </a-form-item>
          <a-form-item field="judgeConfig.memoryLimit" label="内存限制">
            <a-input v-model="form.memoryLimit" placeholder="内存限制" />
          </a-form-item>
          <a-form-item field="judgeConfig.stackLimit" label="堆栈限制">
            <a-input v-model="form.stackLimit" placeholder="堆栈限制" />
          </a-form-item>
        </a-space>
      </a-form-item>
      <el-upload
        class="upload-demo"
        drag
        headers="POST"
        :action="judgeCaseUploadPath"
        multiple
        :data="fileData"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
      <a-button
        v-if="isUpdate === false"
        status="success"
        @click="addQuestion()"
        >提交
      </a-button>
      <a-button v-else status="success" @click="updateQuestion()"
        >修改
      </a-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeMount, onMounted, ref } from "vue";
import MdEditor from "@/components/MdEditor.vue";
import { useRoute } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import { ProblemControllerService } from "../../../generated/services/ProblemControllerService";
import { useUserStore } from "@/store/UserStore";
import { ProblemVO } from "../../../generated/models/ProblemVO";
import { ProblemAddDTO } from "../../../generated/models/ProblemAddDTO";

const route = useRoute();
const userStore = useUserStore();
const isUpdate = ref(false);

const fileData = ref({
  pid: "deafualt",
});
const judgeCaseUploadPath = ref("");
const form = ref({
  problemId: "",
  title: "",
  author: "",
  description: "",
  tagList: [],
  timeLimit: 0,
  memoryLimit: 0,
  stackLimit: 0,
  input: "",
  output: "",
  source: "",
  difficulty: 0,
  auth: 0,
  judgeMode: "default",
  spjCode: "",
  spjLanguage: "",
} as ProblemAddDTO);

const addQuestion = async () => {
  form.value.author = userStore.userInfo.userName;
  const result = await ProblemControllerService.addProblem(form.value);
  if (result.code === 0) {
    ElMessage.success("添加成功");
  } else if (result.code === 201) {
    ElMessageBox.confirm("没有该标签，是否创建？", "Warning", {
      confirmButtonText: "添加",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(() => {
        if (Array.isArray(form.value.tagList)) {
          for (const s of form.value.tagList) {
            ProblemControllerService.addTag(s);
          }
        }
        ElMessage({
          type: "success",
          message: "添加成功",
        });
      })
      .catch(() => {
        ElMessage({
          type: "info",
          message: "取消添加",
        });
      });
  } else {
    ElMessage.error("添加失败：" + result.message);
  }
};
const updateQuestion = async () => {
  const result = await ProblemControllerService.updateProblem(form.value);
  if (result.code === 0) {
    ElMessage.success("修改成功");
  } else {
    ElMessage.error("修改失败：" + result.message);
  }
};

const onContentMdchange = (v: string) => {
  form.value.description = v;
};

const loadData = async () => {
  const id = route.query.id;
  const update = route.query.update;
  if (update) {
    isUpdate.value = true;
  }
  if (!id) {
    return;
  }
  const res = await ProblemControllerService.getProblem(Number(id));
  if (res.code === 0 && res.data !== undefined) {
    form.value = res.data as ProblemVO;
  } else {
    ElMessage.error("加载失败：" + res.message);
  }
};

onBeforeMount(() => {
  loadData();
  fileData.value.pid = form.value.problemId as string;
});
onMounted(() => {
  judgeCaseUploadPath.value = process.env
    .VUE_APP_JUDGECASE_UPLOAD_PATH as string;
});
</script>

<style scoped>
#addQuestion {
  background: white;
  width: 100%;
  margin: auto;
}

.panel-body {
  padding: 15px;
}
</style>

<template>
  <div id="problem-content">
    <div class="panel-body">
      <h2>{{ problemData.title }}</h2>
      <div class="content">
        <Viewer :value="problemData.description" />
      </div>
      <br />
      <div>
        <CodeEditor ref="codeEditor" />
      </div>
      <div>
        <a-button @click="submit">提交</a-button>
      </div>
      <div :class="[isHide == true ? 'cardIsHide' : 'cardNoHide']">
        代码提交状态：
        <a-spin :class="[isState == true ? 'isHide' : 'noHide']" />
        {{ message }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";
import {
  ProblemControllerService,
  ProblemSubmitControllerService,
} from "../../../generated";
import { ElMessage } from "element-plus";
import CodeEditor from "@/components/CodeEditor.vue";
import { Viewer } from "@bytemd/vue-next";

const route = useRoute();
const isState = ref(true);
const isHide = ref(true);
const message = ref("");
const codeEditor = ref();

const problemData = ref({
  id: 0,
  problemId: 0,
  title: 0,
  author: "",
  tagList: [],
  description: "",
  input: "",
  output: "",
  source: "",
  difficulty: 0,
  auth: 0,
});

const submitData = ref({
  language: "c++",
  code: "",
  pid: 0,
});

const submit = async () => {
  if (
    codeEditor.value.codeEditorData === null ||
    codeEditor.value.codeEditorData === ""
  ) {
    ElMessage.error("代码不能为空");
    return;
  }
  isState.value = false;
  isHide.value = false;
  //先清空以前的数据
  message.value = "";
  submitData.value.code = codeEditor.value.codeEditorData;
  const result = await ProblemSubmitControllerService.doSubmit(
    submitData.value
  );
  isState.value = true;
  if (result.code === 0) {
    message.value = result.message ?? "";
    ElMessage.success("添加成功");
  } else {
    ElMessage.error("添加失败：" + result.message);
  }
};

const loadData = async () => {
  const id = route.query.id;
  if (!id) {
    return;
  }
  const res = await ProblemControllerService.getProblem(id as any);
  if (res.code === 0) {
    problemData.value = res.data as any;
  } else {
    ElMessage.error("加载失败：" + res.message);
  }
};
onBeforeMount(() => {
  loadData();
  submitData.value.pid = route.query.id as any;
});
</script>

<style scoped>
#problem-content {
  background: rgba(0, 0, 0, 0%);
  width: 95%;
  margin: auto;
}

.panel-body {
  padding: 15px;
}
</style>

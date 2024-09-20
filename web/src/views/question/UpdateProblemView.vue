<template>
  <div id="addQuestion">
    <div class="panel-body">
      <a-space style="justify-content: space-between">
        <a-form-item field="title" label="题目">
          <a-input v-model="form.title" placeholder="请输入题目标题" />
        </a-form-item>
        <a-form-item field="tags" label="标签">
          <a-input-tag v-model="form.tags" placeholder="请输入题目标签" />
        </a-form-item>
        <a-form-item field="difficulty" label="难度">
          <a-select
            :style="{ width: '100px' }"
            placeholder="请选择"
            v-model="form.difficulty"
          >
            <a-option>简单</a-option>
            <a-option>中等</a-option>
            <a-option>困难</a-option>
          </a-select>
        </a-form-item>
      </a-space>
      <a-form-item field="content" label="内容">
        <MdEditor :value="form.content" :handle-change="onContentMdchange" />
      </a-form-item>
      <a-form-item field="answer" label="答案">
        <MdEditor :value="form.answer" :handle-change="onAnswerMdchange" />
      </a-form-item>
      <a-form-item label="配置" :content-flex="false" :merge-props="false">
        <a-space direction="vertical" fill>
          <a-form-item field="judgeConfig.timeLimit1" label="时间限制">
            <a-input
              v-model="form.judgeConfig.timeLimit"
              placeholder="时间限制"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.memoryLimit" label="内存限制">
            <a-input
              v-model="form.judgeConfig.memoryLimit"
              placeholder="内存限制"
            />
          </a-form-item>
          <a-form-item field="judgeConfig.stackLimit" label="堆栈限制">
            <a-input
              v-model="form.judgeConfig.stackLimit"
              placeholder="堆栈限制"
            />
          </a-form-item>
        </a-space>
      </a-form-item>
      <div>测试用例</div>
      <a-form-item
        v-for="(judgeCaseItem, index) of form.judgeCase"
        :label="`测试用例${index}`"
        :key="index"
      >
        <a-space align="center">
          <a-form-item
            :field="`form.judgeCase[${index}].input`"
            :label="`输入`"
            :key="index"
          >
            <a-input
              v-model="judgeCaseItem.input"
              placeholder="请输入测试输入用例"
            />
          </a-form-item>
          <a-form-item
            :field="`form.judgeCase[${index}].output`"
            :label="`输出`"
            :key="index"
          >
            <a-input
              v-model="judgeCaseItem.output"
              placeholder="请输入测试输出用例"
            />
          </a-form-item>
          <a-button @click="handleDelete(index)" :style="{ marginLeft: '10px' }"
            >删除
          </a-button>
        </a-space>
      </a-form-item>
      <div>
        <a-button @click="handleAdd">新增测试用例</a-button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import MdEditor from "@/components/MdEditor.vue";
import { useRoute } from "vue-router";
import { QuestionControllerService } from "../../../generated";
import { ElMessage } from "element-plus";

const route = useRoute();
const updatePage = route.path.includes("id");

let form = ref({
  title: "",
  content: "",
  tags: [],
  difficulty: "",
  answer: "",
  isRead: false,
  judgeConfig: {
    timeLimit: 0,
    memoryLimit: 0,
    stackLimit: 0,
  },
  judgeCase: [{ input: "", output: "" }],
});

const loadData = async () => {
  const id = route.query.id;
  if (!id) {
    return;
  }
  const res = await QuestionControllerService.getQuestionVoByIdUsingGet(
    id as any
  );
  if (res.code === 0) {
    form.value = res.data as any;
    console.log(form);
  } else {
    ElMessage.error("加载失败：" + res.message);
  }
};

const handleAdd = () => {
  form.value.judgeCase.push({
    input: "",
    output: "",
  });
};
const handleDelete = (index: number) => {
  form.value.judgeCase.splice(index, 1);
};

const diff = (index: number) => {
  form.value.difficulty = String(index);
};
const onContentMdchange = (v: string) => {
  form.value.content = v;
};
const onAnswerMdchange = (v: string) => {
  form.value.answer = v;
};

onMounted(() => {
  console.log(4);
  loadData();
  console.log(5);
});
</script>

<style scoped>
#addQuestion {
  background: white;
  width: 75%;
  margin: auto;
}

.panel-body {
  padding: 15px;
}
</style>

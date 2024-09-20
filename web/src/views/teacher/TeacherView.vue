<template>
  <div id="teacher">
    <div class="panel-body">
      <div class="title">
        {{ data.title }}
      </div>
      <div :class="[isHide == true ? 'hide' : 'nohide']">{{ data.answer }}</div>
      <div>
        <el-select
          placeholder="章"
          size="large"
          style="width: 240px"
          v-model="context"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-select
          placeholder="节"
          size="large"
          style="width: 240px"
          v-model="context2"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-select
          placeholder="小节"
          size="large"
          style="width: 240px"
          v-model="context3"
        >
          <el-option
            v-for="item in options"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-button type="primary" @click="load()">下一题</el-button>
        <el-button type="success" @click="isHide = false" plain
          >查看答案
        </el-button>
        <div>
          开启背题模式
          <el-switch
            v-model="value2"
            class="ml-2"
            style="
              --el-switch-on-color: #13ce66;
              --el-switch-off-color: #ff4949;
            "
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { TeacherControllerService } from "../../../generated";
import { ElMessage } from "element-plus";

const isHide = ref(true);

let data = ref({
  title: "点击下一题开始",
  answer: "",
  id: 0,
  context: "",
  context2: "",
  context3: "",
});
const context = ref(null);
const context2 = ref(null);
const context3 = ref(null);
const value2 = ref(false);
const options = [
  {
    value: null,
    label: "null",
  },
  {
    value: "1",
    label: "1",
  },
  {
    value: "2",
    label: "2",
  },
  {
    value: "3",
    label: "3",
  },
  {
    value: "4",
    label: "4",
  },
];

const load = async () => {
  const result =
    await TeacherControllerService.getTeacherQuestionVoByIdUsingGet(
      context.value,
      context2.value,
      context3.value
    );
  console.log(result);
  if (result.code === 0) {
    data.value = result.data as any;
    value2.value ? (isHide.value = false) : (isHide.value = true);
  } else {
    ElMessage.error(result.message ? result.message : "获取题目失败");
  }
};
</script>

<style scoped>
#teacher {
  background: #fffffe;
  height: 1000px;
  width: 75%;
  margin: auto auto;
  justify-content: center;
}

.panel-body {
  padding: 15px;
}

.hide {
  display: none;
}

.nohide {
  display: block;
}
</style>

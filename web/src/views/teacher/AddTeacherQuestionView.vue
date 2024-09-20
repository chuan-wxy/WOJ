<template>
  asd
  <!--  <div id="teacher">-->
  <!--    <div class="panel-body">-->
  <!--      <el-input-->
  <!--        v-model="data.title"-->
  <!--        style="width: 240px"-->
  <!--        autosize-->
  <!--        type="textarea"-->
  <!--        placeholder="题目"-->
  <!--        ref="myInput"-->
  <!--      />-->
  <!--      <div></div>-->
  <!--      <el-input-->
  <!--        v-model="data.answer"-->
  <!--        style="width: 240px"-->
  <!--        autosize-->
  <!--        type="textarea"-->
  <!--        placeholder="答案"-->
  <!--      />-->
  <!--      <div></div>-->
  <!--      <el-input v-model="data.context" style="width: 40px" placeholder="章" />-->
  <!--      <el-input v-model="data.context2" style="width: 40px" placeholder="节" />-->
  <!--      <el-input-->
  <!--        v-model="data.context3"-->
  <!--        style="width: 40px"-->
  <!--        placeholder="小节"-->
  <!--      />-->
  <!--      <el-button type="primary" @click="submit()">提交</el-button>-->
  <!--    </div>-->
  <!--  </div>-->
</template>

<script setup lang="ts">
// import { ref } from "vue";
// import { TeacherControllerService } from "../../../generated";
// import { ElMessage } from "element-plus";
//
// const isHide = ref(true);
//
// const myInput = ref(null || HTMLInputElement);
//
// let data = ref({
//   title: "",
//   answer: "",
//   context: "",
//   context2: "",
//   context3: "",
// });
//
// const submit = async () => {
//   const result = await TeacherControllerService.addTeacherUsingPost(data.value);
//   console.log(result);
//   if (result.code === 0) {
//     ElMessage.success("添加成功");
//     data.value.title = "";
//     data.value.answer = "";
//     myInput.value.focus();
//   } else {
//     ElMessage.error("添加失败");
//   }
// };
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

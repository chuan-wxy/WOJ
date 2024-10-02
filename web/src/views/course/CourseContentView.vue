<template>
  <el-menu default-active="2" class="el-menu-vertical-demo">
    <el-sub-menu v-for="(item, index) in data" :key="index" :index="index">
      <template #title>
        <el-icon><location /></el-icon>
        <span> {{ item.name }}</span>
      </template>
      <el-menu-item
        v-for="(item1, index1) in item.children"
        :index="index1"
        :key="index1"
        @click="handClick(item1.id)"
        >WOJ.{{ item1.name }}</el-menu-item
      >
    </el-sub-menu>
  </el-menu>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { CourseControllerService } from "../../../generated";
import { useRoute } from "vue-router";
import { Location } from "@element-plus/icons-vue";
import router from "@/router";

const data = ref();
const parentId = ref();
const route = useRoute();

const loadData = async () => {
  const res = await CourseControllerService.getSecond(parentId.value);
  console.log(res);
  data.value = res.data["1"]?.children;
};

const handClick = (id) => {
  router.push({
    name: "ProblemContentView",
    query: { id: id },
  });
};

const gotoContentById = (id: string) => {
  router.push({
    path: "/problem/content",
    query: {
      id: id,
    },
  });
};

onMounted(() => {
  parentId.value = route.query.id;
  loadData();
});
</script>

<style scoped>
h2 {
  text-align: center;
}
</style>

<!--<template>-->
<!--  <div class="activitydefault">-->
<!--    <div class="panel-body">-->
<!--      <h2 style="color: var(&#45;&#45;theme-bg-color)">AcKing在线活动</h2>-->
<!--    </div>-->
<!--    <el-menu-->
<!--      default-active="2"-->
<!--      class="el-menu-vertical-demo"-->
<!--      @open="handleOpen"-->
<!--      @close="handleClose"-->
<!--    >-->
<!--      <el-sub-menu index="1">-->
<!--        <template #title>-->
<!--          <el-icon><location /></el-icon>-->
<!--          <span> 基础算法</span>-->
<!--        </template>-->
<!--        <el-menu-item-group title="快速排序">-->
<!--          <el-menu-item index="1-1">快速排序</el-menu-item>-->
<!--          <el-menu-item index="1-2">第k个数</el-menu-item>-->
<!--        </el-menu-item-group>-->
<!--        <el-menu-item-group title="归并排序">-->
<!--          <el-menu-item index="1-3">归并排序</el-menu-item>-->
<!--          <el-menu-item index="1-">逆序对的数量</el-menu-item>-->
<!--        </el-menu-item-group>-->
<!--      </el-sub-menu>-->
<!--    </el-menu>-->
<!--    <div v-for="(item, index) in courseData" :key="index">-->
<!--      {{ item.name }}-->
<!--      <div-->
<!--        v-for="(item1, index1) in item.children"-->
<!--        :key="index1"-->
<!--        class="second-floor"-->
<!--      >-->
<!--        {{ item1.name }}-->
<!--        <div-->
<!--          v-for="(item2, index2) in item1.children"-->
<!--          :key="index2"-->
<!--          class="third-floor"-->
<!--        >-->
<!--          {{ item2.name }}-->
<!--          <div-->
<!--            v-for="(item3, index3) in questionData"-->
<!--            :key="index3"-->
<!--            class="second-floor"-->
<!--          >-->
<!--            <div v-if="item3.parentId === item2.id">-->
<!--              {{ item3.name }}-->
<!--              <button @click="gotoquestion(item3.id)">前往</button>-->
<!--            </div>-->
<!--          </div>-->
<!--        </div>-->
<!--      </div>-->
<!--    </div>-->
<!--  </div>-->
<!--</template>-->

<!--<script setup lang="ts">-->
<!--import { onMounted, ref } from "vue";-->
<!--import { CourseControllerService } from "../../../generated";-->
<!--import { useRoute, useRouter } from "vue-router";-->

<!--const route = useRoute();-->
<!--const router = useRouter();-->
<!--const courseData = ref();-->

<!--const questionData = ref();-->
<!--const parentId = ref(0);-->

<!--const gotoquestion = (id) => {-->
<!--  router.push({-->
<!--    path: "/problem/content",-->
<!--    query: {-->
<!--      id: id,-->
<!--    },-->
<!--  });-->
<!--};-->

<!--const loadCourseData = async () => {-->
<!--  const res = await CourseControllerService.getCourseList();-->
<!--  courseData.value = res.data;-->
<!--};-->

<!--</script>-->

<!--<style scoped>-->
<!--.activitydefault {-->
<!--  background: white;-->
<!--  width: 75%;-->
<!--  margin: auto;-->
<!--}-->

<!--.panel-body {-->
<!--  padding: 15px;-->
<!--}-->

<!--h2 {-->
<!--  text-align: center;-->
<!--  color: var(&#45;&#45;theme-bg-color);-->
<!--}-->

<!--.second-floor {-->
<!--  margin-left: 50px;-->
<!--}-->

<!--.third-floor {-->
<!--  margin-left: 50px;-->
<!--}-->
<!--</style>-->

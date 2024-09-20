<template>
  <div class="activitydefault">
    <div class="panel-body">
      <h2 style="color: var(--theme-bg-color)">AcKing在线活动</h2>
    </div>
    <div v-for="(item, index) in courseData" :key="index">
      {{ item.title }}
      <div
        v-for="(item1, index1) in item.children"
        :key="index1"
        class="second-floor"
      >
        {{ item1.title }}
        <div
          v-for="(item2, index2) in item1.children"
          :key="index2"
          class="third-floor"
        >
          {{ item2.title }}
          <div
            v-for="(item3, index3) in questionData"
            :key="index3"
            class="second-floor"
          >
            <div v-if="item3.parentId === item2.id">
              {{ item3.title }}
              <button @click="gotoquestion(item3.id)">前往</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import {
  ActivityControllerService,
  QuestionControllerService,
} from "../../../generated";
import { useRoute, useRouter } from "vue-router";

const route = useRoute();
const router = useRouter();
const data = ref();
const courseData = ref();

const questionData = ref();
const parentId = ref(0);

const gotoquestion = (id) => {
  router.push({
    path: "/problem/content",
    query: {
      id: id,
    },
  });
};
const loadData = async () => {
  const res = await ActivityControllerService.selectByParentIdUsingPost(
    parentId.value as any
  );
  data.value = res.data;
  console.log(data.value);
};

const loadCourseData = async () => {
  const res = await ActivityControllerService.loadTreeUsingPost(
    parentId.value as any
  );
  courseData.value = res.data;
  console.log(courseData.value);
};

const loadQuestionData = async () => {
  const res = await QuestionControllerService.getQuestionTitleUsingGet(
    parentId.value as any
  );
  questionData.value = res.data;
};

onMounted(() => {
  parentId.value = route.query.id as any;
  loadData();
  loadCourseData();
  loadQuestionData();
});
</script>

<style scoped>
.activitydefault {
  background: white;
  width: 75%;
  margin: auto;
}

.panel-body {
  padding: 15px;
}

h2 {
  text-align: center;
  color: var(--theme-bg-color);
}

.second-floor {
  margin-left: 50px;
}

.third-floor {
  margin-left: 50px;
}
</style>

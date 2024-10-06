<template>
  <div id="activity-content">
    <div class="panel-body">
      <h1 style="text-align: center">{{ activityData.title }}</h1>
      <div class="footer">发布于： {{ activityData.createTime }}</div>
      <div class="content">
        <Viewer :value="activityData.description" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onBeforeMount, ref } from "vue";
import { useRoute } from "vue-router";
import { ElMessage } from "element-plus";
import { ActivityControllerService } from "../../../generated/services/ActivityControllerService";
import { ActivityContentVO } from "../../../generated/models/ActivityContentVO";
import { Viewer } from "@bytemd/vue-next";

const route = useRoute();
const activityData = ref({
  id: 0,
  title: "",
  description: "",
  createTime: "",
} as ActivityContentVO);

const loadData = async () => {
  const id = Number(route.query.id);
  if (!id) {
    return;
  }
  const res = await ActivityControllerService.getActivity(id);
  if (res.code === 0) {
    activityData.value = res.data as ActivityContentVO;
  } else {
    ElMessage.error("加载失败：" + res.message);
  }
};

onBeforeMount(() => {
  loadData();
});
</script>

<style scoped>
#activity-content {
  background: rgba(0, 0, 0, 0%);
  width: 95%;
  height: 100vh;
  margin: auto;
}

.panel-body {
  padding: 15px;
}

.footer {
  text-align: right;
}
</style>

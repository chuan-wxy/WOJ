<template>
  <div id="announcement-content">
    <div class="panel-body">
      <h1 style="text-align: center">{{ announcementData.title }}</h1>
      <div class="footer">发布于： {{ announcementData.createTime }}</div>
      <div class="content">
        <Viewer :value="announcementData.content" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import {
  AnnouncementContentVO,
  AnnouncementControllerService,
} from "../../../generated";
import { ElMessage } from "element-plus";
import { useRoute } from "vue-router";
import { Viewer } from "@bytemd/vue-next";

const route = useRoute();
const announcementData = ref([] as AnnouncementContentVO);

const loadData = async () => {
  const id = Number(route.query.id);
  if (!id) {
    return;
  }
  const res = await AnnouncementControllerService.getAnnouncement(id as number);
  if (res.code === 0) {
    announcementData.value = res.data as any;
  } else {
    ElMessage.error("加载公告失败");
  }
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
#announcement-content {
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

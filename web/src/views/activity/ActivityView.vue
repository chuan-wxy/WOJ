<template>
  <div id="courses">
    <div class="panel-body">
      <div class="coursesdefault">
        <h2>WOJ活动</h2>
      </div>
      <a-list class="dataBox">
        <a-list-item
          v-for="(item, index) in data"
          :key="index"
          @click="goto(item.id)"
        >
          <a-list-item-meta :title="item.name" :description="item.description">
            <template #avatar>
              <a-avatar shape="square">
                <img alt="avatar" :src="item.avatar" />
              </a-avatar>
            </template>
          </a-list-item-meta>
        </a-list-item>
      </a-list>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, ref } from "vue";
import { CourseControllerService } from "../../../generated";
import { useRouter } from "vue-router";

const data = ref();

const loadData = async () => {
  const res = await CourseControllerService.getFirst();
  data.value = res.data;
};

const router = useRouter();

const goto = (id: string) => {
  router.push({
    path: "/course/content",
    query: {
      id: id,
    },
  });
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
#courses {
  height: 800px;
  width: 75%;
  margin: auto;
  justify-content: center;
}

.panel-body {
  padding: 15px;
}

.dataBox {
  color: var(--theme-color);
  padding-bottom: 40px;
}

h2 {
  text-align: center;
}
:deep(.arco-list-item-meta-title) {
  color: var(--theme-color);
  font-size: 24px;
}
:deep(.arco-list-item-meta-description) {
  color: var(--theme-color);
}
:deep(.el-menu) {
  background-color: var(--theme-bg-color);
}
:deep(.el-sub-menu__title) {
  color: var(--theme-color);
}
:deep(.el-sub-menu__title:hover) {
  background-color: var(--hover-menu-bg);
}
:deep(.el-menu-item:hover) {
  background-color: var(--hover-menu-bg);
}
:deep(.el-menu-item) {
  color: var(--theme-color);
  background-color: var(--theme-bg-color);
}
</style>

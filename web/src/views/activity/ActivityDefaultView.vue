<template>
  <div class="activitydefault">
    <div class="panel-body">
      <h2>AcKing在线活动</h2>
    </div>
    <a-list>
      <a-list-item
        v-for="(item, index) in data"
        :key="index"
        @click="goto(item.id)"
      >
        <a-list-item-meta :title="item.title" :description="item.description">
          <template #avatar>
            <a-avatar shape="square">
              <img alt="avatar" :src="item.avatar" />
            </a-avatar>
          </template>
        </a-list-item-meta>
      </a-list-item>
    </a-list>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import { ActivityControllerService } from "../../../generated";
import { useRouter } from "vue-router";

const data = ref();

const loadData = async () => {
  const res = await ActivityControllerService.getFirstFloorUsingPost();
  data.value = res.data;
  console.log(data.value);
};

const router = useRouter();

const goto = (id: string) => {
  router.push({
    path: "/activity/content",
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
}
</style>

<template>
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
</template>

<script setup lang="ts">
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

.dataBox {
  color: var(--theme-color);
}

h2 {
  text-align: center;
}
</style>

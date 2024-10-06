<template>
  <div id="home">
    <div style="display: flex">
      <el-card style="min-width: 510px; margin-left: 20px; border-radius: 10px">
        <template #header>
          <div class="card-header" style="width: 470px">
            <span>公告栏</span>
            <router-link style="margin-left: 350px" class="rlink"
              >查看更多</router-link
            >
          </div>
        </template>
        <el-table :data="tableData" style="width: 100%">
          <el-table-column label="标题">
            <template #default="scope">
              <router-link
                style="font-weight: 500"
                class="rlink"
                :to="{
                  name: 'AnnouncementView',
                  query: { id: scope.row.id },
                }"
                >{{ scope.row.title }}</router-link
              >
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="发布时间" />
        </el-table>
      </el-card>
    </div>
  </div>
</template>

<script setup lang="ts">
import {
  AnnouncementControllerService,
  AnnouncementTitleVO,
} from "../../generated";
import { onMounted, ref } from "vue";
import { ElMessage } from "element-plus";

const tableData = ref([] as AnnouncementTitleVO[]);
const loadData = async () => {
  const res = await AnnouncementControllerService.getAnnouncementList();
  if (res.code === 0) {
    tableData.value = res.data as AnnouncementTitleVO[];
  } else {
    ElMessage.error("加载公告栏失败");
  }
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
#home {
  height: 100vh;
}

.rlink {
  text-decoration: none;
  cursor: pointer;
  color: var(--theme-color);
}

.readmore div {
  text-align: right;
}

router-link {
  text-align: right;
}
:deep(.el-card) {
  background-color: rgba(0, 0, 0, 0%);
  color: var(--theme-color);
}

:deep(.el-table) {
  background-color: rgba(0, 0, 0, 0%);
  color: var(--theme-color);
}

:deep(.el-table tr) {
  background-color: rgba(0, 0, 0, 0);
}
:deep(.el-table th.el-table__cell) {
  background-color: rgba(0, 0, 0, 0%);
}
:deep(
    .el-table--enable-row-hover .el-table__body tr:hover > td.el-table__cell
  ) {
  background-color: rgba(0, 0, 0, 0);
}
</style>

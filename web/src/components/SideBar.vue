<template>
  <div id="sidebar">
    <el-row class="tac">
      <el-col :span="12">
        <el-menu router default-active="1" class="el-menu-vertical-demo">
          <el-sub-menu
            v-for="(item, index) in barData"
            :key="index"
            :index="index"
          >
            <template #title>
              <el-icon>
                <location />
              </el-icon>
              <span>{{ item.title }}</span>
            </template>
            <el-menu-item-group
              v-for="(item2, index2) in item.child"
              :key="index2"
              :title="item2.title"
            >
              <el-menu-item
                v-for="(item3, index3) in item2.child"
                :key="index3"
                :index="item3.index"
              >
                {{ item3.title }}
              </el-menu-item>
            </el-menu-item-group>
          </el-sub-menu>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>
<script setup lang="ts">
import { reactive } from "vue";

const barData = reactive([
  {
    title: "用户管理",
    index: "1",
    child: [
      {
        title: "增删改查",
        child: [
          {
            title: "用户列表",
            index: "/admin/userlist",
          },
          {
            title: "添加管理",
            index: "/admin/addmanager",
          },
        ],
      },
    ],
  },
  {
    title: "题目管理",
    index: "2",
    child: [
      {
        title: "增删改查",
        child: [
          {
            title: "新建课程",
            index: "/admin/addcourse",
          },
          {
            title: "添加题目",
            index: "/admin/addproblem",
          },
        ],
      },
    ],
  },
]);
</script>

<style scoped>
#sidebar :deep(.el-menu) {
  background-color: var(--theme-bg-color);
}

#sidebar :deep(.el-sub-menu__title),
#sidebar :deep(.el-menu-item) {
  color: var(--theme-color);
}

#sidebar :deep(.el-menu-item.is-active) {
  background-color: var(--border-color);
}
:deep(.el-col) {
  max-width: 100%;
}
</style>

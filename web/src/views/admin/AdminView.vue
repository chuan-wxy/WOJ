<template>
  <div id="adminview">
    <el-container>
      <el-aside style="width: 150px">
        <SideBarView />
      </el-aside>
      <el-main style="padding-top: 0px; width: 100%">
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts" setup>
import SideBarView from "@/components/SideBar.vue";
</script>
<style scoped>
#adminview {
  height: 800px;
  padding-top: 0px;
}
</style>

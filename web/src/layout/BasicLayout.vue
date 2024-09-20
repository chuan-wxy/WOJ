<template>
  <div id="basicLayout">
    <div class="common-layout">
      <el-container>
        <el-aside width="200px" class="left-side">side</el-aside>
        <el-container>
          <el-header class="header">
            <HeaderMenu />
          </el-header>
          <el-main class="main-container">
            <div class="content-wrapper">
              <router-view />
            </div>
          </el-main>
        </el-container>
      </el-container>
    </div>
  </div>
</template>

<script setup lang="ts">
import HeaderMenu from "@/components/HeaderMenu.vue";
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  flex-shrink: 0;
  height: 58px;
  width: 100%;
  padding: 0px;
  white-space: nowrap;
}

.left-side {
  flex-basis: 120px;
  border-right: 1px solid var(--border-color);
  padding: 26px;
  overflow: auto;
  flex-shrink: 0;
}

.main-container {
  display: flex;
  flex-direction: column;
  flex-grow: 1;
  height: 100%;
  //max-height: 900px;
  padding: 0px;
}

.content-wrapper {
  display: flex;
  flex-direction: column;
  color: var(--theme-color);
  padding-top: 20px;
  padding-bottom: 20px;
  height: 100%;
  width: 100%;
  overflow: auto;
  background-color: var(--theme-bg-color);
  max-height: 760px;
}

.content-wrapper .el-form-item__content {
  background-color: #4cae4c;
}

@media screen and (max-width: 510px) {
  .content-wrapper {
    padding: 20px;
  }
}
</style>

<template>
  <div id="userLaoyout">
    <a-layout style="min-height: 100vh">
      <!--      <a-layout-header class="header">用戶佈局</a-layout-header>-->
      <a-layout-content class="content">
        <user-regist-view />
      </a-layout-content>
      <a-layout-footer class="footer">
        <a href="https://localhost:8080"> acking编程导航 by wxy </a>
      </a-layout-footer>
    </a-layout>
  </div>
</template>

<script setup lang="ts">
import UserRegistView from "@/views/user/UserRegistView.vue";
</script>

<style scoped>
#userLaoyout {
}

#userLaoyout .header {
  padding: 0;
  margin-bottom: 10px;
  box-shadow: #eee 1px 1px 5px;
}

#userLaoyout .content {
  background: linear-gradient(to right, #aaa, #fff);
  margin-bottom: 16px;
}

#userLaoyout .footer {
  background: #efefef;
  padding: 16px;
  position: sticky;
  bottom: 0;
  left: 0;
  right: 0;
  text-align: center;
}
</style>

<template>
  <div id="header" :class="{ wide: isWide }">
    <div class="header-menu">
      <router-link
        v-for="(item, index) in userBar"
        :key="index"
        :index="index"
        :to="item.path"
        :class="{ 'is-active': index === commonStore.isActiveIndexOfBar }"
      >
        {{ item.name }}
      </router-link>
    </div>
    <div class="search-bar">
      <input
        type="text"
        placeholder="Search"
        @focus="isWide = true"
        @blur="isWide = false"
      />
    </div>
    <div
      class="admin header-menu"
      v-if="
        userStore.userRole.includes('admin') ||
        userStore.userRole.includes('root')
      "
    >
      <router-link
        v-for="(item, index) in adminBar"
        :key="index"
        :index="index"
        :to="item.path"
        :class="{ 'is-active': index + 3 === commonStore.isActiveIndexOfBar }"
      >
        {{ item.name }}
      </router-link>
    </div>
    <div class="header-profile">
      <div class="dark-light" @click="changeTheme()">
        <svg
          viewBox="0 0 24 24"
          stroke="currentColor"
          stroke-width="1.5"
          fill="none"
          stroke-linecap="round"
          stroke-linejoin="round"
        >
          <path d="M21 12.79A9 9 0 1111.21 3 7 7 0 0021 12.79z"></path>
        </svg>
      </div>
      <div class="avatar-bar" v-if="userStore.userInfo.userAccount !== null">
        <el-dropdown trigger="click" @command="handleCommand">
          <el-avatar shape="square" :src="userStore.userInfo.avatar" />
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="/user/profile"
                >个人信息
              </el-dropdown-item>
              <el-dropdown-item @click="logout()">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <div class="header-menu" v-else>
        <router-link
          to="/login"
          :class="{ 'is-active': 10 === commonStore.isActiveIndexOfBar }"
          @click="commonStore.setActiveIndex(10)"
          >登录
        </router-link>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import router from "@/router";
import { onBeforeMount, ref } from "vue";
import { useUserStore } from "@/store/UserStore";
import { useCommonStore } from "@/store/CommonStore";

const userStore = useUserStore();
const commonStore = useCommonStore();
const userInfo = ref();

const handleCommand = (command: string | number | object) => {
  router.push({
    path: command as string,
  });
};

const seleteKey = ref(["/"]);
// 选中项
// 搜索选中是置true
const isWide = ref(false);
const userBar = ref([
  {
    name: "首页",
    path: "/home",
  },
  {
    name: "课程",
    path: "/activity",
  },
  {
    name: "题库",
    path: "/problem",
  },
]);
const adminBar = ref([
  {
    name: "管理",
    path: "/admin",
  },
]);

// const visibleRoutes = computed(() => {
//   if (!userStore.userRole.includes("admin")) return [];
//   const routes = router.getRoutes();
//   return routes.filter((item, index) => {
//     if (item.meta?.isHide === true) {
//       return false;
//     }
//     if (item.meta?.premission === PERMISSION_ENUM.MANGE) {
//       return true;
//     } else return false;
//   });
// });

const changeTheme = () => {
  document.body.classList.toggle("light-mode");
};

const logout = () => {
  userStore.clearUserInfo();
};

onBeforeMount(() => {
  userInfo.value = userStore.userInfo;
});
//监听路由跳转，路由跳转后
router.afterEach((to, from, failure) => {
  seleteKey.value = [to.path];
});
</script>
<style scoped>
#header {
  width: 100%;
  padding: 0 !important;
  display: flex;
  align-items: center !important;
}

.wide {
  max-width: 600px;
  margin: auto;
  transition: 0.4s;
  box-shadow: 0 0 0 1px var(--border-color);
  padding-left: 0;
}

.header-menu {
  display: flex;
  align-items: center;
}

.header-menu a {
  padding: 20px 30px;
  text-decoration: none;
  color: var(--inactive-color);
  border-bottom: 2px solid transparent;
  transition: 0.3s;
}

.header-menu a.is-active,
.header-menu a:hover {
  color: var(--theme-color);
  border-bottom: 2px solid var(--theme-color);
}

.search-bar {
  height: 40px;
  display: flex;
  width: 100%;
  max-width: 400px;
  padding-left: 16px;
  border-radius: 4px;
}

.search-bar input {
  width: 100%;
  height: 100%;
  border: none;
  background-color: var(--search-bg);
  border-radius: 4px;
  font-family: var(--body-font);
  font-size: 15px;
  font-weight: 500;
  padding: 0 20px 0 40px;
  box-shadow: 0 0 0 2px rgba(134, 140, 160, 0.02);
  background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 56.966 56.966' fill='%23717790c7'%3e%3cpath d='M55.146 51.887L41.588 37.786A22.926 22.926 0 0046.984 23c0-12.682-10.318-23-23-23s-23 10.318-23 23 10.318 23 23 23c4.761 0 9.298-1.436 13.177-4.162l13.661 14.208c.571.593 1.339.92 2.162.92.779 0 1.518-.297 2.079-.837a3.004 3.004 0 00.083-4.242zM23.984 6c9.374 0 17 7.626 17 17s-7.626 17-17 17-17-7.626-17-17 7.626-17 17-17z'/%3e%3c/svg%3e");
  background-size: 14px;
  background-repeat: no-repeat;
  background-position: 16px 48%;
  color: var(--theme-color);
}

.wide .header-menu,
.wide .header-profile {
  display: none;
}

.wide .search-bar {
  max-width: 600px;
  margin: auto;
  transition: 0.4s;
  box-shadow: 0 0 0 1px var(--border-color);
  padding-left: 0;
}

.header-profile {
  display: flex;
  align-items: center;
  padding: 0 16px 0 40px;
  flex-shrink: 0;
  margin-left: auto;
}

.avatar-bar {
  margin-left: 42px;
}

.dark-light {
  background-color: var(--dropdown-bg) !important;
  box-shadow: -1px 3px 8px -1px rgba(0, 0, 0, 0.2) !important;
  padding: 8px;
  border-radius: 50%;
  z-index: 3;
  cursor: pointer;
}

.dark-light svg {
  width: 24px;
  flex-shrink: 0;
  fill: #ffce45 !important;
  stroke: #ffce45 !important;
  transition: 0.5s;
}
</style>

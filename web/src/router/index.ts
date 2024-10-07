import { createRouter, createWebHistory } from "vue-router";
import HomeView from "@/views/HomeView.vue";
import ProblemView from "@/views/question/ProblemListView.vue";
import UserRegistView from "@/views/user/UserRegistView.vue";
import { useUserStore } from "@/store/UserStore";
import { useCommonStore } from "@/store/CommonStore";
import AdminView from "@/views/admin/AdminView.vue";
import BasicLayout from "@/layout/BasicLayout.vue";
import UserProfileView from "@/views/user/UserProfileView.vue";
import ProblemContentView from "@/views/question/ProblemContentView.vue";
import AddProblemView from "@/views/admin/AddProblemView.vue";
import PERMISSION_ENUM from "@/access/permissionEnum";
import UserListView from "@/views/admin/UserListView.vue";
import AddManagerView from "@/views/admin/AddManagerView.vue";
import AddCourseView from "@/views/admin/AddCourseView.vue";
import ActivityView from "@/views/activity/ActivityView.vue";
import ACCESS_ENUM from "@/access/accessEnum";
import { ElMessage } from "element-plus";
import AddActivityView from "@/views/admin/AddActivityView.vue";
import ActivityContentView from "@/views/activity/ActivityContentView.vue";
import AnnouncementView from "@/views/announcement/AnnouncementView.vue";
import AddAnnouncementView from "@/views/admin/AddAnnouncementView.vue";

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "首页",
      component: BasicLayout,
      redirect: { name: "HomeView" },
      children: [
        {
          path: "home",
          name: "HomeView",
          component: HomeView,
        },
        {
          path: "problem",
          name: "ProblemView",
          component: ProblemView,
        },
        {
          path: "problem/content",
          name: "ProblemContentView",
          component: ProblemContentView,
          meta: {
            isHide: true,
          },
        },
        {
          path: "admin",
          name: "AdminView",
          component: AdminView,
          meta: {
            premission: PERMISSION_ENUM.MANGE,
          },
          children: [
            {
              path: "userlist",
              name: "UserListView",
              component: UserListView,
            },
            {
              path: "addmanager",
              name: "AddManagerView",
              component: AddManagerView,
            },
            {
              path: "addproblem",
              name: "AddProblemView",
              component: AddProblemView,
            },
            {
              path: "addcourse",
              name: "AddCourseView",
              component: AddCourseView,
            },
            {
              path: "addactivity",
              name: "AddActivityView",
              component: AddActivityView,
            },
            {
              path: "addannouncement",
              name: "AddAnnouncementView",
              component: AddAnnouncementView,
            },
          ],
        },
        {
          path: "user/profile",
          name: "UserProfileView",
          component: UserProfileView,
        },
        {
          path: "login",
          name: "UserRegistView",
          component: UserRegistView,
          meta: {
            premission: PERMISSION_ENUM.NO,
          },
        },
        {
          path: "activity",
          name: "ActivityView",
          component: ActivityView,
          meta: {
            premission: PERMISSION_ENUM.NO,
          },
        },
        {
          path: "activity/content",
          name: "ActivityContentView",
          component: ActivityContentView,
          meta: {
            premission: PERMISSION_ENUM.NO,
          },
        },
        {
          path: "announcement",
          name: "AnnouncementView",
          component: AnnouncementView,
          meta: {
            premission: PERMISSION_ENUM.NO,
          },
        },
        //课程页面暂时弃用
        // {
        //   path: "course",
        //   name: "CourseView",
        //   component: CourseView,
        //   children: [
        //     {
        //       path: "",
        //       name: "ActivityDefaultView",
        //       component: ActivityDefaultView,
        //     },
        //     {
        //       path: "content",
        //       name: "ActivityContentView",
        //       component: ActivityContentView,
        //     },
        //   ],
        // },
      ],
    },
  ],
});

router.beforeEach((to, from, next) => {
  const userStore = useUserStore();
  const commonStore = useCommonStore();
  const userRole: string[] = userStore.userRole;

  if (to.path === "/home") {
    commonStore.setActiveIndex(0);
  } else if (to.path.includes("/activity")) {
    commonStore.setActiveIndex(1);
  } else if (to.path.includes("problem")) {
    commonStore.setActiveIndex(2);
  } else if (to.path.includes("/admin")) {
    commonStore.setActiveIndex(3);
  } else {
    commonStore.setActiveIndex(-1);
  }

  const needAccess = (to.meta?.access as string) ?? PERMISSION_ENUM.NO;
  // 不需要特殊权限
  if (needAccess === PERMISSION_ENUM.NO) {
    next();
  } else {
    if (!userRole) {
      ElMessage.error("请先登录");
      next(`/login?redirect=${to.path}`);
    }
    // 需要提交权限
    if (needAccess === PERMISSION_ENUM.SUBMIT) {
      if (
        userRole.indexOf(ACCESS_ENUM.NO_SUBMIT_USER) === -1 &&
        userRole.indexOf(ACCESS_ENUM.NO_SUBMIT_MUTE_USER) === -1 &&
        userRole.indexOf(ACCESS_ENUM.NO_SUBMIT_NO_DISCUSS_USER) === -1
      ) {
        next();
      } else {
        next("/NoAuth");
      }
    }
    // 需要发帖权限,需要讨论权限
    if (
      needAccess === PERMISSION_ENUM.POST_MESSAGE ||
      needAccess === PERMISSION_ENUM.DISCUSS
    ) {
      if (
        userRole.indexOf(ACCESS_ENUM.NO_DISCUSS_USER) === -1 &&
        userRole.indexOf(ACCESS_ENUM.MUTE_USER) === -1 &&
        userRole.indexOf(ACCESS_ENUM.NO_SUBMIT_NO_DISCUSS_USER) === -1 &&
        userRole.indexOf(ACCESS_ENUM.NO_SUBMIT_MUTE_USER) === -1
      ) {
        next();
      } else {
        next("/NoAuth");
      }
    }
    // 需要管理权限
    if (needAccess === PERMISSION_ENUM.MANGE) {
      if (
        userRole.indexOf(ACCESS_ENUM.ROOT) === 1 ||
        userRole.indexOf(ACCESS_ENUM.ADMIN) === 1
      ) {
        next();
      } else {
        next("/NoAuth");
      }
    }
    // 需要题目管理权限
    if (needAccess === PERMISSION_ENUM.PROBLEM_MANGE) {
      if (
        userRole.indexOf(ACCESS_ENUM.ROOT) === 1 ||
        userRole.indexOf(ACCESS_ENUM.ADMIN) === 1 ||
        userRole.indexOf(ACCESS_ENUM.PROBLEM_ADMIN) === 1
      ) {
        next();
      } else {
        next("/NoAuth");
      }
    }
    // 需要回复权限
    if (needAccess === PERMISSION_ENUM.REPLY) {
      if (
        userRole.indexOf(ACCESS_ENUM.MUTE_USER) === -1 &&
        userRole.indexOf(ACCESS_ENUM.NO_SUBMIT_MUTE_USER) === -1
      ) {
        next();
      } else {
        next("/NoAuth");
      }
    }
  }
  next();
});
export default router;

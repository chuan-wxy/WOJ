import { defineStore } from "pinia";
import { UserControllerService } from "../../generated";
import { ElMessage } from "element-plus";

export const useUserStore = defineStore("user", {
  state: () => {
    return {
      userInfo: {
        jwt: "",
        userAccount: null,
        userName: "",
        userProfile: "",
        school: "",
        course: "",
        number: "",
        gender: "",
        github: "",
        blog: "",
        avatar: "",
        signature: "",
        titleName: "",
        titleColor: "",
        createTime: "",
      },
      userRole: ["not_login"],
    };
  },
  persist: {
    afterRestore: (ctx) => {
      const userStore = useUserStore();
      userStore.checkJWT(userStore.userInfo.jwt);
    },
  },
  getters: {},
  actions: {
    // 清除过期信息
    clearUserInfo() {
      (this.userInfo.jwt = ""),
        (this.userInfo.userAccount = null),
        (this.userInfo.userName = ""),
        (this.userInfo.userProfile = ""),
        (this.userInfo.school = ""),
        (this.userInfo.course = ""),
        (this.userInfo.number = ""),
        (this.userInfo.gender = ""),
        (this.userInfo.github = ""),
        (this.userInfo.blog = ""),
        (this.userInfo.avatar = ""),
        (this.userInfo.signature = ""),
        (this.userInfo.titleName = ""),
        (this.userInfo.titleColor = ""),
        (this.userInfo.createTime = ""),
        (this.userRole = []);
    },
    async checkJWT(JWT: string) {
      const res = await UserControllerService.checkJwt(JWT);
      if (res && res.code === 0) {
        if (res.data === true) {
          localStorage.removeItem("user");
          this.clearUserInfo();
        }
      } else {
        ElMessage.error("系统错误");
      }
    },
    async getLoginUser() {
      const res = await UserControllerService.getLoginUser();
      if (res && res.code === 0) {
        this.userInfo = res.data as any;
      }
    },
    async getUserRole() {
      const res = await UserControllerService.getRole(
        this.userInfo.userAccount as any
      );
      if (res && res.code === 0) {
        this.userRole = res.data as any;
      }
    },
  },
});

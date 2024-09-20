<template>
  <div id="userprofile">
    <div class="panel-body">
      <div>
        <h2>个人信息</h2>
      </div>
      <el-form :model="userProfile" label-width="auto" style="max-width: 600px">
        <el-form-item label="账号">
          <el-input disabled v-model="userProfile.userAccount" />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="userProfile.userName" />
        </el-form-item>
        <el-form-item label="用户简介">
          <el-input v-model="userProfile.userProfile" />
        </el-form-item>
        <el-form-item label="github地址">
          <el-input v-model="userProfile.github" />
        </el-form-item>
        <el-form-item label="博客地址">
          <el-input v-model="userProfile.blog" />
        </el-form-item>
        <el-form-item label="性别">
          <el-select v-model="userProfile.gender">
            <el-option label="保密" value="保密" />
            <el-option label="男" value="男" />
            <el-option label="女" value="女" />
          </el-select>
        </el-form-item>
        <el-form-item label="学校">
          <el-input v-model="userProfile.school" />
        </el-form-item>
        <el-form-item label="个性签名">
          <el-input v-model="userProfile.signature" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">修改</el-button>
          <el-button>Cancel</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<style scoped>
#userprofile {
  height: 800px;
  width: 75%;
  margin: auto;
  justify-content: center;
}

.panel-body {
  padding: 15px;
}

#userprofile >>> .el-form-item__content div,
#userprofile >>> .el-form-item__content > div,
#userprofile >>> .el-popper {
  background-color: rgba(0, 0, 0, 0%) !important;
  box-shadow: 0 0 0 1px var(--border-color);
}

#userprofile >>> .el-form-item__label,
#userprofile >>> .el-input__inner {
  color: var(--theme-color);
}

#userprofile >>> .el-select-dropdown__item.is-hovering,
#userprofile >>> .is-hovering,
#userprofile >>> .el-popper,
#userprofile >>> .el-scrollbar li {
  background-color: rgba(0, 0, 0, 0%) !important;
}

#userprofile >>> .el-select__selection,
#userprofile >>> .el-select__selected-item {
  box-shadow: 0 0 0 0px var(--border-color) !important;
}
</style>

<script setup lang="ts">
import { onBeforeMount, reactive } from "vue";
import { useUserStore } from "@/store/UserStore";
import { UserControllerService } from "../../../generated";
import { ElMessage } from "element-plus";

const userStore = useUserStore();

let userProfile = reactive({
  userAccount: "",
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
});

const onSubmit = async () => {
  const res = await UserControllerService.updateProfile(userProfile);
  if (res.code === 0) {
    ElMessage.success("修改成功");
  } else {
    ElMessage.error("修改失败" + res.data);
  }
};
const loadData = () => {
  userProfile = userStore.userInfo as any;
};

onBeforeMount(() => {
  loadData();
});
</script>

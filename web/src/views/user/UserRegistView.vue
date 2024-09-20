<template>
  <div class="body">
    <div class="container" id="container">
      <div class="form-container sign-up-container">
        <div class="form">
          <h1>Create Account</h1>
          <div class="social-container">
            <a href="#" class="social">
              <i class="fab fa-alipay" />
            </a>
            <a href="#" class="social">
              <i class="fab fa-qq" />
            </a>
            <a href="#" class="social">
              <i class="fab fa-weixin" />
            </a>
          </div>
          <span>or use your email for registration</span>
          <input
            type="text"
            placeholder="请输入邮箱"
            v-model="registerData.userAccount"
          />
          <div class="email">
            <input
              type="text"
              placeholder="验证码"
              class="emailcode"
              v-model="registerData.captcha"
            />
            <button ref="getRegisterCodeButton" @click="getRegisterCode()">
              {{ BUttonText }}
            </button>
          </div>
          <input
            type="password"
            placeholder="请输入密码"
            v-model="registerData.userPassword"
          />
          <input
            type="password"
            placeholder="请确认密码"
            v-model="registerData.rePassword"
          />
          <button @click="regist()">注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册</button>
        </div>
      </div>
      <div class="form-container sign-in-container">
        <div class="form">
          <h1>Sign in</h1>
          <div class="social-container">
            <a href="#" class="social">
              <i class="fab fa-alipay" />
            </a>
            <a href="#" class="social">
              <i class="fab fa-qq" />
            </a>
            <a href="#" class="social">
              <i class="fab fa-weixin" />
            </a>
          </div>
          <span>or use your account</span>
          <input
            type="text"
            placeholder="账号"
            v-model="loginData.userAccount"
          />
          <input
            type="password"
            placeholder="密码"
            v-model="loginData.userPassword"
          />
          <a href="#">Forgot your password?</a>
          <button @click="login">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
        </div>
      </div>
      <div class="overlay-container">
        <div class="overlay">
          <div class="overlay-panel overlay-left">
            <h1>Welcome Back!</h1>
            <p>
              To keep connected with us please login with your personal info
            </p>
            <button class="ghost" id="signIn">
              登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录
            </button>
          </div>
          <div class="overlay-panel overlay-right">
            <h1>Hello, Friend!</h1>
            <p>Enter your personal details and start journey with us</p>
            <button class="ghost" id="signUp">
              注&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;册
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, reactive, ref } from "vue";
import {
  UserControllerService,
  UserLoginDTO,
  UserRegisterDTO,
} from "../../../generated";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { useUserStore } from "@/store/UserStore";
import { useCommonStore } from "@/store/CommonStore";

const router = useRouter();
const userStore = useUserStore();
const commonStore = useCommonStore();
const BUttonText = ref("发送验证码");
const second = ref(60);
const getRegisterCodeButton = ref(null);
const timeId = ref(null);
const registerData = reactive({
  userAccount: "",
  userPassword: "",
  rePassword: "",
  captcha: "",
} as UserRegisterDTO);
const getRegisterCode = async () => {
  const res = await UserControllerService.getRegisterCode(
    registerData.userAccount as string
  );
  if (res.code !== 0) {
    ElMessage.error(res.message);
  }
  getRegisterCodeButton.value.disabled = true;
  timeId.value = setInterval(() => {
    BUttonText.value = second.value--;
    if (second.value < 0) {
      second.value = 60;
      if (timeId.value !== null && timeId.value !== "") {
        clearInterval(timeId.value);
      }
      BUttonText.value = "发送验证码";
      getRegisterCodeButton.value.disabled = false;
    }
  }, 1000);
};
const regist = async () => {
  const result = await UserControllerService.register(registerData);
  if (result.code === 0) {
    ElMessage.success("注册成功");
    router.push("/");
  } else {
    ElMessage.error(result.message);
  }
};

const loginData = reactive({
  userAccount: "",
  userPassword: "",
} as UserLoginDTO);
const login = async () => {
  const result = await UserControllerService.login(loginData);

  if (result.code === 0) {
    ElMessage.success("登陆成功");
    userStore.userInfo = result.data as any;
    userStore.getUserRole();
    commonStore.setActiveIndex(0);
    router.push({
      path: "/",
      replace: true,
    });
  } else {
    ElMessage.error(result.message);
  }
};
onMounted(() => {
  const signUpButton = document.getElementById("signUp");
  const signInButton = document.getElementById("signIn");
  const container = document.getElementById("container");

  // 检查元素是否存在，避免运行时错误
  if (signUpButton && signInButton && container) {
    signUpButton.addEventListener("click", () => {
      container.classList.add("right-panel-active");
    });

    signInButton.addEventListener("click", () => {
      container.classList.remove("right-panel-active");
    });
  } else {
    console.error("DOM not found.");
  }
});
</script>

<style lang="scss" scoped>
* {
  box-sizing: border-box;
}

.body {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  font-family: "Montserrat", sans-serif;
  margin: 40px 0 50px;
}

h1 {
  font-weight: bold;
  margin: 0;
}

h2 {
  text-align: center;
}

p {
  font-size: 14px;
  font-weight: 100;
  line-height: 20px;
  letter-spacing: 0.5px;
  margin: 20px 0 30px;
}

span {
  font-size: 12px;
}

a {
  color: #333;
  font-size: 14px;
  text-decoration: none;
  margin: 15px 0;
}

button {
  border-radius: 20px;
  border: 1px solid #ff4b2b;
  background-color: #ff4b2b;
  color: #ffffff;
  font-size: 12px;
  font-weight: bold;
  padding: 12px 45px;
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
}

button:active {
  transform: scale(0.95);
}

button:focus {
  outline: none;
}

button.ghost {
  background-color: transparent;
  border-color: #ffffff;
}

.form {
  background-color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 50px;
  height: 100%;
  text-align: center;
}

input {
  background-color: #eee;
  border: none;
  padding: 12px 15px;
  margin: 8px 0;
  width: 100%;
}

.email input {
  width: 65%;
  position: relative;
}
.email {
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
}

.email button {
  width: 100px;
  height: 40px;
  position: relative;
  margin-left: 10px;
  border-radius: 5px;
  border: 1px solid #ff4b2b;
  background-color: #ff4b2b;
  color: #ffffff;
  font-size: 12px;
  font-weight: bold;
  padding: 12px 15px;
  letter-spacing: 1px;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
}

.container {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 14px 28px rgba(0, 0, 0, 0.25), 0 10px 10px rgba(0, 0, 0, 0.22);
  position: relative;
  overflow: hidden;
  width: 768px;
  max-width: 100%;
  min-height: 480px;
}

.form-container {
  position: absolute;
  top: 0;
  height: 100%;
  transition: all 0.6s ease-in-out;
}

.sign-in-container {
  left: 0;
  width: 50%;
  z-index: 2;
}

.container.right-panel-active .sign-in-container {
  transform: translateX(100%);
}

.sign-up-container {
  left: 0;
  width: 50%;
  opacity: 0;
  z-index: 1;
}

.container.right-panel-active .sign-up-container {
  transform: translateX(100%);
  opacity: 1;
  z-index: 5;
  animation: show 0.6s;
}

@keyframes show {
  0%,
  49.99% {
    opacity: 0;
    z-index: 1;
  }

  50%,
  100% {
    opacity: 1;
    z-index: 5;
  }
}

.overlay-container {
  position: absolute;
  top: 0;
  left: 50%;
  width: 50%;
  height: 100%;
  overflow: hidden;
  transition: transform 0.6s ease-in-out;
  z-index: 100;
}

.container.right-panel-active .overlay-container {
  transform: translateX(-100%);
}

.overlay {
  background: #ff416c;
  background: -webkit-linear-gradient(to right, #ff4b2b, #ff416c);
  background: linear-gradient(to right, #ff4b2b, #ff416c);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: 0 0;
  color: #ffffff;
  position: relative;
  left: -100%;
  height: 100%;
  width: 200%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.container.right-panel-active .overlay {
  transform: translateX(50%);
}

.overlay-panel {
  position: absolute;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 40px;
  text-align: center;
  top: 0;
  height: 100%;
  width: 50%;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
}

.overlay-left {
  transform: translateX(-20%);
}

.container.right-panel-active .overlay-left {
  transform: translateX(0);
}

.overlay-right {
  right: 0;
  transform: translateX(0);
}

.container.right-panel-active .overlay-right {
  transform: translateX(20%);
}

.social-container {
  margin: 20px 0;
}

.social-container a {
  border: 1px solid #dddddd;
  border-radius: 50%;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  margin: 0 5px;
  height: 40px;
  width: 40px;
}

footer {
  background-color: #222;
  color: #fff;
  font-size: 14px;
  bottom: 0;
  position: fixed;
  left: 0;
  right: 0;
  text-align: center;
  z-index: 999;
}

footer p {
  margin: 10px 0;
}

footer i {
  color: red;
}

footer a {
  color: #3c97bf;
  text-decoration: none;
}
</style>

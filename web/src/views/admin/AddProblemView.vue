<template>
  <div id="addQuestion">
    <div class="panel-body">
      <a-space style="justify-content: space-between">
        <a-form-item field="title" label="题目">
          <a-input v-model="form.title" placeholder="请输入题目标题" />
        </a-form-item>
        <a-form-item field="problemId" label="自定义ID">
          <a-input v-model="form.problemId" placeholder="请输入自定义id" />
        </a-form-item>
        <a-form-item field="tags" label="标签">
          <a-input-tag v-model="form.tagList" placeholder="请输入题目标签" />
        </a-form-item>
        <a-form-item field="difficulty" label="难度">
          <a-select
            :style="{ width: '100px' }"
            placeholder="请选择"
            v-model="form.difficulty"
          >
            <a-option value="0">简单</a-option>
            <a-option value="1">中等</a-option>
            <a-option value="2">困难</a-option>
          </a-select>
        </a-form-item>
      </a-space>
      <a-form-item field="content" label="内容">
        <MdEditor
          :value="form.description"
          :handle-change="onContentMdchange"
        />
      </a-form-item>
      <a-form-item label="配置" :content-flex="false" :merge-props="false">
        <a-space direction="vertical" fill>
          <a-form-item field="judgeConfig.timeLimit1" label="时间限制">
            <a-input v-model="form.timeLimit" placeholder="时间限制" />
          </a-form-item>
          <a-form-item field="judgeConfig.memoryLimit" label="内存限制">
            <a-input v-model="form.memoryLimit" placeholder="内存限制" />
          </a-form-item>
          <a-form-item field="judgeConfig.stackLimit" label="堆栈限制">
            <a-input v-model="form.stackLimit" placeholder="堆栈限制" />
          </a-form-item>
        </a-space>
      </a-form-item>
      <el-upload
        class="upload-demo"
        drag
        headers="POST"
        action=process.env.VUE_APP_PROBLEM_FILE_UPLOAD_PATH
        multiple
        :data="fileData"
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      </el-upload>
      <a-button status="success" @click="addQuestion()">提交</a-button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import MdEditor from "@/components/MdEditor.vue";
import { useRoute } from "vue-router";
import { ElMessage, ElMessageBox } from "element-plus";
import { ProblemControllerService } from "../../../generated/services/ProblemControllerService";
import { useUserStore } from "@/store/UserStore";
import { ProblemVO } from "../../../generated/models/ProblemVO";
import { ProblemAddDTO } from "../../../generated/models/ProblemAddDTO";

const route = useRoute();
const userStore = useUserStore();
const fileData = ref({
  pid: "hahah",
});
const form = ref({
  problemId: "",
  title: "",
  author: "",
  description: "",
  tagList: [],
  timeLimit: 0,
  memoryLimit: 0,
  stackLimit: 0,
  input: "",
  output: "",
  source: "",
  difficulty: 0,
  auth: 0,
  judgeMode: "default",
  spjCode: "",
  spjLanguage: "",
} as ProblemAddDTO);

const addQuestion = async () => {
  form.value.author = userStore.userInfo.userName;
  const result = await ProblemControllerService.addProblem(form.value);
  if (result.code === 0) {
    ElMessage.success("添加成功");
  } else if (result.code === 201) {
    ElMessageBox.confirm("没有该标签，是否创建？", "Warning", {
      confirmButtonText: "添加",
      cancelButtonText: "取消",
      type: "warning",
    })
      .then(() => {
        if (Array.isArray(form.value.tagList)) {
          for (const s of form.value.tagList) {
            ProblemControllerService.addTag(s);
          }
        }
        ElMessage({
          type: "success",
          message: "添加成功",
        });
      })
      .catch(() => {
        ElMessage({
          type: "info",
          message: "取消添加",
        });
      });
  } else {
    ElMessage.error("添加失败：" + result.message);
  }
};

const onContentMdchange = (v: string) => {
  form.value.description = v;
};

const loadData = async () => {
  const id = route.query.id;
  if (!id) {
    return;
  }
  const res = await ProblemControllerService.getProblem(Number(id));
  if (res.code === 0 && res.data !== undefined) {
    form.value = res.data as ProblemVO;
  } else {
    ElMessage.error("加载失败：" + res.message);
  }
};

onMounted(() => {
  loadData();
});
</script>

<style scoped>
#addQuestion {
  background: white;
  width: 100%;
  margin: auto;
}

.panel-body {
  padding: 15px;
}
</style>
<!--<template>-->
<!--  <el-row style="margin: auto; max-width: 1500px; min-width: 800px">-->
<!--    <el-col :xs="24" :sm="24" :md="17">-->
<!--      <el-card class="box-card" shadow="hover">-->
<!--        <template #header>-->
<!--          <div class="card-header" style="height: 35px">-->
<!--            <p class="title">-->
<!--              <span style="vertical-align: -3px">#{{ problemInfo.pid }}、</span>-->
<!--              <el-input-->
<!--                size="large"-->
<!--                v-model="problemInfo.title"-->
<!--                style="width: 200px"-->
<!--              />-->
<!--              <el-switch-->
<!--                v-model="problemInfo.isPublic"-->
<!--                style="margin-left: 10px"-->
<!--                size="large"-->
<!--                active-text="公开"-->
<!--                inactive-text="隐藏"-->
<!--              />-->
<!--            </p>-->
<!--          </div>-->
<!--        </template>-->
<!--        <v-md-editor-->
<!--          height="600px"-->
<!--          left-toolbar="undo redo clear | h bold italic strikethrough quote | ul ol table hr | link image code"-->
<!--          v-model="problemInfo.description"-->
<!--        ></v-md-editor>-->
<!--      </el-card>-->
<!--    </el-col>-->
<!--    <el-col :xs="24" :sm="24" :md="7">-->
<!--      <el-card class="box-card" shadow="hover">-->
<!--        <template #header>-->
<!--          <div class="card-header">-->
<!--            <div-->
<!--              class="stat-item clickable"-->
<!--              @click="-->
<!--                this.$router.push({-->
<!--                  path: '/submission',-->
<!--                  query: { pid: pid, res: 4, queryAll: true },-->
<!--                })-->
<!--              "-->
<!--            >-->
<!--              <div class="stat-number">{{ problemInfo.acCnt }}</div>-->
<!--              <div class="stat-label">通过</div>-->
<!--            </div>-->
<!--            <div class="stat-divider"></div>-->
<!--            <div-->
<!--              class="stat-item clickable"-->
<!--              @click="-->
<!--                this.$router.push({-->
<!--                  path: '/submission',-->
<!--                  query: { pid: pid, queryAll: true },-->
<!--                })-->
<!--              "-->
<!--            >-->
<!--              <div class="stat-number">{{ problemInfo.submitCnt }}</div>-->
<!--              <div class="stat-label">提交</div>-->
<!--            </div>-->
<!--          </div>-->
<!--        </template>-->
<!--        <el-descriptions direction="vertical" :column="1" border>-->
<!--          <el-descriptions-item label="时间限制">-->
<!--            <el-input v-model="problemInfo.timeLimit" style="width: 80px" />-->
<!--            ms-->
<!--          </el-descriptions-item>-->
<!--          <el-descriptions-item label="空间限制">-->
<!--            <el-input v-model="problemInfo.memoryLimit" style="width: 80px" />-->
<!--            MB-->
<!--          </el-descriptions-item>-->
<!--          <el-descriptions-item label="题目类型">-->
<!--            <el-select-->
<!--              v-model="problemInfo.type"-->
<!--              placeholder="评测结果"-->
<!--              style="width: 150px"-->
<!--            >-->
<!--              <el-option-->
<!--                v-for="item in ptype"-->
<!--                :key="item.value"-->
<!--                :label="item.label"-->
<!--                :value="item.value"-->
<!--              />-->
<!--            </el-select>-->
<!--          </el-descriptions-item>-->
<!--          <el-descriptions-item label="题目标签">-->
<!--            <el-tag-->
<!--              type="info"-->
<!--              v-for="tag in problemInfo.tags"-->
<!--              :key="tag"-->
<!--              closable-->
<!--              @close="removeTag(tag)"-->
<!--              :color="getTagColor(tag)"-->
<!--              @click="-->
<!--                this.$router.push({-->
<!--                  path: '/problem',-->
<!--                  query: { tags: JSON.stringify([tag]) },-->
<!--                })-->
<!--              "-->
<!--            >-->
<!--              <span class="tag-text">{{ tag }} </span>-->
<!--            </el-tag>-->
<!--            <el-input-->
<!--              v-if="inputVisible"-->
<!--              v-model="newTag"-->
<!--              size="small"-->
<!--              ref="inputRef"-->
<!--              @keyup.enter="addTag"-->
<!--              @blur="addTag"-->
<!--              style="width: 80px"-->
<!--            />-->
<!--            <el-button-->
<!--              v-else-->
<!--              class="button-new-tag ml-1"-->
<!--              size="small"-->
<!--              @click="showInput"-->
<!--              style="width: 80px"-->
<!--            >-->
<!--              + New Tag-->
<!--            </el-button>-->
<!--          </el-descriptions-item>-->
<!--          <el-descriptions-item label="难度评级">-->
<!--            <el-select-->
<!--              v-model="problemInfo.level"-->
<!--              placeholder="难度评级"-->
<!--              style="width: 150px"-->
<!--            >-->
<!--              <el-option-->
<!--                v-for="item in levels"-->
<!--                :key="item.value"-->
<!--                :label="item.label"-->
<!--                :value="item.value"-->
<!--              />-->
<!--            </el-select>-->
<!--          </el-descriptions-item>-->
<!--          <el-descriptions-item label=" 出题人">-->
<!--            <router-link-->
<!--              class="rlink"-->
<!--              :to="'/user/' + problemInfo.publisherUid"-->
<!--            >-->
<!--              {{ problemInfo.publisher }}-->
<!--            </router-link>-->
<!--          </el-descriptions-item>-->
<!--          <el-descriptions-item label="发布时间">-->
<!--            {{ problemInfo.time }}</el-descriptions-item-->
<!--          >-->
<!--        </el-descriptions>-->
<!--        <el-divider style="margin-top: 20px; margin-bottom: 20px" />-->
<!--        <div style="text-align: center">-->
<!--          <el-button-->
<!--            type="primary"-->
<!--            @click="this.$router.push('/problem/' + problemInfo.pid)"-->
<!--          >-->
<!--            <el-icon class="el-icon&#45;&#45;left">-->
<!--              <Back />-->
<!--            </el-icon>-->
<!--            返回题目-->
<!--          </el-button>-->
<!--          <el-button type="danger" @click="updateProblem">-->
<!--            <el-icon class="el-icon&#45;&#45;left">-->
<!--              <CircleCheck />-->
<!--            </el-icon>-->
<!--            更新题目-->
<!--          </el-button>-->
<!--          <el-button-->
<!--            type="success"-->
<!--            @click="this.$router.push('/problem/case/' + problemInfo.pid)"-->
<!--          >-->
<!--            <el-icon class="el-icon&#45;&#45;left">-->
<!--              <SetUp />-->
<!--            </el-icon>-->
<!--            管理数据-->
<!--          </el-button>-->
<!--        </div>-->
<!--      </el-card>-->
<!--    </el-col>-->
<!--  </el-row>-->
<!--</template>-->

<!--<script>-->
<!--import axios from "axios";-->
<!--import { ElMessage } from "element-plus";-->

<!--export default {-->
<!--  name: "problemEdit",-->
<!--  data() {-->
<!--    return {-->
<!--      gid: 1,-->
<!--      problemInfo: [],-->
<!--      newTag: "",-->
<!--      inputVisible: false,-->
<!--      ptype: [-->
<!--        { value: 0, label: "传统文本比较" },-->
<!--        { value: 1, label: "Special Judge" },-->
<!--      ],-->
<!--      levels: [-->
<!--        {-->
<!--          value: 0,-->
<!--          label: "暂未评级",-->
<!--        },-->
<!--        {-->
<!--          value: 1,-->
<!--          label: "入门",-->
<!--        },-->
<!--        {-->
<!--          value: 2,-->
<!--          label: "普及",-->
<!--        },-->
<!--        {-->
<!--          value: 3,-->
<!--          label: "提高",-->
<!--        },-->
<!--        {-->
<!--          value: 4,-->
<!--          label: "省选",-->
<!--        },-->
<!--        {-->
<!--          value: 5,-->
<!--          label: "NOI / NOI+",-->
<!--        },-->
<!--      ],-->
<!--      tagColorList: [-->
<!--        "#2d8cf0",-->
<!--        "#3f51b5",-->
<!--        "#9c27b0",-->
<!--        "#009688",-->
<!--        "#19be6b",-->
<!--        "#689f38",-->
<!--        "#ff9900",-->
<!--        "#E91E63",-->
<!--        "#ed4014",-->
<!--      ],-->
<!--    };-->
<!--  },-->
<!--  methods: {-->
<!--    updateProblem() {-->
<!--      axios-->
<!--        .post("/api/problem/updateProblem", {-->
<!--          pid: this.problemInfo.pid,-->
<!--          info: this.problemInfo,-->
<!--        })-->
<!--        .then((res) => {-->
<!--          if (res.status === 200) {-->
<!--            ElMessage({-->
<!--              message: "更新题目成功",-->
<!--              type: "success",-->
<!--              duration: 1000,-->
<!--            });-->
<!--          } else {-->
<!--            ElMessage({-->
<!--              message: res.data.message,-->
<!--              type: "error",-->
<!--              duration: 2000,-->
<!--            });-->
<!--          }-->
<!--          this.all();-->
<!--        });-->
<!--    },-->
<!--    showInput() {-->
<!--      this.inputVisible = true;-->
<!--      this.$nextTick(() => this.$refs.inputRef.focus());-->
<!--    },-->
<!--    addTag() {-->
<!--      if (this.newTag) this.problemInfo.tags.push(this.newTag);-->
<!--      this.newTag = "";-->
<!--      this.inputVisible = false;-->
<!--    },-->
<!--    removeTag(tag) {-->
<!--      if (this.problemInfo.tags.includes(tag)) {-->
<!--        this.problemInfo.tags.splice(this.problemInfo.tags.indexOf(tag), 1);-->
<!--      }-->
<!--    },-->
<!--    all() {-->
<!--      axios-->
<!--        .post("/api/problem/getProblemInfo", { pid: this.pid })-->
<!--        .then((res) => {-->
<!--          this.problemInfo = res.data.data;-->
<!--          if (!this.problemInfo.description)-->
<!--            this.problemInfo.description = "请输入题目描述";-->
<!--          if (!this.problemInfo.title)-->
<!--            this.problemInfo.title = "请输入题目标题";-->
<!--          this.problemInfo.isPublic = res.data.data.isPublic ? true : false;-->
<!--        });-->
<!--    },-->
<!--    hash(str) {-->
<!--      let t = 0;-->
<!--      for (let i = 0; i < str.length; i++) t = 31 * t + str.charCodeAt(i);-->
<!--      return t;-->
<!--    },-->
<!--    getTagColor(tag) {-->
<!--      return this.tagColorList[this.hash(tag) % this.tagColorList.length];-->
<!--    },-->
<!--  },-->
<!--  mounted() {-->
<!--    if (this.$store.state.gid < 2) {-->
<!--      this.$router.push("/");-->
<!--      return;-->
<!--    }-->
<!--    this.pid = this.$route.params.pid;-->
<!--    this.all();-->
<!--  },-->
<!--};-->
<!--</script>-->

<!--<style scoped>-->
<!--.box-card {-->
<!--  margin: 10px;-->
<!--  text-align: left;-->
<!--}-->

<!--.card-header {-->
<!--  display: flex;-->
<!--  align-items: center;-->
<!--  justify-content: space-around;-->
<!--}-->

<!--.stat-item {-->
<!--  text-align: center;-->
<!--  flex: 1;-->
<!--}-->

<!--.clickable {-->
<!--  cursor: pointer;-->
<!--  transition: background-color 0.3s;-->
<!--  border-radius: 5px;-->
<!--}-->

<!--.clickable:hover {-->
<!--  background-color: #f5f7fa;-->
<!--}-->

<!--.stat-number {-->
<!--  font-size: 28px;-->
<!--  font-weight: bold;-->
<!--  color: #303133;-->
<!--}-->

<!--.stat-label {-->
<!--  font-size: 14px;-->
<!--  color: #909399;-->
<!--  margin-top: 3px;-->
<!--}-->

<!--.stat-divider {-->
<!--  width: 1px;-->
<!--  height: 60px;-->
<!--  background-color: #e0e0e0;-->
<!--  margin: 0 20px;-->
<!--}-->

<!--.title {-->
<!--  margin: 0;-->
<!--  font-size: 25px;-->
<!--}-->

<!--.el-tag {-->
<!--  cursor: pointer;-->
<!--  margin-right: 5px;-->
<!--}-->

<!--.tag-text {-->
<!--  color: white;-->
<!--  font-weight: 600;-->
<!--  font-size: 14px;-->
<!--}-->
<!--</style>-->

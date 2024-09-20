<template>
  <div id="problem">
    <div class="panel-body">
      <h2>在线题库</h2>
      <el-card class="box-card" shadow="hover">
        <template #header>
          <div class="card-header">
            <el-pagination
              @current-change="handleCurrentChange"
              :current-page="current"
              :page-size="size"
              :total="total"
              layout="total, prev, pager, next"
            />
          </div>
        </template>
        <div style="display: inline-flex">
          <el-form :inline="true" :model="filter">
            <el-form-item>
              <el-input
                v-model="filter.id"
                type="text"
                placeholder="id"
                style="width: 50px"
              />
            </el-form-item>
            <el-form-item>
              <el-input
                v-model="filter.title"
                type="text"
                placeholder="题目标题"
                style="width: 150px"
                @keyup.enter="getQuestion()"
              />
            </el-form-item>
            <el-form-item>
              <el-select
                v-model="filter.difficulty"
                placeholder="难度评级"
                style="width: 120px"
                @change="getQuestion()"
              >
                <el-option
                  v-for="it in levels"
                  :key="it.index"
                  :label="it.label"
                  :value="it.index"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select
                v-model="filter.tags"
                multiple
                filterable
                clearable
                placeholder="题目标签"
                style="width: 350px"
                @change="getQuestion()"
              >
                <el-option
                  v-for="tag in tagList"
                  :key="tag"
                  :label="tag.name"
                  :value="tag.name"
                >
                  <el-tag type="info">
                    <span class="tag-text">{{ tag.name }} </span>
                  </el-tag>
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          <el-button-group>
            <el-button type="primary" @click="getQuestion()"
              >筛选记录
            </el-button>
            <el-button type="success" @click="clear"> 显示全部</el-button>
          </el-button-group>
        </div>
        <el-table
          class="list"
          :data="problemList"
          style="width: 100%"
          v-loading="!finished"
        >
          <el-table-column fixed prop="id" label="id" width="100" />
          <el-table-column prop="title" label="题目" width="200" />
          <el-table-column prop="tagList" label="标签" width="250">
            <template #default="scope">
              <div v-for="(item, index) in scope.row.tagList" :key="index">
                <el-tag type="success">{{ item }}</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column prop="difficulty" label="难度" width="100">
            <template #default="scope">
              <div v-if="scope.row.difficulty === 0">
                <el-tag type="success">简单</el-tag>
              </div>
              <div v-else-if="scope.row.difficulty === 1">
                <el-tag type="warning">适中</el-tag>
              </div>
              <div v-else>
                <el-tag type="danger">困难</el-tag>
              </div>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" min-width="20">
            <template #default="scope">
              <el-button type="primary" @click="gotoContentById(scope.row.id)">
                学习
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </div>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRouter } from "vue-router";
import { ProblemControllerService } from "../../../generated/services/ProblemControllerService";
import { ElMessage } from "element-plus";

const current = ref(1);
const size = ref(20);
const total = ref(0);
const finished = ref(false);

const problemList = ref();

const columns = ref([
  {
    title: "Id",
    dataIndex: "id",
  },
  {
    title: "标题",
    dataIndex: "title",
    slotName: "title",
  },
  {
    title: "标签",
    dataIndex: "tags",
    slotName: "tags",
  },
  {
    title: "难度",
    dataIndex: "difficulty",
    slotName: "difficulty",
  },
]);

const tagList = ref([]);

const levels = ref([
  {
    index: 0,
    label: "简单",
    color: "#BFBFBF",
  },
  {
    index: 1,
    label: "适中",
    color: "#FE4C61",
  },
  {
    index: 2,
    label: "困难",
    color: "#FFC116",
  },
]);

const router = useRouter();
const gotoContentById = (id: string) => {
  router.push({
    path: "/problem/content",
    query: {
      id: id,
    },
  });
};

const filter = ref({
  title: null,
  difficulty: null,
  tags: [],
  id: null,
});

const getQuestion = async () => {
  finished.value = false;
  console.log("get" + current.value);
  const res = await ProblemControllerService.searchProblemTitleTwo(
    current.value,
    size.value,
    filter.value.id as any,
    filter.value.tags as any,
    filter.value.difficulty as any,
    filter.value.title as any
  );
  if (res.code === 0) {
    console.log(res.data);
    total.value = res.data?.total;
    problemList.value = res.data?.records;
    finished.value = true;
  } else {
    ElMessage.error("获取题目列表失败" + res.message);
  }
};
const getTagList = async () => {
  const res = await ProblemControllerService.getProblemTagList();
  if (res.code !== 0) {
    ElMessage.error("获取题目标签失败");
  } else {
    tagList.value = res.data;
  }
};
const handleCurrentChange = (val: number) => {
  current.value = val;
  getQuestion();
};
const clear = () => {
  filter.value.difficulty = null;
  filter.value.title = null;
  filter.value.tags = [];
  filter.value.id = null;
  getQuestion();
};

onMounted(() => {
  getQuestion();
  getTagList();
});
</script>
<style scoped>
#problem {
  height: 800px;
  width: 95%;
  margin: auto auto;
  justify-content: center;
}

h2 {
  text-align: center;
}

hr {
  color: #aaaaaa;
  margin-top: 32px;
}

.panel-body {
  padding: 2px;
}

#problem >>> .el-table tr,
#problem >>> .el-table th,
#problem >>> .el-table__body tr.hover-row > td.el-table__cell,
#problem >>> .el-table {
  color: var(--theme-color);
  background-color: var(--content-bg);
}
</style>

<template>
  <div id="manage">
    <div class="panel-body">
      <div class="problemName">{{}}</div>
      <div id="searchBox">
        <a-space direction="vertical" size="large">
          <a-input-search
            :style="{ width: '320px' }"
            placeholder="搜索题目"
            v-model="searchValue"
          />
        </a-space>
      </div>
      <hr />
      <a-table
        :columns="columns"
        :data="data"
        :pagination="{
          pageSize: questionQueryData.pageSize,
          current: questionQueryData.current,
          total: total,
        }"
      >
        <template #title="{ record }">
          <a-button onclick="window.location.href = 'www.baidu.com'"
            >{{ record.title }}
          </a-button>
        </template>
        <template #tags="{ record }">
          <a-button
            v-for="item in record.tags"
            :key="item.id"
            class="tagsButton"
            onclick="window.location.href = 'www.baidu.com'"
            >{{ item }}
          </a-button>
        </template>
        <template #difficulty="{ record }">
          <a-button onclick="window.location.href = 'www.baidu.com'"
            >{{ record.difficulty }}
          </a-button>
        </template>
        <template #caozuo="{ record }">
          <a-space>
            <a-button type="primary" @click="doUpdate(record)"> 修改</a-button>
            <a-button status="danger" @click="doDelete(record)"> 删除</a-button>
          </a-space>
        </template>
      </a-table>
      {{ searchValue }}
    </div>
  </div>
</template>
<script setup lang="ts">
import { onMounted, ref } from "vue";
import {
  Question,
  QuestionControllerService,
  QuestionQueryRequest,
} from "../../../generated";

import { useRoute, useRouter } from "vue-router";
import { ElMessage } from "element-plus";

const route = useRoute();
const total = ref(0);
const searchValue = ref("");
const router = useRouter();
const doUpdate = async (question: Question) => {
  router.push({
    path: "/updatequestion",
    query: {
      id: question.id,
    },
  });
};
const doDelete = async (question: Question) => {
  const result = await QuestionControllerService.deleteQuestionUsingPost({
    id: question.id,
  });
  if (result.code === 0) {
    ElMessage.success("删除成功");
    listPage();
  }
};

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
  {
    title: "创建人id",
    dataIndex: "userId",
    slotName: "userId",
  },
  {
    title: "创建时间",
    dataIndex: "createTime",
    slotName: "createTime",
  },
  {
    title: "更新时间",
    dataIndex: "updateTime",
    slotName: "updateTime",
  },
  {
    title: "提交数",
    dataIndex: "submitNum",
    slotName: "submitNum",
  },
  {
    title: "通过数",
    dataIndex: "accepteNum",
    slotName: "accepteNum",
  },
  {
    title: "内容",
    dataIndex: "content",
    slotName: "content",
  },
  {
    title: "判题规则",
    dataIndex: "judgeConfig",
    slotName: "judgeConfig",
  },
  {
    title: "操作",
    dataIndex: "caozuo",
    slotName: "caozuo",
  },
]);

const data = ref([]);

const questionQueryData = ref({
  current: 1,
  pageSize: 10,
} as QuestionQueryRequest);

const listPage = async () => {
  const result = await QuestionControllerService.listQuestionByPageUsingPost(
    questionQueryData.value
  );
  total.value = result.data.total;
  data.value = result.data.records;
};
onMounted(() => {
  listPage();
});
</script>
<style scoped>
#problem {
  background: #fffffe;
  height: 1000px;
  width: 75%;
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
  padding: 15px;
}

#searchBox {
  margin-left: 29%;
}

.tagsButton {
  margin-left: 8px;
}
</style>

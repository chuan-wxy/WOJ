<template>
  <div id="code-editor" ref="codeEditorRef" style="min-height: 500px" />
</template>

<script setup lang="ts">
import * as monaco from "monaco-editor";
import { defineExpose, onMounted, ref } from "vue";

const codeEditorRef = ref();
const codeEditorData = ref();

defineExpose({
  codeEditorData,
});

onMounted(() => {
  if (!codeEditorRef.value) {
    return;
  }
  const editor = monaco.editor.create(codeEditorRef.value, {
    value: codeEditorData.value,
    language: "c", // 语言
    // automaticLayout: true, // 宽度自适应
    lineNumbers: "on", // 行号
    roundedSelection: false,
    scrollBeyondLastLine: true,
    readOnly: false,
    theme: "vs-dark",
  });
  const model = editor.getModel();
  model?.onDidChangeContent(() => {
    codeEditorData.value = model?.getValue();
  });
});
</script>

<style scoped></style>

import { defineStore } from "pinia";

export const useCommonStore = defineStore("common", {
  state: () => {
    return {
      webStyle: 0, // 0暗模式，1亮模式
      isActiveIndexOfBar: 0,
    };
  },
  getters: {},
  actions: {
    setActiveIndex(index: number) {
      this.isActiveIndexOfBar = index;
    },
  },
});

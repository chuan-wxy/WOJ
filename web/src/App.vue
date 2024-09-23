<template>
  <div class="video-bg">
    <video width="320" height="240" autoplay loop muted>
      <source
        src="https://assets.codepen.io/3364143/7btrrd.mp4"
        type="video/mp4"
      />
    </video>
  </div>
  <div id="main">
    <basic-layout />
  </div>
</template>

<style>
.light-mode .dark-light svg {
  fill: transparent;
  stroke: var(--theme-color);
}

.light-mode .profile-img {
  border: 2px solid var(--theme-bg-color);
}

.light-mode .content-section ul {
  background-color: var(--theme-bg-color);
}

.light-mode .pop-up__title {
  border-color: var(--theme-color);
}

.light-mode .dropdown.is-active ul {
  background-color: rgba(255, 255, 255, 0.94);
}

body.light-mode:before,
body.light-mode .video-bg:before {
  content: "";
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 100vh;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0.72) 0%,
    rgba(255, 255, 255, 0.45) 100%
  );
  -webkit-backdrop-filter: saturate(3);
  backdrop-filter: saturate(3);
}

html {
  box-sizing: border-box;
  -webkit-font-smoothing: antialiased;
}

#app {
  font-family: var(--body-font);
  background-image: url(https://wallpapershome.com/images/wallpapers/macos-big-sur-1280x720-dark-wwdc-2020-22655.jpg);
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 2em;
  height: 100%;
}

#main {
  background-color: var(--theme-bg-color);
  max-width: 1200px;
  max-height: 860px;
  height: 90vh;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  position: relative;
  width: 100%;
  border-radius: 14px;
  backdrop-filter: blur(20px);
  -webkit-backdrop-filter: blur(20px);
  font-size: 15px;
  font-weight: 500;
}

.video-bg {
  position: fixed;
  right: 0;
  top: 0;
  width: 100%;
  height: 100%;
}

.video-bg video {
  width: 100%;
  height: 100%;
  -o-object-fit: cover;
  object-fit: cover;
}
</style>

<script setup lang="ts">
import BasicLayout from "@/layout/BasicLayout.vue";
</script>

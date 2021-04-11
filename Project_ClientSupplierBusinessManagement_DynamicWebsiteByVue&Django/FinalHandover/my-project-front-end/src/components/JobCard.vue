<template>
  <el-card shadow="always" class="card-box" :body-style="cardContainerStyle">
    <!-- img on left side -->
    <img src="../assets/furniture.jpg" class="image" />
    
    <!-- text-container on right side of card -->
    <div class="text-container">
      <!-- job title -->
      <span class="title">{{ job.title }}</span>
      <!-- job location -->
      <div class="location">
        <i class="el-icon-location"></i>
        <span>{{ job.suburb }}</span>
      </div>
      <!-- job timestamp -->
      <div class="time">
        <i class="el-icon-time"></i>
        <span>{{ job.date }}</span>
      </div>
    </div>
    <!-- description block on right bottom -->
    <div class="description">
      {{ job.description }}
    </div>
  </el-card>
</template>

<script>
export default {
  props:['job'],
  data() {
    return {
      cardContainerStyle: {
        display: "flex",
        padding: "0px",
      }
    };
  },
};
</script>

<style scoped>
/* card container */
.el-card.card-box {
  position: relative;
  width: 100%;
  margin-top: 11px;
  margin-bottom: 11px;
}

.el-card.card-box:hover {
  /* position: relative;
  width: 100%;
  margin-top: 11px;
  margin-bottom: 11px; */
  box-shadow: 10px 10px 8px rgba(0, 0, 0, .3);
}

/* set card container body parts */
.el-card img {
  width: 45%;
}

.text-container {
  position: absolute;
  left: 50%;
  top: 5%;
  /* border: 1px red solid; */
}

.text-container .title {
  font-size: 26px;
}
.text-container .title:hover {
  text-decoration: underline;
}
.text-container .location {
  margin-top: 10px;
  margin-bottom: 5px;
}
.text-container .time {
  margin-bottom: 5px;
}
.description {
  position: absolute;
  width: 48%;
  height: 40%;
  left: 50%;
  bottom: 5%;
  overflow: hidden;
}

</style>
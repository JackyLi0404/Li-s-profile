<template>
  <el-card shadow="always" class="card-box" :body-style="cardContainerStyle">
    <!-- img on left side -->
    <img src="../assets/picture/furniture.jpg" class="image" />
    <!-- text-container on right side of card -->
    <div class="text-container" style="margin-left:25px;">
      <!-- job title -->
      <span class="title">{{ job.title }}</span>
      <!-- job budget -->
      <div class="budget">
        <i class="el-icon-s-claim"></i>
        <span>{{ job.budget }}</span>
      </div>
      <!-- job location -->
      <div class="location">
        <i class="el-icon-location"></i>
        <span>{{ job.suburb }}</span>
      </div>
      <!-- job category -->
      <div class="category">
        <i class="el-icon-s-order"></i>
        <span>{{ job.category }}</span>
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
  props: ['job'],
  data () {
    return {
      cardContainerStyle: {
        display: 'flex',
        padding: '0px'
      }
    }
  }
}
</script>

<style scoped>
/* card container */
.el-card.card-box {
  position: relative;
  width: 100%;
  margin-bottom: 11px;
  height: 120px;
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
  margin-top: 10px;
  margin-left: 10px;
  width: 20%;
  height: 100px;
}

.text-container {
  position: absolute;
  left: 22%;
  top: 5%;
  /* border: 1px red solid; */
}

.text-container .title {
  font-size: 20px;
}
.text-container .title:hover {
  text-decoration: underline;
}
.text-container .budget {
  margin-top: 15px;
  margin-bottom: 5px;
  text-align: left;
}
.text-container .location {
  margin-bottom: 5px;
  text-align: left;
}
.text-container .category {
  margin-bottom: 5px;
  text-align: left;
}
.description {
  position: absolute;
  width: 48%;
  height: 70%;
  left: 50%;
  bottom: 5%;
  overflow: hidden;
}
</style>

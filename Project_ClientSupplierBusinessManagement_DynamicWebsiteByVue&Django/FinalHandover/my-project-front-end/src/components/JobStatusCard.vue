<template>
  <el-card shadow="always" class="card-box" :body-style="cardContainerStyle">
    
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
    
    <div class="status" :style="handleStyle(job.order_state)">
      {{job.order_state}}
      <el-progress :percentage="percentage" :color="customColor"></el-progress>      
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
      },
      percentage: 20,
      customColor: '#a16b1bbe',
    };
  },
  methods:{
    // set different colors for different statuses
    handleStyle(order_state){
      if(order_state === 'work in progress') {
        this.percentage = 50
        this.customColor = '#a16b1bbe'
        return {color:'#a16b1bbe'}
      } 
      
      if(order_state === 'completed') {
        this.percentage = 90
        this.customColor = 'green'
        return {color:'green'}
      }

      if(order_state === 'closed') {
        this.percentage = 100
        this.customColor = 'grey'
        return {color:'grey'}
      }
      // for bid on statuses: pending, accepted, rejected 
      if(order_state === 'pending') {
        this.percentage = 50
        this.customColor = '#a16b1bbe'
        return {color:'#a16b1bbe'}
      } 
      if(order_state === 'accepted') {
        this.percentage = 100
        this.customColor = 'green'
        return {color:'green'}
      } 
      if(order_state === 'rejected') {
        this.percentage = 100
        this.customColor = 'brown'
        return {color:'brown'}
      } 
      
    }
  }
};
</script>

<style scoped>
/* card container */
.el-card.card-box {
  position: relative;
  width: 100%;
  height: 105px;
  margin-top: 11px;
  margin-bottom: 11px;
}

.el-card.card-box:hover {
  box-shadow: 10px 10px 8px rgba(0, 0, 0, .3);
}

.text-container {
  position: absolute;
  left: 3%;
  width: 32%;
  height: 100%;
  top: 5%;
  /* border: 1px red solid; */
}

.text-container .title {
  font-size: 21px;
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
  width: 30%;
  top: 10%;
  height: 80%;
  left: 38%;
  overflow: hidden;
}

/* status */
.status{
  position: absolute;
  /* border: red 1px solid; */
  top: 35%;
  width: 29%;
  font-size: 21px;
  padding: 0%;
  right: 1%;
  color: grey;
}


</style>
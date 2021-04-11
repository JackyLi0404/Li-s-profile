<template>
  <div>
    <div class="box">
      <i class="el-icon-circle-check"></i>
      <p class="text1">Congratulations! you have successfully sign up.</p>
      <span>if the page is not automatically redirected, please <el-button @click="toDashboard" type="text">click here</el-button></span>
    </div>
  </div>
</template>

<script>
export default {
    mounted:function(){
        setTimeout(() => {
        this.$router.push({path: "/Dashboard"}); // 强制切换当前路由 path
        loading.close();
        }, 3000);
    },
    methods:{
        toDashboard:function(){
            this.$router.push('./dashboard')
        }
    }
}
</script>

<style scoped>
.box {
  /* border: 10px red solid; */
  width: 500px;
  margin: 100px auto;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.el-icon-circle-check {
  font-size: 100px;
  /* position: absolute;
        left: 45%;
        top: 30%; */
  color: #009e0f;
}

.box a{
    text-decoration: none;
}
.box a:hover{
    text-decoration: paleturquoise;
}
</style>
<template>
  <el-menu
    :default-active="activeIndex2"
    class="el-menu-container"
    mode="horizontal"
    @select="handleSelect"
    background-color="#DDDDDD"
    text-color="#000000"
    active-text-color="#ffd04b"
  >
    <div>
      <router-link to="/"><i class="el-icon-discount"></i></router-link>
    </div>
    <el-button class="btn-highlight" type="primary">Get Quote</el-button>
    <el-button @click.native="toBrowseTask" class="btn" type="primary">Browse jobs</el-button>
    <el-button class="btn" type="primary">Company View</el-button>
    <div class="right-container">
      <el-button class="btn-highlight" @click="toLogin" type="primary"
        >Log in</el-button
      >
      <el-button class="btn" @click="toSignUp" type="primary">Sign up</el-button>
      <!-- <el-button type="text"><el-avatar icon="el-icon-user-solid"></el-avatar></el-button> -->
      
      <!-- <el-avatar icon="el-icon-user-solid"></el-avatar> -->
      <el-button round class="btn">Join as business</el-button>
    </div>

    <!-- <el-menu-item index="1">Browse jobs</el-menu-item>
  <el-menu-item index="2">Company View</el-menu-item>
  
  
  <el-menu-item index="4" disabled>消息中心</el-menu-item>
  <el-menu-item index="5"><a href="https://www.ele.me" target="_blank">订单管理</a></el-menu-item> -->
  </el-menu>
</template>

<script>
export default {
  methods: {
    toLogin() {
      this.$router.push("/login");
    },
    toSignUp() {
      this.$router.push("/signup");
    },
    toBrowseTask() {
      this.$router.push("/browsetask");
    }
  },
};
</script>

<style scoped>
.el-menu-container {
  display: flex;
  align-items: center;
  position: fixed;
  height: 8%;
  top: 0%;
  left: 0%;
  right: 0%;
  padding: 0 30px;
}

.el-icon-discount {
  font-size: 40px;
  margin-left: 10px;
  color: black;
}

.btn-highlight {
  color: black;
  background-color: #ff9900;
  border: #ff9900;
  margin-left: 20px;
}

.btn-highlight:hover {
  background-color: #e98e06;
}

.btn {
  color: black;
  background-color: #fff;
  border: #fff;
  margin-left: 15px;
}

.right-container {
  height: 50px;
  position: absolute;
  right: 2%;
  display: flex;
  align-items: center;
}

.right-container:focus {
  outline: none;
}

.el-avatar {
  margin-left: 15px;
}
</style>
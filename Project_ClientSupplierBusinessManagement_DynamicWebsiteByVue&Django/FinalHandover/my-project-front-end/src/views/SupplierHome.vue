<template>
  <el-container>
    <el-header>
      <!-- header area -->
      <loggedinheader class="header"></loggedinheader>
    </el-header>
    <!-- side bar container -->
    <el-aside>
      <el-menu
        default-active="1"
        class="side-navigation-bar"
        :router="true"
      >
        <router-link class="avatar-box" to="/updateprofile">
          <el-avatar
            :size="100"
            class="side-user-icon"
            icon="el-icon-user-solid"
          ></el-avatar>
        </router-link>
        <el-menu-item :class="{'activeDashboard':style1}" index="/dashboard">
          <!-- <i class="el-icon-setting"></i> -->
          <span slot="title">Dashboard</span>
        </el-menu-item>
        <el-menu-item :class="{'activeStatus':style2}"  index="/status">
          <!-- <i class="el-icon-setting"></i> -->
          <span slot="title">Check quote/job status</span>
        </el-menu-item>
        <el-menu-item :class="{'activeMessage':style3}" index="/message">
          <!-- <i class="el-icon-setting"></i> -->
          <span slot="title">My messages</span>
        </el-menu-item>
      </el-menu>
    </el-aside>

    <!-- main-container -->
    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
import loggedinheader from "../components/LoggedInHeader";
export default {
  data() {
    return {
      style1:true,
      style2:false,
      style3:false,
      cardContainerStyle: {
        display: "flex",
        padding: "0px",
      },
    };
  },
  watch:{
    $route(to,from){
      if(to.path === '/dashboard') {
        this.style1 = true;
        this.style2 = false;
        this.style3 = false;
      }
      if(to.path === '/status') {
        this.style1 = false;
        this.style2 = true;
        this.style3 = false;
      }
      if(to.path === '/message') {
        this.style1 = false;
        this.style2 = false;
        this.style3 = true;
      }
      if(to.path === '/updateprofile') {
        this.style1 = false;
        this.style2 = false;
        this.style3 = false;
      }
    }
      
  },
  components: {
    loggedinheader
  },
};
</script>

<style scoped>
.header {
  position: fixed;
  z-index: 100;
}

/* side navigation bar area*/
.side-navigation-bar {
  display: flex;
  flex-direction: column;
  align-items: center;
  position: fixed;
  left: 170px;
  top: 57px;
  border: #e6e6e6 1px solid;
  width: 205px;
  height: 100%;
}

.avatar-box {
  width: 100%;
  border: #e6e6e6 1px solid;
  text-align: center;
  display: flex;
  height: 170px;
  justify-content: center;
  align-items: center;
}

.side-user-icon {
  font-size: 60px;
}

.el-menu-item {
  width: 100%;
  text-align: center;
  font-size: 18px;
  border-bottom: #e6e6e6 1px solid;
  color: #838485;
}

.el-menu-item:hover {
  background-color: rgba(128, 128, 128, 0.308);
}

.el-menu-item:active {
  background-color: rgba(128, 128, 128, 0.308);
}

.activeDashboard{
  color:#b9771498;
  background-color: rgba(128, 128, 128, 0.308);
}
.activeStatus {
  color:#b9771498;
  background-color: rgba(128, 128, 128, 0.308);
}
.activeMessage {
  color:#b9771498;
  background-color: rgba(128, 128, 128, 0.308);
}
</style>
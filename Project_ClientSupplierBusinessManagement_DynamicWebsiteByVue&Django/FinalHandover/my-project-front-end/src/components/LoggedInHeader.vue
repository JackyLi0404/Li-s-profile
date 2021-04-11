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
      <el-dropdown trigger="click">
        <span class="el-dropdown-link">
          <el-button type="text"
            ><el-avatar icon="el-icon-user-solid"></el-avatar
          ></el-button>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item @click.native="toStatus">Check status</el-dropdown-item>
          <el-dropdown-item @click.native="toMessages">My messages</el-dropdown-item>
          <el-dropdown-item @click.native="toSettings">Update profile</el-dropdown-item>
          <el-dropdown-item @click.native="logout">Logout</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </el-menu>
</template>

<script>
export default {
  methods: {
    toStatus() {
      this.$router.push("/status");
    },
    toMessages() {
      this.$router.push("/message");
    },
    toSettings() {
      this.$router.push("/updateprofile");
    },
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/login");
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
  /* height: 8%; */
  height: 57px;
  top: 0%;
  left: 0%;
  right: 0%;
  padding: 0 30px;
  z-index: 9999;
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
  right: 3%;
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
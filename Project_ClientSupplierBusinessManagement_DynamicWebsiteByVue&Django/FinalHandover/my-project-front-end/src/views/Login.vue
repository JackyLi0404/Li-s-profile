<template>
  <el-container>
    <!-- header area -->
    <el-header>
      <index></index>
    </el-header>
    <!-- main area -->
    
    <div class="general-container">
      <el-main style="padding: 0px;">
      <div class="login-container">
        <!-- img box -->
        <div class="img-box">
          <img src="../assets/bckground.jpg" alt="supplier_login_img" />
        </div>
        <!-- supplier description box -->
        <div class="text-box">
          <h2>Supplier</h2>
          <h3>****************</h3>
          <h2>placeholder</h2>
          <h3>****************</h3>
        </div>
        <!-- login box -->
        <el-form
          hide-required-asterisk
          ref="loginForm"
          :model="loginForm"
          :rules="rules"
          label-width="80px"
          class="login-box"
        >
          <h3 class="login-title">Login with your email address</h3>
          <el-form-item prop="username">
            <el-input
              class="login-input"
              type="text"
              placeholder="Email"
              v-model="loginForm.username"
              @keydown.enter.native="onSubmit"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              class="login-input"
              type="password"
              placeholder="Password"
              v-model="loginForm.password"
              @keydown.enter.native="onSubmit"
            />
          </el-form-item>

          <!-- <el-form-item>
            <password-strength class="strength-bar" v-model="loginForm.password"></password-strength>
          </el-form-item> -->

          <a class="forgot-password" href="#javascript:;">forgot password?</a>

          <el-form-item>
            <el-button type="primary" v-on:click="onSubmit">Log in</el-button>
          </el-form-item>
          
        </el-form>
      </div>
      </el-main>
    </div>
    

    <!-- footer area -->
    <el-footer>
      <footerbox></footerbox>
    </el-footer>
  </el-container>
</template>

<script>
import index from "../components/Index";
import footerbox from "../components/FooterBox";
import CryptoJS from "crypto-js";
// import PasswordStrength from '../components/PasswordStrength';
// import qs from "qs";

export default {
  name: "Login",
  components: {
    index,
    footerbox,
    CryptoJS
  },
  data() {
    var validateUsername = (rule, value, callback) => {
      const mailReg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
      if (!value) {
        return callback(new Error("Email address cannot be empty"));
      }
      setTimeout(() => {
        if (mailReg.test(value)) {
          callback();
        } else {
          callback(new Error("Please enter a valid email address"));
        }
      }, 100);
    };

    return {
      salt: "",
      iteration: "",
      loginForm: {
        username: "",
        password: "",
      },
      //表单验证，需要再el-form-item 元素中增加prop属性
      rules: {
        username: [{ validator: validateUsername, trigger: "blur" }],
        password: [
          {
            required: true,
            message: "Please enter your password",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    onSubmit() {
      //validata the username and password
      this.$refs.loginForm.validate(async (valid) => {
        if (valid) {
          const salt_iteration = await this.login_site()

          if (salt_iteration.data.status === 200) {
            this.salt = salt_iteration.data.salt;
            this.iteration = salt_iteration.data.iteration;
          } else {
            this.$message({
              message: "Incorrect username or password",
              type: "error",
              offset: "10px",
            });
            return;
          }
          const result = await this.login_cof()
          if (result.data.status === 200) {
            this.$message({
                message: "Login successfully",
                type: "success",
                customClass: "zIndex",
              });
              window.sessionStorage.setItem("token", result.data.token);
              this.$router.push("/supplierhome");
          } else {
            this.$message({
                message: "Incorrect username or password",
                type: "error",
                offset: "10px",
              });
              return
          }

        } else {
          return;
        }
      });
    },
    login_site() {
      const salt_and_iteration = this.$http.post("login_site/", {
        username: this.loginForm.username,
      });
      return salt_and_iteration;
    },

    login_cof() {
      console.log("login_cof:");
      console.log(this.salt);
      var encryptedKey = CryptoJS.PBKDF2(this.loginForm.password, this.salt, {
        keySize: 32,
        iterations: this.iteration,
        hasher: CryptoJS.algo.SHA256,
      });
      const result = this.$http.post("login_cof/", {
        username: this.loginForm.username,
        password: encryptedKey.toString(),
      });
      return result;
    },
  },
};
</script>

<style scoped>
/* resolve height collapse issue */
/* .el-container{
  background-color: #f1f1f18c;
  height: 100%;
  width: 100%;
  
  position: absolute;
  white-space: nowrap;
  z-index: 1;
} */

.general-container{
  /* border: 1px red solid; */
  width: 1200px;
  height: 400px;
  /* width: 80%;
  height: 50%; */
  padding: 0px;
  /* box-sizing: content-box; */
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  
}

/* .el-main{
  border: 1px blue solid;
  padding: 0px;
} */

/* container */
.login-container {
  /* border: 1px red solid; */
  /* margin-top: 100px; */
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content:space-between;
  

  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
  /* font-size: 1rem; */
  /* left: 0%;
  right: 0%; */
}

/* flex element 1 */
.img-box {
  margin-right: 100px;
  /* margin-left: 0px; */
  width: 450px;
  /* width: 35%; */
  flex-shrink: 0;
}

/* flex element 2 */
.text-box {
  width: 150px;
  /* width: 10%;
  height: 50%; */
  margin-right: 100px;
  /* word-break: break-all;
  word-wrap: break-word;
  white-space: normal; */
  flex-shrink: 0;
  /* font-size: 10px; */
}

/* flex element 3 */
.login-box {
  border: 1px solid #dcdfe6;
  width: 350px;
  /* width: 27%;
  height: 73%; */
  padding: 35px 0px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 3px #909399;
  flex-shrink: 0;
}

.img-box img {
  vertical-align: top;
  width: 100%;
}

.login-title {
  text-align: center;
  margin: 0 auto 40px -80px;
  color: #303133;
}

.login-input {
  margin-left: -70px;
}

.strength-bar{
  margin-left: -75px;
  width: 255px;
}



.el-button--primary {
  color: rgba(0, 0, 0, 0.616);
  background-color: white;
  border-color: #d5d8df;
  margin-left: 120px;
  margin-top: 20px;
  margin-right: 65px;
}
.el-button--primary:hover {
  background-color: #d5d8df;
  border-color: #d5d8df;
}
.el-button--primary:active {
  background-color: #5a585a7c;
  border-color: #d5d8df;
}

.forgot-password {
  text-decoration: none;
  color: #ff9900;
  margin: 10px;
}
</style>
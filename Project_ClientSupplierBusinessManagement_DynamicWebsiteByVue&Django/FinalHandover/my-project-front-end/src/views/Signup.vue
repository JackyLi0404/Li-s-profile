<template>
  <el-container>
    <!-- header area -->
    <el-header>
      <index></index>
    </el-header>
    <!-- main area -->
    <div class="general-container">
      <el-main>
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
          ref="signupForm"
          :model="signupForm"
          :rules="rules"
          label-width="80px"
          class="signup-box"
        >
          <h3 class="signup-title">Sign up with your email address</h3>
          <el-form-item prop="username">
            <el-input
              class="signup-input"
              type="text"
              placeholder="Email"
              v-model="signupForm.username"
              @keydown.enter.native="onSubmit"
            />
          </el-form-item>
          <el-form-item prop="password">
            <el-input
              class="signup-input"
              type="password"
              placeholder="Password"
              v-model="signupForm.password"
              @keydown.enter.native="onSubmit"
            />
            </el-form-item>
            <el-form-item prop="repeat">
            <el-input
              class="signup-input"
              type="password"
              placeholder="Password Confirm"
              v-model="signupForm.repeat"
              @keydown.enter.native="onSubmit"
            />
          </el-form-item>

          <el-form-item>
            <password-strength  class="strength-bar" v-model="signupForm.password"></password-strength>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" v-on:click="onSubmit">Sign up</el-button>
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
import passwordStrength from '../components/PasswordStrength';

export default {
  name: "signup",
  components: {
    index,
    footerbox,
    CryptoJS,
    passwordStrength
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
     var validatePass = (rule, value, callback) => {
       console.log(value,"kkkkk")
        if (value.length <8) {
          callback(new Error('The password should be longer than 8 digits.'));
        } else {
          // if (this.signupForm.password !== '') {
          //   this.$refs.signupForm.validateField('password');
          // }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        console.log(value,this.signupForm,"hhhhhhhhh")
        if (value.length <8) {
          callback(new Error('The password should be longer than 8 digits.'));
        } else if (value !== this.signupForm.password) {
          callback(new Error('Two inputs don\'t match!'));
        } else {
          callback();
        }
      };

    return {
      signupForm: {
        username: "",
        password: "",
        repeat: ''
      },
      //表单验证，需要再el-form-item 元素中增加prop属性
      rules: {
        username: [{ validator: validateUsername, trigger: "blur" }],
        password: [
            { validator: validatePass, trigger: 'blur' }
          ],
        repeat: [
            { validator: validatePass2, trigger: 'blur' }
        ],
      },
    };
  },
  methods: {
    
    onSubmit() {
      //validata the username and password
      this.$refs.signupForm.validate(async (valid) => {
        if (valid) {
          // use PBKDF2 and sha-256 to enrypt the password, with 1000 iterations
          var salt = CryptoJS.lib.WordArray.random(128 / 8).toString(); 

          var encryptedKey = CryptoJS.PBKDF2(this.signupForm.password, salt, {
                keySize: 32,
                iterations: 1000,
                hasher: CryptoJS.algo.SHA256,
              });      
          // send post request with encrypted password to validate if the username and password are correct   
          const result = await this.$http.post(
            "signup_site/",
            {
              username: this.signupForm.username,
              password: encryptedKey.toString(),
              salt: salt,
              iteration: "1000"
            }
          );
          // if result is correct, store the token and push to dashboard page
          if (result.data.status == 200) {
            this.$message({
              message: "Sign up successfully",
              type: "success",
              offset: "10px",
              customClass: "zIndex",
            });

            window.sessionStorage.setItem("token", result.data.token);

            this.$router.push("/supplierhome");
          } else {
            this.$message({
              message: "Incorrect username or password",
              type: "error",
              offset: "10px",
              customClass: "zIndex",
            });
          }
          //if vaild,pop up a success message and push to dashboard page
        } else {
          return;
        }
      });
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
  /* width: 500px; */
  height: 330px;
  /* height: 100%; */
  /* border: black 1px solid; */
  flex-shrink: 1;
}

/* flex element 2 */
.text-box {
  width: 150px;
  margin-right: 100px;
  /* word-break: break-all;
  word-wrap: break-word;
  white-space: normal; */
  flex-shrink: 1;
}

/* flex element 3 */
.signup-box {
  border: 1px solid #dcdfe6;
  width: 350px;
  height: 330px;
  padding: 35px 0px 15px 35px;
  border-radius: 5px;
  -webkit-border-radius: 5px;
  -moz-border-radius: 5px;
  box-shadow: 0 0 3px #909399;
  flex-shrink: 1;
}

.img-box img {
  vertical-align: top;
  /* width: 100%; */
  height: 100%;
}

.signup-title {
  text-align: center;
  margin: 0 auto 40px -80px;
  color: #303133;
}

.signup-input {
  margin-left: -70px;
}

.strength-bar {
  margin-left: -75px;
  width: 255px;
}

.el-button--primary {
  color: rgba(0, 0, 0, 0.616);
  background-color: white;
  border-color: #d5d8df;
  margin-left: 110px;
  margin-top: 10px;
  margin-right: 75px;
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
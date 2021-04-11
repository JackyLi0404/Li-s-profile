<template>
  <div class="update">
    <el-card
      shadow="always"
      class="card-box"
      :body-style="cardContainerStyle"
      style="width: 30%"
      :data="tableData"
      :stript="true"
    >
      <div class="profile-page">
        <form v-if="!isReg">
          <!-- job title -->
          <h1
            style="
              padding: 10px;
              margin: 10px 10px;
              font-size: 16px;
              -webkit-box-shadow: -4px 1px 3px 0px #e7e8e8;
              box-shadow: -4px 1px 3px 0px #e7e8e8;
            "
          >
            Profile
          </h1>
          <div class="form-container"></div>
          <el-form
            label-width="150px"
            ref="updateForm"
            :model="ruleForm"
            :rules="rules"
            style="width: 600px"
          >
            <el-form-item
              style="margin-left: 100px"
              label="first name"
              prop="firstname"
            >
              <el-input v-model="ruleForm.firstname"></el-input>
            </el-form-item>
            <el-form-item
              style="margin-left: 100px"
              label="last name"
              prop="lastname"
            >
              <el-input v-model="ruleForm.lastname"></el-input>
            </el-form-item>
            <el-form-item
              style="margin-left: 100px"
              label="business name"
              prop="businessname"
            >
              <el-input v-model="ruleForm.businessname"></el-input>
            </el-form-item>
            <el-form-item
              style="margin-left: 100px"
              label="password"
              prop="pass"
            >
              <el-input type="password" v-model="ruleForm.pass"></el-input>
            </el-form-item>
            <el-form-item
              style="margin-left: 100px"
              label="password confirm"
              prop="check"
            >
              <el-input type="password" v-model="ruleForm.check"></el-input>
            </el-form-item>
            <el-form-item style="margin-left: 100px" label="ABN" prop="ABN">
              <el-input v-model="ruleForm.ABN"></el-input>
            </el-form-item>
            <el-form-item style="margin-left: 100px" label="ACN" prop="ACN">
              <el-input v-model="ruleForm.ACN"></el-input>
            </el-form-item>
            <el-form-item
              style="margin-left: 100px"
              label="address"
              prop="address"
            >
              <el-input v-model="ruleForm.address"></el-input>
            </el-form-item>
            <!-- <el-form-item style="margin-left: 100px" label="upload avatar">
              <el-upload
              class="avatar-uploader"
              action="https://jsonplaceholder.typicode.com/posts/"
              :show-file-list="true"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              limit="1"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
            </el-form-item> -->
            
          </el-form>

          <div
            slot="footer"
            class="dialog-footer"
            style="margin-left: 415px; margin-bottom: 20px"
          >
            <el-button type="primary" @click="submitForm">Submit</el-button>
            <el-button>Cancel</el-button>
          </div>
        </form>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "update",
  data() {
    return {
      isReg: false,
      tableData: "",
      cardContainerStyle: {
        display: "flex",
        padding: "0px",
      },
      ruleForm: {
        firstname: "",
        lastname: "",
        pass: "", //密码
        check: "", //二次密码
        email: "",
        phone_number: "",
        landline_no: "",
        address: "",
        ABN: "",
        ACN: "",
        notification: "",
        businessname: ""
      },
      rules: {
        firstname: [
          {
            required: false,
            message: "please input firstname",
            trigger: "blur",
          },
        ],
        lastname: [
          {
            required: false,
            message: "please input lasttname",
            trigger: "blur",
          },
        ],
        pass: [
          {
            required: false,
            message: "please input password",
            trigger: "blur",
          },
        ],
        check: [
          {
            required: false,
            message: "please input password again",
            trigger: "blur",
          },
        ],
        email: [
          { required: true, message: "please input email", trigger: "blur" },
        ],
        phone_number: [
          {
            required: true,
            message: "please input phone number",
            trigger: "blur",
          },
        ],
        landline_no: [
          {
            required: true,
            message: "please input landline number",
            trigger: "blur",
          },
        ],
        address: [
          { required: false, message: "please input address", trigger: "blur" },
        ],
        ABN: [{ required: true, message: "please input ABN", trigger: "blur" }],
        ACN: [{ required: true, message: "please input ACN", trigger: "blur" }],
        notification: [
          {
            required: true,
            message: "please input notification",
            trigger: "blur",
          },
        ],
        businessname: [
          {
            required: true,
            message: "please input businessname",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    submitForm() {
      this.$refs.updateForm.validate(async (valid) => {
        if (valid) {
          console.log("this.ruleForm");
          console.log(this.ruleForm);
          var token_form = {
            firstname: this.ruleForm.firstname,
            lastname: this.ruleForm.lastname,
            pass: this.ruleForm.pass, //密码
            check: this.ruleForm.check, //二次密码
            address: this.ruleForm.address,
            ABN: this.ruleForm.ABN,
            ACN: this.ruleForm.ACN,
            businessname: this.ruleForm.businessname,
            token: sessionStorage.getItem("token"),
          };
          const result = await this.$http.post("update_profile/", token_form);
          console.log(result);
          if (result.data.status === 200) {
            this.$message({
              message: "Update successfully",
              type: "success",
              offset: "10px",
              customClass: "zIndex",
            });
          } else {
            this.$message({
              message: "submit failed",
              type: "error",
              offset: "10px",
              customClass: "zIndex",
            });
          }
          this.$router.push("/dashboard");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm() {
      this.$refs.updateForm.resetFields();
    },
  },
};
</script>

<style scoped>
/* card container */
.update {
  /* padding-top: 0px; */
  /* border: 1px red solid; */
  padding-top:220px;
  width:700px;
  height: 800px;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}
.el-card.card-box {
  width: 100% !important;
  margin-top: 0px;
  padding-top: 5px;
  position: absolute;
  left: 0%;
  z-index: 3;
}

/* img upload css */
/* .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
  border: 1px dashed #d9d9d9;
}

.avatar-uploader-icon:hover {
  border-color: #409eff;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
} */
</style>

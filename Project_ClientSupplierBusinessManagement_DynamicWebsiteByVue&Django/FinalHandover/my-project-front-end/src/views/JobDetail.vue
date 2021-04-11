<template>
  <div class="detail">
    <loginheader></loginheader>
    <div class="generalCss">
      <div class="content">
        <div class="left">
          <ul class="left-list">
            <li
              class="cardBox"
              v-for="(item, index) of jobList"
              :key="index"
              @click="tabs(index)"
              :class="{ cardBoxActive: index == tabsIndex }"
            >
              <div>
                  <span style="margin-bottom: 5px; font-size: 15px; font-weight: bold">{{ item.title }}</span>
              </div>
              
              <div>
                
                <span
                  ><i class="el-icon-tickets"></i>&#32;{{ item.category }}</span
                >
                <span
                  ><i class="el-icon-document-checked"></i>&#32;Expect: ${{
                    item.budget
                  }}</span
                >
              </div>
              <div style="margin-top: 20px">
                <span 
                  ><i class="el-icon-map-location"></i>&#32;{{
                    item.suburb
                  }}</span
                >
                <span><i class="el-icon-timer"></i>&#32;{{ item.date }}</span>
              </div>
            </li>
          </ul>
        </div>
        <div class="right">
          <h1>{{ jobInfo.title }}</h1>
          <h3 style="margin-top: 30px">Details</h3>
          <div class="rightBox">
            <div class="BoxLine">
              <i class="el-icon-tickets a" style="font-size: 16px">
                <p>{{ jobInfo.category }}</p>
              </i>
              <i class="el-icon-timer a" style="font-size: 16px">
                <p>{{ jobInfo.date }}</p>
              </i>
            </div>
            <div class="BoxLine">
              <router-link
                class="customerID"
                @click.native="addChatUser"
                to="/message"
              >
                <i
                  class="el-icon-user-solid a"
                  style="font-size: 16px; display: block"
                >
                  <div style="display: inline-block">
                    <p>Poster</p>
                    <p style="font-size: 12px">{{ jobInfo.customer_id_id }}</p>
                  </div>
                </i>
              </router-link>
              <i class="el-icon-document-checked a" style="font-size: 16px">
                <p>$ {{ jobInfo.budget }}</p>
              </i>
            </div>
          </div>
          <div class="cost">
            <h4>Job Description</h4>
            <span style="width: 65%">{{ jobInfo.description }}</span>
          </div>
          <div style="width: 20%">
            <el-input v-model="input" placeholder="Your Quote"></el-input>
          </div>
          <div style="width: 100%; margin-top: 10px">
            <el-input
              type="textarea"
              placeholder="Additional Comment"
              v-model="textarea"
              maxlength="60"
              show-word-limit
            >
            </el-input>
          </div>
          <div class="offerbutton">
            <el-button type="success" plain @click="open"
              >Make an Offer</el-button
            >
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import index from "../components/Index";
import loginheader from "../components/LoggedInHeader"
export default {
  name: "JobDetail",
  components: {
    index,loginheader

  },
  data() {
    return {
      tabsIndex: null,
      jobInfo: {},
      input: "",
      textarea: "",
    };
  },
  mounted: function () {
    console.log(this.$route.query.selectedJob)
    var urlId = this.$route.query.id;
    if(this.$route.query.jobList[0] === "[object Object]"){
      console.log("if statement")
      this.jobList = JSON.parse(sessionStorage.getItem('jobList'));
      this.jobInfo = JSON.parse(sessionStorage.getItem('selectedJob'));
    } else {
      sessionStorage.setItem('jobList', JSON.stringify(this.$route.query.jobList));
      sessionStorage.setItem('selectedJob', JSON.stringify(this.$route.query.selectedJob));
      this.jobList = JSON.parse(sessionStorage.getItem('jobList'));
      this.jobInfo = JSON.parse(sessionStorage.getItem('selectedJob'));
    }
    for(var i=0; i < this.jobList.length; i++){
      if(this.jobList[i].id === this.jobInfo.id){
        this.tabsIndex = i;
      }
    }

    // this.tabsIndex = 0;
  },
  methods: {
    tabs(index) {
      this.tabsIndex = index;
      this.jobInfo = this.jobList[index];
    },
    open() {
      this.sendCommentAndQuote();
    },
    async sendCommentAndQuote() {
      console.log(this.jobInfo);

      var job_with_token = this.jobInfo;

      var bidOnInfo = {
        date: this.jobInfo.date,
        order_state: this.jobInfo.order_state,
        title: this.jobInfo.title,
        description: this.jobInfo.description,
        category: this.jobInfo.category,
        budget: this.jobInfo.budget,
        suburb: this.jobInfo.suburb,
        token: sessionStorage.getItem('token')
      };

      console.log("bidOnInfo")
      console.log(bidOnInfo)
      

      // console.log(typeof job_with_token);

      const result = await this.$http.post("createNewOrder/", bidOnInfo);
      console.log(result)
      if (result.data.status === 200) {
        this.$message({
              message: "Make an Offer Successfully!",
              type: "success",
              offset: "10px",
              customClass: "zIndex"
            });
        this.textarea = "";
        this.input = "";
      } else {
        this.$message("Error! Please resubmit your offer");
      }
    },
    async addChatUser() {

      var myDate = new Date();
      Date.prototype.Format = function (fmt) {
        var o = {
          "M+": this.getMonth() + 1, //月份
          "d+": this.getDate(), //日
          "H+": this.getHours(), //小时
          "m+": this.getMinutes(), //分
          "s+": this.getSeconds(), //秒
          "q+": Math.floor((this.getMonth() + 3) / 3), //季度
          S: this.getMilliseconds(), //毫秒
        };
        if (/(y+)/.test(fmt))
          fmt = fmt.replace(
            RegExp.$1,
            (this.getFullYear() + "").substr(4 - RegExp.$1.length)
          );
        for (var k in o)
          if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(
              RegExp.$1,
              RegExp.$1.length == 1
                ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length)
            );
        return fmt;
      };
      
      var currentDate = myDate.Format("yyyy-M-d HH:mm:ss")
      // console.log(currentDate)

      const result = await this.$http.post("new_messages/", {
        token: sessionStorage.getItem('token'),
        customer_id: this.jobInfo.customer_id_id,
        date: currentDate
      });

      console.log("add new chat user result")
      console.log(result)
    }
  },
};
</script>

<style scoped>
.detail {
  font: 400 13.3333px Arial;
  height: 100%;
}
.cardBox {
  cursor: pointer;
}
.cardBox:hover {
  box-shadow: 10px 10px 8px rgba(0, 0, 0, 0.3);
}
.cardBoxActive {
  box-shadow: 10px 10px 8px #DDDDDD;
}
.generalCss {
  padding-top:88px;
  width:1200px;
  height: 800px;
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  margin: auto;
}
.content {
  display: flex;
  padding-top: 40px;
  height: 94%;
  width: 100%;
  margin-left: 5%;
}
.left {
  color: #838485;
  border-right: 1px solid rgb(221, 221, 221);
  padding: 0 0 0 30px;
  height: 700px;
  overflow: hidden;
  width: 38%;
}
.left-flex {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 10px 0;
}
.left-list {
  margin-top: 20px;
  padding-top: 20px;
  height: 100%;
  overflow: auto;
}
.left-list li {
  display: flex;
  align-items: center;
  padding: 15px;
  border: 1px solid #DDDDDD;
  border-left: 2px solid #FF9900;
  margin-bottom: 20px;
  font-size: 12px;
  position: relative;
  height: 50px;

}

.left-list li:last-child {
  margin-bottom: 50px;
}
.left-list li > div {
  display: flex;
  flex-direction: column;
  text-align: left;
}


.left-list li > div:nth-child(1) {
  margin-right: 20px;
  position: absolute;
  top: 8%;

}
.left-list li > div:nth-child(2) {
  margin-right: 20px;
  position: absolute;
  left: 5%;
  top: 43%;
}
.left-list li > div:nth-child(3) {
  margin-right: 20px;
  position: absolute;
  top: 20%;
  left: 50%;
}
.customerID {
  color: rgb(255, 200, 98);
}
.customerID :hover {
  color: rgb(255, 102, 0);
}
.right {
  padding: 40px;
}
.rightBox {
  display: flex;
  justify-content: space-between;
  align-content: center;
  align-items: center;
  width: 500px;
}
.BoxLine > i {
  margin-top: 40px;
}
.cost {
  display: flex;
  flex-direction: column;
  margin: 20px 0;
  font-size: 16px;
}
.cost > * {
  padding: 8px 0;
}
.a {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}
.a:before {
  font-size: 40px;
  margin-right: 20px;
}

.left-list {
  padding-right: 50px;
}
.left-list::-webkit-scrollbar {
  width: 6px;
  height: 200px;
}

.left-list::-webkit-scrollbar-thumb {
  border-radius: 10px;
  box-shadow: inset 0 0 5px #d8d8d8;
  background: #535353;
}

.left-list::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px #d8d8d8;
  border-radius: 10px;
  background: #ededed;
}

.offerbutton {
  margin-top: 25px;
  margin-left: 400px;
}
</style>

<template>
  <div class="right">
    <h1>{{ jobInfo.title }}</h1>
    <div class="location">
        <i class="el-icon-location"></i>
        <span>{{ jobInfo.suburb }}</span>
      </div>
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
        <router-link class="customerID" @click.native="addChatUser" to="/message">
          <i class="el-icon-user-solid a" style="font-size: 16px; display: block">
          <div style="display: inline-block">
            <p>Poster</p>
            <p>{{jobInfo.id}}</p>
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
  </div>
</template>

<script>
export default {
  name: "HelloWorld",
  created() {
    // console.log(this.$route.query.id); 
    // console.log(this.$route.query.title); 
    // console.log(this.$route.query.suburb);
    // console.log(this.$route.query.date);
    // console.log(this.$route.query.description);

    let info = {
      id: this.$route.query.id,
      title: this.$route.query.title,
      suburb: this.$route.query.suburb,
      date: this.$route.query.date,
      description: this.$route.query.description,
      category: this.$route.query.category,
      budget: this.$route.query.budget,
    };

    this.jobInfo = info;
  },
  data() {
    return {
      jobInfo: {},
    };
  },
  methods:{
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
  }
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.right {
  padding: 40px;
  margin-left: 450px;
  margin-top: 40px;
  width: 600px;
  border: 1px #D7DAE2 solid;
  border-radius: 4px;
  background-color: rgb(235, 235, 235);
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

.el-icon-location{
  font-size: 30px;
}

.customerID {
  color: rgb(248, 189, 78);
}
.customerID :hover {
  color: rgb(255, 102, 0);
}
</style>

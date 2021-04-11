<template>
  <div class="message-container">
    <!-- chat user list -->
    <div class="chat-user-container">
      <el-badge
        class="single-chat-user"
        v-for="(item, index) in userList"
        :key="index"
      >
        <el-button @click="chooseEvent(item)">
          <el-avatar :src="require('@/assets/3.jpg')"></el-avatar>
          <div class="username">{{ item.customer_name }}</div>
          <div class="date">{{ item.date }}</div>
        </el-button>
      </el-badge>
    </div>

    <!-- chat window -->
    <div id="dialogue_box" class="chat-window pop">
      <!-- recordContent 聊天记录数组-->
      <div v-for="(item, index) in currentChat" :key="index">
        <!-- 对方 -->
        <div class="word" v-if="!item.mineMessage">
          <img :src="require('@/assets/3.jpg')" alt="" />
          <!-- <img :src="item.userimg" > -->
          <div class="info-word">
            <p class="time-word">{{ item.customer_name }} {{ item.date }}</p>
            <div class="info-content">{{ item.content }}</div>
          </div>
        </div>
        <!-- 我的 -->
        <div class="word-my" v-else>
          <div class="info-word-my">
            <p class="time-word-my">
              {{ item.date }}
            </p>
            <div class="info-content-my">{{ item.content }}</div>
          </div>
          <img :src="require('@/assets/3.jpg')" />
        </div>
      </div>
    </div>

    <!-- input box -->
    <div class="input-box">
      <el-input
        class="textarea-box"
        type="textarea"
        :rows="5"
        placeholder="type here"
        v-model="textarea"
        @keyup.enter.native="sendMessage()"
      >
      </el-input>
      <el-button @click.native="sendMessage()" class="send-button"
        >send</el-button
      >
    </div>
  </div>
</template>

<script>
import index from "../components/Index";
import loggedinheader from "../components/LoggedInHeader";
import io from "socket.io-client";
export default {
  // control the scroll bar to scroll down to bottom each time the window is remounted
  updated: function () {
    this.$nextTick(function () {
      var div = document.getElementById("dialogue_box");
      div.scrollTop = div.scrollHeight;
    });
  },
  data() {
    return {
      userList: [],
      currentChat: [],
      textarea: "",
      chat_with_customer_id: "",
    };
  },
  // send request to fetch user list before
  created() {
    this.getUserList();
    console.log(this.$route.query.customerId);
  },

  mounted: function () {
    // this.getMessage();
    this.myInterval = window.setInterval(() => {
      setTimeout(() => {
        this.getUserList(); //调用接口的方法
      }, 1);
    }, 1000);
    // setInterval(this.getUserList(), 3000);
  },

  destroyed(){
  clearInterval(this.myInterval)
  },
 

  methods: {
    async chooseEvent(user) {
      // store id of the user that you are trying to chat with
      this.chat_with_customer_id = user.customer_id;
      // console.log("this.chat_with_customer_id")
      // console.log(this.chat_with_customer_id)

      // get chat message  through http request
      const result = await this.$http.post("give_messages/", {
        token: sessionStorage.getItem("token"),
        customer_id: this.chat_with_customer_id,
      });
      console.log("give_messages");
      console.log(result);

      var chatMessage = [];

      chatMessage = result.data.data;

      for (let i = 0; i < chatMessage.length; i++) {
        if (chatMessage[i].sender === "Customer") {
          chatMessage[i].mineMessage = false;
        } else {
          chatMessage[i].mineMessage = true;
        }
      }

      // adjust date format
      for (let j = 0; j < chatMessage.length; j++) {
        // chatMessage[j].date
        if (chatMessage[j].date === null) {
          continue;
        } else {
          var split1 = chatMessage[j].date.split("T");
          var join = split1.join(" ");
          join = join.substr(0, join.length - 1);
          chatMessage[j].date = join;
        }
      }

      this.currentChat = chatMessage;
      console.log("current chat content list");
      console.log(this.currentChat);

      // var myDate = new Date();
      // console.log(myDate.toLocaleString())
    },
    async sendMessage() {
      if (this.textarea.trim().length === 0) return;

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
      console.log(myDate.Format("yyyy-M-d HH:mm:ss"));

      // update the new message to the chat window
      let newMessage = {
        customer_id: this.chat_with_customer_id,
        text: this.textarea,
        timestamp: myDate.Format("yyyy-M-d HH:mm:ss"),
        token: sessionStorage.getItem("token"),
      };
      console.log("newMessage");
      console.log(newMessage);

      // var test = "2020-11-22T13:27:30Z"
      // var split1 = test.split('T')
      // var join = split1.join(' ')

      // console.log("split1");
      // console.log(split1);
      // console.log("join");
      // console.log(join);
      // join = join.substr(0, join.length - 1);
      // console.log("join");
      // console.log(join);

      var temp = {
        mineMessage: true,
        customer_id: newMessage.customer_id,
        content: newMessage.text,
        date: myDate.Format("yyyy-M-d HH:mm:ss"),
      };

      this.currentChat.push(temp);
      this.textarea = "";

      // send the latest message to backend server
      const result = await this.$http.post("get_messages/", newMessage);
      console.log("get_messages");
      console.log(result);
    },
    async getUserList() {
      const result = await this.$http.post("initial_messages/", {
        token: sessionStorage.getItem("token"),
      });
      console.log("init userlist");
      console.log(result);
      var rawUserList = result.data.data;
      var modifiedUserList = [];

      // filter out information that is not needed
      for (var i = 0; i < rawUserList.length; i++) {
        var singleUser = {
          customer_id: rawUserList[i].customer_id_id,
          customer_name: rawUserList[i].customer_name,
          date: rawUserList[i].date,
        };

        var exist = false;
        for (var j = 0; j < modifiedUserList.length; j++) {
          if (singleUser.customer_id === modifiedUserList[j].customer_id) {
            exist = true;
          }
        }
        if (!exist) modifiedUserList.push(singleUser);
      }

      // adjust date format
      for (let j = 0; j < modifiedUserList.length; j++) {
        // chatMessage[j].date
        if (modifiedUserList[j].date === null) {
          continue;
        } else {
          var split1 = modifiedUserList[j].date.split("T");
          var split2 = split1[0].split("-");
          var join = split2[1] + "-" + split2[2];
          modifiedUserList[j].date = join;
        }
      }
      console.log("modifiedUserList");
      console.log(modifiedUserList);
      if(this.userList.toString() === modifiedUserList.toString()){
        return;
      } else {
        this.userList = modifiedUserList;
      }
      
      this.userList = modifiedUserList;
      console.log("UserList");
      console.log(this.userList);
    },
  },
  components: {
    index,
    loggedinheader,
  },
};
</script>

<style scoped>
/* container */
.message-container {
  position: absolute;
  width: 1000px;
  height: 645px;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.3);
  left: 380px;
  top: 8.3%;
  bottom: 1%;
  background-color: #c7d1d996;
}

/* chat user list area */
.chat-user-container {
  position: absolute;
  z-index: 2;
  /* border: red 1px solid; */
  /* background-color: #fff; */
  background-color: rgba(255, 255, 255, 0.349);
  width: 25%;
  left: 0.3%;
  /* height: 700px; */
  top: 0.5%;
  bottom: 0.5%;
  padding-top: 10px;
  padding-bottom: 10px;
  /* padding-right: 12px; */

  display: flex;
  flex-direction: column;
  align-items: center;
  box-shadow: 1px 2px 8px rgba(0, 0, 0, 0.3);
}

.chat-user-container .single-chat-user {
  width: 98%;
  /* border: black 1px solid; */
}

.chat-user-container .single-chat-user .el-button {
  width: 100%;
  border: none;
  border-radius: 10px;
  position: relative;
  height: 70px;
  background-color: transparent;
}

.chat-user-container .single-chat-user .el-button .el-avatar {
  position: absolute;
  left: 2%;
  top: 25%;
}
.chat-user-container .single-chat-user .el-button .username {
  position: absolute;
  left: 22%;
  top: 43%;
  font-size: 19px;
}
.chat-user-container .single-chat-user .el-button .date {
  position: absolute;
  right: 10%;
  top: 60%;
  font-size: 16px;
}

.single-chat-user .el-button:hover {
  background-color: #c4cfd277;
  color: black;
  border: none;
}

/* chat window */
.chat-window {
  position: absolute;
  height: 65%;
  overflow: auto;

  top: 0.5%;
  left: 26%;
  right: 0.5%;
  background-color: #f5f5f5c9;
  z-index: 1;

  padding: 15px;
  padding-top: 25px;
}

.word {
  display: flex;
  margin-bottom: 20px;
}

img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}
.info-word {
  margin-left: 10px;
}

.time-word {
  font-size: 12px;
  color: rgba(51, 51, 51, 0.8);
  margin: 0;
  height: 20px;
  line-height: 20px;
  margin-top: -5px;
}

.info-content {
  padding: 12px;
  /* border: 1px red solid; */
  font-size: 16px;
  background: #fff;
  position: relative;
  margin-top: 8px;
  margin-bottom: 5px;
}

/* set the icon shape of message box */
.info-content::before {
  position: absolute;
  left: -8px;
  top: 8px;
  content: "";
  border-right: 10px solid #fff;
  border-top: 8px solid transparent;
  border-bottom: 8px solid transparent;
}

.word-my {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 20px;
}

img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
}

.info-word-my {
  width: 90%;
  margin-left: 10px;
  text-align: right;
}

.time-word-my {
  font-size: 12px;
  color: rgba(51, 51, 51, 0.8);
  margin: 0;
  height: 20px;
  line-height: 20px;
  margin-top: -5px;
  margin-right: 10px;
}
.info-content-my {
  max-width: 70%;
  padding: 12px;
  font-size: 16px;
  float: right;
  margin-right: 10px;
  position: relative;
  margin-top: 8px;
  background: #a3c3f6;
  text-align: left;
  margin-bottom: 5px;
}
.info-content-my::after {
  position: absolute;
  right: -8px;
  top: 8px;
  content: "";
  border-left: 10px solid #a3c3f6;
  border-top: 8px solid transparent;
  border-bottom: 8px solid transparent;
}
/*滚动条整体样式*/
.pop::-webkit-scrollbar {
  width: 6px; /*竖向滚动条的宽度*/
  height: 6px; /*横向滚动条的高度*/
}
.pop::-webkit-scrollbar-thumb {
  /*滚动条里面的小方块*/
  background: #ccc;
  border-radius: 3px;
}
.pop::-webkit-scrollbar-track {
  /*滚动条轨道的样式*/
  background: #f0f1f2;
  border-radius: 3px;
}

/* input box */
.input-box {
  position: absolute;
  z-index: 1;
  background-color: rgb(255, 255, 255);
  height: 22%;
  width: 73.5%;
  box-sizing: content-box;
  top: 71.5%;
  bottom: 1%;
  right: 0.5%;
  padding-bottom: 3%;
  margin-top: 4px;
}


.send-button {
  position: absolute;
  color: black;
  background-color: #f5a021e0;
  border: #ff9900;
  width: 10%;
  bottom: 5%;
  right: 1%;
  width: 10%;
}
.send-button:hover {
  background-color: #e98e06;
  border: #e98e06;
}
.send-button:active {
  background-color: #e98e06;
  border: #e98e06;
}
</style>
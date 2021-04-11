<template>
  <div>
    <!-- job box -->
    <div class="job-list-box">
      <div class="job-list-title">Recent jobs</div>
      <router-link :to="{path: '/jobdescription', query: this.choosenJobItem}">
        <jobcard
          v-for="item in filteredJobList"
          :key="item.id"
          v-bind:job="item"
          @click.native="chooseJob(item)"
        ></jobcard>
      </router-link>
    </div>
    <!-- notification box -->
    <ul class="notification-box">
      <li>Notifications</li>
      <li v-for="item in notificationList" :key="item.id">
        <!-- <router-link class="notification-link" to="/random"> -->
          <div class="notification-link">
            {{ item.title }}
          </div>
        <!-- </router-link> -->
        <div>budget: {{ item.budget }}</div>
        <div>date: {{ item.date }}</div>
      </li>
    </ul>
  </div>
</template>

<script>
import jobcard from "../components/JobCard";
export default {
  components: {
    jobcard
  },
  created() {
    this.getFiveRecentJobs()
    this.getNotificationList()
  },
  data() {
    return {
      filteredJobList: [],
      choosenJobItem:{},
      notificationList: [
        // {
        //   id: "11111",
        //   title: "Clean roof of leaves",
        //   category: "quote",
        //   order_state: "accepted"
        // },
        // {
        //   id: "11112",
        //   title: "General inspection of water pipes in roof",
        //   category: "quote",
        //   order_state: "rejected"
        // },
        // {
        //   id: "11113",
        //   title: "Clean, remove leaves in gutters and birds nests",
        //   category: "job",
        //   order_state: "in progress"
        // },
        // {
        //   id: "11114",
        //   title: "Repair leaking toilet",
        //   category: "job",
        //   order_state: "completed"
        // },
        // {
        //   id: "11115",
        //   title: "Backyard cleaning",
        //   category: "quote",
        //   order_state: "accepted"
        // },
      ],
    };
  },
  methods:{
    chooseJob(item) {
      this.choosenJobItem = item
      console.log("choosen job item:")
      console.log(this.choosenJobItem)
    },
    toJobDescription(job) {
      console.log('click router-link event')
      console.log(job)
      this.$router.push({
                name: "JobDescription",
                params: {
                  id: 2048,
                  book: "了不起的Node.js",
                  job: "Web前端",
                  list: this.filteredJobList
                }
            });
    },
    async getFiveRecentJobs() {
      const result = await this.$http.post("get_order_profile/", {
        token: window.sessionStorage.getItem('token'),
      });
      console.log("result: ")
      console.log(result)
      var list = result.data.data

      for (let j = 0; j < list.length; j++) {
        // chatMessage[j].date
        if (list[j].date === null) {
          continue;
        } else {
          var split1 = list[j].date.split("T");
          var join = split1.join(" ");
          join = join.substr(0, join.length - 1);
          list[j].date = join;
        }
      }
      
      for (var i = 0; i < 5; i++) {
      this.filteredJobList.push(list[i]);
      // console.log(jobList[i])

      // this.filteredJobList = result.data.data
    }
      // console.log(this.filteredJobList)
    },
    async getNotificationList(){
      const result = await this.$http.post("save_quote/", {
        token: window.sessionStorage.getItem('token'),
      });
      console.log("notification result:")
      console.log(result)
      
      this.notificationList = result.data.data

      for (let j = 0; j < this.notificationList.length; j++) {
        // chatMessage[j].date
        if (this.notificationList[j].date === null) {
          continue;
        } else {
          var split1 = this.notificationList[j].date.split("T");
          var join = split1.join(" ");
          join = join.substr(0, join.length - 1);
          this.notificationList[j].date = join;
        }
      }

      console.log(this.notificationList)
      

    }
  }
};
</script>

<style scoped>
/* job list area */
.job-list-box {
  position: absolute;
  top: 8%;
  left: 23%;
  right: 30%;
  width: 600px;
  /* border: 1px red solid; */
  padding-left: 7%;
  padding-right: 7%;
  /* background-color: #dddddd3a; */
}
.job-list-title {
  font-size: 25px;
  margin-top: 20px;
  line-height: 45px;
  /* border: red 1px solid; */
  border-bottom: 1px solid #c7cdd1;
}

/* notification area */
.notification-box {
  position: absolute;
  top: 8%;
  left: 70%;
  width: 350px;
}

.notification-box li {
  list-style-type: none;
  /* border: red 1px solid; */
  border-bottom: 1px solid #c7cdd1;
  height: 75px;
  /* position: absolute; */
  /* width: 80px; */
  /* vertical-align: ; */
}

.notification-box li:first-child {
  font-size: 25px;
  margin-top: 10px;
  height: 40px;
  /* border: red 1px solid; */
  border-bottom: 1px solid #c7cdd1;
}

.notification-box li:not(:first-child) {
  font-size: 19px;
  color: #8b969e;
}

.notification-link {
  display: inline-block;
  font-size: 20px;
  width: 100%;
  text-decoration: none;
  color: #b9771498;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.notification-link:hover {
  text-decoration: underline;
}
</style>
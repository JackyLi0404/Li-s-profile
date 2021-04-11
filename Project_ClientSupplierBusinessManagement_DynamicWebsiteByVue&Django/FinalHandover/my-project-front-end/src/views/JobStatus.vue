<template>
  <div class="status-page">
    <!-- dropdown area -->
    <el-dropdown @command="handleCommand">
      <span class="el-dropdown-link">
        {{ currentCommand }}<i class="el-icon-arrow-down el-icon--right"></i>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="Current">Current</el-dropdown-item>
        <el-dropdown-item command="History">History</el-dropdown-item>
        <el-dropdown-item command="Bid on">Bid on</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>

    <!-- search area -->
    <el-input @keydown.enter.native="searchEvent" v-model="searchKey" class="search-bar" placeholder="search here">
      <el-button
        @click="searchEvent"
        slot="append"
        icon="el-icon-search"
      ></el-button>
    </el-input>

    <!-- card and pagination area -->
    <div class="right-box">
      <div class="job-list-box">
        <!-- <router-link to="/random"> -->
          <jobstatuscard
            v-for="(item, index) in dataShow"
            :key="index"
            v-bind:job="item"
          ></jobstatuscard>
        <!-- </router-link> -->
      </div>
    </div>

    <div class="pagination" v-if="pageshow">
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        layout="total, prev, pager, next, jumper"
        :total="total"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import jobstatuscard from "../components/JobStatusCard";
export default {
  components: {
    jobstatuscard
  },
  created() {
    // console.log("created execute");
    const jobList = [
      {
        id: "123",
        title: "Furniture assembly",
        suburb: "Surry Hills",
        date: "Sunday, 27th Sep",
        description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
        order_state: "0",
      },
      {
        id: "124",
        title: "Gutter cleaning",
        suburb: "Surry Hills",
        date: "Sunday, 27th Sep",
        description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
        order_state: "1",
      },
      {
        id: "125",
        title: "Rubbish removal",
        suburb: "Camperdown, NSW",
        date: "Sunday, 27th Sep",
        description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
        order_state: "1",
      },
      {
        id: "126",
        title: "General plumbing",
        suburb: "Camperdown, NSW",
        date: "Sunday, 27th Sep",
        description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
        order_state: "0",
      },
      {
        id: "127",
        title: "Backyard cleaning",
        suburb: "Camperdown, NSW",
        date: "Sunday, 27th Sep",
        description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
        order_state: "0",
      },
      {
        id: "128",
        title: "Backyard cleaning",
        suburb: "Camperdown, NSW",
        date: "Sunday, 27th Sep",
        description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
        order_state: "0",
      },
      {
        id: "129",
        title: "Backyard cleaning",
        suburb: "Camperdown, NSW",
        date: "Sunday, 27th Sep",
        description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
        order_state: "0",
      },
    ];

    this.total = jobList.length;

    for (let i = 0; i < jobList.length; i++) {
      if (jobList[i].order_state === "0") {
        jobList[i].order_state = "work in progress";
      }
      if (jobList[i].order_state === "1") {
        jobList[i].order_state = "completed";
      }
    }
    // count how many pages in total
    this.pageNumCurrent = Math.ceil(jobList.length / this.pageSize) || 1;

    for (let i = 0; i < this.pageNumCurrent; i++) {
      // 每一页都是一个数组 形如 [['第一页的数据'],['第二页的数据'],['第三页数据']]
      // 根据每页显示数量 将后台的数据分割到 每一页,假设pageSize为5， 则第一页是1-5条，即slice(0,5)，第二页是6-10条，即slice(5,10)...
      this.totalPage[i] = jobList.slice(
        this.pageSize * i,
        this.pageSize * (i + 1)
      );
    }

    this.dataShow = this.totalPage[this.currentPage];
  },
  data() {
    return {
      data: [],
      // page size固定
      pageSize: 5,
      // 页数固定
      pageNumCurrent: 0,
      pageNumHistory: 0,
      pageNumBidOn: 0,
      pageNumFiltered: 0,
      // 储存每一个分页的数据
      totalPage: [],
      totalPageHistory: [],
      totalPageBidOn: [],
      // 当前所在页数
      currentPage: 0,
      // 当前需要显示的数据
      dataShow: [],
      total: 0,
      currentCommand: "Current",
      pageshow: true,

      // searching keywords
      searchKey: "",
      filteredResult: [],
    };
  },
  methods: {
    // within a command, how to switch pages
    handleCurrentChange(val) {
      console.log(val);
      if (this.filteredResult.length > 0){
        this.dataShow = this.filteredResult[val - 1]
        return;
      }
      // if current command is current
      if (this.currentCommand === "Current") {
        this.dataShow = this.totalPage[val - 1];
        return;
      }
      if (this.currentCommand === "History") {
        this.dataShow = this.totalPageHistory[val - 1];
        return;
      }
      if (this.currentCommand === "Bid on") {
        this.dataShow = this.totalPageBidOn[val - 1];
        return;
      }
    },
    // actions when there is a change on command
    handleCommand(val) {
      console.log(val);

      // change to current
      if (val === "Current") {
        this.currentCommand = "Current";
        this.searchKey = '';
        this.filteredResult = [];
        this.initCurrentData();
      }
      // change to history
      if (val === "History") {
        this.currentCommand = "History";
        this.searchKey = '';
        this.filteredResult = [];
        this.initHistoryData();
      }
      // change to bid on
      if (val === "Bid on") {
        this.currentCommand = "Bid on";
        this.filteredResult = [];
        this.initBidOnData();
      }
    },
    // handle search events
    async searchEvent() {
      // if search key doesn't exist, do nothing
      if(this.searchKey === '') return;
      const result = [];
      // filter current job data
      for (let i = 0; i < this.totalPage.length; i++) {
        for (let j = 0; j < this.totalPage[i].length; j++) {
          let lowerCaseTitle = this.totalPage[i][j].title.toLowerCase();
          console.log(lowerCaseTitle);
          if (lowerCaseTitle.search(this.searchKey.toLowerCase()) !== -1) {
            result.push(this.totalPage[i][j]);
          }
        }
      }
      // filter history data
      const historyData = this.getHistoryData();
      for (let i = 0; i < historyData.length; i++) {
        let lowerCaseTitle = historyData[i].title.toLowerCase();
        if (lowerCaseTitle.search(this.searchKey.toLowerCase()) !== -1) {
          result.push(historyData[i]);
        }
      }
      // filter bid on data
      const bidOnData = await this.getBidOnData();
      
      for (let i = 0; i < bidOnData.length; i++) {
        let lowerCaseTitle = bidOnData[i].title.toLowerCase();
        if (lowerCaseTitle.search(this.searchKey.toLowerCase()) !== -1) {
          result.push(bidOnData[i]);
        }
      }


      this.total = result.length;
      console.log(result)
      // count how many pages in total
      this.pageNumFiltered = Math.ceil(result.length / this.pageSize) || 1;

      for (let i = 0; i < this.pageNumFiltered; i++) {
        this.filteredResult[i] = result.slice(
          this.pageSize * i,
          this.pageSize * (i + 1)
        );
      }
      console.log(this.filteredResult)
      this.currentPage = 0;
      this.pageshow = false;
      this.$nextTick(() => {
        this.pageshow = true;
      });
      this.dataShow = this.filteredResult[this.currentPage];
    },
    initCurrentData() {
      const jobList = [
        {
          id: "123",
          title: "Furniture assembly",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "0",
        },
        {
          id: "124",
          title: "Gutter cleaning",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "1",
        },
        {
          id: "125",
          title: "Rubbish removal",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "1",
        },
        {
          id: "126",
          title: "General plumbing",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "0",
        },
        {
          id: "127",
          title: "Backyard cleaning",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "0",
        },
        {
          id: "127",
          title: "Backyard cleaning",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "0",
        },
        {
          id: "127",
          title: "Backyard cleaning",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "0",
        },
      ];

      this.total = jobList.length;

      for (let i = 0; i < jobList.length; i++) {
        if (jobList[i].order_state === "0") {
          jobList[i].order_state = "work in progress";
        }
        if (jobList[i].order_state === "1") {
          jobList[i].order_state = "completed";
        }
      }
      // count how many pages in total
      this.pageNumCurrent = Math.ceil(jobList.length / this.pageSize) || 1;

      for (let i = 0; i < this.pageNumCurrent; i++) {
        // 每一页都是一个数组 形如 [['第一页的数据'],['第二页的数据'],['第三页数据']]
        // 根据每页显示数量 将后台的数据分割到 每一页,假设pageSize为5， 则第一页是1-5条，即slice(0,5)，第二页是6-10条，即slice(5,10)...
        this.totalPage[i] = jobList.slice(
          this.pageSize * i,
          this.pageSize * (i + 1)
        );
      }
      this.currentPage = 0;
      this.pageshow = false; //让分页隐藏
      this.$nextTick(() => {
        //重新渲染分页
        this.pageshow = true;
      });
      this.dataShow = this.totalPage[this.currentPage];
    },
    initHistoryData() {
      const historyList = [
        {
          id: "123",
          title: "Furniture assembly1",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "124",
          title: "Gutter cleaning2",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "125",
          title: "Rubbish removal3",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "123",
          title: "Furniture assembly4",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "124",
          title: "Gutter cleaning5",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "125",
          title: "Rubbish removal6",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "123",
          title: "Furniture assembly7",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "124",
          title: "Gutter cleaning8",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "125",
          title: "Rubbish removal9",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
      ];

      this.total = historyList.length;

      // for (let i = 0; i < historyList.length; i++) {
      //   historyList[i].status = "closed";
      // }

      // count how many pages in total
      this.pageNumHistory = Math.ceil(historyList.length / this.pageSize) || 1;

      for (let i = 0; i < this.pageNumHistory; i++) {
        // 每一页都是一个数组 形如 [['第一页的数据'],['第二页的数据'],['第三页数据']]
        // 根据每页显示数量 将后台的数据分割到 每一页,假设pageSize为5， 则第一页是1-5条，即slice(0,5)，第二页是6-10条，即slice(5,10)...
        this.totalPageHistory[i] = historyList.slice(
          this.pageSize * i,
          this.pageSize * (i + 1)
        );
      }

      console.log(this.totalPageHistory);

      this.currentPage = 0;
      this.pageshow = false; //让分页隐藏
      this.$nextTick(() => {
        //重新渲染分页
        this.pageshow = true;
      });
      console.log(this.currentPage);
      this.dataShow = this.totalPageHistory[this.currentPage];
      console.log(this.dataShow);
    },
    async initBidOnData() {
      const result = await this.$http.post("save_quote/", {
        token: window.sessionStorage.getItem('token'),
      });

      console.log("result:")
      console.log(result)

      const bidOnList= result.data.data

      for (let j = 0; j < bidOnList.length; j++) {
        // chatMessage[j].date
        if (bidOnList[j].date === null) {
          continue;
        } else {
          var split1 = bidOnList[j].date.split("T");
          var join = split1.join(" ");
          join = join.substr(0, join.length - 1);
          bidOnList[j].date = join;
        }
      }

      this.total = bidOnList.length;

      // for (let i = 0; i < historyList.length; i++) {
      //   historyList[i].status = "closed";
      // }

      // count how many pages in total
      this.pageNumBidOn = Math.ceil(bidOnList.length / this.pageSize) || 1;

      for (let i = 0; i < this.pageNumBidOn; i++) {
        // 每一页都是一个数组 形如 [['第一页的数据'],['第二页的数据'],['第三页数据']]
        // 根据每页显示数量 将后台的数据分割到 每一页,假设pageSize为5， 则第一页是1-5条，即slice(0,5)，第二页是6-10条，即slice(5,10)...
        this.totalPageBidOn[i] = bidOnList.slice(
          this.pageSize * i,
          this.pageSize * (i + 1)
        );
      }

      // console.log(this.totalPageBidOn);

      this.currentPage = 0;
      this.pageshow = false; //让分页隐藏
      this.$nextTick(() => {
        //重新渲染分页
        this.pageshow = true;
      });
      // console.log(this.currentPage);
      this.dataShow = this.totalPageBidOn[this.currentPage];
      // console.log(this.dataShow);
    },
    getHistoryData() {
      const historyList = [
        {
          id: "123",
          title: "Furniture assembly1",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "124",
          title: "Gutter cleaning2",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "125",
          title: "Rubbish removal3",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "123",
          title: "Furniture assembly4",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "124",
          title: "Gutter cleaning5",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "125",
          title: "Rubbish removal6",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "123",
          title: "Furniture assembly7",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "124",
          title: "Gutter cleaning8",
          suburb: "Surry Hills",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
        {
          id: "125",
          title: "Rubbish removal9",
          suburb: "Camperdown, NSW",
          date: "Sunday, 27th Sep",
          description: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, quam amet corporis atque nisi
              tenetur cupiditate voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          order_state: "closed",
        },
      ];

      return historyList
    },
    async getBidOnData() {
      
      const result = await this.$http.post("save_quote/", {
        token: window.sessionStorage.getItem('token'),
      });
      
      var bidOnList = [];
      bidOnList= result.data.data

      for (let j = 0; j < bidOnList.length; j++) {
        // chatMessage[j].date
        if (bidOnList[j].date === null) {
          continue;
        } else {
          var split1 = bidOnList[j].date.split("T");
          var join = split1.join(" ");
          join = join.substr(0, join.length - 1);
          bidOnList[j].date = join;
        }
      }
      console.log("getBidOnData")
      console.log(bidOnList)

      return bidOnList
    }
  },
};
</script>

<style scoped>
.status-page {
  
  /* border: 1px red solid; */
  width: 1000px;
  height: 700px;
  /* padding: 0px; */
  /* padding-left: 250px; */
  /* padding-top: 100px; */
  position: absolute;
  left: 400px;
  right: 0;
  top: 80px;
  bottom: 0;
  /* margin: auto; */
  
}

.el-dropdown {
  /* border: red 1px solid; */
  position: absolute;
  width: 345px;
  /* left: 460px; */
  /* top: 11%; */
  top: 15px;
  font-size: 22px;
}

.el-dropdown .el-dropdown-link {
  position: absolute;
  width: 100%;
  /* border: solid 1px green; */
  box-shadow: 0px 0px 2px;
  padding: 6px;
}

/* search bar area */
.search-bar {
  position: absolute;
  width: 360px;
  /* left: 460px; */
  /* top: 25%; */
  margin-top: 180px;
}

/* cards  box */
.right-box {
  position: absolute;
  /* border: brown 1px solid; */
  /* top: 9%; */
  /* left: 850px; */
  right: 0px;
  width: 650px;
  height: 650px;
  top: 0%;
}
.el-icon-arrow-down.el-icon--right {
  position: absolute;
  right: 2%;
}

.el-dropdown-menu {
  /* border: green 1px solid; */
  margin-top: 40px;
  width: 20%;
}

/* job list area */
.job-list-box {
  /* position: absolute; */

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

.pagination {
  position: absolute;
  margin-top: 20px;
  right: 20%;
  top: 580px;
  /* bottom: 3%; */
  /* border: 1px blue solid; */
}
</style>
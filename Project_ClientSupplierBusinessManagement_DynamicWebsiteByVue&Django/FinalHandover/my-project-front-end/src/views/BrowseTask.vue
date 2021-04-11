<template>
  <div class="browseTask">
    <loginheader></loginheader>
    <div class="generalCss">
      <div style="display: flex; justify-content: space-around">
        <div class="filterTitle">
          <span>Find The Task You Like</span>
        </div>
        <div class="search">
          <el-autocomplete
            v-model="value"
            clearable
            :fetch-suggestions="queryJob"
            @select="selectJob"
            placeholder="search Tasks"
            :style="{ width: '150%' }"
          ></el-autocomplete>
        </div>
      </div>
      <div
        style="
          margin: 0 auto;
          height: 600px;
          width: 1200px;
          display: flex;
          justify-content: space-around;
          padding-top: 30px;
        "
      >
        <div class="filter">
          <div
            class="filterFunction"
            style="padding-right: 100px; padding-left: 50px"
          >
            <el-menu
              default-active="1"
              class="el-menu-vertical-demo"
              @select="findFilter"
              @open="handleOpen"
              @close="handleClose"
            >
              <el-menu-item index="1" @click="clear()" style="text-align: left">
                <i class="el-icon-menu"></i>
                <span slot="title">All tasks</span>
              </el-menu-item>
              <el-submenu index="2" style="text-align: left">
                <template slot="title">
                  <i class="el-icon-menu"></i>
                  <span>Filtering</span>
                </template>
                <el-submenu index="2-1">
                  <template slot="title">By Category</template>
                  <el-menu-item
                    v-for="Category in Categorys"
                    :key="Category.value"
                    v-bind:job="Category.label"
                    :index="Category.value"
                    >{{ Category.label }}</el-menu-item
                  >
                </el-submenu>
                <el-submenu index="2-2">
                  <template slot="title">By Budget</template>
                  <el-menu-item
                    v-for="Budget in Budgets"
                    :key="Budget.value"
                    v-bind:job="Budget.label"
                    :index="Budget.value"
                    >{{ Budget.label }}</el-menu-item
                  >
                </el-submenu>
              </el-submenu>
            </el-menu>
          </div>
          <!-- <div class="sortFunction">
            <el-menu :default-active="activeIndex2" class="el-menu-demo" mode="horizontal" @select="handleSelect" background-color="#FFFFFF" text-color="#409EFF" active-text-color="#ffd04b">
              <el-submenu index="1">
                <template slot="title">Sorted by</template>
                <el-menu-item index="1-1" @click="findData">Price from High to Low</el-menu-item>
                <el-menu-item index="1-2">Price from Low to High</el-menu-item>
              </el-submenu>
              <el-menu-item index="2">All Tasks</el-menu-item>
            </el-menu>
          </div> -->
        </div>
        <div class="body">
          <div class="jobList">
            <router-link
              :to="{ path: '/jobdetail', query: { jobList: jobListData, selectedJob: this.clickedJob} }"
            >
              <jobcard
                v-for="item in jobListData2"
                :key="item.id"
                v-bind:job="item"
                @click.native="getClickedJob(item)"
              ></jobcard>
            </router-link>
          </div>
          <div class="pageCount" style="padding-right:100px;margin-top: 20px">
            <el-pagination
              @size-change="handleSizeChange"
              @current-change="current_change"
              :current-page.sync="currentPage"
              layout="total, prev, pager, next, jumper"
              :page-size="pageSize"
              :pager-count="pagerCount"
              :total="total"
              style="text-align: right"
            ></el-pagination>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import jobcard from "../components/TaskCard";
import loginheader from "../components/LoggedInHeader";
export default {
  data() {
    return {
      Categorys: [
        {
          label: "Cleaning",
          value: "cleaning",
        },
        {
          label: "Fixing",
          value: "fixing",
        },
        {
          label: "Unlocking",
          value: "unlocking",
        },
        {
          label: "Weeding",
          value: "weeding",
        },
      ],
      Budgets: [
        {
          label: "$0-$100",
          value: "0-100",
        },
        {
          label: "$100-$200",
          value: "100-200",
        },
        {
          label: "$200+",
          value: "200-999999999999",
        },
      ],
      options: [],
      value: [],
      list: [],
      jobListData: [],
      jobListData2: [],
      currentPage: 1,
      pageSize: 4,
      total: 0,
      jobList: [],
      activeIndex2: "2",
      clickedJob:{}
    };
  },
  
  methods: {
    queryJob(query, callback) {
      let list = [];
      this.jobList.forEach((e) => {
        if (e.title.toLowerCase().indexOf(query) > -1) {
          let noRepeat = false;
          list.forEach((g) => {
            if (g.value === e.title) {
              noRepeat = true;
            }
          });
          if (noRepeat === false) {
            list.push({ value: e.title });
          }
        }
      });
      callback(list);
    },
    // handleSelect (key, keyPath) {
    //   console.log(key, keyPath)
    // },
    current_change: function (currentPage) {
      this.currentPage = currentPage;
    },
    findFilter(index, indexPath) {
      let data = JSON.parse(JSON.stringify(this.jobList));
      let arr = index.split("-");
      let list = [];
      data.forEach((e) => {
        if (e.category === index) {
          list.push(e);
          this.jobListData = [];
        }
        if (
          parseFloat(arr[0]) <= parseFloat(e.budget) &&
          parseFloat(e.budget) < parseFloat(arr[1])
        ) {
          list.push(e);
          this.jobListData = [];
        }
      });
      this.jobListData = JSON.parse(JSON.stringify(list));
      this.total = this.jobListData.length;
    },
    clear: function () {
      let data = JSON.parse(JSON.stringify(this.jobList));
      let data2 = [];
      data2 = data;
      this.jobListData = JSON.parse(JSON.stringify(data2));
      this.total = this.jobListData.length;
    },
    getClickedJob(val) {
      this.clickedJob = val
    }
  },
  created: async function () {
    const result = await this.$http.get("get_all_job/");
    this.jobList = result.data.data;
    console.log(this.jobList)

    for (let j = 0; j < this.jobList.length; j++) {
        // jobList[j].date
        if (this.jobList[j].date === null) {
          continue;
        } else {
          var split1 = this.jobList[j].date.split("T");
          var join = split1.join(" ");
          join = join.substr(0, join.length - 1);
          this.jobList[j].date = join;
        }
      }

    console.log(this.jobList)
    this.jobListData = JSON.parse(JSON.stringify(this.jobList));
    this.total = this.jobListData.length;
    let data = JSON.parse(JSON.stringify(this.jobListData));
    this.jobListData2 = data.slice(
      (this.currentPage - 1) * this.pageSize,
      this.currentPage * this.pageSize
    );
  },
  watch: {
    // eslint-disable-next-line no-irregular-whitespace
    currentPage: function () {
      // eslint-disable-next-line no-unused-vars
      let data = JSON.parse(JSON.stringify(this.jobListData));
      this.jobListData2 = data.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      );
    },
    value: function () {
      let data = JSON.parse(JSON.stringify(this.jobList));
      let data2 = [];
      if (this.value === "") {
        data2 = data;
      } else {
        data.forEach((e) => {
          if (e.title === this.value) {
            this.jobListData = [];
            data2.push(e);
          }
        });
      }
      this.jobListData = JSON.parse(JSON.stringify(data2));
      this.total = this.jobListData.length;
    },
    jobListData: function () {
      let data = JSON.parse(JSON.stringify(this.jobListData));
      this.jobListData2 = data.slice(
        (this.currentPage - 1) * this.pageSize,
        this.currentPage * this.pageSize
      );
    },
    // let data = JSON.parse(JSON.stringify(this.jobList))
    // function bubbleSort(data) {
    // var i = data.length, j;
    // var tempExchangVal;
    // while (i > 0) {
    //     for (j = 0; j < i - 1; j++) {
    //         if (arr[j] > arr[j + 1]) {
    //             tempExchangVal = arr[j];
    //             arr[j] = arr[j + 1];
    //             arr[j + 1] = tempExchangVal;
    //         }
    //     }
    //     i--;
    // }
    // return arr;
    // }
    // var arr = [3, 2, 4, 9, 1, 5, 7, 6, 8];
    // var arrSorted = bubbleSort(arr);
    // console.log(arrSorted);
    // alert(arrSorted);
    // this.jobListData = JSON.parse(JSON.stringify(this.data))
  },
  components: {
    jobcard,
    loginheader,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
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
.search {
  margin-top: 100px;
  margin-right: 20px;
}
.filter {
  background-color: #ffffff;
  border-color: #ffffff !important;
  margin-top: 10px;
  width: 30%;
}
.filterTitle {
  margin-top: 100px;
  font-size: 30px;
}
.sortFunction {
  margin-top: 160px;
}
.body {
  background-color: #ffffff;
  margin-top: 10px;
  width: 800px;
}
</style>

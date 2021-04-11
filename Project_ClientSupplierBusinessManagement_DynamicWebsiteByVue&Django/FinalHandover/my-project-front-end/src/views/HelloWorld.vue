<template>
  <div class="hello">
    <index></index>
    <div class="content">
      <div class="left">
        <ul class="left-list">
          <li
            class="cardBox"
            v-for="(item, index) of Unfiltereds.Unfiltereds"
            :key="index"
          >
            <div>
              <i class="el-icon-user-solid" style="font-size: 30px"></i>
              <span>{{ item.user }}</span>
            </div>
            <div>
              <span
                ><i class="el-icon-s-order" style="font-size: 20px"></i
                >{{ item.title }}</span
              >
              <span>Expect Budget:{{ item.money }} AUD</span>
              <span>Suburb : {{ item.suburb }}</span>
              <span>Due date : {{ item.dateTime }}</span>
            </div>
          </li>
        </ul>
      </div>
      <div class="right">
        <h3>Repair my floors</h3>
        <h4 style="margin-top: 45px">Description</h4>
        <div class="rightBox">
          <div class="BoxLine">
            <i class="el-icon-s-promotion a" style="font-size: 16px">
              <p>Rosebery</p>
            </i>
            <i class="el-icon-date a" style="font-size: 16px">
              <p>30/09/2020</p>
            </i>
          </div>
          <div class="BoxLine">
            <i
              class="el-icon-user-solid a"
              style="font-size: 16px; display: block"
            >
              <div style="display: inline-block">
                <p>Poster</p>
                <p style="font-size: 12px">UserA</p>
              </div>
            </i>
            <i class="el-icon-s-marketing a" style="font-size: 16px">
              <p>Make a offer</p>
            </i>
          </div>
        </div>
        <div class="cost">
          <span>Cost:60$</span>
          <span>My floor is damaged</span>
          <span>I need somone to fix it</span>
        </div>
      </div>
    </div>
    <div class="Footer" style="display: flex; justify-content: space-around">
      <div>
        <span style="color: white; font-size: 20px">Tools</span>
        <ul>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
        </ul>
      </div>
      <div>
        <span style="color: white; font-size: 20px">Company</span>
        <ul>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
        </ul>
      </div>
      <div>
        <span style="color: white; font-size: 20px">Popular Categories</span>
        <ul>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
        </ul>
      </div>
      <div>
        <span style="color: white; font-size: 20px">FAQ</span>
        <ul>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
          <ol style="color: white; font-size: 10px">
            ************************************
          </ol>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import index from "../components/Index";
export default {
  name: "HelloWorld",
  components: {
    index,
  },
  data() {
    return {
      // 唯一字段
      id: null,
      // 模拟参数
      Unfiltereds: [],
      jobList: [
        {
          id: "123",
          jobTitle: "Furniture assembly",
          budget: "1000",
          location: "Surry Hills",
          category: "engineering",
          summary: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, voluptatum voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          Unfiltereds: [
            {
              user: "UserA",
              title: "模拟数据A",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },
            {
              user: "UserA",
              title: "模拟数据A",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },
            {
              user: "UserA",
              title: "模拟数据A",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },
            {
              user: "UserA",
              title: "模拟数据A",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },
            {
              user: "UserA",
              title: "模拟数据A",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },
            {
              user: "UserA",
              title: "模拟数据A",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },

            {
              user: "UserA",
              title: "模拟数据A",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },
            {
              user: "UserA",
              title: "模拟数据A",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },
          ],
        },
        {
          id: "124",
          jobTitle: "Furniture assembly",
          budget: "1000",
          location: "Surry Hills",
          category: "engineering",
          summary: `Lorem ipsum dolor sit amet consectetur adipisicing elit. Eum unde
              libero reiciendis similique magni, voluptatum voluptate voluptatum nemo suscipit ea. Tempora
              illo molestias quod.`,
          Unfiltereds: [
            {
              user: "UserC",
              title: "模拟数据124",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },
            {
              user: "User124",
              title: "模拟数据124",
              money: "$60",
              suburb: "chengdu",
              dateTime: "2020/05/05",
            },
          ],
        },
      ],
    };
  },
  mounted: function () {
    // url http://localhost:8080/#/?id=123
    var id = this.$route.query.id == undefined ? "123" : this.$route.query.id;
    this.UnfilteredList(id);
  },
  methods: {
    UnfilteredList(id) {
      var res = this.jobList.find((v) => {
        return v.id == id;
      });
      this.Unfiltereds = res;
    },
  },
};
</script>

<style scoped>
.hello {
  font: 400 13.3333px Arial;
  height: 100%;
}
.cardBox {
  cursor: pointer;
}
.cardBox:hover {
  box-shadow: 10px 10px 8px rgba(0, 0, 0, 0.3);
}
.content {
  display: flex;
  padding-top: 66px;
  height: calc(100% - 286px);
}
.left {
  color: #838485;
  border-right: 1px solid rgb(221, 221, 221);
  padding: 0 0 0 30px;
  height: 100%;
  overflow: hidden;
}
.left-flex {
  display: flex;
  align-items: center;
  justify-content: center;
  margin: 10px 0;
}
.left-list {
  margin-top: 20px;
  height: 100%;
  overflow: auto;
}
.left-list li {
  display: flex;
  align-items: center;
  padding: 15px;
  border: 1px solid rgba(128, 128, 128, 0.308);
  margin-bottom: 20px;
  font-size: 12px;
}
.left-list li > div {
  display: flex;
  flex-direction: column;
  text-align: left;
}
.left-list li > div:nth-child(1) {
  margin-right: 20px;
}
.Footer {
  height: 200px;
  background-color: rgb(36, 34, 34);
  margin-bottom: 0px;
  margin-top: 20px;
  display: flex;
  align-items: center;
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
  margin: 40px 0;
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
</style>

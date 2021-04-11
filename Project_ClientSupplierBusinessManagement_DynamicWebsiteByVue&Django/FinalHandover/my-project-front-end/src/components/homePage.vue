<template>
  <div class="Home">
    <div class="Body1">
      <img :src="background" style="margin-top:58px;width:100%;height:500px;">
    </div>
    <div class="Body2" style="margin:50px; autp;display:flex;justify-content:space-around">
      <div>
        <img :src="Body2_1" style="width:100px;height:100px;">
        <div style="margin-top:10px;font-weight:700;">Choose your tasks</div>
      </div>
      <div>
        <img :src="Body2_2" style="width:100px;height:100px;">
        <div style="margin-top:10px;font-weight:700;">Complete the tasks</div>
      </div>
      <div>
        <img :src="Body2_3" style="width:100px;height:100px;">
        <div style="margin-top:10px;font-weight:700;">Get the payment</div>
      </div>
    </div>
    <div style="margin-top:80px;text-align:center;">
      <router-link to="/signup">
              <el-button type="primary">Become a Supplier</el-button>
      </router-link>

    </div>
    <div class="steps">
      <div class="browsetasks" style="margin-top:80px;margin-left:200px;margin-right:200px;display:flex;justify-content:space-around">
        <img :src="browsetasks" style="width:40%;height:240px;">
        <div style="font-size:26px">
          Browse Tasks<br>
          <br>
          ****************************<br>
          ****************************<br>
          ****************************<br>
          ****************************<br>
        </div>
      </div>
      <div class="working" style="margin-top:80px;margin-left:200px;margin-right:200px;display:flex;justify-content:space-around">
        <img :src="working" style="width:40%;height:240px;">
        <div style="font-size:26px">
          Do The Task<br>
          <br>
          ****************************<br>
          ****************************<br>
          ****************************<br>
          ****************************<br>
        </div>
      </div>
      <div class="paid" style="margin-top:80px;margin-left:200px;margin-right:200px;display:flex;justify-content:space-around">
        <img :src="paid" style="width:40%;height:240px;">
        <div style="font-size:26px">
          Get The Paid<br>
          <br>
          ****************************<br>
          ****************************<br>
          ****************************<br>
          ****************************<br>
        </div>
      </div>

      <div>
        <div class="footer"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      background: require('@/assets/picture/background.jpg'),
      Body2_1: require('@/assets/picture/Body2_1.png'),
      Body2_2: require('@/assets/picture/Body2_2.png'),
      Body2_3: require('@/assets/picture/Body2_3.png'),
      browsetasks: require('@/assets/picture/browsetasks.jpg'),
      working: require('@/assets/picture/working.jpg'),
      paid: require('@/assets/picture/paid.jpg')
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

.footer {
    width: 100%;
    height: 100px;
    background-color: grey;
    margin-top: 30px;
  }
</style>

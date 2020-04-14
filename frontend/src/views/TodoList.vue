<template>
  <div class="scrolling-wrapper">
      <div>
          <b-list-group horizontal>
              <b-list-group-item v-for="item in getAllTaskList">
                  <TaskList :taskList="item"></TaskList>
              </b-list-group-item>
              <b-list-group-item>
                  <div>
                      <b-card class="btn" align="center" style="width: 25vw; border-style: dotted" @click="addTaskList()">
                          <i class="im im-plus" style="font-size: small"></i>
                          Add New TaskList
                    </b-card>
                  </div>
              </b-list-group-item>
          </b-list-group>
      </div>
  </div>
</template>
<script>
import TaskList from "../components/TaskList"
  export default {
    name: 'Todolist',
    components: {
        TaskList
    },
    watch:{
      getCurrentChannel: function () {
        this.$http.get('/api/tasklist/get/'+this.$store.state.currentChannel.id)
          .then(res => {
            this.taskList = res.data
            console.log(this.taskList)
          })
      }
    },
    computed: {
        getAllTaskList: function(){
            for(let i=0;i<this.taskList.length;i++){
              this.taskList[i].position = i;
            }
            return this.taskList
        },
      getCurrentChannel: function () {
        return this.$store.state.currentChannel
      }
    },
    created(){
        this.$http.get('/api/tasklist/get/'+this.$store.state.currentChannel.id)
        .then(res => {
            this.taskList = res.data
            console.log(this.taskList)
        })
    },
    data() {
      return {
          taskList:[],
          taskListItem:{
              id: '',
              name : '',
              channel_id : this.$store.state.currentChannel.id,
              position: '',
              tasks: []
          }
      }
    },
    methods: {
        addTaskList: function () {
            this.taskList.push(JSON.parse(JSON.stringify(this.taskListItem)))
        }
    }
  }
</script>
<style scoped>
.btn:hover{
    background-color: #BDBDBD;
}
</style>

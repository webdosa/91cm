<template>
  <div class="scrolling-wrapper">
      <div>
          <b-button @click="init">Init</b-button>
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
    computed: {
        getAllTaskList: function(){
            return this.taskList
        }
    },
    data() {
      return {
          taskList:[],
          taskListItem:{
              name : '',
              channel_id : '',
              register_date: '',
              edit_date: '',
              position: '',
          }
      }
    },
    methods: {
        addTaskList: function () {
            this.taskList.push(this.taskListItem)
        },
        init: function(){
             for(let i=0;i<10;i++){
                this.taskListItem.name = 'test'+i
                this.taskList.push(JSON.parse(JSON.stringify(this.taskListItem)))
             }
        }
        
    }
  }
</script>
<style scoped>
.btn:hover{
    background-color: #BDBDBD;
}
</style>

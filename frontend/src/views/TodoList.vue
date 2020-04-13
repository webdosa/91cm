<template>
  <div class="scrolling-wrapper">
      <div>
          <b-list-group horizontal>
              
              <b-list-group-item v-for="item in list" :key="item">
                  <TaskList :list="item"></TaskList>
              </b-list-group-item>
              <b-list-group-item>
                  <div>
                      <b-card class="btn" align="center" style="width: 25vw; border-style: dotted" @click="addTaskList">
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
    data() {
      return {
          list : [{
              title:'title 1',
              taskList:[{
                 content: 'content one',
                  register_date: '2018-01-02',
                  member_email: '이주혁' 
              },{content: '겁나길게 행ㅈadsaaasdadasddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd',
                  register_date: '2018-01-03',
                  member_email: '원주민'}]
          },{
              title:'title 2',
              taskList:[{
                 content: 'content one',
                  register_date: '2018-01-02',
                  member_email: '이주혁' 
              },{content: 'content two',
                  register_date: '2018-01-03',
                  member_email: '원주민'}]
          }]
      }
    },
    methods: {
        addTaskList: function () {
            alert('test')
        }
    }
  }
</script>
<style scoped>
.btn:hover{
    background-color: #BDBDBD;
}
</style>

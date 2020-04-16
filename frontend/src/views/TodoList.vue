<template>
  <div>
    <div class="scrolling-wrapper">
      <b-list-group horizontal>
        <draggable :list="getAllTaskList" class="row flex-nowrap" v-bind="dragOptions">
            <b-list-group-item v-for="item in getAllTaskList" :key="item">
              <TaskList :taskList="item"></TaskList>
            </b-list-group-item>
        </draggable>
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
  import draggable from 'vuedraggable'

  export default {
    name: 'Todolist',
    components: {
      TaskList,
      draggable
    },
    watch: {
      getCurrentChannel: function () {
        this.$http.get('/api/tasklist/get/' + this.$store.state.currentChannel.id)
          .then(res => {
            this.taskList = res.data
            console.log(this.taskList)
          })
      }
    },
    computed: {
      getAllTaskList: function () {
        for (let i = 0; i < this.taskList.length; i++) {
          this.taskList[i].position = i;
        }
        return this.taskList
      },
      getCurrentChannel: function () {
        return this.$store.state.currentChannel
      },
      dragOptions(){
        return{
          animated: "200",
          group: "todo"
        }
      }
    },
    created() {
      this.$http.get('/api/tasklist/get/' + this.$store.state.currentChannel.id)
        .then(res => {
          this.taskList = res.data
          console.log(this.taskList)
        })
      this.$eventBus.$on('deleteTaskList', data => {
        this.taskList.splice(this.taskList.indexOf(data), 1)
      })
    },
    data() {
      return {
        taskList: [],
        taskListItem: {
          id: '',
          name: '',
          channel_id: this.$store.state.currentChannel.id,
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
  .btn:hover {
    background-color: #BDBDBD;
  }

  .task-board-enter-active, .task-board-leave-active {
    transition: all 1s;
  }

  .task-board-enter, .task-board-leave-to {
    opacity: 0;
    transform: translateX(50px);
  }

  .task-board-move {
    transition: transform 1s;
  }
  .scrolling-wrapper{
    overflow-x: scroll;
    -webkit-overflow-scrolling: touch;
  }
</style>

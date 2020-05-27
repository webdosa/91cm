<template>
  <div class="mainwrapper" style="height: calc(100vh - 150px);">
    <div class="scrolling-wrapper h-inherit">
      <b-list-group horizontal style="overflow-x:auto; height: inherit;" id="wheelReverse">
        <draggable :list="getTaskBoard" style="display:flex; flex-wrap:nowrap" v-bind="dragOptions" @change="tasklistEventHandler">
          <b-list-group-item v-for="item in getTaskBoard" :key="item" style="min-width: 355px; max-width: 355px;">
            <TaskList :taskList="item"></TaskList>
          </b-list-group-item>
        </draggable>
        <b-list-group-item style="min-width: 355px; max-width: 355px;">
          <div>
            <b-card class="btn" align="center" style="border-style: dotted" @click="addTaskList()">
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
  import TaskList from "../../components/TaskList"
  import draggable from 'vuedraggable'
  import Loading from "../main/Loading";
  import {mapGetters} from "vuex";

  export default {
    name: 'Todolist',
    components: {
      Loading,
      TaskList,
      draggable
    },
    watch: {
      getCurrentChannel: function () {
        this.$http.get('/api/tasklist/get/' + this.$store.state.currentChannel.id)
          .then(res => {
            this.taskList = res.data
            this.$store.commit('setTaskBoard', this.taskList)
          })
      },
      getTaskBoard: function () {
        this.taskList = this.$store.state.taskBoard
      }
    },
    computed: {
      ...mapGetters({
        getTaskBoard: 'getTaskBoard'
      }),
      getAllTaskList: function () {
        for (let i = 0; i < this.taskList.length; i++) {
          this.taskList[i].position = i;
        }
        return this.taskList
      },
      getCurrentChannel: function () {
        return this.$store.state.currentChannel
      },
      dragOptions() {
        return {
          animation: "200",
          group: "todo"
        }
      }
    },
    activated() {
      this.taskSubscribe=this.$store.state.stompClient.subscribe('/sub/todo/' + this.$store.state.currentChannel.id, (res) => {
        if (res.headers.typename == 'taskUpdate') {
          this.$store.dispatch('updateTaskBoard')
        }
      })
    },
    deactivated() {
      this.taskSubscribe.unsubscribe()
      this.taskSubscribe = null
    },
    created() {
      this.$http.get('/api/tasklist/get/' + this.$store.state.currentChannel.id)
        .then(res => {
          this.taskList = res.data
          this.$store.commit('setTaskBoard',this.taskList)
        })
      this.$eventBus.$on('deleteTaskList', data => {
        this.taskList.splice(this.taskList.indexOf(data), 1)
      })
    },
    data() {
      return {
        taskSubscribe: null,
        connetionCheck: false,
        taskList: [],
        taskListItem: {
          id: '',
          name: '',
          channel_id: this.$store.state.currentChannel.id,
          position: '',
          tasks: []
        },
      }
    },
    methods: {
      addTaskList: function () {
        this.taskList.push(JSON.parse(JSON.stringify(this.taskListItem)))
      },
      tasklistEventHandler: function ({added, moved, removed}) {
        if (moved) {
          this.$http.post('/api/tasklist/update/position', {
            tasklistOldIndex: moved.oldIndex,
            tasklistNewIndex: moved.newIndex,
            tasklistId: moved.element.id
          }).then(res => {
            this.$store.state.stompClient.send('/sub/todo/' + this.$store.state.currentChannel.id, {}, {typename: 'taskUpdate'})
          }).catch(error => {
            console.error(error)
          })
        }
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

  .scrolling-wrapper {
    overflow-x: scroll;
    -webkit-overflow-scrolling: touch;
  }
</style>

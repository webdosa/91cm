<template>
  <div style="padding: 0px; height: 100%;display: flex; flex-direction: column;" class="col-12 rounded-lg bg-light">
    <div v-if="taskList.name != ''" style="padding: 10px;">
      <draggable :list="getTasks" :group="'tasks'" @change="taskEventHandler" draggable=".item">
        <div class="rounded-lg bg-secondary" style="padding:10px; display:flex;">
          <div v-if="!edit" slot="header" style="width: 100%;display: flex;align-items: center;">
            <span style="color: white; font-size:20px;">{{taskList.name}}</span>
            <div style="flex-grow: 1;display: flex;align-items: center;justify-content: flex-end;">
            <i class="im im-pencil" @click="editToggle" style="cursor:pointer;"></i>
            <i class="im im-trash-can" @click="msgBox" style="cursor:pointer;"></i>
            <i class="im im-plus" style="padding:0px; color: white; cursor:pointer;" @click="createFormToggle"></i>
            </div>
          </div>
          <b-form-input v-else
                        @keydown.enter.exact="editTaskListName"
                        @keydown.esc="editToggle"
                        v-model="taskList.name"
                        autofocus></b-form-input>
        </div>
      </draggable>

    </div>
    <div v-else>
      <b-form-input placeholder="내용을 입력해주세요" v-model="taskListName" autofocus
                    @keydown.enter.exact="setTaskListName"></b-form-input>
    </div>

    <div style="height: 100%;overflow-y: auto;">
    <b-list-group style="width: 100%; padding: 10px;"> <!-- 임시로 정해주 높이 값 정확한 반응형 높이가 아님 -->
      <b-list-group-item v-if="create" style="padding: 10px;">
        <TaskEdit @createFormToggle="createFormToggle" :color="color" :date="date"
                  :tasks="getTasks" :task-list-id="taskList.id"></TaskEdit>
      </b-list-group-item>
      <draggable :list="getTasks" :group="'tasks'" @change="taskEventHandler" draggable=".item">
        <transition-group name="task-list">
          <b-list-group-item v-for="(task,index) in getTasks" :key="task" style="margin-bottom: 10px; padding: 10px 10px;" class="item">
            <div v-if="index != editSelector">
              <div style="display: flex; align-items: center;">
              <span class="small text-muted" v-if="task.start_date">{{getDateFormat(task.start_date)}} ~ {{getDateFormat(task.end_date)}}</span>
              <div class="td-dd-wrapper" style="flex-grow: 1;display: flex;justify-content: flex-end;">
                <b-dropdown no-caret variant="nonoutline" toggle-class="text-decoration-none"
                            style="padding: 0px;">
                  <template v-slot:button-content>
                    <i class="im im-menu-dot-h"></i>
                  </template>
                  <b-dropdown-item @click="editFormToggle(index)">Edit</b-dropdown-item>
                  <b-dropdown-item v-if="task.state" @click="editTask(task,false)">Done</b-dropdown-item>
                  <b-dropdown-item v-else @click="editTask(task,true)">Revoke</b-dropdown-item>
                  <b-dropdown-item @click="deleteTask(task,index)" variant="danger">Delete</b-dropdown-item>
                </b-dropdown>
              </div>
              </div>
              <p id="content" style="margin:0">{{task.content}}</p>
              <footer style="display: flex;justify-content: flex-end;">
                <small>created by {{channelUsers.find(user => user.email ==
                  task.member_email).name}}</small>
              </footer>
            </div>
            <div v-else>
              <TaskEdit @editFormToggle="editFormToggle" :color="color" :date="date"
                        :tasks="getTasks" :task-list-id="taskList.id" :index="index"></TaskEdit>
            </div>
          </b-list-group-item>
        </transition-group>
      </draggable>
    </b-list-group>
    </div>
  </div>
</template>
<script>
  import draggable from 'vuedraggable'
  import DatePicker from 'vue2-datepicker'
  import 'vue2-datepicker/index.css'
  import VSwatches from 'vue-swatches'
  import 'vue-swatches/dist/vue-swatches.css'
  import {mapGetters} from "vuex";
  import TaskEdit from "../views/todolist/TaskEdit";

  export default {
    name: 'TaskList',
    props: ["taskList"],
    computed: {
      ...mapGetters({
        channelUsers: 'getChannelUsers',
        currentChannel: 'getCurrentChannel'
      }),
      getTasks: function () {
        return this.taskList.tasks
      },
    },
    watch: {
      getTasks: function (newVal, oldVal) {
        this.taskList.tasks.forEach(task => {
          task.position = this.taskList.tasks.indexOf(task)
        })
      }
    },
    components: {
      TaskEdit,
      draggable,
      DatePicker,
      VSwatches
    },
    data() {
      return {
        color: '#A463BF',
        date: [],
        updateTask: {
          taskOldIndex: null,
          taskNewIndex: null,
          tasklistOldId: null,
          tasklistNewId: null,
          taskId: null
        },
        editSelector: -1,
        taskListName: '',
        create: false,
        edit: false,
      }
    },
    methods: {
      taskEventHandler: function ({added, moved, removed}) {
        let updateTaskItem = {
          taskOldIndex: null,
          taskNewIndex: null,
          tasklistOldId: null,
          tasklistNewId: null,
          tasklistId: null,
          taskId: null
        }
        if (added) {
          added.element.tasklist_id = this.taskList.id
        }
        if (moved) {
          updateTaskItem.taskNewIndex = moved.newIndex
          updateTaskItem.taskOldIndex = moved.oldIndex
          updateTaskItem.tasklistId = this.taskList.id
          updateTaskItem.taskId = moved.element.id
          this.$http.post('/api/task/update/position', updateTaskItem)
            .then(res => {
              this.$store.state.stompClient.send('/sub/todo/' + this.currentChannel.id, {}, {typename: 'taskUpdate'})
            }).catch(error => {
            console.error(error)
          })
        }
        if (removed) {
          updateTaskItem.taskOldIndex = removed.oldIndex
          updateTaskItem.taskNewIndex = removed.element.position
          updateTaskItem.tasklistOldId = this.taskList.id
          updateTaskItem.tasklistNewId = removed.element.tasklist_id
          updateTaskItem.taskId = removed.element.id
          this.$http.post('/api/task/update/position', updateTaskItem)
            .then(res => {
              this.$store.state.stompClient.send('/sub/todo/' + this.currentChannel.id, {}, {typename: 'taskUpdate'})
            }).catch(error => {
            console.error(error)
          })
        }
      },
      checkTask: function (evt) {
        evt.draggedContext.element.tasklist_id = this.taskList.id
        evt.draggedContext.element.position = evt.draggedContext.index
      },
      deleteTaskList: function () {
        this.$http.post('/api/tasklist/delete', {
          id: this.taskList.id,
          position: this.taskList.position
        })
          .then(res => {
            this.$eventBus.$emit('deleteTaskList', this.taskList)
            this.$store.state.stompClient.send('/sub/todo/' + this.currentChannel.id, {}, {typename: 'taskUpdate'})
          })
          .catch(error => {

          })
      },
      editToggle: function () {
        this.edit = !this.edit
      },
      editTaskListName: function () {
        this.$http.post('/api/tasklist/update/name', {
          id: this.taskList.id,
          name: this.taskList.name
        }).then(res => {
          this.$store.state.stompClient.send('/sub/todo/' + this.currentChannel.id, {}, {typename: 'taskUpdate'})
          this.editToggle()
        }).catch(error => {
          console.error(error)
        })
      },
      editTask: function (task,state) {
        task.state = state
        this.$http.post('/api/task/update/content', task)
          .then(res => {
            this.$store.state.stompClient.send('/sub/todo/'+this.currentChannel.id,{},{typename: 'taskUpdate'})
          }).catch(error => {
          console.error(error)
        })
      },
      deleteTask: function (task, index) {
        // 현저 유저와 작성자가 같은지 비교해서 삭제할 수 있도록 변경 필요
        this.$http.post('/api/task/delete', task)
          .then(res => {
            this.$store.state.stompClient.send('/sub/todo/' + this.currentChannel.id, {}, {typename: 'taskUpdate'})
            this.taskList.tasks.splice(index, 1)
          }).catch(error => {
          console.error(error)
        })
      },
      createFormToggle: function () {
        this.create = !this.create
        this.taskContent = ''
      },
      editFormToggle: function (index) {
        this.editSelector = index
      },
      setTaskListName: function () {
        this.taskList.channel_id = this.currentChannel.id
        this.taskList.name = this.taskListName
        this.$http.post('/api/tasklist/insert', JSON.stringify(this.taskList), {
          headers: {
            'Content-Type': 'application/json'
          }
        })
          .then(res => {
            this.taskList.id = res.data.id
            this.$store.state.stompClient.send('/sub/todo/' + this.currentChannel.id, {}, {typename: 'taskUpdate'})
          })
          .catch(error => {
            console.error(error)
          })
      },
      getDateFormat: function(dateData){
        const date = new Date(dateData)
        let dateToString = ''
        dateToString = date.getFullYear() + '-' + (date.getMonth()+1) + '-' + date.getDate()
        return dateToString
      },
      msgBox: async function () {
        await this.$bvModal.msgBoxConfirm("정말로 이 TaskList를 삭제하시겠습니끼?", {
          title: '확인',
          okTitle: '확인',
          okVariant: 'danger',
          buttonSize: 'sm',
          cancelTitle: '취소'
        })
          .then(value => {
            if (value){
              this.deleteTaskList()
            }
          })
      }

    }
  }
</script>
<style scoped>
  .task-list-enter-active, .task-list-leave-active {
    transition: all 1s;
  }

  .task-list-enter, .task-list-leave-to {
    opacity: 0;
    transform: translateX(50px);
  }

  .task-list-move {
    transition: transform 1s;
  }

  i {
    margin-left: 10px;
  }

  .im-pencil {
    opacity: 0.3;
  }

  .im-trash-can {
    opacity: 0.3;
  }

  .im-pencil:hover {
    opacity: 1;
    color: white;
  }

  .im-trash-can:hover {
    opacity: 1;
    color: red;
  }

  P {
    font-size: 20px;
  }

  #content {
    overflow: hidden;
    word-wrap: break-word;
  }


</style>

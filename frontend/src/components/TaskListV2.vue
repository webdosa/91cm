<template>
       <div class="card task-board">
            <div class="card-header">
                <h3>Todos</h3>
                <div class="card-header-right">
                    <ul class="list-unstyled card-option">
                        <li><i class="ik ik-chevron-left action-toggle"></i></li>
                        <li><i class="ik ik-rotate-cw reload-card" data-loading-effect="pulse"></i></li>
                        <li><i class="ik ik-minus minimize-card"></i></li>
                        <li><i class="ik ik-x close-card"></i></li>
                    </ul>
                </div>
            </div>
            <div class="card-body todo-task">
                <div class="dd" data-plugin="nestable">
                    <ol class="dd-list">
                        <li class="dd-item" data-id="1">
                            <div class="dd-handle">
                                <h6>Dashbaord</h6>
                                <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                            </div>
                        </li>
                        <li class="dd-item" data-id="2">
                            <div class="dd-handle">
                                <h6>New project</h6>
                                <p>It is a long established fact that a reader will be distracted.</p>
                            </div>
                        </li>
                        <li class="dd-item" data-id="3">
                            <div class="dd-handle">
                                <h6>Feed Details</h6>
                                <p>here are many variations of passages of Lorem Ipsum available, but the majority have suffered.</p>
                            </div>
                        </li>
                    </ol>
                </div>

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
      },
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

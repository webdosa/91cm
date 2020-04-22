<template>
  <div style="padding: 15px 20px 0px 20px; background-color: gray;" class="col-12">

    <div v-if="taskList.name != ''">
      <draggable :list="getTasks" :group="'tasks'" @change="taskEventHandler">
        <span class="h3" style="color: white;" v-if="!edit" slot="header">{{ taskList.name }}
          <b-badge variant="nonoutline" @click="editToggle"><i class="im im-pencil"></i></b-badge>
          <b-badge variant="nonoutline" @click="deleteTaskList"><i class="im im-trash-can"></i></b-badge>
        </span>
        <b-form-input v-else
                      @keydown.enter.exact="editTaskListName"
                      @keydown.esc="editToggle"
                      v-model="taskList.name"
                      autofocus></b-form-input>
        <i class="im im-plus float-right btn" style="color: white;" @click="createFormToggle"></i>
      </draggable>

    </div>
    <div v-else>
      <b-form-input placeholder="내용을 입력해주세요" v-model="taskListName" autofocus
                    @keydown.enter.exact="setTaskListName"></b-form-input>
    </div>
    <br><br>
    <b-list-group style="width: 25vw; height: 75vh; overflow-y: scroll;"> <!-- 임시로 정해주 높이 값 정확한 반응형 높이가 아님 -->
      <b-list-group-item v-if="create" style="padding: 10px 0px; margin-bottom: 10px;">
        <b-form-textarea placeholder="내용을 입력해주세요" v-model="taskContent" @keydown.enter.exact="addTask">
        </b-form-textarea>
        <div class="float-right">
          <b-button size="sm" variant="primary" style="margin-right: 5px;" @click="addTask">Save</b-button>
          <b-button size="sm" variant="danger" @click="createFormToggle">Cancel</b-button>
        </div>
      </b-list-group-item>
      <draggable :list="getTasks" :group="'tasks'" @change="taskEventHandler">
        <transition-group name="task-list">
          <b-list-group-item v-for="(task,index) in getTasks" :key="task" style="margin-bottom: 10px;">
            <div>
              <b-dropdown no-caret variant="nonoutline" toggle-class="text-decoration-none"
                          class="float-right" style="padding: 0px;">
                <template v-slot:button-content>
                  <i class="im im-menu-dot-h"></i>
                </template>
                <b-dropdown-item @click="editFormToggle(index)">Edit</b-dropdown-item>
                <b-dropdown-item @click="deleteTask(task,index)" variant="danger">Delete</b-dropdown-item>
              </b-dropdown>
              <br>
              <p v-if="index != editSelector" id="content">{{task.content}}</p>
              <div v-else>
                <b-form-textarea placeholder="내용을 입력해주세요" v-model="task.content" autofocus>
                </b-form-textarea>
                <div class="float-right">
                  <b-button size="sm" variant="primary" style="margin-right: 5px;" @click="editTask(task)">Edit
                  </b-button>
                  <b-button size="sm" variant="danger" @click="editFormToggle">Cancel</b-button>
                </div>
                <br><br>
              </div>

              <footer>
                <small class="float-right">created by {{task.member_email}}</small>
                <!--            <small class="float-left">created date {{task.register_date}}</small>-->
              </footer>
            </div>
          </b-list-group-item>
        </transition-group>
      </draggable>
    </b-list-group>
  </div>
</template>
<script>
  import draggable from 'vuedraggable'

  export default {
    name: 'TaskList',
    props: ["taskList"],
    computed: {
      getTasks: function () {
        return this.taskList.tasks
      }
    },
    watch: {
      getTasks: function () {
        this.taskList.tasks.forEach(task => {
          task.position = this.taskList.tasks.indexOf(task)
        })
        console.log(this.taskList.tasks)
      }
    },
    components: {
      draggable
    },
    data() {
      return {
        updateTask: {
          taskOldIndex: null,
          taskNewIndex: null,
          tasklistOldId: null,
          tasklistNewId: null,
          taskId: null
        },
        editSelector: -1,
        taskContent: '',
        taskListName: '',
        create: false,
        edit: false,
        task: {
          tasklist_id: this.taskList.id,
          content: '',
          member_email: this.$store.state.currentUser.email
        }
      }
    },
    created() {
    },
    methods: {
      taskEventHandler: function ({added, moved, removed}) {
        let oldIndex = -1
        let newIndex = -1
        let task
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
              console.log("task update ok")
              this.$store.state.stompClient.send('/sub/todo/'+this.$store.state.currentChannel.id,{},{typename: 'taskUpdate'})
            }).catch(error => {
            console.log(error)
          })
          console.log(updateTaskItem)
        }
        if (removed) {
          updateTaskItem.taskOldIndex = removed.oldIndex
          updateTaskItem.taskNewIndex = removed.element.position
          updateTaskItem.tasklistOldId = this.taskList.id
          updateTaskItem.tasklistNewId = removed.element.tasklist_id
          updateTaskItem.taskId = removed.element.id
          console.log(updateTaskItem)
          this.$http.post('/api/task/update/position', updateTaskItem)
            .then(res => {
              console.log("task update ok")
              this.$store.state.stompClient.send('/sub/todo/'+this.$store.state.currentChannel.id,{},{typename: 'taskUpdate'})
            }).catch(error => {
            console.log(error)
          })
        }
      },
      updateTask: function (updateTaskItem) {

      },
      checkTask: function (evt) {
        evt.draggedContext.element.tasklist_id = this.taskList.id
        evt.draggedContext.element.position = evt.draggedContext.index
      },
      deleteTaskList: function () {
        console.log(this.taskList)
        this.$http.post('/api/tasklist/delete', {
          id: this.taskList.id,
          position: this.taskList.position
        })
          .then(res => {
            console.log('delete success : ' + res.data)
            this.$eventBus.$emit('deleteTaskList', this.taskList)
            this.$store.state.stompClient.send('/sub/todo/'+this.$store.state.currentChannel.id,{},{typename: 'taskUpdate'})
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
          console.log(res.data)
          this.$store.state.stompClient.send('/sub/todo/'+this.$store.state.currentChannel.id,{},{typename: 'taskUpdate'})
          this.editToggle()
        }).catch(error => {
          console.log(error)
        })
      },
      editTask: function (task) {
        this.$http.post('/api/task/update/content', task)
          .then(res => {
            this.$store.state.stompClient.send('/sub/todo/'+this.$store.state.currentChannel.id,{},{typename: 'taskUpdate'})
            console.log(res.data)
            this.editFormToggle(-1)
          }).catch(error => {
          console.log(error)
        })
      },
      deleteTask: function (task, index) {
        // 현저 유저와 작성자가 같은지 비교해서 삭제할 수 있도록 변경 필요
        this.$http.post('/api/task/delete', task)
          .then(res => {
            this.$store.state.stompClient.send('/sub/todo/'+this.$store.state.currentChannel.id,{},{typename: 'taskUpdate'})
            console.log(res.data)
            this.taskList.tasks.splice(index, 1)
          }).catch(error => {
          console.log(error)
        })
      },
      addTask: function () {
        this.task.content = this.taskContent
        this.$http.post('/api/task/insert', this.task)
          .then(res => {
            this.taskContent = ''
            this.taskList.tasks.unshift(res.data)
            this.createFormToggle()
            this.$store.state.stompClient.send('/sub/todo/'+this.$store.state.currentChannel.id,{},{typename: 'taskUpdate'})
            console.log(this.taskList)
          }).catch(error => {
          console.log(error)
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
        this.taskList.name = this.taskListName
        console.log(this.taskList)
        this.$http.post('/api/tasklist/insert', JSON.stringify(this.taskList), {
          headers: {
            'Content-Type': 'application/json'
          }
        })
          .then(res => {
            this.taskList.id = res.data.id
            this.task.tasklist_id = res.data.id
            this.$store.state.stompClient.send('/sub/todo/'+this.$store.state.currentChannel.id,{},{typename: 'taskUpdate'})
          })
          .catch(error => {
            console.log(error)
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

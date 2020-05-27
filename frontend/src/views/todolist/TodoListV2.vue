<template>
  <div class="mainwrapper" style="height: calc(100vh - 150px);">
     <div class="container-fluid">
        <div class="page-header">
            <div class="row align-items-end">
                <div class="col-lg-8">
                    <div class="page-header-title">
                        <i class="ik ik-server bg-blue"></i>
                        <div class="d-inline">
                            <h5>Taskboard</h5>
                            <span>해당 채널 이름을 넣어줍시다</span>
                        </div>
                    </div>
                </div>
                <div class="col-lg-4">
                    <nav class="breadcrumb-container" aria-label="breadcrumb">
                       <ol class="breadcrumb" style="display: flex;align-items: center;">
                           
                           <li class="breadcrumb-item"> 리스트 생성</li>
                            <li class="breadcrumb-item" @click="createList">
                                <i class="ik ik-plus-circle" style="font-size:1.8rem;"></i>

                            </li>
                        </ol>
                    </nav>
                </div>
            </div>
        </div>


        <div class="row">
            <draggable :list="getTaskBoard" style="display:flex; flex-wrap:nowrap" v-bind="dragOptions" @change="tasklistEventHandler">
                <div class="col-md-4" v-for="item in getTaskBoard" :key="item">
                    <TaskList :taskList="item"></TaskList>
                </div>
            </draggable>
            <!-- <div class="col-md-4">
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
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h3>In Progress</h3>
                        <div class="card-header-right">
                            <ul class="list-unstyled card-option">
                                <li><i class="ik ik-chevron-left action-toggle"></i></li>
                                <li><i class="ik ik-rotate-cw reload-card" data-loading-effect="pulse"></i></li>
                                <li><i class="ik ik-minus minimize-card"></i></li>
                                <li><i class="ik ik-x close-card"></i></li>
                            </ul>
                        </div>
                    </div>
                    <div class="card-body progress-task">
                        <div class="dd" data-plugin="nestable">
                            <ol class="dd-list">
                                <li class="dd-item" data-id="1">
                                    <div class="dd-handle">
                                        <h6>New Code Update</h6>
                                        <p>Lorem Ipsum is simply dummy text of the printing and typesetting industry.</p>
                                    </div>
                                </li>
                                <li class="dd-item" data-id="2">
                                    <div class="dd-handle">
                                        <h6>Meeting</h6>
                                        <p>The standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from "de Finibus Bonorum et Malorum" by Cicero</p>
                                    </div>
                                </li>
                            </ol>
                        </div>

                    </div>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card">
                    <div class="card-header">
                        <h3>Completed</h3>
                        <div class="card-header-right">
                            <ul class="list-unstyled card-option">
                                <li><i class="ik ik-chevron-left action-toggle"></i></li>
                                <li><i class="ik ik-rotate-cw reload-card" data-loading-effect="pulse"></i></li>
                                <li><i class="ik ik-minus minimize-card"></i></li>
                                <li><i class="ik ik-x close-card"></i></li>
                            </ul>
                        </div>
                    </div>
                    <div class="card-body completed-task">
                        <div class="dd" data-plugin="nestable">
                            <ol class="dd-list">                                   
                                <li class="dd-item" data-id="1">
                                    <div class="dd-handle">                                        
                                        <h6>Job title</h6>
                                        <p>If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text.</p>
                                    </div>
                                </li>
                                <li class="dd-item" data-id="2">
                                    <div class="dd-handle">
                                        <h6>Event Done</h6>
                                        <p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical</p>
                                    </div>
                                </li>
                            </ol>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-4">
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
            </div>
            <div class="col-md-4">
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
            </div>
            <div class="col-md-4">
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
            
            </div> -->
        </div>
    </div>

     <v-dialog v-model="dialog" persistent max-width="600px">
      <v-card>
        <v-card-title>
          <span class="headline">리스트 생성</span>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
             
              <v-col cols="12">
                <v-text-field label="리스트 이름" required></v-text-field>
              </v-col>
              
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="dialog = false">Close</v-btn>
          <v-btn color="blue darken-1" text @click="dialog = false">Save</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>
<script>
  import TaskList from "../../components/TaskListV2"
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
        dialog:false,
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
      createList: function(){
        this.dialog = true;
      },
      close: function(){
          this.dialog= false;
      },
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

  .breadcrumb-item + .breadcrumb-item::before {
    display: inline-block;
    padding-right: 0.5rem;
    color: #6c757d;
    content: "";
    }
</style>

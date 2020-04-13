<template>
  <div style="padding: 15px 20px 0px 20px; background-color: gray;">
      <span class="h3" style="color: white;">{{list.title}}</span>
        <i class="im im-plus float-right btn" style="color: white;" @click="createTask"></i>
      <br><br>
      <b-list-group style="width: 25vw;">
          <b-list-group-item v-if="create" style="padding: 10px 0px; margin-bottom: 10px;">
            <b-form-textarea placeholder="내용을 입력해주세요">
            </b-form-textarea>
            <div class="float-right">
              <b-button size="sm" variant="primary" style="margin-right: 5px;">Save</b-button>
              <b-button size="sm" variant="danger" @click="createTask">Cancel</b-button>
            </div>
          </b-list-group-item>
          <b-list-group-item v-for="(item,index) in list.taskList" :key="index" style="margin-bottom: 10px;">
            <div>    
                <b-dropdown no-caret variant="nonoutline" toggle-class="text-decoration-none" 
                class="float-right" style="padding: 0px;">
                  <template v-slot:button-content>
                    <i class="im im-menu-dot-h"></i>
                  </template>
                  <b-dropdown-item @click="editTask(index)">Edit</b-dropdown-item>
                  <b-dropdown-item @click="deleteTask" variant="danger">Delete</b-dropdown-item>
                </b-dropdown>
            <br>
                <p v-if="!item.edit" id="content">{{item.content}}</p>
                <div v-if="item.edit">
                  <b-form-textarea placeholder="내용을 입력해주세요">
                  </b-form-textarea>
                  <div class="float-right">
                    <b-button size="sm" variant="primary" style="margin-right: 5px;">Save</b-button>
                    <b-button size="sm" variant="danger" @click="editTask">Cancel</b-button>
                  </div>
                  <br><br>
                </div>
                
            <footer>
                <small class="float-right">created by {{item.member_email}}</small>
                <small class="float-left">created date {{item.register_date}}</small>
            </footer>
            </div>
          </b-list-group-item>
      </b-list-group>
  </div>
</template>
<script>
import Task from '../components/Task'
  export default {
    name: 'TaskList',
    props: ["list"],
    components: {
        Task
    },
    data() {
      return {
        create: false,
        edit: false
      }
    },
    created() {
      for(task in this.list.taskList){
        task.edit = false
      }  
    },
    methods: {
      createTask: function(){
        this.create = !this.create
      },
      editTask: function (index) {
        this.list.taskList[index].edit = !list.taskList[index].edit
      }
    }
  }
</script>
<style scoped>
    i{
        margin-left: 10px;
    }
    i:hover{
      color: gray;
    }
    P{
        font-size: 20px;
    }
    #content{
      overflow:hidden;
      word-wrap: break-word;
    }

    
    
  
</style>

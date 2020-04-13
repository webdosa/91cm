<template>
     <div class="scrolling-wrapper">
    <draggable v-model="dragList" class="row flex-nowrap" style="overflow:auto;" v-bind="getDragOptions">
       <TaskList 
            v-for="(listItem, index) in lists"
            :key="index"
            :list="listItem">
          </TaskList>
    </draggable>
  </div>
</template>
<script>
import Draggable from 'vuedraggable'
import TaskList from './Lists/TaskList'

export default {
    name:'TaskBoard',
    components: {
    TaskList,
    Draggable
    },
    mounted(){

    },
    computed: {
        dragList:{
            get() {
                return this.lists
            },
            set(value) {
              console.log(value)
                return this.lists = value
            }
        },
        getDragOptions() {
            return {
                animation: "200",
                ghostClass: "ghost",
                handle: ".heading", 
                // group: "kanban-board-lists"
                // 뭔지 잘 모르겠음
            }
        }
    },
    data() {
      return {

          lists: [
            {
              id: "1",
              name: "todo",
              items: [
                { 
                  id: "1",
                  text: "Build the feature #1"
                },
                { 
                  id: "2",
                  text:"Test the feature #1"
                },
                { 
                  id: "3",
                  text:"Commit feature #1 to the repository"
                },
                { 
                  id: "4",
                  text:"Deploy the feature #1"
                }
              ]
            },
            {
              id: "2",
              name: "doing",
              items: [
                { 
                  id:"5",
                  text: "Build the demo app"
                },
                { 
                  id:"6",
                  text:"Put in some sample data in app"
                },
                { 
                  id:"7",
                  text:"Test the app before launching"
                }
              ]
            },
            {
              id: "3",
              name: "done",
              items: [
                { 
                  id:"8",
                  text: "Set up app landing page"
                },
                { 
                  id:"9",
                  text:"Send out email invitations to the subscribers"
                }
              ]
            },
            {
              id: "4",
              name: "deploy",
              items: [
                { 
                  id:"10",
                  text: "Send notification to the customer"
                },
                { 
                  id:"11",
                  text:"Project go live"
                }
              ]
            }
          ]
      }
    },
}
</script>
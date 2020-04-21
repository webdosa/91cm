<template>
  <div class="container">
    <full-calendar :config="config" :events="events"></full-calendar>
  </div>
</template>

<script>
  import {FullCalendar} from 'vue-full-calendar'
  import {mapGetters} from "vuex";
  export default {
    name: "Calendar",
    components: {
      FullCalendar
    },
    watch:{
      getTaskBoard: function (newTaskBoard) {
        newTaskBoard.forEach(tasklist =>{
          tasklist.tasks.forEach(task=>{
            console.log(task)
            if (task.start_date){
              this.events.push({
                title: task.content,
                start: task.start_date,
                end: task.end_date,
                color: 'green'
              })
            }
          })
        })
      }
    },
    computed:{
      ...mapGetters({
        taskBoard: 'getTaskBoard'
      })
    },
    data() {
      return {
        events: [],
        config: {
          editable: false,
          defaultView: 'month',
          selectHelper: false,
          locale: 'ko'
        }
      }
    },
    created() {
      console.log(this.$store.state.taskBoard)
      this.$store.state.taskBoard.forEach(tasklist =>{
        tasklist.tasks.forEach(task=>{
          console.log(task)
          if (task.start_date){
            this.events.push({
              title: task.content,
              start: task.start_date,
              end: task.end_date,
              color: 'green'
            })
          }
        })
      })
    }

  }
</script>

<style>
  @import "~fullcalendar/dist/fullcalendar.min.css";
  .fc-title {
    color: white;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    text-shadow:
    -1px -1px 0 #2d2d2d,
    1px -1px 0 #2d2d2d,
    -1px 1px 0 #2d2d2d,
    1px 1px 0 #2d2d2d;
  }
</style>

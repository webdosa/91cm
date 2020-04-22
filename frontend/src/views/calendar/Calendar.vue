<template>
  <div class="container">
    <full-calendar ref="calendar" :config="config" :events="events" @event-selected="selectEvent"></full-calendar>
  <b-modal id="change-event" @ok="handleOk">
    <label>일정 제목</label>
    <b-input v-model="eventTitle" title="일정 수정" style="margin-right: 15px;" class="float-left"></b-input>
    <v-swatches v-model="eventColor" popover-x="right" show-fallback placeholder="제목을 입력해주세요."></v-swatches>
  </b-modal>
  </div>
</template>

<script>
  import {FullCalendar} from 'vue-full-calendar'
  import VSwatches from 'vue-swatches'
  import 'vue-swatches/dist/vue-swatches.css'
  import {mapGetters} from "vuex";
  // Date 유틸을 만들어서 import 하면 현재 선언된 main의 Date의 prototype이 가능한지
  export default {
    name: "Calendar",
    components: {
      FullCalendar,
      VSwatches
    },
    watch: {
      // getTaskBoard: function (newTaskBoard) {
      //   newTaskBoard.forEach(tasklist => {
      //     tasklist.tasks.forEach(task => {
      //       console.log(task)
      //       if (task.start_date) {
      //         this.events.push({
      //           title: task.content,
      //           start: task.start_date,
      //           end: task.end_date,
      //           color: task.color
      //         })
      //       }
      //     })
      //   })
      // }
    },
    computed: {
      ...mapGetters({
        taskBoard: 'getTaskBoard'
      })
    },
    data() {
      return {
        events: [],
        selectEventItem: {},
        eventTitle: null,
        eventColor: null,
        config: {
          editable: false,
          defaultView: 'month',
          selectHelper: false,
          locale: 'ko'
        }
      }
    },
    created() {
      this.$http.get('/api/tasklist/get/' + this.$store.state.currentChannel.id)
        .then(res => {
          const taskBoard = res.data
          this.$store.commit('setTaskBoard', taskBoard)
          console.log(taskBoard)
          taskBoard.forEach(taskList => {
            taskList.tasks.forEach(task => {
              console.log(task)
              if (task.start_date) {
                let title = '제목 없음'
                if(task.title==null || task.title==''){
                  title = task.content
                }else{
                  title = task.title
                }
                this.events.push({
                  title: title,
                  start: new Date(task.start_date),
                  end: new Date(task.end_date).addDays(1),
                  color: task.color,
                  taskid: task.id
                })
              }
            })
          })

        })
      console.log(this.$store.state.taskBoard)

    },
    mounted() {
      console.log("calendar mounted")
    },
    methods: {
      selectEvent: function (event) {
        this.selectEventItem = event
        this.eventTitle = event.title
        this.$bvModal.show('change-event')
      },
      handleOk: function () {
        const eventIndex = this.events.findIndex(event => event.taskid == this.selectEventItem.taskid)
        console.log(this.events[eventIndex])
        this.events[eventIndex].title = this.eventTitle
        this.eventTitle=null
        if (this.eventColor != null){
          this.events[eventIndex].color = this.eventColor
          this.eventColor=null
        }
        //엑시오스로 db 업데이트 및 실시간 처리
      },

    }
  }
</script>

<style>
  @import "~fullcalendar/dist/fullcalendar.min.css";

  .fc-title {
    text-align: center;
    color: white;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
    text-shadow: -1px -1px 0 #2d2d2d,
    1px -1px 0 #2d2d2d,
    -1px 1px 0 #2d2d2d,
    1px 1px 0 #2d2d2d;
  }
  .fc-content{
    text-align: center;
  }
</style>

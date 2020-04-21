<template>
  <div>
  </div>
</template>
<script>
  import draggable from 'vuedraggable'
  export default {
    name: 'About',
    components: {
      draggable
    },
    computed:{
      dragOptiopns(){
        return{
          animation: "200",
          ghostClass: "ghost",
          group: "Kanban-board-list-items"
        }
      }
    },
    data() {
      return {
        number: 0,
        datePickerConfig:{
          night: '일',
          nights: '일',
          'day-names':['일','월','화','수','목','금','토'],
          'check-in': 'start',
          'check-out': 'end',
          'month-names': ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월',]
        },
        list: [{
          name: 'name1'
        }]
      }
    },
    methods: {
      test: function () {
        this.list.push({name: 'name'+this.number++})
      },
      moveItem: function () {
        let rows = [this.list[0],this.list[1]]
        this.list.splice(0,2,rows[1],rows[0])
      }
    }

  }

</script>
<style scoped>
  .list-enter-active, .list-leave-active{
    transition: all 1s;
  }
  .list-enter, .list-leave-to{
    opacity: 0;
    transform: translateX(30px);
  }
  .list-move{
    transition: transform 1s;
  }
  #esc {
    color: #7f7f7f;
  }

  #esc:hover {
    color: black;
  }
</style>

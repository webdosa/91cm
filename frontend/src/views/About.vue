<template>
  <div>
    <h1>test</h1>
    <div class="col-5 float-left">
      <b-button @click="moveItem">moveTest</b-button>
      <b-button @click="test" class="float-right">test</b-button>
      <b-list-group>
        <draggable :list="list">
          <transition-group name="list" >
            <b-list-group-item v-for="item in list" :key="item" class="list-item">
              {{item.name}}
            </b-list-group-item>
          </transition-group>
        </draggable>
      </b-list-group>
    </div>
    <div class="col-5 float-right">
      <div v-for="(item,index) in list">
        <p>{</p>
          <p>index: {{index}}</p>
          <p>name : {{item.name}}</p>
        <p>}</p>
      </div>
    </div>
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

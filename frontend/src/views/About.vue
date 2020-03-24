<template>
  <div>
    <input v-model="message">
    <p>역순으로 표시한 메시지: {{reversedMessage}}</p>
    <b-button @click="test">tests</b-button>
  </div>
</template>
<script>
export default {
  name: 'About',
  data () {
    return {
      message: '안녕하세요',
    }
  },
  methods: {
    test: function () {
      let booleans = this.$alertModal('select','정말로 삭제하시겠습니까?')
      console.log(booleans)
    }
  },
  computed: {
    reversedMessage: function () {
      return this.message.split('').reverse().join('')
    }
  }
}

</script>

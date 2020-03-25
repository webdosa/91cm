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
      testvalue: null
    }
  },
  updated() {
  },
  methods: {
    test: async function () {
      this.testvalue = await this.$alertModalCon('select redirect', '정말로 삭제하시겠습니까?')
      console.log(this.testvalue)
    }
  },
  computed: {
    reversedMessage: function () {
      return this.message.split('').reverse().join('')
    }
  }
}

</script>

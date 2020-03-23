<template>
  <div>
    <input v-model="message">
    <p>역순으로 표시한 메시지: {{reversedMessage}}</p>
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
  computed: {
    reversedMessage: function () {
      return this.message.split('').reverse().join('')
    }
  }
}

</script>

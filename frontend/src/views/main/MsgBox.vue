<template>
  <li class="list-unstyled chat-message">
    <div class="icon">
      <!-- <svg
        style="fill:#e6e6e6;"
        xmlns="http://www.w3.org/2000/svg"
        width="40"
        height="40"
        viewBox="0 0 24 24"
      >
        <path
          d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
        ></path>
      </svg> -->
      <slot name="m-icon">
        <img src="../../assets/images/default-user-picture.png" width="40" height="40"/>
      </slot>
    </div>
    <!-- flex에서 벗어나기 위해 감쌈  -->
    <div>
      <div class="verti-align">
        <slot name="m-info">
          <strong>username</strong>
          <span style="font-size: 11px; margin-left:3px;">&nbsp;&nbsp;11:09 AM May 9</span>
        </slot>

      </div>
      <!-- 채팅메세지내용 -->
      <div class="verti-align">
        <slot name="m-content"></slot>
      </div>
      <!-- 채팅메시지내용끝 -->
    </div>
  </li>
</template>
<script>
  export default {
    name: 'MsgBox'
  }
</script>

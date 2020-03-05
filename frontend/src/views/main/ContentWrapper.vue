<template>
    <main class="mainwrapper">
        <div class="h-inherit">
          <ul class="c-c-wrapper list-unstyled">
            <MsgBox></MsgBox>
          </ul>
          <div class="c-i-wrapper">
            <!-- 더 뭔가 추가할 거 같아서 div로 감싸놓음 -->
            <div style="flex-grow:1;">
              <b-form-textarea
                id="textarea-no-resize"
                placeholder="Fixed height textarea"
                rows="3"
                no-resize
              ></b-form-textarea>
            </div>
            <b-button style="height: 57px; width: 70px; margin-left:20px;" variant="primary">전송</b-button>
          </div>
        </div>
      </main>
</template>
<script>
import MsgBox from './MsgBox'
export default {
    name: 'ContentWrapper',
    components: {
        MsgBox
    }
}
</script>
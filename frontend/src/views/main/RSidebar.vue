<template>
  <nav id="r-sidebar" class="myflex-column" v-bind:class="{barRtoggle: $store.state.isRActive}">
    <div class="r-header r-style">
      <span>About this Channel</span>
      <div style="flex-grow: 1;justify-content: flex-end;display: flex;">
        <a @click="RSidebarClose">
          <svg
            width="24"
            height="24"
            xmlns="http://www.w3.org/2000/svg"
            fill-rule="evenodd"
            clip-rule="evenodd"
          >
            <path
              d="M12 11.293l10.293-10.293.707.707-10.293 10.293 10.293 10.293-.707.707-10.293-10.293-10.293 10.293-.707-.707 10.293-10.293-10.293-10.293.707-.707 10.293 10.293z"
            ></path>
          </svg>
        </a>
      </div>
    </div>
    <!-- content wrapper -->
    <div style="overflow:auto;">
      <!-- 메뉴하나시작 -->
      <a class="r-style" v-b-toggle.channel-info>
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
          <path
            d="M12 2c5.514 0 10 4.486 10 10s-4.486 10-10 10-10-4.486-10-10 4.486-10 10-10zm0-2c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm-.001 5.75c.69 0 1.251.56 1.251 1.25s-.561 1.25-1.251 1.25-1.249-.56-1.249-1.25.559-1.25 1.249-1.25zm2.001 12.25h-4v-1c.484-.179 1-.201 1-.735v-4.467c0-.534-.516-.618-1-.797v-1h3v6.265c0 .535.517.558 1 .735v.999z"
          ></path>
        </svg>
        <span style="margin-left:20px;">Channel Details</span>
        <svg
          class="ml-auto"
          xmlns="http://www.w3.org/2000/svg"
          width="10"
          height="10"
          viewBox="0 0 24 24"
        >
          <path d="M12 21l-12-18h24z"></path>
        </svg>
      </a>
      <!-- 메뉴하나끝 -->
      <!-- 콜랩스 -->
      <b-collapse id="channel-info">
        <div class="s-coll-style">
          <div>
            <div style="display:flex;">
              <p>Channel Name</p>
              <a class="verti-align" data-mode="edit" @click="prepareModal">Edit</a>
              <a class="verti-align" >Delete</a>
            </div>
            <li class="list-unstyled">Cha1</li>
          </div>
          <div>
            <p>Created</p>
            <!-- MMMM dd, yyyy 형식으로 출력하는게 나을 듯-->
            <li class="list-unstyled">Created by username on March 09, 2020</li>
          </div>
          <div style="display:flex; justify-content:flex-start;">
            <b-button variant="primary">나가기</b-button>
          </div>
        </div>
      </b-collapse>

      <!-- 메뉴하나시작 -->
      <a class="r-style" v-b-toggle.user-info>
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
          <path
            d="M17.997 18h-.998c0-1.552.06-1.775-.88-1.993-1.438-.332-2.797-.645-3.293-1.729-.18-.396-.301-1.048.155-1.907 1.021-1.929 1.277-3.583.702-4.538-.672-1.115-2.707-1.12-3.385.017-.576.968-.316 2.613.713 4.512.465.856.348 1.51.168 1.908-.49 1.089-1.836 1.4-3.262 1.728-.982.227-.92.435-.92 2.002h-.995l-.002-.623c0-1.259.1-1.985 1.588-2.329 1.682-.389 3.344-.736 2.545-2.209-2.366-4.365-.676-6.839 1.865-6.839 2.492 0 4.227 2.383 1.867 6.839-.775 1.464.824 1.812 2.545 2.209 1.49.344 1.589 1.072 1.589 2.333l-.002.619zm4.81-2.214c-1.289-.298-2.489-.559-1.908-1.657 1.77-3.342.47-5.129-1.4-5.129-1.265 0-2.248.817-2.248 2.325 0 1.269.574 2.175.904 2.925h1.048c-.17-.75-1.466-2.562-.766-3.736.412-.692 1.704-.693 2.114-.012.38.631.181 1.812-.534 3.161-.388.733-.28 1.301-.121 1.648.305.666.977.987 1.737 1.208 1.507.441 1.368.042 1.368 1.48h.997l.002-.463c0-.945-.074-1.492-1.193-1.75zm-22.805 2.214h.997c0-1.438-.139-1.039 1.368-1.48.761-.221 1.433-.542 1.737-1.208.159-.348.267-.915-.121-1.648-.715-1.349-.914-2.53-.534-3.161.41-.682 1.702-.681 2.114.012.7 1.175-.596 2.986-.766 3.736h1.048c.33-.75.904-1.656.904-2.925.001-1.509-.982-2.326-2.247-2.326-1.87 0-3.17 1.787-1.4 5.129.581 1.099-.619 1.359-1.908 1.657-1.12.258-1.194.805-1.194 1.751l.002.463z"
          ></path>
        </svg>
        <span style="margin-left:20px;">24 Users</span>
        <svg
          class="ml-auto"
          xmlns="http://www.w3.org/2000/svg"
          width="10"
          height="10"
          viewBox="0 0 24 24"
        >
          <path d="M12 21l-12-18h24z"></path>
        </svg>
      </a>
      <!-- 메뉴하나끝 -->
      <b-collapse id="user-info">
        <div class="s-coll-style">
          <div>
            <li class="list-unstyled">user1</li>
            <li class="list-unstyled">user2</li>
            <li class="list-unstyled">user3</li>
          </div>
          <div style="display:flex; justify-content:flex-start;">
            <b-button variant="primary">초대하기</b-button>
          </div>
        </div>
      </b-collapse>
      <!-- 메뉴하나시작 -->
      <a class="r-style">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
          <path
            d="M22 2v20h-20v-20h20zm2-2h-24v24h24v-24zm-4 7h-8v1h8v-1zm0 5h-8v1h8v-1zm0 5h-8v1h8v-1zm-10.516-11.304l-.71-.696-2.553 2.607-1.539-1.452-.698.71 2.25 2.135 3.25-3.304zm0 5l-.71-.696-2.552 2.607-1.539-1.452-.698.709 2.249 2.136 3.25-3.304zm0 5l-.71-.696-2.552 2.607-1.539-1.452-.698.709 2.249 2.136 3.25-3.304z"
          ></path>
        </svg>
        <span style="margin-left:20px;">Todo List</span>
      </a>
      <!-- 메뉴하나끝 -->
      <!-- 메뉴하나시작 -->
      <a class="r-style">
        <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
          <path
            d="M20 20h-4v-4h4v4zm-6-10h-4v4h4v-4zm6 0h-4v4h4v-4zm-12 6h-4v4h4v-4zm6 0h-4v4h4v-4zm-6-6h-4v4h4v-4zm16-8v22h-24v-22h3v1c0 1.103.897 2 2 2s2-.897 2-2v-1h10v1c0 1.103.897 2 2 2s2-.897 2-2v-1h3zm-2 6h-20v14h20v-14zm-2-7c0-.552-.447-1-1-1s-1 .448-1 1v2c0 .552.447 1 1 1s1-.448 1-1v-2zm-14 2c0 .552-.447 1-1 1s-1-.448-1-1v-2c0-.552.447-1 1-1s1 .448 1 1v2z"
          ></path>
        </svg>
        <span style="margin-left:20px;">Calendar</span>
      </a>
      <!-- 메뉴하나끝 -->
    </div>
  </nav>

</template>
<script>
  export default {
    props: ['modalObj'],
    name: 'RSidebar',
    data() {
      return {

      }
    },
    methods: {
      RSidebarClose: function () {
        this.$store.state.isRActive= false
      },
      prepareModal: function (e){
        if(e.target.dataset.mode=='edit'){
          this.modalObj.modalTitle = '채널 수정'
          //Cha1는 나중에 현재 채널 정보에서 가져올 채널 이름값
          this.modalObj.channelTitle ='Cha1'
          this.$emit('passData',this.modalObj)
          this.$bvModal.show('channelCU')
        }
      }
    }
  }
</script>

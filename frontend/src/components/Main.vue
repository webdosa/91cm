<template>
  <div class="wrapper">
    <!-- Sidebar  -->
    <sidebar id="sidebar" class="myflex-column" v-bind:class="{active: isLActive}"></sidebar>
    <!-- Page Content  -->
    <div id="m-wrapper" v-bind:class="{active: isLActive}">
      <header>
        <b-navbar toggleable="lg" type="light" variant="white">
          <button type="button" @click="siderbartoggle" id="sidebarCollapse" class="btn btn-info">
            <svg
              style="fill:white;"
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
            >
              <path d="M24 6h-24v-4h24v4zm0 4h-24v4h24v-4zm0 8h-24v4h24v-4z"></path>
            </svg>
            <span>&nbsp;Toggle Sidebar</span>
          </button>
          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown no-caret right toggle-class="nonoutline" class="verti-align">
              <template v-slot:button-content>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
                  <path
                    d="M15 21c0 1.598-1.392 3-2.971 3s-3.029-1.402-3.029-3h6zm.137-17.055c-.644-.374-1.042-1.07-1.041-1.82v-.003c.001-1.172-.938-2.122-2.096-2.122s-2.097.95-2.097 2.122v.003c.001.751-.396 1.446-1.041 1.82-4.668 2.709-1.985 11.715-6.862 13.306v1.749h20v-1.749c-4.877-1.591-2.193-10.598-6.863-13.306zm-3.137-2.945c.552 0 1 .449 1 1 0 .552-.448 1-1 1s-1-.448-1-1c0-.551.448-1 1-1zm-6.451 16c1.189-1.667 1.605-3.891 1.964-5.815.447-2.39.869-4.648 2.354-5.509 1.38-.801 2.956-.76 4.267 0 1.485.861 1.907 3.119 2.354 5.509.359 1.924.775 4.148 1.964 5.815h-12.903z"
                  ></path>
                </svg>
              </template>
              <b-dropdown-item href="#">EN</b-dropdown-item>
              <b-dropdown-item href="#">ES</b-dropdown-item>
              <b-dropdown-item href="#">RU</b-dropdown-item>
              <b-dropdown-item href="#">FA</b-dropdown-item>
            </b-nav-item-dropdown>
            <div class="verti-align useridsty">useridorname</div>
            <b-nav-item-dropdown no-caret right toggle-class="nonoutline">
              <!-- Using 'button-content' slot -->
              <template v-slot:button-content style="padding:0px;">
                <svg
                  style="fill:#e6e6e6;"
                  xmlns="http://www.w3.org/2000/svg"
                  width="40"
                  height="40"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
                  ></path>
                </svg>
              </template>
              <b-dropdown-item href="#">Profile</b-dropdown-item>
              <b-dropdown-item @click="logout">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-navbar>
        <b-navbar toggleable="lg" type="light" variant="white">
          <!-- <b-nav-text>Navbar text</b-nav-text> -->
          <li class="list-unstyled" style="font-weight:bold;">ChannelName</li>

          <a
            v-b-tooltip.hover
            v-b-tooltip.bottom
            title="View user list"
            class="nuj verti-align ml-auto"
            @click="rightbaropen"
          >
            <span>24</span>
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24">
              <path
                d="M17.997 18h-.998c0-1.552.06-1.775-.88-1.993-1.438-.332-2.797-.645-3.293-1.729-.18-.396-.301-1.048.155-1.907 1.021-1.929 1.277-3.583.702-4.538-.672-1.115-2.707-1.12-3.385.017-.576.968-.316 2.613.713 4.512.465.856.348 1.51.168 1.908-.49 1.089-1.836 1.4-3.262 1.728-.982.227-.92.435-.92 2.002h-.995l-.002-.623c0-1.259.1-1.985 1.588-2.329 1.682-.389 3.344-.736 2.545-2.209-2.366-4.365-.676-6.839 1.865-6.839 2.492 0 4.227 2.383 1.867 6.839-.775 1.464.824 1.812 2.545 2.209 1.49.344 1.589 1.072 1.589 2.333l-.002.619zm4.81-2.214c-1.289-.298-2.489-.559-1.908-1.657 1.77-3.342.47-5.129-1.4-5.129-1.265 0-2.248.817-2.248 2.325 0 1.269.574 2.175.904 2.925h1.048c-.17-.75-1.466-2.562-.766-3.736.412-.692 1.704-.693 2.114-.012.38.631.181 1.812-.534 3.161-.388.733-.28 1.301-.121 1.648.305.666.977.987 1.737 1.208 1.507.441 1.368.042 1.368 1.48h.997l.002-.463c0-.945-.074-1.492-1.193-1.75zm-22.805 2.214h.997c0-1.438-.139-1.039 1.368-1.48.761-.221 1.433-.542 1.737-1.208.159-.348.267-.915-.121-1.648-.715-1.349-.914-2.53-.534-3.161.41-.682 1.702-.681 2.114.012.7 1.175-.596 2.986-.766 3.736h1.048c.33-.75.904-1.656.904-2.925.001-1.509-.982-2.326-2.247-2.326-1.87 0-3.17 1.787-1.4 5.129.581 1.099-.619 1.359-1.908 1.657-1.12.258-1.194.805-1.194 1.751l.002.463z"
              ></path>
            </svg>
          </a>
        </b-navbar>
      </header>
      <main class="mainwrapper">
        <div class="h-inherit">
          <ul class="c-c-wrapper list-unstyled">
            <li class="list-unstyled chat-message">
              <div class="icon">
                <svg
                  style="fill:#e6e6e6;"
                  xmlns="http://www.w3.org/2000/svg"
                  width="40"
                  height="40"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
                  ></path>
                </svg>
              </div>
              <!-- flex에서 벗어나기 위해 감쌈  -->
              <div>
                <div>
                  <strong>username</strong>
                  <span style="font-size: 11px;">&nbsp;11:09 AM May 9</span>
                </div>
                <!-- 채팅메세지내용 -->
                <div>asdasdasdadadad
                  <br>asdasdasdadadad
                </div>
                <!-- 채팅메시지내용끝 -->
              </div>
            </li>
            <li class="list-unstyled chat-message">
              <div class="icon">
                <svg
                  style="fill:#e6e6e6;"
                  xmlns="http://www.w3.org/2000/svg"
                  width="40"
                  height="40"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
                  ></path>
                </svg>
              </div>
              <!-- flex에서 벗어나기 위해 감쌈  -->
              <div>
                <div>
                  <strong>username</strong>
                  <span style="font-size: 11px;">&nbsp;11:09 AM May 9</span>
                </div>
                <!-- 채팅메세지내용 -->
                <div>asdasdasdadadad
                  <br>asdasdasdadadad
                </div>
                <!-- 채팅메시지내용끝 -->
              </div>
            </li>
            <li class="list-unstyled chat-message">
              <div class="icon">
                <svg
                  style="fill:#e6e6e6;"
                  xmlns="http://www.w3.org/2000/svg"
                  width="40"
                  height="40"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
                  ></path>
                </svg>
              </div>
              <!-- flex에서 벗어나기 위해 감쌈  -->
              <div>
                <div>
                  <strong>username</strong>
                  <span style="font-size: 11px;">&nbsp;11:09 AM May 9</span>
                </div>
                <!-- 채팅메세지내용 -->
                <div>asdasdasdadadad
                  <br>asdasdasdadadad
                </div>
                <!-- 채팅메시지내용끝 -->
              </div>
            </li>
            <li class="list-unstyled chat-message">
              <div class="icon">
                <svg
                  style="fill:#e6e6e6;"
                  xmlns="http://www.w3.org/2000/svg"
                  width="40"
                  height="40"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
                  ></path>
                </svg>
              </div>
              <!-- flex에서 벗어나기 위해 감쌈  -->
              <div>
                <div>
                  <strong>username</strong>
                  <span style="font-size: 11px;">&nbsp;11:09 AM May 9</span>
                </div>
                <!-- 채팅메세지내용 -->
                <div>asdasdasdadadadasdasdasdadadadsadsasdsdsad</div>
                <!-- 채팅메시지내용끝 -->
              </div>
            </li>
            <li class="list-unstyled chat-message">
              <div class="icon">
                <svg
                  style="fill:#e6e6e6;"
                  xmlns="http://www.w3.org/2000/svg"
                  width="40"
                  height="40"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
                  ></path>
                </svg>
              </div>
              <!-- flex에서 벗어나기 위해 감쌈  -->
              <div>
                <div>
                  <strong>username</strong>
                  <span style="font-size: 11px;">&nbsp;11:09 AM May 9</span>
                </div>
                <!-- 채팅메세지내용 -->
                <div>asdasdasdadadadasdasdasdadadadsadsasdsdsad</div>
                <!-- 채팅메시지내용끝 -->
              </div>
            </li>
            <li class="list-unstyled chat-message">
              <div class="icon">
                <svg
                  style="fill:#e6e6e6;"
                  xmlns="http://www.w3.org/2000/svg"
                  width="40"
                  height="40"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
                  ></path>
                </svg>
              </div>
              <!-- flex에서 벗어나기 위해 감쌈  -->
              <div>
                <div>
                  <strong>username</strong>
                  <span style="font-size: 11px;">&nbsp;11:09 AM May 9</span>
                </div>
                <!-- 채팅메세지내용 -->
                <div>asdasdasdadadadasdasdasdadadadsadsasdsdsad</div>
                <!-- 채팅메시지내용끝 -->
              </div>
            </li>
            <li class="list-unstyled chat-message">
              <div class="icon">
                <svg
                  style="fill:#e6e6e6;"
                  xmlns="http://www.w3.org/2000/svg"
                  width="40"
                  height="40"
                  viewBox="0 0 24 24"
                >
                  <path
                    d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
                  ></path>
                </svg>
              </div>
              <!-- flex에서 벗어나기 위해 감쌈  -->
              <div>
                <div>
                  <strong>username</strong>
                  <span style="font-size: 11px;">&nbsp;11:09 AM May 9</span>
                </div>
                <!-- 채팅메세지내용 -->
                <div>asdasdasdadadadasdasdasdadadadsadsasdsdsad</div>
                <!-- 채팅메시지내용끝 -->
              </div>
            </li>
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
    </div>

    <nav id="r-sidebar" class="myflex-column" v-bind:class="{barRtoggle: isRActive}">
      <div class="r-header r-style">
        <span>About this Channel</span>
        <div style="flex-grow: 1;justify-content: flex-end;display: flex;">
          <a @click="rightbarclose">
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
            <p>Channel Name</p>
            <li class="list-unstyled">Cha1</li>
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
            <p>Channel Name</p>
            <li class="list-unstyled">Cha1</li>
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
    <b-modal id="channel-create" centered title="채널 생성">
      <p class="my-4">Vertically centered modal!</p>
    </b-modal>
  </div>
</template>
<script>
import Sidebar from '../views/main/Sidebar'
import { AxiosInstance as axios } from 'axios'
export default {
  name: 'Main',
  components: { Sidebar },
  data () {
    return {
      isLActive: false,
      isRActive: false
    }
  },
  methods: {
    siderbartoggle: function () {
      this.isLActive = !this.isLActive
    },
    rightbaropen: function () {
      this.isRActive = true
    },
    rightbarclose: function () {
      this.isRActive = false
    },
    logout: function () {
      axios.get('http://localhost:9191/logout')
        .then(res => {
          console.log(res)
        })
        .error(error => {
          console.log(error)
        })
    }
  }
}
</script>

<style scoped>
  #r-sidebar {
    width: 320px;
    position: fixed;
    top: 106px;
    right: -320px;
    height: 100vh;
    z-index: 999;
    background: #fff;
    transition: all 0.3s;
    border: 1px solid #e6e6e6;
    box-shadow: 0 15px 0 0 rgba(0, 0, 0, 0.11), 0 5px 15px 0 rgba(0, 0, 0, 0.08);
  }
  #r-sidebar .r-header {
    font-weight: 900;
    background-color: #fafafa;
  }

  #r-sidebar .r-style {
    padding: 12px 16px;
    font-size: 18px;
    line-height: 1.33334;
    height: 61px;
    display: flex;
    align-items: center;
    border-bottom: 1px solid #e6e6e6;
  }
  .s-coll-style {
    padding: 12px 16px;
    border-bottom: 1px solid #e6e6e6;
  }
  .s-coll-style > p {
    margin-bottom: 5px;
  }
  .s-coll-style > li {
    font-size: 14px;
  }

  .barRtoggle {
    right: 0 !important;
  }

  /*CHAT MESSAGE*/
  .chat-message {
    padding: 10px;
    display: flex;
  }

  .chat-message .icon {
    padding-right: 10px;
  }
  /*----------------------
  MainContents
  ------------------------*/
  .mainwrapper {
    height: calc(100vh - 105px);
    overflow: hidden;
  }

  .h-inherit {
    height: inherit;
  }
  /* .chat-wrapper {
    height: 100%;
    background: white;
    border: 1px solid #e6e6e6;
    width: 100%;
  } */

  /*chat contents wrapper*/
  .c-c-wrapper {
    overflow: auto;
    height: calc(100% - 141px);
    padding: 20px 25px 0px 25px;
    margin-bottom: 0px;
  }

  .c-i-wrapper {
    height: 141px;
    padding: 0px 25px;
    display: flex;
    /* border-top: 1px solid #e6e6e6; */
  }

  .nuj span {
    font-size: 13px;
    font-weight: bold;
    margin-right: 3px;
  }

  .verti-align {
    display: flex;
    align-items: center;
  }

  /*-------------------------------------------
     CUSTOM NAV STYLE
  ---------------------------------------------*/
  .useridsty {
    font-weight: 600;
    margin-left: 5px;
  }

  .navbar-nav .nav-link {
    padding: 0 0.5rem;
  }
  /*드롭다운 outline없애는데에 쓰임*/
  .nonoutline {
    outline: none;
  }

  /*--------------------Scrollbar-----------------------------*/
  /* width */
  ::-webkit-scrollbar {
    width: 0.4em;
  }

  /* Track */
  ::-webkit-scrollbar-track {
    background: #f1f1f1;
  }

  /* Handle */
  ::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.2);
    boarder-radius: 4px;
  }

  /* Handle on hover */
  ::-webkit-scrollbar-thumb:hover {
    background: #555;
  }

  /*
      DEMO STYLE
  */

  @import "https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700";
  body {
    font-family: "Poppins", sans-serif;
    background: #fafafa;
  }

  p {
    font-family: "Poppins", sans-serif;
    font-size: 1.1em;
    font-weight: 300;
    line-height: 1.7em;
    color: #999;
  }

  a,
  a:hover,
  a:focus {
    cursor: pointer;
    color: inherit;
    text-decoration: none;
    transition: all 0.3s;
  }

  .navbar:nth-child(2) {
    margin-top: 1px;
    padding: 10px 15px;
  }

  .navbar {
    /* position:fixed;
    width:inherit; */

    padding: 10px;
    background: #fff;
    border: none;
    border-radius: 0;
    box-shadow: 1px 1px 3px rgba(0, 0, 0, 0.1);
  }

  .navbar-btn {
    box-shadow: none;
    outline: none !important;
    border: none;
  }

  .line {
    width: 100%;
    height: 1px;
    border-bottom: 1px dashed #ddd;
    margin: 40px 0;
  }

  .myflex-column {
    display: flex;
    flex-direction: column;
  }

  /* ---------------------------------------------------
      SIDEBAR STYLE
  ----------------------------------------------------- */
  .menulist {
    flex-grow: 1;
    overflow: auto;
  }

  .menulist-header-icon {
    flex-grow: 1;
    justify-content: flex-end;
    display: flex;
  }

  .menulist-header-icon a {
    display: flex;
    text-decoration: none;
    width: 17px;
  }
  .wrapper {
    display: flex;
    width: 100%;
  }

  #sidebar {
    width: 250px;
    position: fixed;
    top: 0;
    left: 0;
    height: 100vh;
    z-index: 999;
    background: #7386D5;
    color: #fff;
    transition: all 0.3s;
  }

  #sidebar.active {
    margin-left: -250px;
  }

  #sidebar .sidebar-header {
    padding: 20px;
    background: #6d7fcc;
  }

  #sidebar ul.components {
    padding: 20px 0;
    /* border-bottom: 1px solid #47748b; */
    margin: 0px;
  }

  #sidebar ul .menulist-header:nth-child(1) {
    padding: 0px 15px 0px 10px;
  }

  #sidebar ul .menulist-header {
    color: #fff;
    margin: 0px;
    padding: 20px 15px 0px 10px;
    font-weight: bold;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
  }

  #sidebar ul li a {
    padding: 10px;
    font-size: 15px;
    display: block;
  }

  #sidebar ul li a:hover {
    color: #7386D5;
    background: #fff;
  }

  /* #sidebar ul li.active > a,
  a[aria-expanded="true"] {
    color: #fff;
  } */

  #sidebar ul li a li {
    overflow: hidden;
    text-overflow: ellipsis;
    margin-right: 30px;
  }

  .dropdown-toggle {
    position: relative;
  }

  .dropdown-toggle::after {
    display: block;
    position: absolute;
    top: 50%;
    right: 20px;
    transform: translateY(-50%);
  }

  ul ul a {
    font-size: 0.9em !important;
    padding-left: 30px !important;
    background: #6d7fcc;
  }

  ul.CTAs {
    padding: 20px;
  }

  ul.CTAs a {
    text-align: center;
    font-size: 0.9em !important;
    display: block;
    border-radius: 5px;
    margin-bottom: 5px;
  }

  a.download {
    background: #fff;
    color: #7386D5;
  }

  a.article,
  a.article:hover {
    background: #6d7fcc !important;
    color: #fff !important;
  }

  /* ---------------------------------------------------
      CONTENT STYLE
  ----------------------------------------------------- */

  #m-wrapper {
    word-break: break-all;
    width: calc(100% - 250px);
    min-height: 100vh;
    transition: all 0.3s;
    position: absolute;
    top: 0;
    right: 0;
  }

  #m-wrapper.active {
    width: 100%;
  }
  /*-------------------------*/
  .navbar-nav .dropdown-menu {
    position: absolute;
  }

  .navbar-nav {
    flex-direction: row;
  }

  /* ---------------------------------------------------
      MEDIAQUERIES
  ----------------------------------------------------- */

  @media (max-width: 768px) {
    #sidebar {
      margin-left: -250px;
    }
    #sidebar.active {
      margin-left: 0;
    }
    #m-wrapper {
      width: 100%;
    }
    #m-wrapper.active {
      width: calc(100% - 250px);
    }
    #sidebarCollapse span {
      display: none;
    }
    .useridsty {
      display: none;
    }
  }
</style>

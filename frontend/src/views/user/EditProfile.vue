<template>
    <main class="mainwrapper">
        <div class="h-inherit cetered-align" style="flex-direction:column;">
            <h4>회원정보 수정</h4>
          <div class="info-wrapper">
            <div class="hori-align" style="margin: 24px 0px 35px 0;">
              <svg
                style="fill:#e6e6e6;"
                xmlns="http://www.w3.org/2000/svg"
                width="100"
                height="100"
                viewBox="0 0 24 24"
              >
                <path
                  d="M12 0c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm0 22c-3.123 0-5.914-1.441-7.749-3.69.259-.588.783-.995 1.867-1.246 2.244-.518 4.459-.981 3.393-2.945-3.155-5.82-.899-9.119 2.489-9.119 3.322 0 5.634 3.177 2.489 9.119-1.035 1.952 1.1 2.416 3.393 2.945 1.082.25 1.61.655 1.871 1.241-1.836 2.253-4.628 3.695-7.753 3.695z"
                ></path>
              </svg>
            </div>
           <table>
              <tbody>
                <tr>
                  <th>
                    <label for="name">이름</label>
                  </th>
                  <td>
                    <b-input type="text" name="name"></b-input>
                  </td>
                </tr>
                <tr>
                  <th>
                    <label for="name">이메일</label>
                  </th>
                  <td>
                   <b-input type="email" name="email" disabled="true" value="username@test.com"></b-input>
                  </td>
                </tr>
                <tr>
                  <th>
                    <label for="name">전화번호</label>
                  </th>
                  <td>
                    <b-input type="text" name="phone"></b-input>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <b-button style="margin:15px;" variant="primary">수정</b-button>
        </div>
    </main>
</template>
<script>
export default {
    name:'EditUser'
}
</script>
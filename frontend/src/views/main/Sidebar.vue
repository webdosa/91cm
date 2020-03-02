<template>
  <div>
    <nav>
      <div class="sidebar-header">
        <!--        <img style="width: 100%;" src="../assets/images/naver_logo.png">-->
        <h3>91CM</h3>
      </div>
      <div class="myflex-column menulist">
        <ul class="list-unstyled components">
          <div class="menulist-header">
            <span>Channels</span>
            <div class="menulist-header-icon">
              <a v-b-modal.channel-create>
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  width="24"
                  height="24"
                  viewBox="0 0 24 24"
                  style="fill: white;/* height: 100%; */"
                >
                  <path
                    d="M12 2c5.514 0 10 4.486 10 10s-4.486 10-10 10-10-4.486-10-10 4.486-10 10-10zm0-2c-6.627 0-12 5.373-12 12s5.373 12 12 12 12-5.373 12-12-5.373-12-12-12zm6 13h-5v5h-2v-5h-5v-2h5v-5h2v5h5v2z"
                  ></path>
                </svg>
              </a>
            </div>
          </div>
          <li>
            <a v-b-toggle.collapse-1 class="dropdown-toggle">Channels</a>
            <b-collapse id="collapse-1">
              <ul class="list-unstyled">
                <li>
                  <a>cha1</a>
                </li>
                <li>
                  <a>cha2</a>
                </li>
                <li>
                  <a>cha3</a>
                </li>
                <li>
                  <a>cha1</a>
                </li>
                <li>
                  <a>cha1</a>
                </li>
                <li>
                  <a>cha1</a>
                </li>
                <li>
                  <a>cha1</a>
                </li>
                <li>
                  <a>cha1</a>
                </li>
                <li>
                  <a>cha1</a>
                </li>
                <li>
                  <a>cha1</a>
                </li>
                <li>
                  <a>cha1</a>
                </li>
              </ul>
            </b-collapse>
          </li>
          <div class="menulist-header">
            <span>Users</span>
          </div>
          <li>
            <a>Users</a>
          </li>
        </ul>
      </div>
    </nav>
  </div>
</template>

<script>
export default {
  name: 'Sidebar'
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
  .s-coll-style > p {
    margin-bottom: 5px;
  }
  .s-coll-style > li {
    font-size: 14px;
  }

  .chat-message .icon {
    padding-right: 10px;
  }
  /*----------------------
  MainContents
  ------------------------*/

  .nuj span {
    font-size: 13px;
    font-weight: bold;
    margin-right: 3px;
  }

  /*-------------------------------------------
     CUSTOM NAV STYLE
  ---------------------------------------------*/
  .navbar-nav .nav-link {
    padding: 0 0.5rem;
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
  ul.CTAs a {
    text-align: center;
    font-size: 0.9em !important;
    display: block;
    border-radius: 5px;
    margin-bottom: 5px;
  }
  .navbar-nav .dropdown-menu {
    position: absolute;
  }

  /* ---------------------------------------------------
      MEDIAQUERIES
  ----------------------------------------------------- */

  @media (max-width: 768px) {
    #sidebarCollapse span {
      display: none;
    }
  }
</style>

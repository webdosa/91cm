<template>
  <div class="container">
    <div class="row py-5 mt-4 align-items-center">
      <!-- For Demo Purpose -->
      <div class="col-md-5 pr-lg-5 mb-5 mb-md-0">
        <img src="https://res.cloudinary.com/mhmd/image/upload/v1569543678/form_d9sh6m.svg" alt=""
             class="img-fluid mb-3 d-none d-md-block">
        <h1>91CM Sing Up</h1>
                <p class="font-italic text-muted mb-0">협업 메신저</p>
        <!--        <p class="font-italic text-muted">Snippet By <a href="https://bootstrapious.com" class="text-muted">-->
        <!--          <u>Bootstrapious</u></a>-->
        <!--        </p>-->
      </div>

      <!-- Registeration Form -->
      <div class="col-md-7 col-lg-6 ml-auto">
        <form action="#">
          <div class="row">

            <!-- Email -->
            <div class="input-group col-lg-12 mb-4">
              <div class="input-group-prepend">
                            <span class="input-group-text bg-white px-4 border-md border-right-0">
                               <i class="im im-mail"></i>
                            </span>
              </div>
              <input id="firstName" type="text" name="email" placeholder="Email"
                     class="form-control bg-white border-left-0 border-md">
            </div>
            <!-- Password -->
            <div class="input-group col-lg-12 mb-4">
              <div class="input-group-prepend">
                            <span class="input-group-text bg-white px-4 border-md border-right-0">
                                <i class="im im-key"></i>
                            </span>
              </div>
              <input id="password" type="password" name="password" placeholder="Password"
                     class="form-control bg-white border-left-0 border-md">
            </div>

            <!-- Password Confirmation -->
            <div class="input-group col-lg-12 mb-4">
              <div class="input-group-prepend">
                            <span class="input-group-text bg-white px-4 border-md border-right-0">
                                <i class="im im-key"></i>
                            </span>
              </div>
              <input id="passwordConfirmation" type="text" name="passwordConfirmation" placeholder="Confirm Password"
                     class="form-control bg-white border-left-0 border-md">
            </div>
            <!-- Name -->
            <div class="input-group col-lg-12 mb-4">
              <div class="input-group-prepend">
                            <span class="input-group-text bg-white px-4 border-md border-right-0">
                               <i class="im im-user-male"></i>
                            </span>
              </div>
              <input id="lastName" type="text" name="name" placeholder="Name"
                     class="form-control bg-white border-left-0 border-md">
            </div>
            <!-- phone -->
            <div class="input-group col-lg-12 mb-4">
              <div class="input-group-prepend">
                            <span class="input-group-text bg-white px-4 border-md border-right-0">
                               <i class="im im-mobile"></i>
                            </span>
              </div>
              <input id="email" type="email" name="phone" placeholder="Phone"
                     class="form-control bg-white border-left-0 border-md">
            </div>

            <!-- Phone Number -->
<!--            <div class="input-group col-lg-12 mb-4">-->
<!--              <div class="input-group-prepend">-->
<!--                            <span class="input-group-text bg-white px-4 border-md border-right-0">-->
<!--                                <i class="fa fa-phone-square text-muted"></i>-->
<!--                            </span>-->
<!--              </div>-->
<!--              <select id="countryCode" name="countryCode" style="max-width: 80px"-->
<!--                      class="custom-select form-control bg-white border-left-0 border-md h-100 font-weight-bold text-muted">-->
<!--                <option value="">+12</option>-->
<!--                <option value="">+10</option>-->
<!--                <option value="">+15</option>-->
<!--                <option value="">+18</option>-->
<!--              </select>-->
<!--              <input id="phoneNumber" type="tel" name="phone" placeholder="Phone Number"-->
<!--                     class="form-control bg-white border-md border-left-0 pl-3">-->
<!--            </div>-->




            <!-- Submit Button -->
            <div class="form-group col-lg-12 mx-auto mb-0">
              <a href="#" class="btn btn-primary btn-block py-2">
                <span class="font-weight-bold">가입</span>
              </a>
            </div>

            <!-- Divider Text -->
<!--            <div class="form-group col-lg-12 mx-auto d-flex align-items-center my-4">-->
<!--              <div class="border-bottom w-100 ml-5"></div>-->
<!--              <span class="px-2 small text-muted font-weight-bold text-muted">OR</span>-->
<!--              <div class="border-bottom w-100 mr-5"></div>-->
<!--            </div>-->

<!--            &lt;!&ndash; Social Login &ndash;&gt;-->
<!--            <div class="form-group col-lg-12 mx-auto">-->
<!--              <a href="#" class="btn btn-primary btn-block py-2 btn-facebook">-->
<!--                <i class="fa fa-facebook-f mr-2"></i>-->
<!--                <span class="font-weight-bold">Continue with Facebook</span>-->
<!--              </a>-->
<!--              <a href="#" class="btn btn-primary btn-block py-2 btn-twitter">-->
<!--                <i class="fa fa-twitter mr-2"></i>-->
<!--                <span class="font-weight-bold">Continue with Twitter</span>-->
<!--              </a>-->
<!--            </div>-->

            <!-- Already Registered -->
            <br><br><br>
            <div class="text-center w-100">
              <p class="text-muted font-weight-bold">로그인 페이지로 <a href="#" class="text-primary ml-2">Login</a>
              </p>
            </div>

          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
  // For Demo Purpose [Changing input group text on focus]
  // $(function () {
  //   $('input, select').on('focus', function () {
  //     $(this).parent().find('.input-group-text').css('border-color', '#80bdff');
  //   });
  //   $('input, select').on('blur', function () {
  //     $(this).parent().find('.input-group-text').css('border-color', '#ced4da');
  //   });
  // });

  export default {
    name: "FormSignUp"
  }
</script>

<style scoped>
  /*
  *
  * ==========================================
  * CUSTOM UTIL CLASSES
  * ==========================================
  *
  */

  .border-md {
    border-width: 2px;
  }

  .btn-facebook {
    background: #405D9D;
    border: none;
  }

  .btn-facebook:hover, .btn-facebook:focus {
    background: #314879;
  }

  .btn-twitter {
    background: #42AEEC;
    border: none;
  }

  .btn-twitter:hover, .btn-twitter:focus {
    background: #1799e4;
  }


  /*
  *
  * ==========================================
  * FOR DEMO PURPOSES
  * ==========================================
  *
  */

  .form-control:not(select) {
    padding: 1.5rem 0.5rem;
  }
  .form-control::placeholder {
    color: #ccc;
    font-weight: bold;
    font-size: 0.9rem;
  }
  .form-control:focus {
    box-shadow: none;
  }

</style>

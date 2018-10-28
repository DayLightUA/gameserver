

function openLoginPage(event){
            mainContainerDisplayProperty = $('#mainContainer').css("display");
            $('#mainContainer').css("display", "none");
            $('#logRegForm').empty();
            $('#logRegForm').append(
    '<div class="modal-dialog text-center">'+
        '<div class="col-sm-8 main-section">'+
            '<div class="modal-content">'+
                '<div class="col-12 user-img">'+
                    '<img src="./img/userImg.png"/>'+
                '</div>'+
                '<form class="col-12">'+
                    '<div class="form-group">'+
                        '<input id="login" type="text" class="form-control" placeholder="Введіть логін/ел.пошту">'+
                    '</div>'+
                    '<div class="form-group">'+
                        '<input id="password" type="password" class="form-control" placeholder="Введіть пароль">'+
                    '</div>'+
                    '<button type="submit" class="btn login-btn" id="loginSubmitBTN"><i class="fas fa-sign-in-alt"></i>Увійти</button>'+
                '</form>'+
                '<div class="col-12 forgot-pass">'+
                    '<a href="#" id="forgotPass">Забули пароль?</a>'+
                '</div>'+
            '</div>'+
        '</div>'+
    '</div>');
            $('#loginSubmitBTN').click(login);
            $('#forgotPass').click(restorePass);
        };
        
function login(event){
//            var loginForm = {};
//            loginForm.nickName = $('form #login').val();
//            loginForm.password = $('form #password').val();
//            loginForm = accountRequests.loginRequest(loginForm);
//
//            if (loginForm.isLoged){
//                 changeNavBarNReturnToPrevious(loginForm.nickName);
//            } else if (loginForm.uncorectNickName){
//                uncorectInput('form #login', "Введіть коректний логін");
//            } else if (loginForm.uncorectPassword){
//                uncorectInput('form #password', "Пароль не вірний");
//            };


}
        
function restorePass(event){
            $('#logRegForm').empty();
            $('#logRegForm').append(
    '<div class="modal-dialog text-center">'+
        '<div class="col-sm-8 main-section">'+
            '<div class="modal-content">'+
                '<div class="col-12 user-img">'+
                    '<img src="./img/userImg.png"/>'+
                '</div>'+
                '<form class="col-12">'+
                    '<div class="form-group">'+
                        '<input id="email" type="text" class="form-control" placeholder="Електронна адреса">'+
                    '</div>'+
                    '<button type="submit" class="btn login-btn" id="forgotPassSubmitBTN"><i class="fas fa-envelope"></i>Надіслати</button>'+
                '</form>'+
            '</div>'+
        '</div>'+
    '</div>');
            $('#forgotPassSubmitBTN').click(restorePassRequest);
        };
        
function restorePassRequest(event){
            var restorePassForm = {};
            restorePassForm.email = $('form #email').val();
            restorePassForm.nickName = $('form #email').val();
            restorePassForm.date = Date.now();
            restorePassForm = accountRequests.restorePassRequest(restorePassForm);
            if (restorePassForm.userNotFoun){
                uncorectInput('form #email', "Такого користувача не існує");
            } else {
                alert("Вам надіслано листа з новим паролем!!!  \n Тепер увійдіть!!!");
                login({});
            }
        };
        
function openRegistrationPage(event){
            $('#logRegForm').empty();
            $('#logRegForm').append(
    '<div class="modal-dialog text-center">'+
        '<div class="col-sm-8 main-section">'+
            '<div class="modal-content">'+
                '<div class="col-12 user-img">'+
                    '<img src="./img/userImg.png"/>'+
                '</div>'+
                '<form class="col-12">'+
                    '<div class="form-group">'+
                        '<input id="name" type="text" class="form-control" placeholder="Ім\'я">'+
                    '</div>'+
                    '<div class="form-group">'+
                        '<input id="email" type="text" class="form-control" placeholder="Електронна адреса">'+
                    '</div>'+
                    '<div class="form-group">'+
                        '<input id="nickName" type="text" class="form-control" placeholder="Логін">'+
                    '</div>'+
                    '<div class="form-group">'+
                        '<input id="pass1" type="password" class="form-control" placeholder="Пароль">'+
                    '</div>'+
                    '<div class="form-group">'+
                        '<input id="pass2" type="password" class="form-control" placeholder="Пароль ще раз">'+
                    '</div>'+
                    '<button type="submit" class="btn login-btn" id="registrationSubmitBTN"><i class="fas fa-sign-in-alt"></i>Увійти</button>'+
                '</form>'+
            '</div>'+
        '</div>'+
    '</div>');
            $('#registrationSubmitBTN').click(registration);
        };
        
function registration(event){
            var registrationForm = {};
            if ($('form #pass1').val() == $('form #pass2').val()){
                registrationForm.nickName = $('form #nickName').val();
                registrationForm.password = $('form #pass1').val();
                registrationForm.email = $('form #email').val();
                registrationForm = accountRequests.registrationRequest(registrationForm);
                if (registrationForm.isRegistered){
                    alert("Вітаємо!!! Ви успішно зареєструвались!!! \n Тепер увійдіть!!!")
                    login({});
                } else if (registrationForm.nickNameExist){
                    uncorectInput('form #nickName', "Такий логін вже існує");
                } else if (registrationForm.emailExist){
                    uncorectInput('form #email', "Ви вже зареєструвались");
                };
            } else uncorectInput('form #pass2', "Паролі не співпадають");
          
        }
        
function uncorectInput(selector, placeholder){
            $(selector).placeholder = placeholder;
            $(selector).addClass("uncorrectInput");
        }

function changeNavBarNReturnToPrevious(nickName){
    $('#userPanel').empty();
    $('#logRegForm').empty();
    $('#mainContainer').css("display",  mainContainerDisplayProperty);
    $('#userPanel').append(''+
            '<div class="row-flex-end col-3 dropdown">'+
                '<h3 class="nickName">'+nickName+'\ </h3>'+
                '<a class="nav-link" href="#" id="userOptions" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-user-cog"></i>\ </a>'+
                '<div class="dropdown-menu" aria-labelledby="userOption">'+
                    '<a class="dropdown-item" href="#">Змінити пароль</a>'+
                    '<a class="dropdown-item" href="#">Вийти</a>'+
                '</div>'+
            '</div>'
    );
}

function setLogNRegBTN(){
    $('#userPanel').empty();
    $('#userPanel').append(''+
        '<button class="btn login-btn" id="loginBTN" type="button"><i class="fas fa-sign-in-alt"></i>Увійти</button>'+
        '<button class="btn" id="registrationBTN" type="button"><i class="fas fa-user-plus"></i>Регістрація</button>'
    );
    $('#loginBTN').click(openLoginPage);
    $('#registrationBTN').click(openRegistrationPage);
}

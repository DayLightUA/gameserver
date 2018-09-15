var accountRequests = {
            loginRequest: function (form){ 
                var request = {};
                request.form = form;
                request.data = form;
                request.urlAppend = "/login";
                request.success = function (data){
                    form.isLoged = true;
                    form.password = "********";
                    localStorage.setItem("userToken", data.userToken)
                    localStorage.setItem("userRole", data.userRole)
                };
                request.error = function (error){
                    form.password = "";
                    form.isLoged = false;
                    if (error.message == "Nickname is uncorect"){
                        form.uncorectNickName = false;
                    } else if (error.message == "Password is uncorect"){
                        form.uncorectPassword = false;
                    }
                };
                return ajaxRequest(request);
            },
    
            registrationRequest: function (form){ 
                var request = {};
                request.urlAppend = "/registration";
                request.form = form;
                request.data = form;
                request.success = function (data){
                    form.isRegistered = true;
                    form.password = "";
                };
                request.error = function (error){
                    form.password = "";
                    form.isLoged = false;
                    if (error.message == "This nickname already exist"){
                        form.nickNameExist = true;
                    } else if (error.message == "This email already exist"){
                        form.emailExist = true;
                    }
                };
                return ajaxRequest(request);
            },
    
            restorePassRequest: function (form){ 
                var request = {};
                request.urlAppend = "/restore-password";
                request.form = form;
                request.data = form;
                request.success = function (data){
                    form.restorePassword = true;
                    form.message = "New password send to your eMail" + data.eMail;
                };
                request.error = function (error){
                    if (error.message == "Email not exist"){
                        form.restorePassword = true;
                    } else form.message = error.message;
                }
                return ajaxRequest(request); 
            }
        }

var dataRequests = {
    loadNews: function(){
        var request = {};
        request.urlAppend = "/get-news"
        request.form = {};
        request.success = simpleDataResponse(data);
        request.error = simpleErrorResponse(error);
        return ajaxRequest(request);
    },
    loadPost: function(postId){
        var request = {};
        request.urlAppend = "/get-post"
        request.form = {};
        request.data = postId;
        request.success = simpleDataResponse(data);
        request.error = simpleErrorResponse(error);
        return ajaxRequest(request, localStorage.getItem("userToken"));
    },
    loadCategories: function(pageNumber){
        var request = {};
        request.urlAppend = "/get-categories"
        request.form = {};
        request.data = pageNumber;
        request.success = simpleDataResponse(data);
        request.error = simpleErrorResponse(error);
        return ajaxRequest(request, localStorage.getItem("userToken"));
    },
    loadSelectCategory: function(categoryId, pageNumber){
        var request = {};
        request.urlAppend = "/get-category"
        request.form = {};
        request.data = {"categoryId":categoryId, "pageNumber":pageNumber};
        request.success = simpleDataResponse(data);
        request.error = simpleErrorResponse(error);
        return ajaxRequest(request, localStorage.getItem("userToken"));
    },
    loadSelectSubCategory:function(categoryId, pageNumber){
        var request = {};
        request.urlAppend = "/get-subCategory"
        request.form = {};
        request.data = {"categoryId":categoryId, "pageNumber":pageNumber};
        request.success = simpleDataResponse(data);
        request.error = simpleErrorResponse(error);
        return ajaxRequest(request, localStorage.getItem("userToken"));
    }
    
};

var deleteRequest = {
    deleteCategory: function (data){
        var request = {};
        request.urlAppend = data.cardType+"/delete";
        request.form = data;
        request.data = data.cardId;
        request.success = function(data){$('#'+form.containerID+'/'+form.cardId).parent().remove();};
        request.error = function(error){alert(form.cardType+" with Id:"+form.cardId+" not delited:"+error.message)};
        ajaxRequest(request, localStorage.getItem("userToken"));
    }
};


function ajaxRequest(request, token){
    var form = request.form;
    if (token == null){
        request.urlAppend = "public"+request.urlAppend;
    }
    $.ajax({
        url: host + request.urlAppend,
        contentType: "aplication/JSON",
        type: "POST",
        headers:{"token":"Bearer "+token},
        data: JSON.stringify(request.data),
        success: request.success,
        error: request.error
    });
    return form;
}

function simpleDataResponse(data){
    form.data = data;
};
function simpleErrorResponse(error){
    form.message = error.message;
}
var categoriesPage = 0;
var subCategoriesPage = 0;
var postsPage = 0;
function loadingFirstPage(){
    hideAllInMainContainer();
    createScrollMenu("scrolledMenu");
    createcategories("categoriesContainer");
}

function createScrollMenu(containerID){
    $('#'+containerID).empty();
    $('#'+containerID).append(''+
        '<div class="carusel slide" id="slides" data-ride="carusel">'+
            '<ul class="carusel-indicators">'+
                '<li data-target="#slides" data-slide-to="0" class="active"></li>'+
                '<li data-target="#slides" data-slide-to="1"></li>'+
                '<li data-target="#slides" data-slide-to="2"></li>'+
            '</ul>'+
            '<div class="carusel-inner">'+
            '</div>'+
        '</div>'                    
    );
    var news = dataRequests.loadNews().data;
    fillScrollMenu(containerID, news);
    $('#'+containerID).css("display", scrolledMenuDisplayProperty);
};

function fillScrollMenu(containerID, news){
    var newsSlider = $('#'+containerID+' div .carusel-inner');
    newsSlider.empty();
    for (var post in news){
        newsSlider.append(''+
        '<div class="carusel-item" id="caruselPostID'+'/'+post.postId+'">'+
            '<img src="'+post.newsPostImg+'">'+
            '<div class="carusel-capture">'+
                '<h3 class="caruselTitle">'+post.title+'</h3>'+
                '<h6 class="caruselPost">'+post.post+'</h6>'+
            '</div>'+
        '</div>'
        );
        $('#caruselPostID'+'/'+post.postId).click(post.postId, openPost);
    };
};

function openPost(even){
    var post = dataRequests.loadPost(event);
    hideAllInMainContainer();
    fillPostContainer(post);
    $('#postContainer').css("display", postContainerDisplayProperty);
};

function fillPostContainer(post){
    $('#postContainer').empty();
    $('#postContainer').append(''+
        '<div class="post">'+
            '<div id="postTop">'+
                '<button class="btn" id="beckFromPost"><h5>Назад</h5></button>'+
                '<h3 class="postTitle">'+post.title+'</h3>'+
                '<a href="">Автор: '+post.author+'</a>'+
            '</div>'+
            '<div id="postCentr"><p class="post-text">'+post.text+'</p></div>'+
            '<div id="postBotom">'+
                '<button class="btn" id="likePost"><h5><i class="fas fa-thumbs-up"></i></h5></button>'+
                '<button class="btn" id="dislikePost"><h5><i class="fas fa-thumbs-down"></i></h5></button>'+
            '</div>'+
            '<div id="postComments">'+
            '</div>'+
        '</div>'
    );
    addPostFuturesForRole(post);
};

function addPostFuturesForRole(post){
    $('#beckFromPost').click("post", backFrom);
    $('#likePost').click(post.id, likePost);
    $('#dislikePost').click(post.id, dislikePost);
    if (localStorage.getItem('userRole') == roleUser){
        showComments(post);
    }
    if (localStorage.getItem('userRole') == roleAdmin){
        showComments(post);
        addAdminBtns(post);
    }
};

function createcategories(containerID){
    categoriesPage = 0;
    var categories = dataRequests.loadcategories(categoriesPage).data;
    $('#'+containerID).empty();
    $('#'+containerID).append('<div class="row padding" id="categoriesCardContainer"></div>');
    addCards("categoriesCardContainer", categories, null, "category");
    $('#'+containerID).css("display", categoriesContainerDisplayProperty);
};

function addCards(containerID, topicId, cardsData, cardType){
    addCardsIteration(containerID, cardsData, cardType);
    if (cardType == "subcategory" || cardType == "post"){addNextNBackBtn(containerID, cardsData, topicId, cardType);}
    if (localStorage.getItem("userRole") == roleAdmin || (localStorage.getItem("userRole") == roleUser && cardType == "post")){
        $('#'+containerID).append(''+
            '<div class="col-md-4">'+
                '<div class="card" id="'+containerID+'-addBtn">'+
                    
                '</div>'+
            '</div>'
        );
        $('#'+containerID+'-addBtn').click({"containerId": containerId, "cardType": cardType}, addNewCard)
    }
};

function addCardsIteration(containerID, cardsData, cardType){
    for (var cardData in cardsData){
        $('#'+containerID).append(''+
            '<div class="col-md-4">'+
                '<div class="card" id="'+containerID+'/'+cardData.id+'">'+
                    '<h4 class="card-title">'+cardData.title+'</h4>'+
                    '<ul class="card-item-list"></ul>'+
                '</div>'+
            '</div>'
        );
        for (var itemTitle in cardData.itemTitles){
            $('#'+containerID+' div div .card-item-list').append(''+
                '<li class="card-item"><p>'+itemTitle+'</p></li>'
            );
        }
        $('#'+containerID).click({"cardId": cardData.id, "cardType": cardType}, openCard);
        if (localStorage.getItem("userRole") == roleAdmin){
            $('#'+containerID+cardData.id).append(''+
                '<button class="btn delite-card-btn">Видалити</button>'                                                   
            );
            $('#'+containerID+cardData.id).children('.delite-card-btn').click({"containerID":containerID, "cardId":cardData.id, "cardType":cardType}, deleteCard);
        }
    } 
}

function openCard(event){
    if (event.cardType == "category"){
        openCategory(event.cardId);
    }
    else if (event.cardType == "subcategory"){
        openSubCategory(event.cardId);
    }
    else if (event.cardType == "post"){
        openPost(event.cardId);
    }
}
function openCategory(categoryId){
    subCategoriesPage = 0;
    var cardsData = dataRequests.loadSelectCategory(categoryId, subCategoriesPage);
    hideAllInMainContainer();
    $('#categoryContainer').empty();
    $('#categoryContainer').append('<div class="row padding" id="categoryCardContainer"></div>');
    addCards("categoryCardContainer", cardsData, categoryId, "subcategory");
    $('#categoryContainer').css("display", categoryContainerDisplayProperty);
};
function openSubCategory(subCategoryId){
    postsPage = 0;
    var cardsData = dataRequests.loadSubCategory(subCategoryId, postsPage);
    hideAllInMainContainer();
    $('#subCategoryContainer').empty();
    $('#subCategoryContainer').append('<div class="row padding" id="subCategoryCardContainer"></div>');
    addCards("subCategoryCardContainer", cardsData, subCategoryId, "post");
    $('#subCategoryContainer').css("display", subCategoryContainerDisplayProperty);
}
        

function deleteCard(event){
    deleteRequest.deleteCategory(event.cardId);
};

function addNewCard(event){
    alert("not active now!!!")
};

function showComments(post){
    if (post.comments != null){
        for (var comment in post.comments){
            $('#postComments').append(''+
                '<div class="commentContainer col-10" id="commentId/'+comment.Id+'">'+
                    '<div class="summaryComentInf">'+
                        '<h3>Від:'+comment.author+'</h3>'+
                        '<h6>коли:'+coment.pubTime+'</h6>'+
                    '</div>'+
                    '<div class="comment">'+
                        '<p class="comment">'+comment.text+'</p>'+
                    '</div>'+
                '</div>'  
            );
        }
    }
};

function addAdminBtnsToPost(post){
    $('#postContainer').children('.pos').children('.postBotom').append(''+
        '<button class="btn modify-btn" id="postModifyBtn">Редагувати</button>'+
        '<button class="btn delete-btn" id="postDeleteBtn">Видалити</button>'
    );
    $('#postModifyBtn').click(post, modifyPost);
    $('#postDeleteBtn').click(post, deletePost);
    if (post.comments != null){
        for (var comment in post.comments){
            $('#commentId/'+comment.Id).append(''+
                '<div class="admin-btns col-10">'+
                    '<button class="btn modify-btn" id="commentModifyBtn">Редагувати</button>'+
                    '<button class="btn delete-btn" id="commentDeleteBtn">Видалити</button>'+
                '</div>'  
            );
            $('#commentModifyBtn').click({"post":post, "commentId":comment.Id}, modifyComment);
            $('#commentDeleteBtn').click({"post":post, "commentId":comment.Id}, deleteComment);
        }
    }
};

function modifyPost(event){ alert("post:"+event.id+" not Modifyed, function not work")};
function deletePost(event){ alert("post:"+event.id+" not Deleted, function not work")};
function modifyComment(event){alert("comment:"+event.commentId+" not Modifyed, function not work")};
function deleteComment(event){alert("comment:"+event.commentId+" not Deleted, function not work")};

function addNextNBackBtn(containerID, cardsData, topicId, cardType){
    var divTopNBotomBTNs = '<div class="topBtns">'+
            '<button class="btn back-btn"><i class="fas fa-arrow-left"></i> Назад</button>'+
            '<button class="btn next-btn">Показати наступні <i class="fas fa-arrow-right"></i></button>'+
        '</div>';
    var container = $('#'+containerID).parent();
    container.prepend(divTopNBotomBTNs);
    container.parent().append(divTopNBotomBTNs);
    container.children('.topBtns .back-btn').click(cardType, backFrom);
    if (cardsData[cardsData.length -1].last == false) {
        container.children('.topBtns .next-btn').click({"containerID":containerID, "cardsData":cardsData, "topicId":topicId, "cardType":cardType}, loadNext);
    } else  container.children('.topBtns .next-btn').remove();
};

function backFrom(event){
    if (event == "category"){
        $('#scrolledMenu').css("display", scrolledMenuDisplayProperty);
        $('#categoriesContainer').css("display", categoriesContainerDisplayProperty);
        categoryContainerDisplayProperty = $('#categoryContainer').css("display");
        $('#categoryContainer').css("display", "none");
    }
    if (event == "subcategory"){
        $('#categoryContainer').css("display", categoryContainerDisplayProperty);
        subCategoryContainerDisplayProperty = $('#subCategoryContainer').css("display");
        $('#subCategoryContainer').css("display", "none");
    } 
    if (event == "post"){
        $('#subCategoryContainer').css("display", subCategoryContainerDisplayProperty);
        $('#postContainer').css("display", "none");
    } 
};

function loadNext(event){
    if (event.cardType == "category"){
        categoriesPage++;
        event.cardsData = dataRequests.loadcategories(categoriesPage);
        addCardsIteration(event.containerID, event.cardsData, event.cardType);
    }
    if (event.cardType == "subcategory"){
        subCategoriesPage++;
        event.cardsData = dataRequests.loadSelectCategory(event.topicId, subCategoriesPage);
        addCardsIteration(event.containerID, event.cardsData, event.cardType);
    } 
    if (event.cardType == "post"){
        postsPage++;
        event.cardsData = dataRequests.loadSelectSubCategory(event.topicId, postsPage);
        addCardsIteration(event.containerID, event.cardsData, event.cardType);
    } 
};


function hideAllInMainContainer(){
    if ($('#scrolledMenu').css("display") != "none") {
        scrolledMenuDisplayProperty = $('#scrolledMenu').css("display");
        $('#scrolledMenu').css("display", "none");
    }
    if ($('#categoriesContainer').css("display") != "none"){
        categoriesContainerDisplayProperty = $('#categoriesContainer').css("display");
        $('#categoriesContainer').css("display", "none");
    } 
    if ($('#categoryContainer').css("display") != "none"){
        categoryContainerDisplayProperty = $('#categoryContainer').css("display");
        $('#categoryContainer').css("display", "none");
    } 
    if ($('#subCategoryContainer').css("display") != "none"){
        subCategoryContainerDisplayProperty = $('#subCategoryContainer').css("display");
        $('#subCategoryContainer').css("display", "none");
    }
    if ($('#postContainer').css("display") != "none"){
        postContainerDisplayProperty = $('#postContainer').css("display");
        ('#postContainer').css("display", "none");
    } 
};

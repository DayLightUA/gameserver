package pavlo.zayarnyuk.gameserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pavlo.zayarnyuk.gameserver.dto.DataResponse;
import pavlo.zayarnyuk.gameserver.dto.request.LoginRequest;
import pavlo.zayarnyuk.gameserver.dto.request.RegistrationRequest;
import pavlo.zayarnyuk.gameserver.dto.request.RestorePassRequest;
import pavlo.zayarnyuk.gameserver.dto.response.*;
import pavlo.zayarnyuk.gameserver.entity.SubCategory;
import pavlo.zayarnyuk.gameserver.service.AccountService;
import pavlo.zayarnyuk.gameserver.service.ContentService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ContentService contentService;

    @PostMapping("/registration")
    public void registration(@RequestBody RegistrationRequest request){
        accountService.registration(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){
        System.out.println(request.toString());
        LoginResponse response = accountService.login(request);
        System.out.println(response.toString());
        return response;
    }

    @PostMapping("/restore-password")
    public RestorePassResponse login(@RequestBody RestorePassRequest request){
        return accountService.restorePassword(request);
    }


    @PostMapping("/get-news")
    public List<PublicPostResponse> getNews(){
        return contentService.getNews();
    }

    @PostMapping("/get-post")
    private PublicPostResponse getPost(@RequestBody Long postId){
        return contentService.getPublicPost(postId);
    }

    @PostMapping("/get-categories")
    public List<CategoryResponse> getCategories(@RequestBody Long pageNumber){
        return contentService.getCategories(pageNumber);
    }

    @PostMapping("/get-category")
    public List<SubCategoryResponse> getCategory(@RequestParam Long categoryId, @RequestParam Long pageNumber){
        return contentService.getCategory(categoryId, pageNumber);
    }

    @PostMapping("/get-subCategory")
    public List<PublicPostResponse> getSubCategory(@RequestParam Long subCategoryId, @RequestParam Long pageNumber){
        return contentService.getSubCategory(subCategoryId, pageNumber);
    }

}

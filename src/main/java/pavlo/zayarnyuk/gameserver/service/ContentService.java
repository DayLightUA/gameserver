package pavlo.zayarnyuk.gameserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pavlo.zayarnyuk.gameserver.dto.response.PublicPostResponse;
import pavlo.zayarnyuk.gameserver.dto.response.SubCategoryResponse;
import pavlo.zayarnyuk.gameserver.entity.Category;
import pavlo.zayarnyuk.gameserver.entity.Post;
import pavlo.zayarnyuk.gameserver.entity.SubCategory;
import pavlo.zayarnyuk.gameserver.repository.AccountRepository;
import pavlo.zayarnyuk.gameserver.repository.CategoryRepository;
import pavlo.zayarnyuk.gameserver.repository.PostRepository;
import pavlo.zayarnyuk.gameserver.repository.SubCategoryRepository;
import pavlo.zayarnyuk.gameserver.dto.response.CategoryResponse;

import java.util.ArrayList;
import java.util.List;


@Service
public class ContentService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Integer STANDART_PAGE_SIZE = 12;

    public List<PublicPostResponse> getNews() {
        List<Post> news = postRepository.findByNewsStatusOrderByPublishingTimeDesc(true);
        List<PublicPostResponse> response = new ArrayList<>();
        if (news.size()>3) news = news.subList(news.size()-4, news.size()-1);
        for (Post post: news){
            response.add(new PublicPostResponse(post));
        }
        return response;
    }

    public PublicPostResponse getPublicPost(Long postId) {
        return new PublicPostResponse(postRepository.findById(postId).get());
    }

    public List<CategoryResponse> getCategories(Long pageNumber) {
        List<Category> categories = categoryRepository.findAll();
        categories = paging(categories, pageNumber);
        List<CategoryResponse> CRList = new ArrayList<>();
        for (Category category:categories){
            CRList.add(new CategoryResponse(category));
        }
        return CRList;
    }

    public List<SubCategoryResponse> getCategory(Long categoryId, Long pageNumber) {
        List<SubCategory> SCL = subCategoryRepository.findByCategoryId(categoryId);
        SCL = paging(SCL, pageNumber);
        List<SubCategoryResponse> SCRList = new ArrayList<>();
        for (SubCategory sc:SCL){
            SCRList.add(new SubCategoryResponse(sc));
        }
        return SCRList;
    }

    public List<PublicPostResponse> getSubCategory(Long subCategoryId, Long pageNumber) {
        List<Post> posts = postRepository.findBySubCategoryId(subCategoryId);
        posts = paging(posts, pageNumber);
        List<PublicPostResponse> response = new ArrayList<>();
        for (Post p:posts){
            response.add(new PublicPostResponse(p));
        }
        return response;
    }






    private <T> List<T> paging(List<T> list, Long pageNumber){
        Integer firstIndex;
        Integer lastIndex;
        if (STANDART_PAGE_SIZE*pageNumber>= list.size()) lastIndex=list.size()-1;
        else lastIndex=STANDART_PAGE_SIZE*(int)(long)pageNumber;
        firstIndex = lastIndex-12;
        if (firstIndex<0) firstIndex=0;
        return list.subList(firstIndex, lastIndex);
    }


}

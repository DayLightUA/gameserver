package pavlo.zayarnyuk.gameserver.dto.response;


import lombok.Getter;
import lombok.Setter;
import pavlo.zayarnyuk.gameserver.entity.Category;
import pavlo.zayarnyuk.gameserver.entity.SubCategory;


import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class CategoryResponse {

    private Long id;

    private String title;

    private List<SubCategoryResponse> subCategories;

    public CategoryResponse(Category category) {
        this.id = category.getId();
        this.title = category.getCategory();
        this.subCategories = new ArrayList<>();
        for (SubCategory sc:category.getSubCategories()){
            subCategories.add(new SubCategoryResponse(sc));
        }
    }
}

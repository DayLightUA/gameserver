package pavlo.zayarnyuk.gameserver.dto.response;

import lombok.Getter;
import lombok.Setter;
import pavlo.zayarnyuk.gameserver.entity.SubCategory;
import java.util.List;

@Setter @Getter
public class SubCategoryResponse {

    private Long id;

    private String title;

    private List<PostResponse> posts;

    public SubCategoryResponse(SubCategory sc) {
        this.id = sc.getId();
        this.title = sc.getSubCategory();
    }
}

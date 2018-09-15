package pavlo.zayarnyuk.gameserver.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "posts_subcategories")
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "subCategory")
    private List<Post> subCategoryPosts;

    @Column(length = 1000)
    private String subCategory;

    @ManyToOne
    private Category category;


    public SubCategory() {    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Post> getSubCategoryPosts() {
        return subCategoryPosts;
    }

    public void setSubCategoryPosts(List<Post> subCategoryPosts) {
        this.subCategoryPosts = subCategoryPosts;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}

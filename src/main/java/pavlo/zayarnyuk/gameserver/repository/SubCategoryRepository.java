package pavlo.zayarnyuk.gameserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pavlo.zayarnyuk.gameserver.entity.SubCategory;


import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>{
    List<SubCategory> findByCategoryId(Long categoryId);
}

package pavlo.zayarnyuk.gameserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pavlo.zayarnyuk.gameserver.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}

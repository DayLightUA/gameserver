package pavlo.zayarnyuk.gameserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pavlo.zayarnyuk.gameserver.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    List<Post> findByNewsStatusOrderByPublishingTimeDesc(Boolean status);

    List<Post> findBySubCategoryId(Long subCategoryId);
}

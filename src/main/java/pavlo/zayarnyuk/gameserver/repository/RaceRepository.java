package pavlo.zayarnyuk.gameserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pavlo.zayarnyuk.gameserver.entity.Race;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long>{
}

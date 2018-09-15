package pavlo.zayarnyuk.gameserver.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pavlo.zayarnyuk.gameserver.entity.Profession;

@Repository
public interface ProfessionRepository extends JpaRepository<Profession, Long> {
}

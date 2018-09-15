package pavlo.zayarnyuk.gameserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pavlo.zayarnyuk.gameserver.entity.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    Account findByNickName(String nickName);

    Account findByEMail(String eMail);
}

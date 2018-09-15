package pavlo.zayarnyuk.gameserver.entity;



import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "account")
    List<Character> characters;

    @OneToMany(mappedBy = "account")
    List<Post> posts;

    @Column(length = 3000)
    private String eMail;

    @Column(length = 3000)
    private String nickName;

    @Column(length = 3000)
    private String password;

    @Enumerated
    private UserRole userRole;

    private Date regDate;


}

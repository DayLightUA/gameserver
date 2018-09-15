package pavlo.zayarnyuk.gameserver.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "professions")
public class Profession{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String profession;


    @ManyToMany(mappedBy = "professions")
    private List<Race> races;

    @OneToMany(mappedBy = "profession")
    private List<Character> characters;
}

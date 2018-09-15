package pavlo.zayarnyuk.gameserver.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "characters")
public class Character{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Account account;

    @Column(length = 55, name = "char_name", unique = true, nullable = false)
    private String charName;

    private Long experience;


    @ManyToOne
    private Profession profession;


    @ManyToOne
    private Race race;

    private Long level;

    private Long maxHP;

    private Long maxMP;

    @Column(nullable = false)
    private Date saveTime;

    private Long saveHP;

    private Long saveMP;

    @ManyToMany
    private List<Item> items = new ArrayList<Item>();

    @OneToOne
    @PrimaryKeyJoinColumn
    private Equipment equipment;



}
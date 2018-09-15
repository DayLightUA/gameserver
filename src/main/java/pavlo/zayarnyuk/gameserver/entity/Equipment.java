package pavlo.zayarnyuk.gameserver.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Equipment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @MapsId
    private Character character;

    @ManyToOne
    private Item helm;

    @ManyToOne
    private Item chest;

    @ManyToOne
    private Item pants;

    @ManyToOne
    private Item boots;

    @ManyToOne
    private Item leftHand;

    @ManyToOne
    private Item rightHand;

}

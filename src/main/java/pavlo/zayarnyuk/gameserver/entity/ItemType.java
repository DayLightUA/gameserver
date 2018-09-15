package pavlo.zayarnyuk.gameserver.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Item_Types")
public class ItemType{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String value;

    @OneToMany(mappedBy = "itemType")
    private List<Item> itemList = new ArrayList<Item>();


}

package pavlo.zayarnyuk.gameserver.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter @Getter
public class Comment implements Comparable<Comment>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3000)
    private String title;

    @Column(length = 30000)
    private String text;


    @ManyToOne
    private Account account;


    @ManyToOne
    private Post post;


    private Date publishingTime;


    @Override
    public int compareTo(Comment o) {
        if (this.getPost().getId() == o.getPost().getId()){
            if (this.getPublishingTime().getTime()>o.getPublishingTime().getTime()) return 1;
            else if(this.getPublishingTime().getTime()<o.getPublishingTime().getTime()) return -1;
        }
        return 0;
    }
}

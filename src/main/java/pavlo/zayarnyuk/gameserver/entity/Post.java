package pavlo.zayarnyuk.gameserver.entity;



import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Posts")
public class Post{
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
    private SubCategory subCategory;


    private Date publishingTime;

    @OneToMany
    private List<Comment> comments;

    private Boolean newsStatus;

    private Long likes;
    private Long dislikes;

}

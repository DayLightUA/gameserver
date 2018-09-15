package pavlo.zayarnyuk.gameserver.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pavlo.zayarnyuk.gameserver.entity.Comment;
import pavlo.zayarnyuk.gameserver.entity.Post;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostResponse {

    private Long id;

    private String title;

    private String author;

    private String text;

    private List<CommentResponse> comments;

    private Long likes;

    private Long dislikes;

    public PostResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.author = post.getAccount().getNickName();
        this.text = post.getText();
        this.comments = new ArrayList<>();
        for (Comment comment: post.getComments()){
            comments.add(new CommentResponse(comment));
        }
    }

}

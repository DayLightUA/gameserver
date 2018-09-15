package pavlo.zayarnyuk.gameserver.dto.response;


import lombok.Getter;
import lombok.Setter;
import pavlo.zayarnyuk.gameserver.entity.Comment;

@Getter @Setter
public class CommentResponse {

    private Long id;

    private String author;

    private String pubTime;

    private String text;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.author = comment.getAccount().getNickName();
        this.pubTime = comment.getPublishingTime().toGMTString();
    }
}

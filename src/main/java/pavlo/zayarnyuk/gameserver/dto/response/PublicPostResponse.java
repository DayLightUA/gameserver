package pavlo.zayarnyuk.gameserver.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pavlo.zayarnyuk.gameserver.entity.Post;


@Getter
@Setter
@NoArgsConstructor
public class PublicPostResponse {

    private Long id;

    private String title;

    private String author;

    private String text;

    public PublicPostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.text = post.getText();
        this.author = post.getAccount().getNickName();
    }
}

package pavlo.zayarnyuk.gameserver.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter @Setter
public class DataResponse<T> {

    private List<T> content;

    private Integer totalPage;

    public DataResponse(List<T> content,Page page) {
        this.content = content;
        this.totalPage = page.getTotalPages();
    }
}

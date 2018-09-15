package pavlo.zayarnyuk.gameserver.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RestorePassRequest {

    @NotNull
    private String email;
}

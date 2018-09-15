package pavlo.zayarnyuk.gameserver.dto.response;


import lombok.Getter;
import lombok.Setter;
import pavlo.zayarnyuk.gameserver.entity.UserRole;

@Getter
@Setter
public class LoginResponse {

    private String userToken;

    private UserRole userRole;
}

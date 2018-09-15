package pavlo.zayarnyuk.gameserver.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pavlo.zayarnyuk.gameserver.dto.request.LoginRequest;
import pavlo.zayarnyuk.gameserver.dto.request.RegistrationRequest;
import pavlo.zayarnyuk.gameserver.dto.request.RestorePassRequest;
import pavlo.zayarnyuk.gameserver.dto.response.LoginResponse;
import pavlo.zayarnyuk.gameserver.dto.response.RestorePassResponse;
import pavlo.zayarnyuk.gameserver.entity.Account;
import pavlo.zayarnyuk.gameserver.entity.UserRole;
import pavlo.zayarnyuk.gameserver.repository.AccountRepository;
import pavlo.zayarnyuk.gameserver.security.tokenUtils.TokenTools;


import java.sql.Date;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TokenTools tokenTools;

    public Account findByNickName(String nickName){
        return accountRepository.findByNickName(nickName);
    }

    public RegistrationRequest registration(RegistrationRequest request) throws IllegalArgumentException {
        Account account = new Account();
        account.setEMail(request.getEmail());
        account.setNickName(request.getNickName());
        account.setPassword(request.getPassword());
        account.setRegDate(new Date(new java.util.Date().getTime()));
        account.setUserRole(UserRole.ROLE_USER);
        Account accFromDbByEmail = accountRepository.findByEMail(account.getEMail());
        Account accFromDbByNickName = accountRepository.findByNickName(account.getNickName());
        if (accFromDbByEmail != null){
            throwExistException("email");
            return null;
        } else if (accFromDbByNickName != null){
            throwExistException("nickName");
            return null;
        } else {
            accountRepository.save(account);
            return new RegistrationRequest();
        }
    }

    private void throwExistException(String param) throws IllegalArgumentException {
        throw new IllegalArgumentException("Account with such "+param+" exist!!!");
    }

    public LoginResponse login(LoginRequest request) {
        Account account = accountRepository.findByEMail(request.getNickName());
        LoginResponse response;
        if (account == null){
            account = accountRepository.findByNickName(request.getNickName());
        }
        if (account == null) throwNoExistException(request.getNickName());
        String password = decode(account.getPassword());
        if (request.getPassword().equals(password)) {
            response = new LoginResponse();
            response.setUserRole(account.getUserRole());
            response.setUserToken(tokenTools.tokenGenerator(account.getNickName(), account.getUserRole().name()));
        } else throw new IllegalArgumentException("Password incorrect!!!");

        return response;
    }

    private void throwNoExistException(String nickName) throws IllegalArgumentException{
        throw new IllegalArgumentException("User "+nickName+" not exist!!!");
    }

    public RestorePassResponse restorePassword(RestorePassRequest request) {
        Account account = accountRepository.findByEMail(request.getEmail());
        String responseEmail = "";
        if (account == null) throwNoExistException(request.getEmail());
        else {
            String [] emailParts = account.getEMail().split("@");
            responseEmail = account.getEMail().substring(0, 1) + "***"+emailParts[emailParts.length-1];
        }
        RestorePassResponse response = new RestorePassResponse();
        response.setEMail(responseEmail);
        return response;
    }

    private String decode(String codedString){
        return codedString;
    }
}

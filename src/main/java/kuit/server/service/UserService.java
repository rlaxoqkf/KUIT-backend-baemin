package kuit.server.service;

import kuit.server.common.exception.DatabaseException;
import kuit.server.common.exception.UserException;
import kuit.server.dao.UserDao;
import kuit.server.dto.user.*;
import kuit.server.util.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static kuit.server.common.response.status.BaseExceptionResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public PostUserResponse signUp(PostUserRequest postUserRequest) {
        log.info("[UserService.createUser]");

        validateEmail(postUserRequest.getEmail());
        String nickname = postUserRequest.getNickname();
        if (nickname != null) {
            validateNickname(postUserRequest.getNickname());
        }

        String encodedPassword = passwordEncoder.encode(postUserRequest.getPassword());
        postUserRequest.resetPassword(encodedPassword);

        long userId = userDao.createUser(postUserRequest);

        String jwt = jwtTokenProvider.createToken(postUserRequest.getEmail(), userId);

        return new PostUserResponse(userId, jwt);
    }

    public PostLoginResponse login(PostLoginRequest postLoginRequest, long userId) {
        log.info("[UserService.login]");

        validatePassword(postLoginRequest.getPassword(), userId);

        String updatedJwt = jwtTokenProvider.createToken(postLoginRequest.getEmail(), userId);

        return new PostLoginResponse(userId, updatedJwt);
    }

    public void modifyUserStatus_dormant(long userId) {
        log.info("[UserService.modifyUserStatus_dormant]");

        int affectedRows = userDao.modifyUserStatus_dormant(userId);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void modifyUserStatus_deleted(long userId) {
        log.info("[UserService.modifyUserStatus_deleted]");

        int affectedRows = userDao.modifyUserStatus_deleted(userId);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public void modifyNickname(long userId, String nickname) {
        log.info("[UserService.modifyNickname]");

        validateNickname(nickname);
        int affectedRows = userDao.modifyNickname(userId, nickname);
        if (affectedRows != 1) {
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserResponse> getUsers(String nickname, String email, String status) {
        log.info("[UserService.getUsers]");
        return userDao.getUsers(nickname, email, status);
    }

    public long getUserIdByEmail(String email) {
        return userDao.getUserIdByEmail(email);
    }

    private void validatePassword(String password, long userId) {
        String encodedPassword = userDao.getPasswordByUserId(userId);
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new UserException(PASSWORD_NO_MATCH);
        }
    }

    private void validateEmail(String email) {
        if (userDao.hasDuplicateEmail(email)) {
            throw new UserException(DUPLICATE_EMAIL);
        }
    }

    private void validateNickname(String nickname) {
        if (userDao.hasDuplicateNickName(nickname)) {
            throw new UserException(DUPLICATE_NICKNAME);
        }
    }

    public void modifyPhoneNumber(long userId, String phoneNumber) {
        log.info("[UserService.modifyPhoneNumber]");

        validatePhoneNumber(phoneNumber);
        int affectedRows = userDao.modifyPhoneNumber(userId, phoneNumber);
        if(affectedRows != 1){
            throw new DatabaseException(DATABASE_ERROR);
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if(userDao.hasDuplicatePhoneNumber(phoneNumber)){
            throw new UserException(DUPLICATE_PHONE_NUMBER);
        }
    }
}

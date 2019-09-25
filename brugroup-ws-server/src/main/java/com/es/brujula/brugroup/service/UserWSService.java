package com.es.brujula.brugroup.service;

import io.spring.guides.gs_producing_web_service.UserWS;

import java.util.List;
import java.util.Optional;

public interface UserWSService {

    List<UserWS> getUsers();

    Optional<UserWS> getUser(Long userId);

    UserWS createUser(UserWS user);

    UserWS updateUser(UserWS user);

    void delete(Long userId);

    boolean isValid(String username, String password);

}

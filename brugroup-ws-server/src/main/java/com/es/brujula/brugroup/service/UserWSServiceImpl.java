package com.es.brujula.brugroup.service;

import com.es.brujula.brugroup.UserDAO;
import com.es.brujula.brugroup.converter.UserWSServiceConverter;
import com.es.brujula.brugroup.domain.User;
import io.spring.guides.gs_producing_web_service.UserWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserWSServiceImpl implements UserWSService {

    @Autowired
    private UserDAO dao;

    @Autowired
    private UserWSServiceConverter converter;

    @Override
    public List<UserWS> getUsers() {
        return dao.findAll().stream()
                .map(user -> converter.toApiModel(user, UserWS.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserWS> getUser(Long userId) {
        return dao.findById(userId)
                .map(userFromBd -> converter.toApiModel(userFromBd, UserWS.class));
    }

    @Override
    public UserWS createUser(UserWS user) {
        //user.setLastUpdate(LocalDateTime.now());
        User userAux = converter.toDomainModel(user, User.class);
        userAux.setId(null);
        userAux = dao.save(userAux);
        return converter.toApiModel(userAux, UserWS.class);
    }

    @Override
    public UserWS updateUser(UserWS user) {
        // user.setLastUpdate(LocalDateTime.now());
        User userAux = converter.toDomainModel(user, User.class);
        dao.save(userAux);
        return user;
    }

    @Override
    public void delete(Long userId) {
        dao.deleteById(userId);
    }

    @Override
    public boolean isValid(String username, String password) {
        User user = dao.findFirstByUsername(username);
        return user.getId() != null &&  new BCryptPasswordEncoder().matches(password, user.getPassword());
    }

}

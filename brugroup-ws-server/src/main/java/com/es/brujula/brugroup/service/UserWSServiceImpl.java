package com.es.brujula.brugroup.service;

import com.es.brujula.brugroup.UserDAO;
import com.es.brujula.brugroup.converter.UserWSServiceConverter;
import io.spring.guides.gs_producing_web_service.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserWSServiceImpl implements UserWSService {

    @Autowired
    private UserDAO dao;

    @Autowired
    private UserWSServiceConverter converter;

    @Override
    public List<User> getUsers() {
        return dao.findAll().stream()
                .map(user -> converter.toApiModel(user, User.class))
                .collect(Collectors.toList());
    }
}

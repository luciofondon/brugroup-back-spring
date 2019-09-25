package com.es.brujula.brugroup.user;

import com.es.brujula.brugroup.UserClient;
import com.es.brujula.brugroup.api.UserDto;
import com.es.brujula.brugroup.converter.UserWSServiceConverter;
import com.es.brujula.brugroup.domain.User;
import com.es.brujula.brugroup.UserDAO;
import com.es.brujula.brugroup.converter.UserServiceConverter;
import com.es.brujula.brugroup.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {


    @Autowired
    private UserDAO dao;

    @Autowired
    private UserServiceConverter converter;

    @Autowired
    private UserWSServiceConverter converterWS;

    @Autowired
    private UserClient userClient;


    @Override
    public List<UserDto> getUsers() {
        return dao.findAll().stream()
                .map(user -> converter.toApiModel(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserDto> getUsersWS() {
        GetUsersResponse response = userClient.getUsers();
        return response.getUsers().stream()
                .map(user -> converterWS.toApiModel(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDto> getUsersPagination(Pageable pageable) {
        return dao.findAll(pageable).map(hotel -> converter.toApiModel(hotel, UserDto.class));
    }

    @Override
    public Optional<UserDto> getUser(Long userId) {
        return dao.findById(userId)
                .map(userFromBd -> converter.toApiModel(userFromBd, UserDto.class));
    }

    @Override
    public Optional<UserDto> getUserWS(Long userId) {
        GetUserResponse response = userClient.getUser(userId);
        return Optional.ofNullable(converterWS.toApiModel(response.getUser(), UserDto.class));
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = converter.toDomainModel(userDto, User.class);
        user.setId(null);
        String encoded = new BCryptPasswordEncoder().encode(userDto.getPassword());
        user.setPassword(encoded);
        user.setLastUpdate(LocalDateTime.now());
        user = dao.save(user);

        return converter.toApiModel(user, UserDto.class);
    }

    @Override
    public UserDto createUserWS(UserDto userDto) {
        CreateUserResponse response = userClient.createUser(converterWS.toDomainModel(userDto, UserWS.class));
        UserWS user = response.getUser();
        String encoded = new BCryptPasswordEncoder().encode(user.getPassword());
        return converterWS.toApiModel(user, UserDto.class);
    }

    @Override
    public UserDto updateUser(UserDto userDto) {
        userDto.setLastUpdate(LocalDateTime.now());
        User user = converter.toDomainModel(userDto, User.class);
        dao.save(user);
        return userDto;
    }

    @Override
    public UserDto updateUserWS(UserDto userDto) {
        userDto.setLastUpdate(LocalDateTime.now());
        UpdateUserResponse response = userClient.updateUser(converterWS.toDomainModel(userDto, UserWS.class));
        return userDto;
    }

    @Override
    public void deleteUser(Long userId) {
        dao.deleteById(userId);
    }

    @Override
    public void deleteUserWS(Long userId) {
        userClient.deleteUser(userId);
    }

}

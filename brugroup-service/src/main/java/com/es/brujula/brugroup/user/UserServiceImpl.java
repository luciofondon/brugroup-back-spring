package com.es.brujula.brugroup.user;

import com.es.brujula.brugroup.UserClient;
import com.es.brujula.brugroup.api.UserDto;
import com.es.brujula.brugroup.converter.UserWSServiceConverter;
import com.es.brujula.brugroup.domain.User;
import com.es.brujula.brugroup.UserDAO;
import com.es.brujula.brugroup.converter.UserServiceConverter;
import com.es.brujula.brugroup.wsdl.GetUsersResponse;
import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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
    public Optional<UserDto> getHotel(Long hotelId) {
        return dao.findById(hotelId)
                .map(userFromBd -> converter.toApiModel(userFromBd, UserDto.class));
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
    public UserDto updateUser(UserDto userDto) {
        userDto.setLastUpdate(LocalDateTime.now());
        User user = converter.toDomainModel(userDto, User.class);
        dao.save(user);
        return userDto;
    }

    @Override
    public void delete(Long userId) {
        dao.deleteById(userId);
    }

}

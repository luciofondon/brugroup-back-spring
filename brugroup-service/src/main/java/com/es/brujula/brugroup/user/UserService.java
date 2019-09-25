package com.es.brujula.brugroup.user;

import com.es.brujula.brugroup.api.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


public interface UserService {

     List<UserDto> getUsers();

     List<UserDto> getUsersWS();

     Page<UserDto> getUsersPagination(Pageable pageable);

     Optional<UserDto> getUser(Long userId);

     Optional<UserDto> getUserWS(Long userId);

     UserDto createUser(UserDto userDto);

     UserDto createUserWS(UserDto userDto);

     UserDto updateUser(UserDto hotelDto);

     UserDto updateUserWS(UserDto hotelDto);

     void deleteUser(Long userId);

     void deleteUserWS(Long userId);

}

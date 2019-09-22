package com.es.brujula.brugroup.api;

import com.es.brujula.brugroup.user.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(UserController.BASE_MAPPING)
public class UserController {
    static final String BASE_MAPPING = "/api/users";
    private static final String USER_MAPPING = "/{userId}";
    private static final String ERROR = "Error {}";
    private static final String PAGEABLE_FILTER = "/lazy";
    private static final String USER_FILTER = "/filter";

    @Autowired
    private UserService userService;



    @GetMapping
    @ApiOperation(value = "Get all users")
    public ResponseEntity<List<UserDto>> getUsers() {
        try {
            return ResponseEntity.ok(userService.getUsers());
        } catch (Exception e) {
            log.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/ws")
    @ApiOperation(value = "Get all users")
    public ResponseEntity<List<UserDto>> getUsersWS() {
        try {
            return ResponseEntity.ok(userService.getUsersWS());
        } catch (Exception e) {
            log.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(USER_FILTER + PAGEABLE_FILTER)
    @ApiOperation(value = "Get all users pages")
    public ResponseEntity<Page<UserDto>> getUsersPagination(Pageable pageable) {
        try {
            return ResponseEntity.ok(userService.getUsersPagination(pageable));
        } catch (Exception e) {
            log.error(ERROR, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    @ApiOperation(value = "Create hotel")
    public ResponseEntity createUser(@Validated @RequestBody UserDto userDto) {
        try {
            UserDto user = userService.createUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping(UserController.USER_MAPPING)
    @ApiOperation(value = "Get hotel by id")
    public ResponseEntity<UserDto> getUser(@PathVariable("userId") Long userId) {
        try {
            Optional<UserDto> hotelDto = userService.getHotel(userId);
            if (hotelDto.isPresent()) {
                return ResponseEntity.ok(hotelDto.get());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ApiOperation(value = "Update hotel by id")
    @PutMapping(UserController.USER_MAPPING)
    public ResponseEntity updateUser(@PathVariable("userId") Long userId, @Validated @RequestBody UserDto userToPut) {
        UserDto userDto;
        try {
            //  userToPut.setId(userId);
            userDto = userService.updateUser(userToPut);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping(UserController.USER_MAPPING)
    @ApiOperation(value = "Delete hotel by id")
    public ResponseEntity deleteUser(@PathVariable("userId") Long userId) {
        try {
            userService.delete(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

package com.es.brujula.brugroup.converter;

import com.es.brujula.brugroup.api.UserDto;
import com.es.brujula.brugroup.domain.User;
import com.es.brujula.brugroup.converter.common.AbstractModelConverter;
import org.springframework.stereotype.Component;

@Component
public class UserServiceConverter extends AbstractModelConverter<UserDto, User> {
}

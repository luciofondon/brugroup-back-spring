package com.es.brujula.brugroup.converter;

import com.es.brujula.brugroup.api.UserDto;
import com.es.brujula.brugroup.converter.common.AbstractModelConverter;
import com.es.brujula.brugroup.wsdl.User;
import org.springframework.stereotype.Component;

@Component
public class UserWSServiceConverter extends AbstractModelConverter<UserDto, User>{

}

package com.es.brujula.brugroup.converter;

import com.es.brujula.brugroup.api.UserDto;
import com.es.brujula.brugroup.converter.common.AbstractModelConverter;
import com.es.brujula.brugroup.wsdl.UserWS;
import org.springframework.stereotype.Component;

@Component
public class UserWSServiceConverter extends AbstractModelConverter<UserDto, UserWS>{

}

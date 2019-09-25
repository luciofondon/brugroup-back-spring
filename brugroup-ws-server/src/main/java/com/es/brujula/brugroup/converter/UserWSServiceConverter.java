package com.es.brujula.brugroup.converter;

import com.es.brujula.brugroup.converter.common.AbstractModelConverter;
import com.es.brujula.brugroup.domain.User;
import io.spring.guides.gs_producing_web_service.UserWS;
import org.springframework.stereotype.Component;

@Component
public class UserWSServiceConverter extends AbstractModelConverter<UserWS, User> {
}

package com.es.brujula.brugroup;

import com.es.brujula.brugroup.wsdl.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;

public class UserClient extends WebServiceGatewaySupport {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service/";

    private static final Logger log = LoggerFactory.getLogger(UserClient.class);


    @Autowired
    private DiscoveryClient discoveryClient;


    public GetUsersResponse getUsers() {
        GetUsersRequest request = new GetUsersRequest();
        log.info("Requesting users");
        GetUsersResponse response = (GetUsersResponse) getWebServiceTemplate()
                .marshalSendAndReceive(this.getEndpoint(), request,
                        new SoapActionCallback(NAMESPACE_URI + "GetUsersRequest"));

        return response;
    }

    public GetUserResponse getUser(Long userId) {
        GetUserRequest request = new GetUserRequest();
        request.setId(userId);
        log.info("Requesting users");
        GetUserResponse response = (GetUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(this.getEndpoint(), request,
                        new SoapActionCallback(NAMESPACE_URI + "GetUsersRequest"));

        return response;
    }

    public DeleteUserResponse deleteUser(Long userId) {
        DeleteUserRequest request = new DeleteUserRequest();
        request.setId(userId);
        log.info("Requesting users");
        DeleteUserResponse response = (DeleteUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(this.getEndpoint(), request,
                        new SoapActionCallback(NAMESPACE_URI + "DeleteUserRequest"));
        return response;
    }

    public UpdateUserResponse updateUser(UserWS user) {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setId(user.getId());
        request.setFullName(user.getFullName());
        request.setPassword(user.getPassword());
        request.setUsername(user.getUsername());
        log.info("Requesting users");
        UpdateUserResponse response = (UpdateUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(this.getEndpoint(), request,
                        new SoapActionCallback(NAMESPACE_URI + "UpdateUserRequest"));
        return response;
    }

    public CreateUserResponse createUser(UserWS user) {
        CreateUserRequest request = new CreateUserRequest();
        request.setFullName(user.getFullName());
        request.setPassword(user.getPassword());
        request.setUsername(user.getUsername());
        log.info("Requesting users");
        CreateUserResponse response = (CreateUserResponse) getWebServiceTemplate()
                .marshalSendAndReceive(this.getEndpoint(), request,
                        new SoapActionCallback(NAMESPACE_URI + "CreateUserRequest"));
        return response;
    }

    private String getEndpoint() {
        List<ServiceInstance> instances = discoveryClient.getInstances("brugroup-ws-server");
        ServiceInstance serviceInstance = instances.get(0);
        String hostname = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        return new StringBuilder().append("http://").append(hostname).append(":").append(port).append("/ws/users").toString();
    }

}

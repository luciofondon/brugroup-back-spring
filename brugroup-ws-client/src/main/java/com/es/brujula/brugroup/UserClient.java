package com.es.brujula.brugroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.es.brujula.brugroup.wsdl.GetUsersRequest;
import com.es.brujula.brugroup.wsdl.GetUsersResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import java.util.List;

public class UserClient extends WebServiceGatewaySupport {

    private static final String NAMESPACE_URI = "GetUserRequest";

    private static final Logger log = LoggerFactory.getLogger(UserClient.class);


    @Autowired
    private DiscoveryClient discoveryClient;


    public GetUsersResponse getUsers() {

        List<ServiceInstance> instances= discoveryClient.getInstances("brugroup-ws-server");
        ServiceInstance serviceInstance = instances.get(0);
        String hostname = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        String endpoint = new StringBuilder().append("http://").append(hostname).append(":").append(port).toString();

        GetUsersRequest request = new GetUsersRequest();
        log.info("Requesting users");
        GetUsersResponse response = (GetUsersResponse) getWebServiceTemplate()
                .marshalSendAndReceive(endpoint + "/ws/users", request,
                        new SoapActionCallback(
                                "http://spring.io/guides/gs-producing-web-service/GetUserRequest"));

        return response;
    }

}

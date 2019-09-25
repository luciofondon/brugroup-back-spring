package com.es.brujula.brugroup;
import org.springframework.ws.transport.http.HttpUrlConnectionMessageSender;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.HttpURLConnection;


public class CustomWSMessageSender extends HttpUrlConnectionMessageSender {
    public static final String PASSWORD = "passW1ord";
    public static final String SEPARATOR = ":";
    public static final String USER = "userws";
    public static final String AUTHORIZATION = "Authorization";
    public static final String BASIC = "Basic ";

    @Override
    protected void prepareConnection(HttpURLConnection connection)
            throws IOException {
        BASE64Encoder enc = new sun.misc.BASE64Encoder();
        String userpassword = new StringBuilder().append(USER).append(SEPARATOR).append(PASSWORD).toString();
        String encodedAuthorization = enc.encode(userpassword.getBytes());
        connection.setRequestProperty(AUTHORIZATION, BASIC + encodedAuthorization);

        super.prepareConnection(connection);
    }
}
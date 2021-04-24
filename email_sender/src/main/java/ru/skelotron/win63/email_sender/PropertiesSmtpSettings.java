package ru.skelotron.win63.email_sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesSmtpSettings implements SmtpSettings {
    private final String host;
    private final String port;
    private final String auth;
    private final String startTlsEnabled;
    private final String username;
    private final String password;

    public PropertiesSmtpSettings(@Value("${smtp.host}") String host,
                                  @Value("${smtp.port}") String port,
                                  @Value("${smtp.auth}") String auth,
                                  @Value("${smtp.starttls.enabled}") String startTlsEnabled,
                                  @Value("${smtp.username}") String username,
                                  @Value("${smtp.password}") String password) {
        this.host = host;
        this.port = port;
        this.auth = auth;
        this.startTlsEnabled = startTlsEnabled;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return Integer.parseInt(port);
    }

    @Override
    public boolean useAuth() {
        return Boolean.parseBoolean(auth);
    }

    @Override
    public boolean isStartTlsEnabled() {
        return Boolean.parseBoolean(startTlsEnabled);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}

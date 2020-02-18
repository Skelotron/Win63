package email_sender;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertiesSmtpSettings implements SmtpSettings {
    private final ResourceBundle resourceBundle;

    public PropertiesSmtpSettings() {
        this.resourceBundle = PropertyResourceBundle.getBundle("settings");
    }

    @Override
    public String getHost() {
        return resourceBundle.getString("smtp.host");
    }

    @Override
    public int getPort() {
        return Integer.parseInt(resourceBundle.getString("smtp.port"));
    }

    @Override
    public boolean useAuth() {
        return Boolean.parseBoolean(resourceBundle.getString("smtp.auth"));
    }

    @Override
    public boolean isStartTlsEnabled() {
        return Boolean.parseBoolean(resourceBundle.getString("smtp.starttls.enabled"));
    }

    @Override
    public String getUsername() {
        return resourceBundle.getString("smtp.username");
    }

    @Override
    public String getPassword() {
        return resourceBundle.getString("smtp.password");
    }
}

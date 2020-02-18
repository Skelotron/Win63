package email_sender;

public interface SmtpSettings {
    String getHost();
    int getPort();
    boolean useAuth();
    boolean isStartTlsEnabled();
    String getUsername();
    String getPassword();
}

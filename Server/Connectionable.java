package Server;

import AdjacentClass.Notification;

public interface Connectionable extends Runnable {
    void run();
    void sendNotification(Notification notification);
    void close();
    boolean isBan();
    void setBan(boolean isBan);
    boolean isAdmin();
    void setAdmin(boolean isAdmin);
    boolean isAuthorized();
    void setAuthorized(boolean isAuthorized);
    String getLogin();
    void setLogin(String login);
    String getPassword();
    void setPassword(String password);
}

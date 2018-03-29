package com.ttp.shoppingapp.Model;

/**
 * Created by 0047TiTANplateform_ on 2018-03-21.
 */

public class Sender {
    public String to;
    public Notification notification;


    public Sender() {
    }

    public Sender(String to, Notification notification) {
        this.to = to;
        this.notification = notification;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }
}

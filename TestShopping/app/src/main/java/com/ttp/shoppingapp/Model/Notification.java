package com.ttp.shoppingapp.Model;

/**
 * Created by 0047TiTANplateform_ on 2018-03-21.
 */

public class Notification {
    public String body;
    public String title;

    public Notification() {
    }

    public Notification(String body, String title) {
        this.body = body;
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

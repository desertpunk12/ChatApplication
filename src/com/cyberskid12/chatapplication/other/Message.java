package com.cyberskid12.chatapplication.other;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

public class Message implements Serializable {

    private String  from;
    private String  message;
    private Date    timestamp;

    public Message(String from, String message) {
        this.from = from;
        this.message = message;
        this.timestamp = Date.from(Instant.now());
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "<"+this.timestamp.toString() + ">("+this.from+"): " + this.message;
    }
}

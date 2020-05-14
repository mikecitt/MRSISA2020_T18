package com.tim18.bolnicar.service;

public interface EmailService {
    void sendMessage(String to, String subject, String text);
}

package com.gerenciarh.gerenciarh.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_sender_id")
    private User userSender;

    @ManyToOne
    @JoinColumn(name = "user_receiver_id")
    private User userReceiver;

    private boolean status = false;

    public Notification() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public User getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(User userReceiver) {
        this.userReceiver = userReceiver;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

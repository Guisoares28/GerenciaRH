package com.gerenciarh.gerenciarh.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_token")
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    public TokenEntity(String token) {
        this.token = token;
    }

    public TokenEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

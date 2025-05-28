package com.gerenciarh.gerenciarh.DtosResponse;

public class TokenResponseDTO{

    private String token;

    private boolean status;

    public TokenResponseDTO(String token, boolean status) {
        this.token = token;
        this.status = status;
    }

    public TokenResponseDTO() {
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
}

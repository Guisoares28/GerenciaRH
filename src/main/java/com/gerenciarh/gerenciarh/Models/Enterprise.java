package com.gerenciarh.gerenciarh.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_enterprise")
public class Enterprise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true)
    private String cnpj;

    @Column(unique = true)
    private String phone;
    @Column(unique = true)
    private String email;

    public Enterprise(String name, String cnpj, String phone, String email) {
        this.name = name;
        this.cnpj = cnpj;
        this.phone = phone;
        this.email = email;
    }

    public Enterprise() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

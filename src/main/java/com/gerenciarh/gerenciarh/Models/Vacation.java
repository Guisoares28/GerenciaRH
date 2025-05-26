package com.gerenciarh.gerenciarh.Models;

import com.gerenciarh.gerenciarh.Enums.EnumTypeVacationStatus;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_vacation")
public class Vacation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private EnumTypeVacationStatus status = EnumTypeVacationStatus.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user;

    public Vacation(LocalDate data, User user) {
        this.data = data;
        this.user = user;
    }

    public Vacation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public EnumTypeVacationStatus getStatus() {
        return status;
    }

    public void setStatus(EnumTypeVacationStatus status) {
        this.status = status;
    }
}

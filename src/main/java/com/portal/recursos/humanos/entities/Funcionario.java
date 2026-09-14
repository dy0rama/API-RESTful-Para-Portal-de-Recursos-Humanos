package com.portal.recursos.humanos.entities;

import com.portal.recursos.humanos.enums.CargoFuncionario;
import com.portal.recursos.humanos.enums.NivelDeAcesso;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name = "funcionarios")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoFuncionario cargo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NivelDeAcesso role;

    @Size(min =6, message = "A senha deve possuir pelo menos 6 caracteres")
    private String senha;

    public Funcionario() {}

    public Funcionario(String nome, CargoFuncionario cargo, NivelDeAcesso role, String senha) {
        this.nome = nome;
        this.cargo = cargo;
        this.role = role;
        this.senha = senha;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public CargoFuncionario getCargo() {
        return cargo;
    }

    public void setCargo(CargoFuncionario cargo) {
        this.cargo = cargo;
    }

    public NivelDeAcesso getRole() {
        return role;
    }

    public void setRole(NivelDeAcesso role) {
        this.role = role;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

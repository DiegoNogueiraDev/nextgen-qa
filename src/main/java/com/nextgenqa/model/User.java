package com.nextgenqa.model;
import jakarta.persistence.*;
import lombok.Data;
/**
 * Representa a entidade User no banco de dados.
 */
@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
}

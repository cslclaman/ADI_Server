package com.csl.adi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table (name = "Info")
public class Info implements Serializable {

    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column (name = "adi_tag")
    @NotNull
    private Long adiTag;

    @Column (name = "nome")
    @NotNull
    @Size (max = 512)
    private String nome;

    @Column (name = "comentario")
    @Size (max = 512)
    private String comentario;




}

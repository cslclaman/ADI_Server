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
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn (name = "tipo")
public abstract class Info implements Serializable {

    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column (name = "adi_tag")
    @NotNull
    private Long adiTag;

    @Column (name = "tipo", insertable = false, updatable = false)
    @NotNull
    @Size (max = 5)
    private String tipo;

    @Column (name = "tag")
    @NotNull
    @Size (max = 512)
    private String tag;

    @Column (name = "nome")
    @NotNull
    @Size (max = 512)
    private String nome;

    @Column (name = "comentario")
    @Size (max = 512)
    private String comentario;

}

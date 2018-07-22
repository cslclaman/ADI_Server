package com.csl.adi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "Imagem_Tags", uniqueConstraints = @UniqueConstraint(columnNames = {"imagem","adi_tag"}))
public class ImagemTags implements Serializable {

    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column (name = "imagem")
    @NotNull
    private Long imagem;

    @Column (name = "adi_tag")
    @NotNull
    private Long adiTag;

    @Column (name = "referencia")
    private Boolean referencia;
}

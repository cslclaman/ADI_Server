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
@Table (name = "Origem_Tags", uniqueConstraints = @UniqueConstraint(columnNames = {"origem_imagem", "tag"}))
public class OrigemTags implements Serializable {

    @Column (name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column (name = "origem_imagem")
    @NotNull
    private Long origemImagem;

    @Column (name = "tag")
    @NotNull
    private Long tag;

}

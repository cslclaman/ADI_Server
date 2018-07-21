package com.csl.adi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Representa um site/imageboard de origem, provedor de imagens.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "Origem")
public class Origem implements Serializable {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "sigla", unique = true)
    @NotNull
    @Size(max = 2)
    private String sigla;

    @Column (name = "nome")
    @NotNull
    @Size(max = 128)
    private String nome;

    @Column (name = "tipo")
    @NotNull
    @Size(max = 64)
    private String tipo;

    @Column (name = "ativa")
    @NotNull
    private Boolean ativa;

    @Column (name = "url")
    @NotNull
    @Size(max = 1024)
    private String url;

    @Column (name = "url_base_api")
    @Size(max = 1024)
    private String urlBaseApi;

    @Column (name = "formato_data")
    @Size(max = 128)
    private String formatoData;

    @Column (name = "locale")
    @Size(max = 16)
    private String locale;

}

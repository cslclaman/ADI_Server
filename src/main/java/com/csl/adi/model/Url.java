package com.csl.adi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Representa um endereço da API de uma Origem e seu uso.
 */
@Data
@NoArgsConstructor
@Entity
@Table (name = "Url")
public class Url implements Serializable {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "origem")
    @NotNull
    private Integer origem;

    @Column (name = "descricao")
    @NotNull
    @Size(max = 128)
    private String descricao;

    @Column (name = "url")
    @NotNull
    @Size(max = 1024)
    private String url;

    @Column (name = "usa_url_base")
    @NotNull
    private Boolean usaUrlBase;

}

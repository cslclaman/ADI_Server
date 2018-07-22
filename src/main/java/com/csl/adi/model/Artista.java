package com.csl.adi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table (name = "Artista")
public class Artista extends Info {

    @Column (name = "artista_ativo")
    private Boolean ativo;

}

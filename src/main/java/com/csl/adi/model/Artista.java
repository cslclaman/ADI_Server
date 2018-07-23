package com.csl.adi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity (name = "Artista")
@DiscriminatorValue ("a")
public class Artista extends Info {

    @Column (name = "artista_ativo")
    private Boolean ativo;

}

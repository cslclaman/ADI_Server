package com.csl.adi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity (name = "Personagem")
@DiscriminatorValue("p")
public class Personagem extends Info {

    @Column (name = "personagem_serie")
    private Long serie;

    @Column (name = "personagem_artista")
    private Long artista;

}

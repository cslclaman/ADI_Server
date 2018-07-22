package com.csl.adi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table (name = "Info")
public class Personagem extends Info {

    @Column (name = "personagem_serie")
    private Long serie;

    @Column (name = "personagem_artista")
    private Long artista;

}

package com.csl.adi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
@Table (name = "Info")
public class Serie extends Info {

    @Column (name = "serie_nome_alternativo")
    @Size (max = 512)
    private String nomeAlternativo;

}

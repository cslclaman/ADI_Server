package com.csl.adi.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity (name = "Item")
@DiscriminatorValue("i")
public class Item extends Info {

    public static final int ALCANCE_PERSONAGEM = 0;
    public static final int ALCANCE_ACOES = 1;
    public static final int ALCANCE_CENARIO = 2;
    public static final int ALCANCE_META = 3;

    @Column (name = "item_alcance")
    private Integer alcance;

}

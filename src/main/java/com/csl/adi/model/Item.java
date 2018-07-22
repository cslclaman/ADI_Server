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
public class Item extends Info {

    @Column (name = "item_alcance")
    private Integer alcance;

}

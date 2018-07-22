package com.csl.adi.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table (name = "Tag")
public class Tag implements Serializable {

    @Column (name = "id")
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "tag", unique = true)
    @NotNull
    @Size (max = 512)
    private String tag;

    @Column (name = "url")
    @NotNull
    @Size (max = 512)
    private String url;

    @Column (name = "tipo")
    @NotNull
    @Size (max = 64)
    private String tipo;

    @Column (name = "adi_tag")
    private Long adiTag;

}

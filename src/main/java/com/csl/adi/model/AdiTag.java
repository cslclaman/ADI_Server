package com.csl.adi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table (name = "Adi_Tag", uniqueConstraints = @UniqueConstraint(columnNames = {"tipo", "tag"}))
public class AdiTag implements Serializable {

    @Column (name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "tipo")
    @NotNull
    @Size (max = 5)
    private String tipo;

    @Column (name = "tag")
    @NotNull
    @Size (max = 256)
    private String tag;

    @Column (name = "arquivo")
    @NotNull
    private Boolean arquivo;

    @Column (name = "info")
    @NotNull
    private Boolean hasInfo;

    @JsonIgnore
    public String printAdiTag (){
        return "(" + tipo + ")" + tag;
    }

}

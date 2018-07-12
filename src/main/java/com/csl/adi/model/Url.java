package com.csl.adi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

/**
 * Representa um endereço da API de uma Origem e seu uso.
 */
@Entity
@Table (name = "Url")
public class Url {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    /*
    @ManyToOne
    @JoinColumn (name = "origem")
    @NotNull
    private Origem origem;
    */

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

    public Url() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrigem() {
        return origem;
    }

    public void setOrigem(Integer origem) {
        this.origem = origem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getUsaUrlBase() {
        return usaUrlBase;
    }

    public void setUsaUrlBase(Boolean usaUrlBase) {
        this.usaUrlBase = usaUrlBase;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url1 = (Url) o;
        return Objects.equals(origem, url1.origem) &&
                Objects.equals(descricao, url1.descricao) &&
                Objects.equals(url, url1.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(origem, descricao, url);
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", origem=" + origem +
                ", descricao='" + descricao + '\'' +
                ", url='" + url + '\'' +
                ", usaUrlBase=" + usaUrlBase +
                '}';
    }
}

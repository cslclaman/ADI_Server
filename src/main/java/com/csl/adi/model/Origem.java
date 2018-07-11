package com.csl.adi.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "Origem")
public class Origem {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column (name = "sigla", unique = true)
    @NotNull
    @Size(max = 2)
    private String sigla;

    @Column (name = "nome")
    @NotNull
    @Size(max = 128)
    private String nome;

    @Column (name = "tipo")
    @NotNull
    @Size(max = 64)
    private String tipo;

    @Column (name = "ativa")
    @NotNull
    private Boolean ativa;

    @Column (name = "url")
    @NotNull
    @Size(max = 1024)
    private String url;

    @Column (name = "url_base_api")
    @Size(max = 1024)
    private String urlBaseApi;

    @Column (name = "formato_data")
    @Size(max = 128)
    private String formatoData;

    @Column (name = "locale")
    @Size(max = 16)
    private String locale;

    public Origem() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getAtiva() {
        return ativa;
    }

    public void setAtiva(Boolean ativa) {
        this.ativa = ativa;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlBaseApi() {
        return urlBaseApi;
    }

    public void setUrlBaseApi(String urlBaseApi) {
        this.urlBaseApi = urlBaseApi;
    }

    public String getFormatoData() {
        return formatoData;
    }

    public void setFormatoData(String formatoData) {
        this.formatoData = formatoData;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Origem origem = (Origem) o;
        return Objects.equals(sigla, origem.sigla) &&
                Objects.equals(nome, origem.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sigla, nome);
    }

    @Override
    public String toString() {
        return "Origem{" +
                "id=" + id +
                ", sigla='" + sigla + '\'' +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", ativa=" + ativa +
                ", url='" + url + '\'' +
                ", urlBaseApi='" + urlBaseApi + '\'' +
                ", formatoData='" + formatoData + '\'' +
                ", locale='" + locale + '\'' +
                '}';
    }
}

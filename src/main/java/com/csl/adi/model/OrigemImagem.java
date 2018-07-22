package com.csl.adi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table (name = "Origem_Imagem")
public class OrigemImagem implements Serializable {

    @Id
    @Column (name = "id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "imagem")
    @NotNull
    private Long imagem;

    @Column (name = "origem")
    @NotNull
    private Long origem;

    @Column (name = "origem_primaria")
    @NotNull
    private Boolean origemPrimaria;

    @Column (name = "ident_origem")
    @NotNull
    @Size (max = 96)
    private String identOrigem;

    @Column (name = "origem_inativa")
    @NotNull
    private Boolean origemInativa;

    @Column (name = "imagem_deletada")
    @NotNull
    private Boolean imagemDeletada;

    @Column (name = "imagem_censurada")
    @NotNull
    private Boolean imagemCensurada;

    @Column (name = "imagem_banida")
    @NotNull
    private Boolean imagemBanida;

    @Column (name = "url_post")
    @Size (max = 1024)
    private String urlPost;

    @Column (name = "url_arquivo")
    @Size (max = 1024)
    private String urlArquivo;

    @Column (name = "upload_em")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss Z", timezone = "GMT-3")
    private Date uploadEm;

    @Column (name = "md5")
    @Size (max = 1024)
    private String md5;

    @Column (name = "tamanho_arquivo")
    private Long tamanhoArquivo;

    @Column (name = "tag_string")
    @Size (max = 8192)
    private String tagString;

    @Column (name = "classif_etaria")
    @Size (max = 16)
    private String classifEtaria;

}

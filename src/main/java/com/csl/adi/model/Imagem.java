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
@Table(name = "Imagem")
public class Imagem implements Serializable {

    @Column (name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "md5", unique = true)
    @NotNull
    @Size(max = 40)
    private String md5;

    @Column (name = "tag_string")
    @NotNull
    @Size(max = 8192)
    private String tagString;

    @Column (name = "classif_etaria")
    @NotNull
    @Size(max = 16)
    private String classifEtaria;

    @Column (name = "ativa")
    @NotNull
    private Boolean ativa;

    @Column (name = "caminho_arquivo")
    @NotNull
    @Size(max = 256)
    private String caminhoArquivo;

    @Column (name = "tamanho_arquivo")
    @NotNull
    private Long tamanhoArquivo;

    @Column (name = "criada_em")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss Z", timezone = "GMT-3")
    private Date criadaEm;

    @Column (name = "atualizada_em")
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss Z", timezone = "GMT-3")
    private String atualizadaEm;

    @Column (name = "desativada_em")
    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss Z", timezone = "GMT-3")
    private String desativadaEm;

    @Column (name = "arquivo_original")
    @Size(max = 256)
    private String arquivoOriginal;
}

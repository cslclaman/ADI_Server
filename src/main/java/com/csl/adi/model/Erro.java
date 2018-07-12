package com.csl.adi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class Erro {

    @JsonIgnore
    private HttpStatus httpStatus;

    private int codigo;
    private String status;
    private String mensagem;

    @JsonFormat (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss Z", timezone = "GMT-3")
    private Date data;

    public Erro(HttpStatus httpStatus, String mensagem) {
        this.httpStatus = httpStatus;
        this.codigo = httpStatus.value();
        this.status = httpStatus.getReasonPhrase();
        this.mensagem = mensagem;
        this.data = new Date();
    }

    public Erro(int codigo, String mensagem) {
        this.httpStatus = HttpStatus.resolve(codigo);
        this.codigo = httpStatus.value();
        this.status = httpStatus.getReasonPhrase();
        this.mensagem = mensagem;
        this.data = new Date();
    }

    public int getCodigo() {
        return codigo;
    }

    public String getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

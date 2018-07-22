package com.csl.adi.bean;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageBean<T> {

    @JsonUnwrapped
    private T entity;

    private String message;
}

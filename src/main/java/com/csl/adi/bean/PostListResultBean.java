package com.csl.adi.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostListResultBean<T> {

    private List<T> cadastrados;
    private List<T> naoCadastrados;



}

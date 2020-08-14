package com.yurong.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Product {
    private int id;
    @NotNull
    private String name;
    @NotNull
    private float price;
    @NotNull
    private String unit;
    @NotNull
    private String picURL;
}

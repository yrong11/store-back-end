package com.yurong.store.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Order {
    private int id;
    private int proId;
    private int number;
    private String proName;
    private float proPrice;
    private String proUnit;


}

package com.hexad.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private String code;
    private String name;
    private List<Pack> packList;

    public Product(String code, String name, String... packArgs) {
        this.code = code;
        this.name = name;
        this.packList = new ArrayList<>();
        for (String packArg: packArgs) {
            String[] split = packArg.split(" ");
            packList.add(new Pack(Integer.parseInt(split[0]), BigDecimal.valueOf(Double.parseDouble(split[1]))));
        }
    }

    public String getCode() {
        return code;
    }

    public List<Pack> getPackList() {
        return packList;
    }

}

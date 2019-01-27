package com.hexad.model;

import com.hexad.common.ConverterToBigDecimal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Product implements ConverterToBigDecimal {
    private String code;
    private String name;
    private List<Pack> packList;

    public Product(String code, String name, String... packArgs) {
        this.code = code;
        this.name = name;
        this.packList = new ArrayList<>();
        for (String packArg: packArgs) {
            String[] split = packArg.split(" ");
            packList.add(new Pack(Integer.parseInt(split[0]), toBigDecimal(Double.parseDouble(split[1]))));
        }
    }

    public String getCode() {
        return code;
    }

    public List<Pack> getPackList() {
        return packList;
    }

}

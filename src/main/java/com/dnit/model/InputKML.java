package com.dnit.model;

public class InputKML {

    private String snv;
    private Integer categoria;


    public InputKML(String row) {
        String values [] = row.split(";");
        this.snv = values[0];
        if (values.length == 2) 
            this.categoria = Integer.valueOf(values[1]);
        else 
            this.categoria = 0;
    }

    public InputKML(String snv, String categoria) {
        this.snv = snv;
        this.categoria = Integer.valueOf(categoria);
    }


    public String getSnv() {
        return snv;
    }


    public Integer getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return "SNV:" + snv + ",  Category: " + categoria + " ";
    }
    
}

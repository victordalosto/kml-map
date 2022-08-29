package com.dnit.model;

public class OutputKML {

    private String kml;
    private String observacao;


    public OutputKML(String kml, String observacao) {
        this.kml = kml;
        this.observacao = observacao;
    }


    public String getKml() {
        return kml;
    }

    public String getObservacao() {
        return observacao;
    }

    
}
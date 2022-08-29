package com.dnit.model;

public class BodyKML {

    private StringBuffer header;
    private StringBuffer body;
    private String footing;

    public BodyKML() {
        header = new StringBuffer();
        body = new StringBuffer();
    }


    public String getHeader() {
        return header.toString();
    }

    
    public void appendHeader(String header) {
        this.header.append(header);
    }


    public String getBody() {
        return body.toString();
    }

    public void setBody(String body) {
        this.body.setLength(0);
        this.body.append(body.replaceAll("â", "a").replaceAll("ã", "a")
                             .replaceAll("à", "a").replaceAll("á", "a")
                             .replaceAll("é", "e").replaceAll("ê", "e")
                             .replaceAll("í", "i").replaceAll("ô", "o")
                             .replaceAll("ç", "c").replaceAll("ú", "u"));
    }

    public String getFooting() {
        return footing;
    }
    
    public void setFooting(String footing) {
        this.footing = footing;
    }
    
}

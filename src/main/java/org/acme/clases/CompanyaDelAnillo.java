package org.acme.clases;

import java.util.List;

public class CompanyaDelAnillo {

    private List<String> companya;

    private long tiempo;

    public CompanyaDelAnillo(List<String> compañia, long tiempo) {
        this.tiempo = tiempo;
        this.companya = compañia;
    }

    public List<String> getCompañia() {
        return companya;
    }

    public long getTiempo() {
        return tiempo;
    }
}

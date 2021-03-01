package org.acme.services;

import java.time.LocalDate;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.cache.CacheResult;

@ApplicationScoped
public class CompanyaService {

    @CacheResult(cacheName = "companya-cache") 
    public String getCompanyaDelAnillo(LocalDate date, String destino) {
        try {
            Thread.sleep(1250L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "Tu compañia será " + getHeroe(date.getDayOfMonth() % 5) + " y te acompañará hasta " + destino + " y dará su vida si fuera necesario";
    }

    private String getHeroe(int dayOfMonthModuloFour) {
        switch (dayOfMonthModuloFour) {
            case 0:
                return "Gandalf el Gris";
            case 1:
                return "Legolas de Lothlorien";
            case 2:
                return "Gimli, hijo de Gloin";
            case 3:
                return "Aragorn, heredero al trono de Gondor";
            case 4:
            	return "Boromir, defensor de la ciudad blanca";
            default:
                throw new IllegalArgumentException();
        }
    }
}
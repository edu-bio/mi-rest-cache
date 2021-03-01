package org.acme.resources;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import org.acme.clases.CompanyaDelAnillo;
import org.acme.services.CompanyaService;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

@Path("/companya")
public class CompanyaResource {

    @Inject
    CompanyaService service;

    @GET
    public CompanyaDelAnillo getCompanya(@QueryParam String destino, @QueryParam long daysInFuture) { 
        long executionStart = System.currentTimeMillis();
        List<String> dailyForecasts = Arrays.asList(
                service.getCompanyaDelAnillo(LocalDate.now().plusDays(daysInFuture), destino),
                service.getCompanyaDelAnillo(LocalDate.now().plusDays(daysInFuture + 1L), destino),
                service.getCompanyaDelAnillo(LocalDate.now().plusDays(daysInFuture + 2L), destino),
                service.getCompanyaDelAnillo(LocalDate.now().plusDays(daysInFuture + 3L), destino)
                
        );
        long executionEnd = System.currentTimeMillis();
        return new CompanyaDelAnillo(dailyForecasts, executionEnd - executionStart);
    }
}
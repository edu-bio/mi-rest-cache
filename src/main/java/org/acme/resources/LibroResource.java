package org.acme.resources;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.acme.clases.Libro;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;




@Path("/libro")
public class LibroResource {

	@Inject
    PgPool client;
	
	@Inject
	@ConfigProperty(name = "myapp.schema.create", defaultValue = "true") 
	boolean schemaCreate;

	@PostConstruct
	void config() {
	    if (schemaCreate) {
	        initdb();
	    }
	}

	private void initdb() {
		client.query("DROP TABLE IF EXISTS fruits").execute()
	    .flatMap(r -> client.query("CREATE TABLE Libro(id int, isbn int, titulo text)").execute())
	    .flatMap(r -> client.query("INSERT INTO Libro values(7, 200, 'Las amapolas')").execute())
	    .flatMap(r -> client.query("INSERT INTO Libro values(8, 300, 'Los tres tigrillos')").execute())
	    .flatMap(r -> client.query("INSERT INTO Libro values(9, 300, 'Los cuentos inconclusos')").execute())
	    .await().indefinitely();
	}
	
//	@Inject
//	MySQLPool pool;
//
//	public void onStart(@Observes StartupEvent event) {
//        pool.query("DROP TABLE IF EXISTS Libro")
//                .then(it -> pool.query("CREATE TABLE Libro(id int, isbn int, titulo text)"))
//                .then(it -> pool.query("INSERT INTO Libro values(7, 200, 'Las amapolas')"))
//                .then(it -> pool.query("INSERT INTO Libro values(8, 300, 'Los tres tigrillos')"))
//                .subscribeAsCompletionStage()
//                .join();
//    }
//
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public CompletionStage<List<Libro>> hello() {
//        return pool.query("SELECT * FROM Libro")
//                .map(rows -> {
//                    final List<Libro> books = new ArrayList<>();
//                    for (Row row : rows) {
//                        books.add(Libro.from(row));
//                    }
//                    return books;
//                }).subscribeAsCompletionStage();
//    }
    
    @GET
    public Multi<Libro> get() {
        return Libro.findAll(client);
    }
}

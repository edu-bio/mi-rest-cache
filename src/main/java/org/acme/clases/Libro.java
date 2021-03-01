package org.acme.clases;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;



@Table(name="Libro")
@Entity
public class Libro {
@Id
@SequenceGenerator(name="id", initialValue=1)
@GeneratedValue(generator = "id")
private int id;
private int isbn;
private String titulo;

public Libro() {
	
}

public Libro(int id, int isbn, String titulo) {
	super();
	this.id = id;
	this.isbn = isbn;
	this.titulo = titulo;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getIsbn() {
	return isbn;
}
public void setIsbn(int isbn) {
	this.isbn = isbn;
}
public String getTitulo() {
	return titulo;
}
public void setTitulo(String titulo) {
	this.titulo = titulo;
}

public static Libro from(Row row) {
    final Libro libro = new Libro();
    libro.setTitulo(row.getString("titulo"));
    libro.setId(row.getInteger("id"));
    libro.setIsbn(row.getInteger("isbn"));
    return libro;
}

public static Multi<Libro> findAll(PgPool client) {
    return client.query("SELECT id, titulo FROM Libro ORDER BY titulo ASC").execute()
            .onItem().transformToMulti(set -> Multi.createFrom().iterable(set))
            .onItem().transform(Libro::from);
}

}

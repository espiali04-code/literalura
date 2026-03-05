package com.alura.literalura;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AuthorRepository;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.service.ConsumoAPI;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI;
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Principal(ConsumoAPI consumoAPI,
                     BookRepository bookRepository,
                     AuthorRepository authorRepository) {
        this.consumoAPI = consumoAPI;
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void muestraElMenu() {

        var opcion = -1;

        while (opcion != 0) {

            System.out.println("""
                    
                    1 - Buscar libro por título
                    2 - Listar libros guardados
                    3 - Listar autores guardados
                    4 - Listar autores vivos en un año
                    5 - Listar libros por idioma
                    0 - Salir
                    """);

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibro();
                case 2 -> listarLibros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos();
                case 5 -> listarLibrosPorIdioma();
                case 0 -> System.out.println("Cerrando aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    private void buscarLibro() {

        System.out.println("Escribe el nombre del libro:");
        var titulo = teclado.nextLine();

        String direccion = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");

        Datos datos = consumoAPI.obtenerDatos(direccion);

        if (datos.getResults().isEmpty()) {
            System.out.println("No se encontraron resultados.");
            return;
        }

        DatosLibro datosLibro = datos.getResults().get(0);

        if (datosLibro.getAuthors().isEmpty()) {
            System.out.println("El libro no tiene autor registrado.");
            return;
        }

        DatosAutor datosAutor = datosLibro.getAuthors().get(0);

        Author author = authorRepository
                .findByName(datosAutor.getName())
                .orElseGet(() -> authorRepository.save(
                        new Author(
                                datosAutor.getName(),
                                datosAutor.getBirthYear(),
                                datosAutor.getDeathYear()
                        )
                ));

        Optional<Book> libroExistente =
                bookRepository.findByTitle(datosLibro.getTitle());

        if (libroExistente.isPresent()) {
            System.out.println("⚠ El libro ya está guardado.");
            return;
        }

        Book book = new Book(
                datosLibro.getId(),
                datosLibro.getTitle(),
                datosLibro.getLanguages().isEmpty()
                        ? "Desconocido"
                        : datosLibro.getLanguages().get(0),
                datosLibro.getDownloadCount(),
                author
        );

        bookRepository.save(book);

        System.out.println("Libro guardado: " + book.getTitle());
    }

    private void listarLibros() {

        List<Book> libros = bookRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados.");
            return;
        }

        libros.forEach(libro ->
                System.out.println("📖 " + libro.getTitle() +
                        " | Idioma: " + libro.getLanguage())
        );
    }

    private void listarAutores() {

        List<Author> autores = authorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados.");
            return;
        }

        autores.forEach(autor ->
                System.out.println("✍ " + autor.getName() +
                        " (" + autor.getBirthYear() + " - " +
                        (autor.getDeathYear() != null
                                ? autor.getDeathYear()
                                : "Actual") + ")")
        );
    }

    private void listarAutoresVivos() {

        System.out.println("Ingresa el año:");
        int year = teclado.nextInt();
        teclado.nextLine();

        List<Author> vivos = authorRepository
                .findByBirthYearLessThanEqualAndDeathYearGreaterThanEqual(year, year);

        vivos.addAll(
                authorRepository
                        .findByBirthYearLessThanEqualAndDeathYearIsNull(year)
        );

        if (vivos.isEmpty()) {
            System.out.println("No se encontraron autores vivos en ese año.");
            return;
        }

        vivos.forEach(autor ->
                System.out.println("✍ " + autor.getName() +
                        " (" + autor.getBirthYear() + " - " +
                        (autor.getDeathYear() != null
                                ? autor.getDeathYear()
                                : "Actual") + ")"
                )
        );
    }


    private void listarLibrosPorIdioma() {

        System.out.println("""
                
                Escribe el idioma:
                EN - Inglés
                ES - Español
                FR - Francés
                PT - Portugués
                """);

        var idioma = teclado.nextLine();

        List<Book> libros =
                bookRepository.findByLanguageIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("No hay libros en ese idioma.");
            return;
        }

        libros.forEach(libro ->
                System.out.println("📖 " + libro.getTitle() +
                        " | Idioma: " + libro.getLanguage())
        );
    }
}

package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private String datosJson;
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;


    public Principal (LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        int opcion = -1;
        while (opcion != 0) {
            System.out.println("""
                    \n ----- Bienvenido/a a LiterAlura -----
                    1. Buscar libro por título
                    2. Listar libros registrados
                    3. Listar autores registrados
                    4. Listar autores vivos en un determinado año
                    5. Listar libros por idioma
                    
                    0. Salir
                    
                    -------------------------------------""");
            System.out.println("Ingresa una opción: ");

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Gracias por usar LiterAlura! Nos vemos!");
                    break;
                default:
                    System.out.println("Opción inválida");
            } // Cierra el swtich.
        } // Cierra el ciclo while.

    } // Cierra el metodo mostrarMenu.

    private DatosLibro getDatosLibro() {
        System.out.println("Ingresa el titulo del libro: ");
        var titulo = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + titulo.replace(" ","+"));
        DatosRespuesta datos = conversor.obtenerDatos(json, DatosRespuesta.class);
        return datos.resultadosLibros().get(0);
    }

    private void buscarLibroPorTitulo() {
        DatosLibro datos = getDatosLibro();
        Optional<Libro> libroExistente = libroRepository.findByTituloContainingIgnoreCase(datos.titulo());
        if (libroExistente.isPresent()) {
            System.out.println("\nEl libro ya existe en la base de datos: " + datos.titulo());
            return; //
        }

        var datosAutor = datos.autor().get(0);
        Autor autor = autorRepository.findByNombre(datosAutor.nombre())
                .orElseGet(() -> autorRepository.save(new Autor(datosAutor)));

        Libro libro = new Libro(datos);
        libro.setAutor(autor);
        libroRepository.save(libro);

        System.out.println(libro);
    } // Cierra el metodo buscarLibroPorTitulo.

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos");
        } else {
            libros.stream().sorted(Comparator.comparing(Libro::getTitulo)).forEach(System.out::println);
        }
    } // Cierra el metodo listarLibrosRegistrados.

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos");
        } else {
            autores.stream().sorted(Comparator.comparing(Autor::getNombre)).forEach(System.out::println);
        }
    } // Cierra el metodo ListarAutoresRegistrados.

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año para listar los autores vivos en dicho año: ");
        try {
            var fecha = Integer.parseInt(teclado.nextLine());
            List<Autor> autoresVivos = autorRepository.findAutoresVivos(fecha);
            if (autoresVivos.isEmpty()) {
                System.out.println("No hay autores vivos registrados en el año" + fecha);
            } else {
                autoresVivos.forEach(System.out::println);
            }
        }catch (NumberFormatException e) {
            System.out.println("Error. Por favor ingresa un año valido. ");
        }
    } // Cierra el metodo listarAutoresVivos.

    private void listarLibrosPorIdioma() {
        System.out.println("""
                Idiomas disponibles: 
                es - Español
                en - Inglés
                fr - Francés
                pt - Portugués
                """);
        System.out.println("Ingrese el idioma: ");
        var idioma = teclado.nextLine();

        try {
            var idiomaEnum = Idioma.fromString(idioma);
            List<Libro> libros = libroRepository.findByIdioma(idiomaEnum);
            if (libros.isEmpty()) {
                System.out.println("No se encontraron libros en el idioma seleccionado.");
            } else {
                libros.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Idioma no valido. elige una de las opciones: es, en, fr, pt");
        }
    } // Cierra el metodo listarLibrosPorIdioma.


}

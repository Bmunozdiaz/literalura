# 📚 LiterAlura
Aplicación de consola en Java que permite buscar y registrar libros utilizando la API de Gutendex, almacenando la información en una base de datos PostgreSQL.

## Características
* Búsqueda de libros por título consumiendo la API de Gutendex
* Registro automático de libros y autores en base de datos
* Listado de todos los libros registrados
* Listado de todos los autores registrados
* Consulta de autores vivos en un determinado año
* Filtrado de libros por idioma
* Interfaz de consola amigable

## Tecnologías
* Java 17
* Spring Boot 3.5.11
* Spring Data JPA
* Hibernate
* PostgreSQL
* Jackson para parsing de JSON
* HttpClient de Java
* Gutendex API

## Configuración
1. Clona el repositorio
2. Crea una base de datos PostgreSQL llamada `literalura`
3. Configura las siguientes variables de entorno en tu sistema:

```
DB_HOST=localhost
DB_USER=tu_usuario
DB_PASSWORD=tu_contraseña
```

4. Gutendex es una API pública y gratuita, no requiere API key: https://gutendex.com/

## Uso
1. Ejecuta la clase `LiteraluraApplication.java`
2. Selecciona una opción del menú (0-5)

## Opciones del menú
1. Buscar libro por título
2. Listar libros registrados
3. Listar autores registrados
4. Listar autores vivos en un determinado año
5. Listar libros por idioma
0. Salir

## Idiomas disponibles
| Código | Idioma |
|--------|--------|
| es | Español |
| en | Inglés |
| fr | Francés |
| pt | Portugués |

## Autor
Bastián Muñoz Díaz [Bmunozdiaz](https://github.com/Bmunozdiaz)

# Proyecto: ALASDOCE

## Integrantes del Equipo

| Nombre | Apellidos | Correo Oficial | GitHub |
|--------|----------|---------------|--------|
| Jon | Mazcuñán Hernández | j.mazcunan.2022@alumnos.urjc.com | [GitHub1](https://github.com/jonmazh) |
| Marcos | Álvarez Mansilla | m.alvarez.2022@alumnos.urjc.es | [GitHub2](https://github.com/MarcosAlvarezMansilla) |
| Robert Gabriel | Mihai | rg.mihai.2022@alumnos.urjc.es | [GitHub3](https://github.com/robert07112004) |
| Álvaro | Osuna Flores | a.osuna.2022@alumnos.urjc.es | [GitHub4](https://github.com/AlvaroOsunaFlores) |

## Descripción del Proyecto

El proyecto consiste en una aplicación web basada en HTML, CSS, Java con Spring y BBDD (MySQL) diseñada para la gestión de eventos,
mostrando aquellos eventos (conciertos, discotecas y festivales) disponibles y la compra de entradas para estos. Además, es posible
realizar modificaciones en los comentarios y consultar las entradas a los eventos.

## Herramientas de Desarrollo

- **Lenguaje principal:** HTML, CSS, Java con Spring Boot
- **Base de datos:** MySQL
- **Control de versiones:** GitHub ([Repositorio](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13.git))
- **Entorno de desarrollo:** Visual Studio Code

## Entidades y Relaciones

La aplicación maneja las siguientes entidades principales:

1. **Usuario** (id, nombre, apellido, correo, contraseña)
2. **Evento** (id, titulo, descripción, dirección, fechaComienzo, fechaFin, tipo, precio)
3. **Ticket** (id, titulo, precio, horaCreación, formatoTiempo, estado)
4. **Comentario** (id, titulo, autor, texto, valoración)

### Relaciones entre entidades

- `Usuario` puede tener varios `Comentario`
- `Usuario` puede tener varios `Ticket`
- `Evento` puede tener varios `Comentario`
- `Evento` puede tener varios `Ticket`

## Tipos de Usuarios y Permisos

| Tipo de Usuario | Permisos |
|----------------|----------|
| **Anónimo** | Puede visualizar contenido público |
| **Registrado** | Puede crear/modificar contenido propio |
| **Administrador** | Control total sobre la web |

## Gestión de Imágenes

Las siguientes entidades podrán subir imágenes:

- **Adminsitrador** → Imagen portada de eventos

## Instrucciones de Ejecución

1. **Clonar el repositorio:**
```sh
git clone https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13.git
```

2. **Configurar la base de datos:**  
Descargar MySQL WorkBench  
Usuario: `root` | Contraseña: `password` | Puerto: `3306`  
Crear un esquema llamado: grupo_13  
```sql
CREATE SCHEMA grupo_13;
```

3. **Compilar y ejecutar la aplicación:**  
   Abrir el MySQL WorkBench y meterse en la conexión creada anteriormente  
   Run Application en VSCode  

4. **Acceder a la aplicación:**  
Ve a https://localhost/8080/

### Se necesita

- Java: JDK 21
   - Windows: https://www.oracle.com/java/technologies/downloads/#jdk21-windows
   - Linux:  
      ```sh
      sudo apt install openjdk-21   
      ```

- MySQL: v.8.0.33
   - Windows: https://dev.mysql.com/downloads/installer/
   - Linux:  
      ```sh
      sudo apt install mysql-server=8.0.33
      ```

- Maven: 4.0.0
   - Windows: https://maven.apache.org/download.cgi

- Spring Boot 3.4.2

- VSCode + SpringBoot

## Diagrama de navegación


## Diagrama de Entidades

![Diagrama Entidades](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/diagramaEntidades.png)

## Diagrama de clases y templates


## Capturas de Pantalla

- Página de inicio:
![Pagina Inicio](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaInicio.png)

Descripción: es la pantalla inicial, se muestra al acceder a la página web. Muestra un video de fondo y se pueden acceder a todas las secciones
de arriba a la derecha (Home, Clubbing, Conciertos, Festivales, Contactanos, Mis Datos, Icono cuenta).


- Página de registro:
![Pagina Registro](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaRegistro.png)

Descripción: pantalla utilizada para registrar un usuario, posteriormente podrá iniciar sesión y comprar eventos y publicar comentarios.

- Página de inicio de sesión:
![Pagina Inicio Sesion](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaInicioSesion.png)

Descripción: pantalla utilizada para iniciar sesión una vez creada la cuenta. En esta pantalla si inicias sesión con la cuenta del administrador,
al pulsar otra vez en el icono de Iniciar sesión podrás modificar los eventos y los comentarios.

- Página de cerrar sesión:
![Pagina Cierre Sesion](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaCerrarSesion.png)

Descripción: pantalla utilizada para cerrar sesión del usuario.

- Página de administrador:
![Pagina Administrador](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaAdminComentario.png)

![Pagina Administrador](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaAdminEvento.png)

Descripción: pantalla utilizada únicamente con la cuenta del administrador para eliminar comentarios, añadir y eliminar eventos.

- Página de Clubbing:
![Pagina Clubbing](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaClubbing.png)

Descripción: se muestran los eventos de tipo discoteca y se pueden acceder a ellos.

- Página de Conciertos:
![Pagina Conciertos](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaConciertos.png)

Descripción: se muestran los eventos de tipo concierto y se pueden acceder a ellos.

- Página de Festivales:
![Pagina Festivales](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaFestivales.png)

Descripción: se muestran los eventos de tipo festival y se pueden acceder a ellos.

- Página de ticket:
![Pagina Ticket](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaTicketComprar.png)

![Pagina Ticket](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaTicketComentar.png)

Descripción: se muestra la pantalla para comprar el evento y poder publicar algún comentario de tu experiencia.

- Página de Contactanos:
![Pagina Contactanos](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaContactar.png)

Descripción: pantalla para contactar con nostros rellenando el formulario.

- Página de Mis Datos:
![Pagina Mis Datos](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/pantallaMisDatos.png)

Descripción: pantalla que solo se puede acceder al estar con la sesión iniciada y te muestra los tickets a los eventos que has comprado y los
comentarios que has publicado.

## Participación de los Miembros

Cada miembro del equipo debe indicar su contribución en la práctica.

### Jon
- **header.html** → Uso de boostrap para una página top-nav y modales de registro e inicio de sesión
- **Página detalle** → Página detalle para eventos con comentarios y detalles de cada elemento

### Robert

### Álvaro

### Marcos


[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/D1C1HU9V)

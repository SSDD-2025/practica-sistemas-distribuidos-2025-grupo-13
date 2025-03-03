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

   ```
2. **Configurar la base de datos:**
  
3. **Compilar y ejecutar la aplicación:**
   ```sh
  
   ```
4. **Acceder a la aplicación:**
 
## Diagrama de Entidades

![Diagrama ER](img/diagramaer.png)

## Capturas de Pantalla


## Participación de los Miembros

Cada miembro del equipo debe indicar su contribución en la práctica.

### Jon
- **header.html** → Uso de boostrap para una página top-nav y modales de registro e inicio de sesión
- **Página detalle** → Página detalle para eventos con comentarios y detalles de cada elemento

### Robert

### Álvaro

### Marcos


[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/D1C1HU9V)

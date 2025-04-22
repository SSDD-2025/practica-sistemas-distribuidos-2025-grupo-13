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

Las siguientes entidades podrán mostrar imágenes:

- **Evento** -> Imagen de cada tipo de evento
- **Ticket** -> Imagen de la portada de cada ticket

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

![Diagrama Navegacion](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/diagramaNavegacion.png)

## Diagrama de Entidades

![Diagrama Entidades](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/diagramaEntidades.png)

## Diagrama de clases y templates

![Diagrama de clases y templates](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/Main.svg)

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

- Tareas: realizada la mayoría del readme con su debida estructura. Además, he implementado toda la parte del CSS de la página web, uso de boostrap para una página
  top-nav y modales de registro e inicio de sesión. Página detalle para las páginas de los tickets con comentarios y detalles de cada elemento.
  He realizado la subida de las imagenes a la BBDD. A parte, también se ha realizado la interacción de la base de datos con los eventos para que cuando se haga click
  en alguno te muestre el ticket del debido evento.

- 5 commits más significativos:
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/bc379b522f981c263e3c8e6d0fb603dc43f8a37d)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/9314d9516b5b1e2fe733b15ee3fb2ddca382b221)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/fe8f13cb8341354bade0c8469180fc591df5a0bb)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/466ecd478553c141798e45f54700c581d6f0c7f0)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/1b98da9b8f259fb0423aaac78b46a74fa9f600ea)

- 5 ficheros en los que he participado:
   - [EventController.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/controller/EventController.java)
   - [stylesheet.css](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/css/stylesheet.css)
   - [ticket.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/ticket.html)
   - [Event.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/entities/Event.java)
   - [header.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/header.html)

### Robert

- Tareas: realizar la sección de festivales (en un principio se llamaba eventos), empezar con la creación de la BBDD (en un principio fue h2, luego MySQL) creando los ficheros
  necesarios de entidades, repositorios y controladores y modificando el pom.xml. Se ha ayudado a construir las entidades de la BBDD (Client, Event, Ticket y Comment). Se ha
  implementado algunas pantallas para aceptar la compra de tickets, publicación de comentarios... Se ha realizado la sección de Mis Datos junto con las operaciones que tiene
  que realizar la base de datos para mostrar esa sección, además se ha implementado que un cliente pueda comprar un ticket y publicar un comentario y que eso se muestre en la BBDD.
  En el apartado de administrador se ha implementado que se puedan eliminar tanto eventos como comentarios y que los cambios se muestren en la BBDD. Por último, se ha ayudado en
  el readme.

- 5 commits más significativos (cada apartado realizado está separado por varios commits, no está todo el contenido en uno solo):
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/6068a793b0c2ec1fdbe0f82e6cc2a0107bf66aaf)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/c75e85a4836989030d44d7886e753e92d0c3bf25)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/f80d518a7a6c19c004c190c7c8c0f683df0b3011)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/ceca4170154482b09f8a0d9101476f5fc955938a)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/7097fd02a55c77eea78ec2fafd9971a0679f2e70)

- 5 ficheros en los que se ha participado:
   - [mydata.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/myData.html)
   - [newEvent.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/newEvent.html)
   - [festivals.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/festivals.html)
   - [EventController.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/controller/EventController.java)
   - [Client.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/entities/Client.java) 

### Álvaro
- Tareas: realizar la sección de clubbing, creación de los principales mapeos. Creación del administrador, con su respectivo cierre de sesión, añadir eventos, mapeado borrar eventos y mapeado borrar comentarios. Creación del cierre de sesión y, por consecuencia, la diferenciación dentro de otras clases de usuario iniciado o no. Primeras creaciones de entidades comentarios. Inicios del Client Controller y página de compra. Diferenciación del header y el footer, es decir, creación de los ficheros y poner el código que se encontraba al inicio y final de todos los htmls. Mapeo de las secciones del header. Ayuda en otros métodos de Event Controller. Funcionalidad completa de Editar Perfil (htmls,métodos,mappings...).

- 5 commits más significativos :
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/ab1b7ae2a8f39ea3739c49cc8bd1b818e1b469e5)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/b84971f19f1d9727f6e15e711812358613223f5c)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/f5d358fa9db7c2111cefe304dd6ad5e8a3054dc4)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/ce9d442f35964173bfee4c2df482a840461235e1)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/f0f603b3a351ae113e145b7c054138aeab85be74)

- 5 ficheros en los que se ha participado:
   - [profile_admin.html](src/main/resources/Templates/profile_admin.html)
   - [profile.html](src/main/resources/Templates/profile.html)
   - [clubbing.html](src/main/resources/Templates/clubbing.html)
   - [ClientController.java](src/main/java/es/grupo13/ssddgrupo13/controller/ClientController.java)
   - [EventController.java](src/main/java/es/grupo13/ssddgrupo13/controller/EventController.java)

### Marcos

- Tareas: Creación la pestaña conciertos en html con una plantilla con mustache. Adición de ejemplos, tanto de festivales como de conciertos, a la BBDD junto con los
controladores respectivos para que la plantilla mustache funcione. Creación del metodo dentro del clientController para que funcione el añadir un comentario, que se vea en
la página, que se añada correctamente a la BBDD junto con las dependencias con el cliente y el evento. Actualización de la base de datos, upgradeamos de h2 a sql. Por útlimo,
se ha actualizado todo el código al inglés y  modificado muchos css para que la web tuviese una estandarización adecuada tanto de colores como de tipografías

- 5 commits más significativos (cada apartado realizado está separado por varios commits, no está todo el contenido en uno solo):
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/1c32397bba08337940850ace51ba583674cb66ba)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/13a4b0ec47e84b87db2d564646f607a8f52f9922)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/7dbe94a7196fd3862fe8be1b08a920c27b64cc0c)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/221a18e987bc75dd91e4d63efac8c53da8975ecb)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/f9da51124394295731186e6964cb183d9dcc1fc2)

- 5 ficheros en los que se ha participado:
   - [ClientController.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/controller/ClientController.java)
   - [EventController.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/controller/EventController.java)
   - [concerts.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/concerts.html)
   - [ticket.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/ticket.html)
   - [contact.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/contact.html)


# Proyecto: ALASDOCE

## Diagrama de navegación

## Diagrama de Entidades

- No ha cambiado con respecto a la anterior fase.

![Diagrama Entidades](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/diagramaEntidades.png)

## Diagrama de clases y templates

![Diagrama Clases y Templates](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/DiagramaClasesFaseII.png)

## Configuración del entorno de desarrollo

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
Ve a https://localhost/8443/

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

## Participación de los Miembros

Cada miembro del equipo debe indicar su contribución en la práctica.

### Jon

- Tareas:

- 5 commits más significativos:
   - [Commit 1]()
   - [Commit 2]()
   - [Commit 3]()
   - [Commit 4]()
   - [Commit 5]()

- 5 ficheros en los que he participado:
   - []()
   - []()
   - []()
   - []()
   - []()

### Robert

- Tareas:

- 5 commits más significativos (cada apartado realizado está separado por varios commits, no está todo el contenido en uno solo):
   - [Commit 1]()
   - [Commit 2]()
   - [Commit 3]()
   - [Commit 4]()
   - [Commit 5]()

- 5 ficheros en los que se ha participado:
   - []()
   - []()
   - []()
   - []()
   - []() 

### Álvaro

- Tareas: 

- 5 commits más significativos :
   - [Commit 1]()
   - [Commit 2]()
   - [Commit 3]()
   - [Commit 4]()
   - [Commit 5]()

- 5 ficheros en los que se ha participado:
   - []()
   - []()
   - []()
   - []()
   - []()

### Marcos

- Tareas: 

- 5 commits más significativos (cada apartado realizado está separado por varios commits, no está todo el contenido en uno solo):
   - [Commit 1]()
   - [Commit 2]()
   - [Commit 3]()
   - [Commit 4]()
   - [Commit 5]()

- 5 ficheros en los que se ha participado:
   - []()
   - []()
   - []()
   - []()
   - []()


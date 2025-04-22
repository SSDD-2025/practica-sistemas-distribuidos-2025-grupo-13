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
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/f86ea6768f654a0b1b71211ab9be7c0fb31865dd)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/0abcf984741a6cb7eba39354fda20e1c3e0a2a99)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/67f16dbde9e2698839cd674d4f2bc08f6a24a959)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/b74e75d90c0cfbf61adb66df1d59bfe21c0278e1)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/9b3fdc0ecae3c6b99353e57219243a3e9ab77d46)

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
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/ab1b7ae2a8f39ea3739c49cc8bd1b818e1b469e5)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/b84971f19f1d9727f6e15e711812358613223f5c)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/f5d358fa9db7c2111cefe304dd6ad5e8a3054dc4)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/ce9d442f35964173bfee4c2df482a840461235e1)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/f0f603b3a351ae113e145b7c054138aeab85be74)
   
- 5 ficheros en los que se ha participado:
   - [ClientController.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/controller/ClientController.java)
   - [EventController.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/controller/EventController.java)
   - [concerts.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/concerts.html)
   - [ticket.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/ticket.html)
   - [contact.html](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Templates/contact.html)


# Proyecto: ALASDOCE Fase II

## Diagrama de navegación

![Diagrama de Navegación](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/resources/Static/imagesReadme/DiagramaNavegaciónII.png)

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

- Tareas: En el proyecto de aplicación web, me encargué de la implementación de Spring Security, desarrollando el sistema de autenticación de usuarios y gestionando el acceso mediante login personalizado. Unifiqué los datos y el proceso de autenticación para ofrecer una experiencia centralizada y segura. Además, creé y configuré los archivos de seguridad necesarios, estableciendo los permisos adecuados para los distintos roles (admin y user), controlando así el acceso a cada URL de la aplicación.

- 5 commits más significativos:
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/73e42e9400b92fe229c247faed0dfb891141f6a9)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/5407357b9e1ea3dbfa5337c1e14944af8edffe70)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/7a006360e4e369029a080f5c970657cb3b1aa15d)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/b7097cbc92a2af90b96b8c723b0fa05499fec872)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/e04119ec4b1c833cf062118c58e3d52f51d7702e)

- 5 ficheros en los que he participado:
   - [SecurityConfig.java](src/main/java/es/grupo13/ssddgrupo13/security/SecurityConfig.java)
   - [EventController.java](src/main/java/es/grupo13/ssddgrupo13/controller/EventController.java)
   - [EventService.java](src/main/java/es/grupo13/ssddgrupo13/services/EventService.java)
   - [AdminController.java](src/main/java/es/grupo13/ssddgrupo13/controller/AdminController.java)
   - [CommentController.java](src/main/java/es/grupo13/ssddgrupo13/controller/CommentController.java)

### Robert

- Tareas: he implementado de la parte de Rest las clases EventRestController.java y algunas funciones de la clase TicketRestController.java, además para realizar bien los controladores Rest he tenido que desarrollar nuevos métodos en la parte de los servicios al igual que tocar algunos DTOs. He utilizado Postman para comprobar que los controladres Rest funcionan correctamente y he metido la colección dentro del proyecto. He conseguido generar la documentación ApiDocs, la cual se puede consultar dentro del proyecto. Por último, he resuelto algunos problemas que han surgido a medida que se iba haciendo la práctica como algunas funciones que no iban del todo bien: eliminar clientes en controladores Rest, eliminar tu cuenta teniendo en cuenta la parte de Seguridad de Spring o añadir un nuevo evento como administrador.

- 5 commits más significativos (cada apartado realizado está separado por varios commits, no está todo el contenido en uno solo):
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/d870326cd77e5affd09a9f439c92de75a88f2730)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/8075209ea5ba401adf49abc404164027c61221bc)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/a7454e331886e5d16a378c24b6626a815f139090)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/bd79ba58b8a7c7d3544da8236596d8a868b58b96)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/0fd0229c3e507d3edb3698e95ffbd17ac5341796)

- 5 ficheros en los que se ha participado:
   - [EventRestController.java](src/main/java/es/grupo13/ssddgrupo13/controller/EventRestController.java)
   - [TicketRestController.java](src/main/java/es/grupo13/ssddgrupo13/controller/TicketRestController.java)
   - [EventService.java](src/main/java/es/grupo13/ssddgrupo13/services/EventService.java)
   - [ClientController.java](src/main/java/es/grupo13/ssddgrupo13/controller/ClientController.java)
   - [ClientService.java](src/main/java/es/grupo13/ssddgrupo13/services/ClientService.java) 

### Álvaro

- Tareas: En la Fase II me he encargado de añadir las carencias de la anterior fase(servicios, modelos,..), paginación completa, creacion de los DTOs y mappers, el comments API Rest, trabajo conjunto con compañeros en el Ticket API Rest, trabajo conjunto con compañeros en seguridad Rest, trabajo conjunto del jwt y trabajo conjunto de api-doc configurations.

- 5 commits más significativos :
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/f86ea6768f654a0b1b71211ab9be7c0fb31865dd)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/0abcf984741a6cb7eba39354fda20e1c3e0a2a99)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/67f16dbde9e2698839cd674d4f2bc08f6a24a959)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/b74e75d90c0cfbf61adb66df1d59bfe21c0278e1)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/9b3fdc0ecae3c6b99353e57219243a3e9ab77d46)

- 5 ficheros en los que se ha participado:
   - [CommentRestController.java](src/main/java/es/grupo13/ssddgrupo13/controller/CommentRestController.java)
   - [CommentService.java](src/main/java/es/grupo13/ssddgrupo13/services/CommentService.java)
   - [SecurityConfig.java](src/main/java/es/grupo13/ssddgrupo13/security/SecurityConfig.java)
   - [EventController.java](src/main/java/es/grupo13/ssddgrupo13/controller/EventController.java)
   - [profile_admin.html](src/main/resources/Templates/profile_admin.html)

### Marcos

- Tareas: Se implementó la clase ClientRestController.java y se incorporaron nuevos métodos en ClientService.java para habilitar operaciones REST mediante objetos DTO, facilitando pruebas a través de Postman. Se desarrolló también la clase TicketRestController.java, implementando los endpoints GET correspondientes y ampliando la lógica en TicketService.java para soportar estas operaciones utilizando DTOs. Se añadió el método replaceComment en CommentRestController.java para permitir actualizaciones de comentarios mediante peticiones PUT. Finalmente, se refactorizaron los métodos DELETE en las entidades Ticket y Client, ajustando los atributos en sus respectivas clases .java para garantizar una correcta eliminación de recursos.


- 5 commits más significativos (cada apartado realizado está separado por varios commits, no está todo el contenido en uno solo):
   - [Commit 1](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/a4c4477276738e6882026ce8fcba85b3dc8e7e68)
   - [Commit 2](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/9f660f913eecf7f8593333f79e73eb386e30832c)
   - [Commit 3](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/7b170328aa7abf37db78a48a4fbf99ccd657ab2e)
   - [Commit 4](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/6abe0e7972fd0561b95792d14492c152ef286287)
   - [Commit 5](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/commit/cb45b01ab7cc30a05fd2cca60c8a67db38f9482f)

- 5 ficheros en los que se ha participado:
   - [ClientRestController.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/controller/ClientRestController.java)
   - [TicketRestController.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/controller/TicketRestController.java)
   - [ClientService.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/services/ClientService.java)
   - [TicketService.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/services/TicketService.java)
   - [CommentRestController.java](https://github.com/SSDD-2025/practica-sistemas-distribuidos-2025-grupo-13/blob/main/src/main/java/es/grupo13/ssddgrupo13/controller/CommentRestController.java)


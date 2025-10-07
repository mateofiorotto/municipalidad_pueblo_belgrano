# Municipality of Pueblo General Belgrano | Municipalidad de Pueblo General Belgrano

## Technologies / Tecnologias
<p class="technologies">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,hibernate,maven,postman,idea,mysql,git,js,ts,angular,html,css,bootstrap" />
  </a>
</p>

- Java, JDK 21 (Programming Language, Version)
  - JavaDoc (method's documentation)
- Spring Boot (Framework and Dependencies)
    - Validation
    - MySQL Driver
    - Spring DevTools
    - Spring Data JPA (Hibernate + JPA)
    - MySQL Driver
    - Spring Web
    - Springdoc (API endpoint documentation with Swagger)
    - MapStruct (for DTOs conversion)
    - Spring Security (with roles, permissions and users)
      - JWT implementation
    - Bucket4j (rate limiter por IP)
    - OpenPDF
- Maven (Dependencies)
- HTML5
- CSS3
- Javascript
- Bootstrap 5
- Angular 19
- TypeScript
- Postman (Test API)
- XAMPP (Development Environment, Apache, phpmyadmin)
- IntelliJ Idea (IDE)
- Git + GitHub
- Google ReCaptcha
- Amazon S3 (Files, Images) or other


---

- [Go to English documentation](#ENGLISH)
<br>
- [Ir a la documentación en Español](#ESPAÑOL)

---

## ENGLISH
To do..

---

## ESPAÑOL

Este proyecto se trata acerca de un sitio web institucional para la municipalidad de Pueblo General Belgrano. El principal problema que busca solucionar es poder tomar los reclamos de los vecinos del pueblo de forma online (actualmente la municipalidad lo hace de forma presencial con lapiz y papel). A su vez, agrega cosas como noticias, eventos e información útil para los vecinos.

### Entidades DB
- Roles (N:N con usuarios y N:N con permisos): definen a que podran o no acceder los usuarios
- Usuarios (N:N con roles): son los usuarios del sistema, en este caso se usaran para restringir acceso al dashboard en el front o a determinados recursos al hacer consultas a la API
- Permisos (N:N con roles): definen permisos que tiene cada rol, en esta aplicacion web no tienen mucho uso ya que no es tan grande pero se implementan en caso de escalar
- Noticias (1:N con categorias y eventos): serán publicadas y reflejadas en el frontend
- Categorias (1:N con noticias): serviran como filtro de noticias en la web
- Eventos (1:N con noticias): se mostraran los proximos eventos en el pueblo en la web. Pueden aparecer en noticias
- Transparencias: se subiran archivos pdf con las transparencias
- Reclamos (1:N con areas): podran efectuarse reclamos en la web, los cuales pueden ser cerrados una vez atendidos
- Areas (1:N con reclamos): podran delegarse a cierto reclamo de ser necesario 

### BACKEND
- ENTIDADES: Roles, usuarios, permisos, noticias, categorias, eventos, transparencias, reclamos, areas
- Autenticacion mediante login con Tokens JWT.
- Autorizacion a determinados recursos mediante roles (Spring Security)
 - Soft Deletes (en algunas entidades)
 - Validaciones (libreria Validation de spring y personalizadas en capa de service y consultas SQL)
 - Excepciones personalizadas
 - Patron de diseño DTO y Mappers (con mapstruct)
 - Consultas SQL personalizadas y con Spring Data JPA
- Documentación Swagger: http://localhost:8080/swagger-ui/index.html#/
- Rate limiter y Recaptcha para creacion de reclamos y login
- CRUD y metodos personalizados para todas las entidades
- Paginacion (Pageable, PagedModel, Page)
- Configuracion de CORS y Spring Security
- Filtrados

### Estructuras de carpetas backend
<img src="/imgs-readme/carpetas-backend.jpg" alt="carpetas-backend">
(actualizar)

### Frontend
- Vistas
    - Home con banner, areas de uso comun, ultimas noticias, autoridades municipales y numeros de emergencia
    - Login (para entrar al dashboard)
    - Listado de noticias y noticias por individual
    - Reclamos
    - Gobierno (info de cada area municipal con datos de contacto y horarios)
    - El pueblo (mapa con lugares de interes, info de como llegar)
    - Listado de eventos (img de fondo, titulo, fecha). Eventos por individual
    - Honorable concejo deliberante
    - Transparencias
    - Dashboard (manejar CRUDs)
- Manejo de token jwt (login, logout, logout automatico)
- Componentes (Para notificaciones de error o exito, barras de navegacion, noticias, etc...)
- Hooks
- Animaciones (AOS)
- Responsive
- Gestion de permisos en la UI (mostrar o no recursos segun el rol del usuario)
- Filtrados

### Estructuras de carpetas frontend
Por hacer..

### Levantar Proyecto de forma local

**a. LEVANTAR BACKEND**

1. Clonar repositorio

```
git clone https://github.com/mateofiorotto/municipalidad_pueblo_belgrano
```

2. Chequear dependencias
- JDK 21
- Maven (necesario si queres ejecutar comandos con la CLI de maven, caso contrario, no)
- IDE con compilador que mas te guste (En mi caso IntelliJ)

3. Configurar base de datos
- Abri XAMPP e inicia Apache y MySQL
- Crea una base de datos con el nombre que vayas a poner en las variables de entorno

4. application.properties

En tu IDE, configura las variables de entorno locales y asignales el valor correspondiente:

```
# Configuraciones varias
spring.application.name=municipalidad

# Configuraciones de base de datos
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}

# Configuraciones de seguridad
spring.security.user.name=${SS_USER}
spring.security.user.password=${SS_PASSWORD}

# Configuraciones de JWT
security.jwt.private.key=${PRIVATE_KEY}
security.jwt.user.generator=${USER_GENERATOR}
```

Ejemplo:
<img src="/imgs-readme/image.png" alt="ejemplo app.properties">

PD: En la foto falta el user generator

- ${DB_URL} = Direccion a tu base de datos
- ${DB_USER} = Usuario de la base de datos
- ${DB_PASSWORD} = Contraseña de la base de datos
- ${SS_USER} = Usuario de SPRING SECURITY
- ${SS_PASSWORD} = Contraseña de SPRING SECURITY
- ${PRIVATE_KEY} = Clave privada para generar el token JWT
- ${USER_GENERATOR} = Usuario que se encarga de la generacion de token JWT. EJ: MATEO-SEC

**NOTA**: SS_USER y SS_PASSWORD son para usar con HTTP BASIC en Spring security. Esta app no la usa pero dejo estas opciones en el codigo por si clonas el repo y queres experimentar con ello. (Si no los vas a usar, comentalo o borralo de application.properties)

**NOTA 2**: La clave privada debe ir hasheada en el algoritmo SHA256, yo use: https://tools.keycdn.com/sha256-online-generator

5. Ejecutar la app

Ejecuta ```./mvnw spring-boot:run``` o presiona el boton de iniciar en tu IDE mientras estas dentro de la clase "MunicipalidadApplication". (Clase principal con la anotación @SpringBootApplication)

6. Crear usuarios, roles y permisos a mano, o desactivando la autorizacion un momento

Podes poner permitAll() en los endpoints de guardado de usuarios, roles y permisos y crear por lo menos un usuario admin o podes hacerlo a mano (creando roles, usuarios, permisos y relacionandolos en phpmyadmin u otro gestor). Tene en cuenta que si creas un usuario debes hashear su contraseña con BCrypt para que te tome el login. Podes usar esta página: https://bcrypt-generator.com/

También recorda que los roles y permisos van en MAYUSCULAS.

**b. LEVANTAR FRONTEND**
Por hacer..
# Municipality of Pueblo General Belgrano | Municipalidad de Pueblo General Belgrano

## Technologies / Tecnologias
<p class="technologies">
  <a href="https://skillicons.dev">
    <img src="https://skillicons.dev/icons?i=java,spring,hibernate,maven,postman,idea,mysql,git,js,ts,angular,html,css,tailwind" />
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
- Tailwind 4
- Angular 19
- TypeScript
- Postman (Test API)
- XAMPP (Development Environment, Apache, phpmyadmin)
- IntelliJ Idea (IDE)
- Git + GitHub
- Google ReCaptcha
- Docker
- Nginx
- Linux Ubuntu (VPS)


---

- [Go to English documentation](#ENGLISH)
<br>
- [Ir a la documentación en Español](#ESPAÑOL)

---

## ENGLISH

This project is an institutional website for the Municipality of Pueblo General Belgrano.  
Its main goal is to provide an online system for residents to submit complaints or claims, replacing the current in-person, paper-based process used by the municipality.

Additionally, the platform includes features such as news, events, and useful public information for citizens.

### Database Entities
- **Roles**: define what users can or cannot access
- **Users**: system users, mainly used to restrict access to the admin dashboard and protected API resources
- **Permissions**: define specific permissions assigned to roles; while not heavily used in this project due to its size, they are implemented with scalability in mind
- **News**: published and displayed on the frontend
- **Categories**: used to filter news content
- **Events**: upcoming local events displayed on the website; they can also appear within news
- **Transparencies**: PDF files uploaded for public transparency
- **Complaints**: complaints submitted by citizens, which can be marked as resolved once handled
- **Areas**: municipal areas to which complaints can be assigned if needed

### Backend
- **Entities**: Roles, Users, Permissions, News, Categories, Events, Transparencies, Complaints, Areas
- JWT-based authentication
- Role-based authorization (Spring Security)
- Soft deletes (for selected entities)
- Validations (Spring Validation and custom validations in service layer and SQL queries)
- Custom exception handling
- DTO pattern and mappers (MapStruct)
- Custom SQL queries and Spring Data JPA repositories
- Swagger documentation:  
  http://localhost:8080/swagger-ui/index.html#/
- Rate limiting and Google reCAPTCHA for complaint creation and login
- CRUD operations and custom methods for all entities
- Pagination (Pageable, Page, PagedModel)
- CORS and Spring Security configuration
- Filtering capabilities

### Frontend
- Public and private views
- Admin dashboard
- JWT token handling (login, logout, automatic logout)
- Reusable components (notifications, navigation bars, news components, etc.)
- Animations (AOS)
- Fully responsive design
- UI permission management (showing or hiding resources based on user roles)
- Filtering features

### Running the Project Locally

Local setup and deployment instructions are not provided, as this project was developed for a private client.

---

## ESPAÑOL

Este proyecto se trata acerca de un sitio web institucional para la municipalidad de Pueblo General Belgrano. El principal problema que busca solucionar es poder tomar los reclamos de los vecinos del pueblo de forma online (actualmente la municipalidad lo hace de forma presencial con lapiz y papel). A su vez, agrega cosas como noticias, eventos e información útil para los vecinos.

### Entidades DB
- Roles: definen a que podran o no acceder los usuarios
- Usuarios: son los usuarios del sistema, en este caso se usaran para restringir acceso al dashboard en el front o a determinados recursos al hacer consultas a la API
- Permisos: definen permisos que tiene cada rol, en esta aplicacion web no tienen mucho uso ya que no es tan grande pero se implementan en caso de escalar
- Noticias: serán publicadas y reflejadas en el frontend
- Categorias: serviran como filtro de noticias en la web
- Eventos: se mostraran los proximos eventos en el pueblo en la web. Pueden aparecer en noticias
- Transparencias: se subiran archivos pdf con las transparencias
- Reclamos: podran efectuarse reclamos en la web, los cuales pueden ser cerrados una vez atendidos
- Areas: podran delegarse a cierto reclamo de ser necesario 

### BACKEND
- ENTIDADES: Roles, usuarios, permisos, noticias, categorias, eventos, transparencias, reclamos, areas
- Autenticacion mediante login con Tokens JWT
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

### Frontend
- Vistas
- Dashboard para admins
- Manejo de token jwt (login, logout, logout automatico)
- Componentes (Para notificaciones de error o exito, barras de navegacion, noticias, etc...)
- Animaciones (AOS)
- Responsive
- Gestion de permisos en la UI (mostrar o no recursos segun el rol del usuario)
- Filtrados

### Levantar Proyecto de forma local

No brindo una forma de levantar este proyecto ya que es un trabajo para un cliente privado.
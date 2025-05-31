# E-commerce (Microservicios) | Spring Boot | Spring Cloud

## Infraestructura con Docker

Este proyecto usa varios contenedores Docker para servicios de infraestructura clave, orquestados con `docker-compose`. A continuación se explica qué hace cada servicio:

### Servicios

- **PostgreSQL:**  
  Base de datos relacional para almacenamiento de datos estructurados. Los datos se guardan en un volumen persistente para no perderse al reiniciar.

- **PGAdmin:**  
  Interfaz web para administrar la base de datos PostgreSQL. Se accede desde `http://localhost:5050`.

- **MongoDB:**  
  Base de datos NoSQL para almacenar documentos y datos flexibles, con usuario y contraseña configurados.

- **Mongo Express**  
  Interfaz web para administrar MongoDB. Accede desde: [http://localhost:8081](http://localhost:8081)
  > **Credenciales de acceso:**  
  > Usuario: `diego123`  
  > Contraseña: `diego123`

- **Zookeeper:**  
  Servicio de coordinación requerido por Kafka.

- **Kafka:**  
  Plataforma de mensajería para comunicación asíncrona entre microservicios.

- **MailDev:**  
  Servidor SMTP y web para pruebas locales de envío de correos electrónicos, accesible en `http://localhost:1080`.

### Redes y Volúmenes

- **Red `microservices-net`:**  
  Red privada que conecta todos los servicios para que se comuniquen internamente sin exponer puertos innecesarios.

- **Volúmenes:**
    - `postgres`: Persistencia de datos de PostgreSQL.
    - `pgadmin`: Persistencia de datos y configuración de PGAdmin.
    - `mongo`: Persistencia de datos de MongoDB.

---

Para levantar todos estos servicios, ejecuta en la raíz del proyecto:

```bash
docker-compose up -d
```

## Base de datos para `product-service`, `order-service` y `payment-service`

Antes de aplicar las migraciones de los microservicios, es necesario **crear manualmente las bases de datos** que utilizará cada uno de ellos en el contenedor PostgreSQL levantado con Docker.

### Bases de datos requeridas:

- `product` → utilizada por `product-service`
- `order` → utilizada por `order-service`
- `payment` → utilizada por `payment-service`

### 1. Crear las bases de datos

Puedes crear estas bases de datos usando herramientas como **IntelliJ IDEA**, **Visual Studio Code**, o directamente desde la línea de comandos (psql). A continuación, te mostramos cómo hacerlo con las dos primeras opciones:

---

#### Opción A: Usando IntelliJ IDEA

1. Abre IntelliJ IDEA.
2. Ve al panel **Database** (`View > Tool Windows > Database`).
3. Haz clic en `+ > Data Source > PostgreSQL`.
4. Configura la conexión:
    - **Host:** `localhost`
    - **Port:** `5432`
    - **User:** `diego`
    - **Password:** `diego`
    - **Database:** (puedes dejarlo vacío o escribir `postgres` para conectarte primero)
5. Una vez conectada la instancia, haz clic derecho sobre el servidor y selecciona **New > Database**.
6. Crea las siguientes bases de datos:
    - `product`
    - `order`
    - `payment`

---

#### Opción B: Usando Visual Studio Code

1. Instala la extensión **SQLTools** (si aún no la tienes).
2. Agrega una nueva conexión PostgreSQL:
    - **Server/Host:** `localhost`
    - **Port:** `5432`
    - **Username:** `diego`
    - **Password:** `diego`
3. Conéctate al servidor PostgreSQL.
4. Ejecuta el siguiente script SQL para crear las bases de datos necesarias:

```sql
CREATE DATABASE product;
CREATE DATABASE order;
CREATE DATABASE payment;
```

## Activar migración automática de tablas

Una vez creadas las bases de datos, al momento de levantar cada uno de los microservicios (`product`, `order` y `payment`), gracias a la configuración en los archivos `application.yml` (por ejemplo, con la propiedad `spring.jpa.hibernate.ddl-auto=none`), Spring Boot detectará los cambios en las entidades y **creará o actualizará automáticamente las tablas necesarias** en cada base de datos.

Esto significa que **no es necesario crear manualmente las tablas**: sólo con iniciar los microservicios, la migración se ejecutará automáticamente y tendrás la estructura de tablas lista para operar.

### Orden para levantar los microservicios

Para que el sistema funcione correctamente, es importante levantar los servicios en el orden adecuado:

1. **Primero** levanta el **Config Server**  
   Este servicio provee la configuración centralizada para todos los microservicios.

2. **Luego** levanta el **Discovery Server (Eureka Server)**  
   Este servicio permite el registro y descubrimiento dinámico de los microservicios.

3. **Después** levanta cada uno de los microservicios específicos (`product`, `order`, `payment`, etc.)

---

### ¿Qué hacer si un servicio falla al iniciar?

- Puede suceder que algún servicio no se conecte correctamente al **Config Server** o al **Discovery Server** porque aún no están completamente activos.
- En ese caso, intenta:
    - **Volver a levantar el servicio** que falló.
    - **Esperar alrededor de 1 minuto** para que los servicios centrales (Config Server y Discovery Server) terminen de arrancar e igualmente con los demás servicios, esperar un minuto como promedio.
    - Luego prueba nuevamente llamar al servicio usando **Postman** o **Swagger UI** para verificar que esté funcionan

## URLs de Swagger UI por microservicio

| Microservicio     | Base de Datos     | Puerto | URL Swagger UI                       |
|-------------------|-------------------|--------|------------------------------------|
| Customer Service   | MongoDB           | 8090   | http://localhost:8090/swagger-ui.html  |
| Payment Service    | PostgreSQL (payment) | 8060   | http://localhost:8060/swagger-ui.html  |
| Product Service    | PostgreSQL (product) | 8050   | http://localhost:8050/swagger-ui.html  |
| Order Service      | PostgreSQL (order)   | 8070   | http://localhost:8070/swagger-ui.html  |

---

### Notas

- El path base para la documentación JSON de la API es:  
  `/v3/api-docs`  
  Por ejemplo, para el Order Service:  
  `http://localhost:8070/v3/api-docs`

- La UI de Swagger se accede a través del path:  
  `/swagger-ui.html`

- Asegúrate que cada servicio esté corriendo en el puerto asignado para poder acceder correctamente a la documentación.

---

### Resumen de configuración de puertos

- Customer Service → puerto **8090**
- Payment Service → puerto **8060**
- Product Service → puerto **8050**
- Order Service → puerto **8070**
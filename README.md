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


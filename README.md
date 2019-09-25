
# BruGroup

Brugroup es una supuesta gestión de usuarios utilizando diferentes protocolos de comunicación y persistiendo los datos en una Bases de Datos, además, de tener una aplicación web. 

Destacar que los diferentes proyectos se han divididos en tres repositorios, según las tecnologías empleadas:
1. Brugroup-back-spring
2. Brugroup-back-node
3. Brugroup-front


# ARQUITECTURA
El ejemplo de la aplicación que se ha desarrollado es una simulación de una arquitectura orientada a microservicios. La idea es dividir la aplicación en partes funcionales, sin embargo, hay que destacar que es una aplicación demo. 

![image](https://drive.google.com/uc?export=view&id=10jEYNoFteJhcaDJL8iI4y_f84o49TAk6)

A continuación, se detallan los diferentes servidores que forman la aplicación:

- **Bases de Datos HyperSQL**

Para ofrecer persistencia a la aplicación se ha utilizado el Sistema Gestos de Bases de Datos (HSQLDB).


- **Servidor Eureka**

Eureka es un servicio REST cuyo objetivo principal es registrar y localizar microservicios existentes, informar de su localización, su estado y datos relevantes de cada uno de ellos. Además, nos facilita el balanceo de carga y tolerancia a fallos.


En la dirección [http://localhost:8761] se puede acceder al cliente web del servidor para observar los diferentes servicios desplegados y el estado de cada uno de ellos

- **Servidor SOAP**

Este servidor despliega unos Web Service para poder ejecutar llamadas a procecimiento remoto (RPC). Estas llamadas permiten realizar un CRUD directamente con acceso a la Bases de Datos y atacar a la entidad de Usuario. 

Por otra parte, este servidor genera dinámicamente un fichero WSDL [http://localhost:8080/ws/users.wsdl] que se encuentra protegido con usuario "userws" y contraseña "passW1ord". A partir de este fichero WSDL se podrá generar de forma sencilla un cliente, por ejemplo, utilizando *SOAP UI*.

- **Servidor REST**

Este servidor despliega una API REST que permite realizar un CRUD directamente con acceso a la Bases de Datos y atacar a la entidad de Usuario. Además, todos los servicios estarán duplicados, unos atacan directamente a la BD y otros al servidor SOAP. 

Por otra parte, este protocolo al no generar ningún sistema de información de forma automático para poder saber cómo atarlo, se ha añadido la configuración de *Swagger* con toda la documentación necesaria para atarcale. La web de swagger se despliega en [http://localhost:9090/swagger-ui.html]


- **Servidor GraphQL**

Graphql es un lenguaje de consulta que se plantea como alterativa a REST para comunicarse un cliente con servidor. La ventaja es que nos permite recuperar una respuesta predecible

Este servidor despliga una aplicación Graphql y un cliente *GraphiQL* [http://localhost:3000/api] para entornos de desarrollo donde poder lanzar las diferentes consultas. 

# Brugroup-back-spring

## Tecnologías empleadas
En el desarrollo del back de la aplicación se ha empleado el framework **Spring Boot** con **Java 8**. 

Por otra parte, se ha configurado diferentes entornos para poder apuntar a las Bases de Datos correspondientes. Además, para la configuración de la bases de datos se ha empleado **Liquibase**.


Por último, la API REST definida con Spring se ha documenta utilizando el framework **Swagger**. 

## Requisitos

- Apache Maven 3 [https://maven.apache.org]
- JDK 1.8 [https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html]
- HyperSQL [http://hsqldb.org/]

##  Bases de Datos

El proyecto incluye una imagen docker con la configuración y la Bases de Datos necesaria para lanzar la aplicación. Para ellos es necesario tener instalado *docker* y *docker-compose*.

```
cd docker
docker-compose up
```

Para poder crear las diferentes tablas y usuarios  se puede utilizar el plugin de liquibase.

```
cd brugroup-repository
mvn install liquibase:update
```

Destacar que este plugin permite gener los scripts SQL con el siguiente comando.

```
mvn  liquibase:update
```


# Construir proyecto
Para compilar todos los proyectos y constuir los ejecutable hay que lanzar el siguiente comando sobre la raíz del proyecto.  Actualmente, elijirá el perfil por defecto ya que todos los perfiles apuntan a la misma BD y realizan las mismas tareas.


```
mvn clean install
```



# Desplegar proyecto

Una vez compilado y generado los fichero "war" de las aplicaciónes, se podrán lanzar los siguientes comando para lanzar los servidores embebidos que tiene Srpring Boot. 

 1. EurekaApplication

```
cd brugroup-eureka
mvn spring-boot:run
```

2. WSApplication

```
cd brugroup-eureka
mvn spring-boot:run

```

3. RestApplication

```
cd brugroup-eureka
mvn spring-boot:run
```




## Diseño del back con Spring Boot

La aplicación se ha diseñado en modelo dividido en tres capas. Este modelo garantiza que sea modularizable y se pueda extraer y modificar la lógica de una capa sin necesidad de modificar las demás.
 Esto se consigue gracias al uso de iterfaces. Por ello el padre únicamente sabrá la interfaz de la capa hija. 


Destacar que la propia aplicación actúa de servidora (REST y SOAP) y de cliente para consumir servicios (SOAP). 

![image](https://drive.google.com/uc?export=view&id=1qBOOoAHa3U6fkieWP5xv1besXXvoB9vD)

- **Modelo**

Es el paquete donde se almacena todos los objetos que contendrán los datos de la aplición. Además, se ha añadido el patrón *converter* para ofrecer la API al exterior únicamente la información que interese. 

- **Controlador SOAP y REST**

El objetivo es conectar el backend con solicitudes http que se hagan desde afuera de la aplicación. Por lo tanto, esta capa se comunicará  con la de Service para conseguir dicha comunciación. 

- **Service**

Este paquete tiene las interfaces y su implementación que contiene la lógica de toda la aplicación.

- **Repository (DAO)**

Es el paquete que contiene las interfaces que extienden de JPA para realizar la conexión con la Bases de Datos. Estas gestionarán toda la información de búsqueda, borrado, actualizado o creación de algún registro en la Bases de Datos. 

Además, en esta capa se ha añadido el módulo de *Liquibase* para controlar los diferentes cambios que se van realizando en la Bases de Datos.

- **Client WS**

De igual forma que funciona la capa de acceso a la Bases de Datos está actúa consumiento recursos de un servidor SOAP. 

# NOTAS

## Gestión de versiones

Para poder trabajar en equipo en este proyecto una solución puede ser usar *GIT* con un workflow parecida a "Git-flow". Las tres ramas principales estarán desplegadas en su propio servidor. Además, todos los proyectos permiten gestionar diferentes perfiles de configuración gracias a Maven y NPM. 

Destacar que cada rama tendrá configurado integración continua para cuando se incluya un nuevo cambio pase todo el flujo necesario para desplegarse de forma autónoma. 

- **Master**

Esta rama será la de producción y, por lo tanto, será el código de la aplicación que está desplegada en el entorno de producción apuntando a los servicios y bases de datos de producción. 

- **Rama de PRE**

Esta rama será la de preproducción y, por lo tanto, será el código de la aplicación que está desplegada en el entorno de preproducción apuntando a los servicios y bases de datos de preproducción. 

Cada vez que se desee desplegar los cambios en producción bastará con integrarlo en la rama Master. Tener en cuenta que se pueden ir generando diferentes *release* e indicar un **tag** de versión en git por si hubiera que volver a una versión anterior. 

- **Rama de Develop**

Esta rama será la de develop y, por lo tanto, será el código de la aplicación que está desplegada en el entorno de develop apuntando a los servicios y bases de datos de develop. 

Esta rama después habría que integrarla con la rama de PRE.

- **Features**

Los diferentes evolutivos se crearán a partir de la rama de Develop y se integrarán en la misma rama. 

- **Hotfix**

Cualquier fallo sucedido en producción se puede crear una rama a partir de *master* y agregarlo a la misma rama. Después estos cambios hay que añadirlo a la rama de *PRE* y *Develop*


Por otra parte, los tres proyectos se puede subdividir en dependencias y subir versiones a un repositorio interno como *Nexus* o *Artifactory*, tanto dependencias Java como Javascript. 



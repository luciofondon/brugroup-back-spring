
## Brugroup

Brugroup es una supuesta gestión de usuarios

# Tecnologías empleadas
En el desarrollo del back de la aplicación se ha empleado el framework **Spring Boot** con **Java 8**. 

Por otra parte, se ha configurado diferentes entornos para poder apuntar a las Bases de Datos correspondientes. Además, para la configuración de la bases de datos se ha empleado **Liquibase**. Destacar que en el entorno por defecto (desarrollo) se ha empleado la Bases de Datos embebida (H2).


Por último, la API REST definida con Spring se ha documenta utilizando el framework **Swagger**. 



# Configuración Bases de Datos embebida H2

http://localhost:8080/h2-console

# SWAGGER

http://localhost:8080/swagger-ui.html

# Instalacción

```
mvn install liquibase:update
```

# Aplicar modificaciones Bases de datos

```
mvn  liquibase:update
```

Modelo en 3 capas


comunicacion hacia abajo por eso interfaces



log4j


 mvn clean install -e -X


auditoria

WORKFLOW GIT tag
# FLUJO DE TRABAJO


# CONSTRUIR PROYECTO
```
mvn clean install
```

```
mvn liquibase:update
```


# DESPLEGAR PROYECTO

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

# ARQUITECTURA
![image](https://drive.google.com/uc?export=view&id=10jEYNoFteJhcaDJL8iI4y_f84o49TAk6)

1. Servidor Eureka
En la dirección [http://localhost:8761] se puede acceder al cliente web del servidor para observar los diferentes servicios desplegados

2. Servidor Soap

3. Servidor REST


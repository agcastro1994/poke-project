# Esta imagen es descartable. Los objetivos son:
# 1. Descargar dependencias
# 2. Compilar
# 3. Testear
# 4. Buildear el jar
FROM maven:3.9.5-amazoncorretto-21 AS build


# Copiamos el pom para exclusivamente descargar las dependencias y que pueda guardase este layer en la cache. 
# [cache-layers]: https://docs.docker.com/develop/develop-images/dockerfile_best-practices/#leverage-build-cache
# HELP: COPY <archivo_de_nuestro_directorio> <destino_dentro_de_la_imagen_*build*>
COPY ./pom.xml /app/pom.xml

# Con el comando WORKDIR cambiamos de directorio
# HELP: WORKDIR <nuevo_directorio_de_trabajo>
WORKDIR /app/

# Descargamos dependencias exclusivamente para que pueda guardarse en la cache si no hubo cambios en el pom
# HELP: RUN <comandos a ejecutar>
RUN mvn dependency:go-offline -B

# Copio exclusivamente el directorio para compilarlo
# HELP: COPY <archivo_de_nuestro_directorio> <destino_dentro_de_la_imagen_*build*>
COPY ./src /app/src

RUN mvn install -Dmaven.test.skip=true dependency:resolve dependency:resolve-plugins

# Buildeamos el jar
# HELP: RUN <comandos a ejecutar>
RUN mvn package spring-boot:repackage

FROM amazoncorretto:21-alpine-jdk AS final


EXPOSE 8081

# En este caso, copiamos de la imagen descartable el jar generado
# HELP: COPY <archivo_de_directorio_de_build> <destino_dentro_de_la_imagen_*final*>
COPY --from=build /app/target/pokefilter-0.0.1-SNAPSHOT.jar app.jar


CMD ["java","-jar","app.jar" ]

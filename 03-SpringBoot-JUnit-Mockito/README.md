# INTEGRACIÓN DE JUNIT5 Y MOCKITO EN SPRING BOOT

## CONFIGURACIÓN

Cuando creamos un proyecto con **Spring Initializr**, agregando la dependencia **Spring Web**, automáticamente ya nos integra la dependencia para trabajar con JUnit5 y Mockito, podemos visualizar la dependencia en el **pom.xml**.

~~~
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
~~~

## JPA TEST

Para realizar pruebas unitarias de repositorios, se recomienda utilizar una Base de Datos en memoria, tal como **h2**. Para poder utilizarla, debemos agregar la dependencia al **pom.xml** especificando el scope: **test**.

~~~
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>test</scope>
</dependency>
~~~

## WEB MVC TEST
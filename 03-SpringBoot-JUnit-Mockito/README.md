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

Cuando realizamos transacciones en la Base de Datos a través de los métodos test, al finalizar cada uno de ellos se realiza por detrás un _rollback_, de tal manera evitando que afecte un método a otro.

## WEB MVC TEST

Nos permite realizar test de controladores, simulando las respuestas y los Http Status Code.

## TEST DE INTEGRACIÓN

Sirven para realizar pruebas unitarias consumiendo API Rest de forma real, es decir, invocan controllers, asu vez estos a los servicios y estos a los repositorios afectando la Base de Datos, por lo que es poco recomendado, al menos no en producción, trabajar con ellos. Existen dos maneras de trabajar con Test de integración: 

* WebTestCliente
* TestRestTemplate
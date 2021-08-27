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


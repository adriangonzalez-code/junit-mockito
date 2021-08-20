# MOCKITO

**Mockito** es un framework de pruebas unitarias que se complementa con JUnit, que nos permite crear objetos simulados (mock) en un entorno controlado y determinado.

Le podemos dar un comportamiento deseado a un mock.

Para trabajar con mockito, tenemos 3 situaciones presentes:

* Dado que: preparamos el contexto, dependencia, parámetros de entrada, etc.
* Cuando: cuando invocamos el método que queremos probar con esos datos o comportamiento simulado.
* Entonces: validamos el proceso o la información que devuelve.

## BDD

Cuando trabajamos con Mockito, aplicamos una metodología de desarrollo llamada: Behavior Driven Development (Desarrollo impulsado a comportamiento).

## DEPENDENCIAS

Para empezar a trabajar con Mockito, debemos agregar las dependencias JUnit y propiamente la de mockito al **pom.xml**:

~~~
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.7.2</version>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>3.11.2</version>
</dependency>
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-junit-jupiter</artifactId>
    <version>3.11.2</version>
</dependency>
~~~
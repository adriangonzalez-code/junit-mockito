# JUNIT 5

JUnit es un framework para escribir pruebas unitarias de nuestro código y ejecutarlas en JVM. Utiliza programación funcional y lambda e incluye varios estilos diferentes de pruebas, configuraciones, anotaciones, ciclo de vida, etc.

## ARQUITECTURA JUNIT 5
  * JUnit Platform
  * JUnit Jupiter
  * Junit Vintage

### JUNIT JUPITER

 * API para escribir nuestros test.
 * Agrega nuevo modelo y características en JUnit 5.
 * Nuevas anotaciones y estilos de testing.
 * Permite escribir extensiones.

 ## ANOTACIONES DE JUNIT JUPITER

 * @Test
 * @DisplayName
 * @Nested
 * @Tag
 * @ExtendWith
 * @BeforeEach
 * @AfterEach
 * @BeforeAll
 * @AfterAll
 * @Disable

## TRABAJANDO CON JUNIT JUPITER

### DEPENDENCIAS MAVEN

Para empezar a trabajar con pruebas unitarias con JUnit, requerimos agregar al `pom.xml` de nuestro proyecto la dependencia de JUnit 5.

~~~
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>X.X.X</version>
</dependency>
~~~

### DEFINICIÓN DE UN MÉTODO TEST

Para indicarle a JUnit que un método lo ejecutará como test, debemos anotar a nivel método con `@Test`

~~~
@Test
void test () {
    ...
}
~~~

### AFIRMACIONES

Existen diferentes formas para afirmar valores esperados evaluando valores reales a través de los **assertions**, se mencionan algunos:

  * assertEquals: afirmar que un valor dado sea el esperado
  * assertTrue: afirmar que un valor booleano esperado sea true
  * assertFalse: afirmar que un valor booleano esperado sea false
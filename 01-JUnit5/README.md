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

## TDD (TEST DRIVEN DEVELOPMENT)

Desarrollo orientado a pruebas unitarias o desarrollo guiado a pruebas, es una técnica de desarrollo de aplicaciones de software que involucra dos partes, primero escribimos nuestros test, métodos test sin código y después implementamos el código del negocio, es decir las reglas del negocio; y por último, mediante refactorización, implementamos los `test methods` definidos en un principio para validar y verificar que se cumplan las reglas del negocio definidas.

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

  * assertEquals: afirmar que un valor dado sea igual al valor esperado
  * assertNotEquals: afirmar que un valor dado no sea igual al valor esperado
  * assertTrue: afirmar que un valor booleano esperado sea true
  * assertFalse: afirmar que un valor booleano esperado sea false
  * assertThrows: afirmar que se dispara una excepción específica o esperada, como primer argumento recibe la clase excepción esperada, y como segundo argumento una expresión lambda `() -> {}` con la lógica del test. **assertThrows** devuelve Exception, donde podemos acceder a los atributos de esa clase y evaluarlos posteriormente.
  * assertAll: ir a **assertAll**

#### assertAll

La expresión `assertAll`, nos permite visualizar todos los assertions que no se cumplieron dentro de un método test, ya que normalmente, cuando se declaran las afirmaciones (assertions), al fallar el primero se detiene en ese punto impidiendo la ejecución del resto de los assertions de ese mismo método, por lo que no nos es posible visualizar si el resto de los assertions también pueden fallar.

La expresión `assertAll`, nos muestra en el reporte todos los assertions que no se cumplieron. Esta expresión recibe como argumento un conjunto de expresiones lambda, donde en cada una de ella se definen los assertions a ejecutar.

#### INTEGRAR MENSAJES EN LOS ASSERTIONS

Todos los assertions, aceptan como último argumento un objeto de tipo String, el cual nos permite agregar un mensaje personalizado para describir de una manera más clara el error producido en caso de que el assertion falle.

Ejemplo:

~~~
assertTrue(exp, "Message");
~~~

Como medida de buena práctica, no es recomendable definir el String literal, ya que, independientemente el assertion falle o no falle, el String por detrás se construye y esto consume recurso, y considerando si tenemos muchos test con muchos assertions, el consumo de recurso será significativo; para ello, se recomienda que en vez de pasar el String literal se pase una expresión lambda, para que así no se construya el String sino solo si el assertion falla.

Por ejemplo:

~~~
assertTrue(exp, () -> "Message");
~~~

### ANOTACIONES ADICIONALES

#### @DisplayName

Esta anotación nos permite agregar mayor información a un método test, información descriptiva a cerca de su funcionalidad, de tal manera que se nos sea más fácil identificar en el reporte.

#### @Disabled

Esta anotación nos sirve para indicarle a JUnit que el test no debe ejecutarse, es decir, se ignora, y en el reporte se muestra como test ignorado.

#### @RepeatedTest

JUnit nos provee una característica que nos permite ejecutar los test una cierta cantidad de veces que deseemos. Cuando especificamos la anotación **@RepeatedTest** en un método test ya no debemos escribir la anotación **@Test**.

### CICLO DE VIDA

El ciclo de vida es el proceso en el cual se crea una instancia, se procesa y se destruye. Un punto a tener en cuenta, es que en JUnit, por cada método de test, se va a tener una instancia distinta, es decir, por cada método se va a crear una nueva instancia de la clase.

#### ANOTACIONES DEL CICLO DE VIDA

##### @BeforeAll

Se ejecuta antes de todos los métodos, se ejecuta una sola vez durante la ejecución de nuestra prueba unitaria. Es un método que se ejecuta antes de crear la instancia de la clase, por lo que debe ser declarado como static.

##### @BeforeEach

Se ejecuta antes de la invocación de cada método test.

##### @AfterEach

Se ejecuta después de la invocación de cada método test.

##### @AfterAll

Se ejecuta una sola vez al finalizar la prueba unitaria. Es un método que se ejecuta antes de crear la instancia de la clase, por lo que debe ser declarado como static.

#### HOOKS O EVENTOS

Es un mecanismo para ejecutar algún código específico en cierto punto del ciclo de vida.

#### ESTABLECER ORDEN EN LOS TEST

Aunque no es buena práctica establecer un orden en los test, podemos ordenarlos de acuerdo a nuestras necesidades, para ello, a nivel clase, debemos anotar con:

~~~
TestInstance(TestInstance.Lifecycle.PER_METHOD)
class ClassTest {
    ...
}
~~~

### TEST CONDICIONALES

Las condicionales son pruebas unitarias que se van a ejecutar en cierto escenario, contexto o configuraciones, se mencionan algunos:

* **@EnabledOnOs**: Ejecutar el test en cierto(s) sistema(s) operativo(s), podemos especificar un S.O., o mediante arreglo `{}` un conjunto de S.O. 
* **@DisabledOnOs**: Deshabilitar el test en cierto(s) sistema(s) operativo(s), podemos especificar un S.O., o mediante arreglo `{}` un conjunto de S.O.
* **@EnabledOnJre**: Ejecutar el test en cierta(s) version(es) de Java, podemos especificar una versión, o mediante arreglo `{}` un conjunto de versiones.
* **@DisabledOnJre**: Deshabilitar el test en cierta(s) version(es) de Java, podemos especificar una versión, o mediante arreglo `{}` un conjunto de versiones.
* **@EnabledIfSystemProperty**: Ejecutar test cuando una propiedad del sistema dada tenga un valor determinado. Para crear una nueva property en el arranque de la aplicación, escribimos `-DENV=DEV` en *Build and Run*
* **@DisabledIfSystemProperty**: Deshabilitar test cuando una propiedad del sistema dada tenga un valor determinado.
* **@EnabledIfEnvironmentVariable**: Ejecutar test cuando una variable de ambiente dada tenga un valor determinado. Para crear una nueva variable en el arranque de la aplicación, escribimos `ENVIRONMENT=DEV` en *Environment Variables*
* **@DisabledIfEnvironmentVariable**: Deshabilitar test cuando una variable de ambiente dada tenga un valor determinado.

### ASSUMPTIONS

Nos sirven para evaluar una expresión booleana de forma programática, y con ella podemos habilitar o deshabilitar una prueba unitaria, un método o parte de un método.

La sintaxis para utilizar *assumptions*:

* Los *asserts* que se ejecutarán son aquellos que se encuentren definidos antes del método **assumeTrue**, los que están después de ese método, se ejecutarán si la expresión que se está evaluando dentro del **assumeTrue** es *true*, ó en su caso *false* si se llama al método **assumeFalse**. 

    ~~~
    @Test
    void test() {
        boolean esDev = "DEV".equals(System.getProperty("ENV"));
        ...
        assertNotNull(this.cuenta);
        assumeTrue(esDev);
        assertNotNull(this.cuenta.getSaldo());
        ...
    }
    ~~~
  
* Otra alternativa para especificar los *asserts* a ejecutar si una regla se cumple, sería usando **assumingThat**, que además de recibir la expresión booleana a evaluar, recibe como segundo argumento una expresión lambda donde se especifican los *asserts* a ejecutar si la expresión booleana se cumple.

    ~~~
    @Test
    void test() {
        boolean esDev = "DEV".equals(System.getProperty("ENV"));
        assumingThat(esDev, () -> {
            assertTrue(this.cuenta.getSaldo().compareTo(BigDecimal.ZERO) > 0);
            ...
        });
        assertNotNull(this.cuenta.getSaldo());
        ...
    }
    ~~~
  
### CLASES ANIDADAS

Esta característica que nos provee JUnit nos permite ordenar y organizar nuestros métodos de *test* pero dentro de una clase anidada, en Java se le conoce como *inner class*, una clase que está dentro de otra clase.

Para poder utilizar clases anidadas, debemos anotar a nivel clase con **@Nested**.

~~~
public class NestedClassTest {

    @Nested
    @DisplayName("Some name class")
    class FirstInnerClass {
    
        @Test
        void testOne() {
            ...
        }
    }
    
    @Nested
    @DisplayName("Some name class")
    class SecondInnerClass {
    
        @Test
        void testTwo() {
            ...
        }
    }
    
    ...
}
~~~

**Nota**: El ciclo de vida en clases anidadas, solo funcionan **@BeforeEach** y **@AfterEach** dentro de cada clase anidada pero **@BeforeAll** y **@AfterAll** no, por lo que esos últimos deben declararse en la clase principal.

### PRUEBAS PARAMETRIZADAS

Es una característica que nos provee JUnit 5 para pasar o enviar por medio de parámetros (source) valores o datos al método test y así poder ejecutar varios escenarios en un solo método test. Tienen similitud con las pruebas repetidas (@RepeatedTest) en que por cada dato que se provee como parámetro es una ejecución de ese test.

Para declarar un Parametrized Test, debemos invocar la anotación `@ParameterizedTest` en lugar de `@Test`, seguido de la anotación del recurso de los datos, dependiendo de dónde se obtendrán los datos para el test utilizaremos una de las siguientes anotaciones:

* @ValueSource
* @ArgumentsSource
* @CsvFileSource
* @CsvSource
* @EmptySource
* @EnumSource
* @MethodSource
* @NullAndEmptySource
* @NullSource
* @ArgumentsSources

~~~
@ParameterizedTest(name = "number {index} with value {0} - {argumentsWithNames}")
@ValueSource(ints = {100, 200, 300, 500, 700, 1000})
void myTestMethod(int value) {
    ...
}
~~~

### TAGGING

Podemos categorizar nuestras pruebas a través de etiquetas, esta característica nos sirve para ejecutar nuestras pruebas de forma selectiva, es decir, podemos ejecutar ciertos métodos que tengan ciertas etiquetas. Las etiquetas se pueden aplicar a nivel método o a nivel clase.

Para etiquetar se usa:

~~~
@Tag("name tag")
@Test
void testMethod() {
  ...
}
~~~

Para ejecutar test con etiqueta en particular, en las opciones de arranque, en *build and run* seleccionamos *tag* y escribimos el nombre del tag.

### INYECCIÓN DE DEPENDENCIAS, COMPONENTES **TestInfo** Y **TestReporter**

JUnit nos provee unas interfaces a través de la inyección de dependencias que podemos utilizar para darle una mayor funcionalidad a nuestros test, las cuales son **TestInfo** y **TestReporter**.

* **TestInfo** nos provee información del test en ejecución, nombre del método, tags, displayName, etc. Para poder utilizar esa interfaz, solo debemos especificarla como argumento de nuestro método test.

  ~~~
  @Test
  void testMethod(TestInfo testInfo) {
    ...
  }
  ~~~

* **TestReporter** nos sirve para imprimir información en el log de JUnit de manera informativa, con timestamps.

  ~~~
  @Test
  void testMethod(TestReporter testReporter) {
    testReporter.publishEntry("Your entry");
  }
  ~~~
  
### TIMEOUT

Podemos disparar un error cuando una prueba unitaria haya tardado una cierta cantidad de tiempo en terminar de ejecutarse. Mencionaré 3 formas de manejar los timeouts.

~~~
@Test
@Timeout(1)
void pruebaTimeout() throws InterruptedException {
    ...
}

~~~

~~~
@Test
@Timeout(value = 1000, unit = TimeUnit.MILLISECONDS)
void pruebaTimeout2() throws InterruptedException {
    ...
}
~~~

~~~
@Test
void testTimeoutAssertions() {
    assertTimeout(Duration.ofSeconds(5), () -> {
        ...
    });
}
~~~

### MAVEN SUREFIRE PLUGIN

Es un plugin que nos permite ejecutar nuestras pruebas unitarias desde la terminal cuando no tenemos instalado un IDE. Para ello, debemos agregamos el plugin en la sección de *plugins* en el **pom.xml**

~~~
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.22.2</version>
</plugin>
~~~

Y en la terminal, desde la raíz del proyecto, ejecutamos el comando:

  > mvn test

Si deseamos ejecutar test con ciertas etiquetas, configuramos en el **pom.xml**, debajo de *version* agregamos la etiqueta *configuration*

~~~
<configuration>
    <groups>tagName</groups>
</configuration>
~~~
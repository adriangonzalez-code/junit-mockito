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

### TRABAJANDO CON MOCKITO

#### WHEN THEN-RETURN

Como mencioné anteriormente, trabajar con Mockito significa simular (mockear) objetos para definir el comportamiento que deseamos en un método y obtener de este, un resultado esperado.

Para indicarle a Mockito la clase o interfaz a simular (mockear), utilizamos el método `mock()`, que recibe como parámetro la clase o interfaz a mockear, es decir, aquella que se va a simular su comportamiento.

~~~
@Test
void testMethod() {
    MyClassRepository myClass = mock(MyClassRepository.class);
    ...
}
~~~

Para definir el comportamiento de un método de una clase mockeada con `mock()`, utilizamos el método `when()` que recibe como parámetro el nombre del método a simular, y posteriormente llamamos al método `thenReturn()` para definir el valor a retornar tras la llamada al método definido.

~~~
@Test
void testMethod() {
    MyClassRepository myClass = mock(MyClassRepository.class);
    String myReturnData = "My return data after method call"; 

    when(myClass.myMethod()).thenReturn(myReturnData);
    ...
}
~~~

**Notas**: Solo se pueden hacer mocks de métodos que son public o default, pero no de métodos private ni de métodos estáticos ni de métodos final.

#### ANY
A los métodos que son simulados mediante `when()`, que reciben argumentos ciertos valores para ser ejecutados, es recomendable enviarles valores a través de los métodos `any()` en vez de pásarles valores específicos, esto con la finalidad de que la ejecución sea más genérica.

* anyLong()
* anyString()
* anyInt()
* anyShort()
* any(myClass.class)

~~~
@Test
void testMethod() {
    MyClassRepository myClass = mock(MyClassRepository.class);
    String myReturnData = "My return data after method call"; 

    when(myClass.myMethod(anyLong())).thenReturn(myReturnData);
    ...
}
~~~

#### VERIFY

El método `verify` nos permite verificar y confirmar si efectivamente se invocó un determinado método de nuestro **mock**.

~~~
@Test
void testMethod() {
    ...    
    verify(this.myClass).myMethod();
    verify(this.myOtherClass).myOtherMethod(anyLong());
}
~~~

#### INYECCIÓN DE DEPENDENCIAS

Una mejor práctica para inyectar las dependencias, en vez de utilizar el método `mock()`, es inyectando a través de anotaciones.

* `@Mock`: indicamos que cree la instancia de la clase
* `@InjectMocks`: indicamos que cree la instancia de la clase, pero además que inyecte los mocks definidos en esa clase Test que son los que se inyectarán vía constructor a la clase que los requiere.

~~~
class MyClassTest {
    
    @Mock
    private MyFirstClass myFirstClass;

    @Mock
    private MySecondClass mySecondClass;

    @InjectMocks
    private MyPrincipalClass myPrincipalClass;
    
    ...
}
~~~

**Nota importante**: Si nuestra clase que vamos a testear es una interfaz (a la que le estamos inyectando los mocks), va a generar errores al ejecutar los test, esto debido a que la interfaz no se puede instanciar, por lo que se debe especificar la clase del tipo genérico (la implementación).

#### HABILITAR EL USO DE ANOTACIONES

Si inyectamos clases a través de anotaciones, debemos realizar ciertas configuraciones para habilitar el uso de anotaciones:

* Opción 1: definimos en el método del ciclo de vida `@BeforeEach` el método `MockitoAnnotation.openMocks(this)`.

    ~~~
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    ~~~
  
* Opción 2: anotar a nivel clase con `@ExtendWith(MockitoExtension.class)`

    ~~~
    @ExtendWith(MockitoExtension.class)
    class MyClassTest {
        ...
    }
    ~~~
  
    **Nota**: Para que esta cualquiera de estas opciones puedan funcionar, debemos tener agregada la siguiente dependencia en el *pom.xml*:
    
    ~~~
    <dependency>
        <groupId>org.mockito</groupId>
        <artifactId>mockito-junit-jupiter</artifactId>
    </dependency>
    ~~~

#### ARGUMENT MATCHERS

ArgumentMatchers es una característica de Mockito que nos permite conocer si coincide el valor real que se pasa por argumento en un método. Se puede utilizar para asegurarse de que ciertos argumentos se pasen a los Mocks.

Se utiliza con el verify, para verificar, además si se invocó un método, también si se invocó con un valor de argumento en específico.

#### ARGUMENT CAPTOR

Nos permite capturar el valor que se pasa como argumento en un método, para así verificar que sea un valor en específico.

#### do... FAMILY

Es una familia de métodos similar al `when().thenReturn()`, casi con las mismas similitudes, pero con la nueva particularidad que son ideales para testear métodos void.

##### doThrow()

Para testear un método que, en su proceso puede generar una excepción sin devolverla propiamente.

##### doAnswer()

Nos permite generar una respuesta personalizada al testear un método.

#### doCallRealMethod

Nos permite invocar el método real del mock, es decir, ya no es simulado. Para poder utilizar este método, el mock debe ser una clase con implementación, no puede ser una clase abstracta o una interfaz.

#### SPY

Los Spy no son 100% mocks, son híbridos entre el objeto real y el mock, por lo que nos permite invocarlos sin definir un `when()` o simulacro, no tenemos que mockear ningún método, En pocas palabras, cuando invoquemos el método, va a hacer la llamada real a ese método. Para ser mas claros, un Spy es un clon del objeto real pero con características de un mock.

Los Spy requieren ser creados a partir de una clase concreta, no desde una clase abstracta o una interfaz, ya que el Spy realiza llamadas a los métodos reales.

Si queremos simular el proceso de algunos de los métodos de los Spy, es recomendable utilizar la familia de métodos `do...`. 
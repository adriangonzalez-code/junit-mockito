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
  

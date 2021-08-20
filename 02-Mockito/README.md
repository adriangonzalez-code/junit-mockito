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

    when(myClass.myMethod()).thenReturn(muReturnData);
}
~~~

**Notas**: Solo se pueden hacer mocks de métodos que son public o default, pero no de métodos private ni de métodos estáticos ni de métodos final.
# 제네릭(Generics)

<details>

타입 파라미터 정의

이 타입 매개변수는 제네릭을 이용한 클래스나 메소드를 설계할 때 사용된다.

클래스명 옆에 <T> 기호로 제네릭을 붙여줌. 클래스 내부에서 식별자 기호 T 를 클래스 필드와, 메소드의 매개변수의 타입으로 지정되어 있다.

```java
class FruitBox<T> {
    List<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }
}
```

이제 인스턴스화 시키는데 꺾쇠 괄호 안에 지정할 타입명을 할당해주면 지정된 타입으로 모두 변환되어 클래스의 타입이 지정됨

```java
// 제네릭 타입 매개변수에 정수 타입을 할당
FruitBox<Integer> intBox = new FruitBox<>(); 

// 제네릭 타입 매개변수에 실수 타입을 할당
FruitBox<Double> intBox = new FruitBox<>(); 

// 제네릭 타입 매개변수에 문자열 타입을 할당
FruitBox<String> intBox = new FruitBox<>(); 

// 클래스도 넣어줄 수 있다. (Apple 클래스가 있다고 가정)
FruitBox<Apple> intBox = new FruitBox<Apple>();
```
그럼 위 FruitBox< T > 에 지정된 타입을 받아와 T에 적용됨 제네릭 타입 전파라고 하는데 쉽게 구체화라고 부른다.

타입 네이밍이 있는데 암묵적인 규칙으로 쓰이니 참고

< T >	타입(Type) <br>
< E >	요소(Element), 예를 들어 List <br>
< K >	키(Key), 예를 들어 Map<k, v> <br>
< V >	리턴 값 또는 매핑된 값(Variable) <br>
< N >	숫자(Number) <br>
< S, U, V >	2번째, 3번째, 4번째에 선언된 타입

타입 파라미터 생략도 가능한데

```java
FruitBox<Apple> intBox = new FruitBox<Apple>();

// 다음과 같이 new 생성자 부분의 제네릭의 타입 매개변수는 생략할 수 있다.
FruitBox<Apple> intBox = new FruitBox<>();
```
이미 자바는 앞쪽 제네릭 타입을 추론해서 뒤에 적용함 굳이 중복하여 생성자까지 지정해 줄 필요는 없다.

제네릭에서 할당 받는 타입은 기본타입 뿐임 !!!!!

```java
// 기본 타입 int는 사용 불가 !!!
List<int> intList = new List<>(); 

// Wrapper 클래스로 넘겨주어야 한다. (내부에서 자동으로 언박싱되어 원시 타입으로 이용됨)
List<Integer> integerList = new List<>();
```

제네릭 타입 파라미터에 클래스가 타입으로 온다?? 상속을 통해 관계를 맺는 객체 지향 프로그래밍의 다형성 원리가 적용됨

```java
class Fruit { }
class Apple extends Fruit { }
class Banana extends Fruit { }

class FruitBox<T> {
    List<T> fruits = new ArrayList<>();

    public void add(T fruit) {
        fruits.add(fruit);
    }
}

public class Main {
    public static void main(String[] args) {
        FruitBox<Fruit> box = new FruitBox<>();
        
        // 제네릭 타입은 다형성 원리가 그대로 적용된다.
        box.add(new Fruit());
        box.add(new Apple());
        box.add(new Banana());
    }
}
```

이거 상속 받은거 다 쓸 수 있음. ~

또 복수 타입 파라미터도 사용이 가능한데 2개, 3개 얼마든 만들 수 있음 단, 클래스 초기화 단계에서 제네릭 만든 타입 수 대로 넘겨줘야함

```java
import java.util.ArrayList;
import java.util.List;

class Apple {}
class Banana {}

class FruitBox<T, U> {
    List<T> apples = new ArrayList<>();
    List<U> bananas = new ArrayList<>();

    public void add(T apple, U banana) {
        apples.add(apple);
        bananas.add(banana);
    }
}

public class Main {
    public static void main(String[] args) {
    	// 복수 제네릭 타입
        FruitBox<Apple, Banana> box = new FruitBox<>();
        box.add(new Apple(), new Banana());
        box.add(new Apple(), new Banana());
    }
}
```

중첩 타입 파라미터도 가능하다 
```java
public static void main(String[] args) {
    // LinkedList<String>을 원소로서 저장하는 ArrayList
    ArrayList<LinkedList<String>> list = new ArrayList<LinkedList<String>>();

    LinkedList<String> node1 = new LinkedList<>();
    node1.add("aa");
    node1.add("bb");

    LinkedList<String> node2 = new LinkedList<>();
    node2.add("11");
    node2.add("22");

    list.add(node1);
    list.add(node2);
    System.out.println(list);

} //결과는 [aa, bb], [11, 22]로 나옴 
```

그렇다면 제네릭을 왜 사용 하는가?

1. 컴파일 타임에 타입 검사를 통해 예외 방지 <br>

Object로 타입을 선언할 경우 반환된 Object 객체를 다시 원하는 타입으로 일일히 타입 변환을 해야 하며, 런타임 에러가 발생할 가능성도 존재

```java

class Apple {}
class Banana {}

class FruitBox {
    // 모든 클래스 타입을 받기 위해 최고 조상인 Object 타입으로 설정
    private Object[] fruit;

    public FruitBox(Object[] fruit) {
        this.fruit = fruit;
    }

    public Object getFruit(int index) {
        return fruit[index];
    }
}
//사과? 바나나??

public static void main(String[] args) {
    Apple[] arr = {
            new Apple(),
            new Apple()
    };
    FruitBox box = new FruitBox(arr);

    Apple apple = (Apple) box.getFruit(0);
    Banana banana = (Banana) box.getFruit(1);
}

//바나나를 모르고 가져왔네 ClassCastException 발생함

```
Apple 객체 타입의 배열을 FruitBox에 넣었는데, 개발자가 착각하고 Banana를 형변환하여 가져오려고 하였기 때문에 생긴 현상이다. 미리 코드에서 빨간줄로 알려줬으면 좋겠지만 깨끗하게 작동한다. 하지만 제네릭을 사용하면??
잘못된 타입이 사용될 수 있는 문제를 컴파일 과정에서 제거하여 개발을 용이하게 해준다.

```java
class FruitBox<T> {
    private T[] fruit;

    public FruitBox(T[] fruit) {
        this.fruit = fruit;
    }

    public T getFruit(int index) {
        return fruit[index];
    }
}

public static void main(String[] args) {
    Apple[] arr = {
            new Apple(),
            new Apple()
    };
    FruitBox<Apple> box = new FruitBox<>(arr);

    Apple apple = (Apple) box.getFruit(0);
    Banana banana = (Banana) box.getFruit(1); //컴파일 타임에 미리 잘못된거라고 빨간줄 생김 ~!~!
}

```

2. 불필요한 캐스팅을 없애 성능 향상

Apple 배열을 FruitBox의 Object 배열 객체에 넣고, 배열 요소를 가져올때 반드시 다운 캐스팅(down casting)을 통해 가져와야함 오버헤드 발생으로 성능 낭비 심함

```java
//일반으로 코드 짰을떄
Apple[] arr = { new Apple(), new Apple(), new Apple() };
FruitBox box = new FruitBox(arr);

// 가져온 타입이 Object 타입이기 때문에 일일히 다운캐스팅을 해야함 - 쓸데없는 성능 낭비
Apple apple1 = (Apple) box.getFruit(0);
Apple apple2 = (Apple) box.getFruit(1);
Apple apple3 = (Apple) box.getFruit(2);



// 제네릭 사용으로 코드 짰을때
// 미리 제네릭 타입 파라미터를 통해 형(type)을 지정해놓았기 때문에 별도의 형변환은 필요없다.
FruitBox<Apple> box = new FruitBox<>(arr);

Apple apple = box.getFruit(0);
Apple apple = box.getFruit(1);
Apple apple = box.getFruit(2);
```
- 제네릭 타입은 객체 생성이 불가능함 !!! <br>

제네릭 타입 자체로 타입을 지정하여 객체를 생성하는 것은 불가능 한다. 즉, new 연산자 뒤에 제네릭 타입 파라미터가 올수는 없다.
class Sample<T> 에   T t = new T(); 를 못함

- static 멤버에 제네릭 타입이 올수 없음 <br>

static 멤버는 클래스가 동일하게 공유하는 변수로서 제네릭 객체가 생성되기도 전에 이미 자료 타입이 정해져 있어야 하기 때문
```java

 // static 메서드의 반환 타입으로 사용 불가
    public static T addAge(int n) 
 // static 메서드의 매개변수 타입으로 사용 불가
    public static void addAge(T n) {
```

- 제네릭으로 배열 선언 주의점<br>

제네릭 클래스 자체를 배열로 만들 수는 없다. 
```java

class Sample<T> { 
}

public class Main {
    public static void main(String[] args) {
        Sample<Integer>[] arr1 = new Sample<>[10];
    }
}

```

제네릭 타입의 배열 선언은 허용된다.
```java
class Sample<T> { 
}

public class Main {
    public static void main(String[] args) {
    	// new Sample<Integer>() 인스턴스만 저장하는 배열을 나타냄
        Sample<Integer>[] arr2 = new Sample[10]; 
        
        // 제네릭 타입을 생략해도 위에서 이미 정의했기 때문에 Integer 가 자동으로 추론됨
        arr2[0] = new Sample<Integer>(); 
        arr2[1] = new Sample<>();
        
        // ! Integer가 아닌 타입은 저장 불가능
        arr2[2] = new Sample<String>();
    }
}
```

제네릭 클래스 : 클래스 선언문 옆에 제네릭 타입 매개변수가 쓰이면, 이를 제네릭 클래스<br>
class Sample< T > <br>
    Sample<Integer> s1 = new Sample<>(); <br>
     Sample<Double> s2 = new Sample<>();<br>
     
기본 타입들이 올 수 있음.

제네릭 인터페이스 : 인터페이스를 implements 한 클래스에서도 오버라이딩한 메서드를 제네릭 타입에 맞춰서 똑같이 구현

```java

interface ISample<T> {
    public void addElement(T t, int index);
    public T getElement(int index);
}

class Sample<T> implements ISample<T> {
    private T[] array;

    public Sample() {
        array = (T[]) new Object[10];
    }

    @Override
    public void addElement(T element, int index) {
        array[index] = element;
    }

    @Override
    public T getElement(int index) {
        return array[index];
    }
}


public static void main(String[] args) {
    Sample<String> sample = new Sample<>();
    sample.addElement("This is string", 5);
    sample.getElement(5);
}

```

제네릭 함수형 인터페이스: 제네릭 인터페이스가 정말 많이 사용되는 곳이 바로 람다 표현식의 함수형 인터페이스


```java
// 제네릭으로 타입을 받아, 해당 타입의 두 값을 더하는 인터페이스
interface IAdd<T> {
    public T add(T x, T y);
}

public class Main {
    public static void main(String[] args) {
        // 제네릭을 통해 람다 함수의 타입을 결정
        IAdd<Integer> o = (x, y) -> x + y; // 매개변수 x와 y 그리고 반환형 타입이 int형으로 설정된다.
        
        int result = o.add(10, 20);
        System.out.println(result); // 30
    }
}
```

제네릭 메서드 : 메서드의 선언부에 <T> 가 선언된 메서드는 독립적으로 운용 가능한 제네릭 메서드 그러니까 제네릭 클래스에 정의된 타입 매개변수와 제네릭 메서드에 정의된 타입 매개변수는 별개인게 되는 것이다. 제네릭 메서드의 제네릭 타입 선언 위치는 메서드 반환 타입 바로 앞

```java
class FruitBox<T> {
	
    // 클래스의 타입 파라미터를 받아와 사용하는 일반 메서드
    public T addBox(T x, T y) {
        // ...
    }
    
    // 독립적으로 타입 할당 운영되는 제네릭 메서드
    public static <T> T addBoxStatic(T x, T y) {
        // ...
    }
}
```

제네릭 메서드 호출은 
```java
FruitBox.<Integer>addBoxStatic(1, 2);
FruitBox.<String>addBoxStatic("안녕", "잘가");

// 메서드의 제네릭 타입 생략
FruitBox.addBoxStatic(1, 2); 
FruitBox.addBoxStatic("안녕", "잘가");

```

제네릭 타입 범위 한정하기 : 제한된 타입 매개변수 (Bounded Type Parameter) <br><br>
타입 한정 키워드 extends : 기본적인 용법은 <T extends [제한타입]> 이다. 제네릭 <T> 에 extends 키워드를 붙여줌으로써, <T extends Number> 제네릭을 Number 클래스와 그 하위 타입(Integer, Double)들만 받도록 타입 파라미터 범위를 제한 한 것 단, extends 키워드 다음에 올 타입은 일반 클래스, 추상 클래스, 인터페이스 모두 올 수 있다<br>
class Calculator< T extends Number >


</details>

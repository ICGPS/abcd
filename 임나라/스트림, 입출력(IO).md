# 스트림 / 입출력 (IO)

### 스트림(stream) **API**

스트림 API는 데이터를 추상화하여 다루므로, 다양한 방식으로 저장된 데이터를 읽고 쓰기 위한 공통된 방법을 제공합니다.

따라서 스트림 API를 이용하면 배열이나 컬렉션뿐만 아니라 파일에 저장된 데이터도 모두 같은 방법으로 다룰 수 있게 됩니다.

람다식과 함께 사용되어 컬렉션에 들어있는 데이터에 대한 처리를 매우 간결하게 표현으로 작성할 수 있다.

1. 데이터를 담고 있는 저장소 (컬렉션)이 아니다.
2. 스트림은 원본 데이터 소스를 변경하지 않는다.(Read Only)
3. 스트림은 `lterator`처럼 일회용이다. (필요하면 다시 스트림을 생성해야 함)
4. 최종 연산 전까지 중간연산을 수행되지 않는다.(lazy)
5. 무제한일 수도 있다. (Short Circuit 메소드를 사용해서 제한할 수 있다.)
6. 손쉽게 병렬 처리할 수 있다. (멀티 쓰레드 사용) (`.parallel`)
7. **기본형** 스트림으로 `IntStream`, `LongStream`, `DoubleStream`등 제공
    - 오토박싱 등의 불필요한 과정이 생략됨.
    - `Stream<Integer>` 대신에 `IntStream`을 사용하는게 더 효율적이다.
    - 뿐만 아니라 숫자의 경우 더 유용한 메서드를 `Stream<T>`보다 더 많이 제공한다.(`.sum()`, `.averge()` 등)

1. 스트림의 생성
2. 스트림의 중개 연산 (스트림의 변환)
3. 스트림의 최종 연산 (스트림의 사용) 

### 스트림의 생성

스트림 API는 다음과 같은 다양한 데이터 소스에서 생성할 수 있습니다.

1. 컬렉션

2. 배열

3. 가변 매개변수

4. 지정된 범위의 연속된 정수

5. 특정 타입의 난수들

6. 람다 표현식

7. 파일

8. 빈 스트림

****컬렉션 스트림****

컬렉션 타입(*Collection, List, Set*)의 경우 인터페이스에 추가된 디폴트 메소드 `stream` 을 이용해서 스트림을 만들 수 있습니다.

```java
public interface Collection<E> extends Iterable<E> {
  default Stream<E> stream() {
    return StreamSupport.stream(spliterator(), false);
  } 
  // ...
}

List<String> list = Arrays.asList("a", "b", "c");
Stream<String> stream = list.stream();
Stream<String> parallelStream = list.parallelStream(); // 병렬 처리 스트림
```

****배열 스트림****

```java
String[] arr = new String[]{"a", "b", "c"};
Stream<String> stream = Arrays.stream(arr);
Stream<String> streamOfArrayPart = 
  Arrays.stream(arr, 1, 3); // 1~2 요소 [b, c]
```

*****Stream.builder()*****

빌더(*Builder*)를 사용하면 스트림에 직접적으로 원하는 값을 넣을 수 있습니다. 마지막에 `build` 메소드로 스트림을 리턴합니다.

```java
String<String> stream = Stream<String>builder()
                          .add("Apple")
                          .add("Banana")
                          .add("Melon")
                          .build();
```

### **스트림의 중개 연산(intermediate operation)**

스트림 API에 의해 생성된 초기 스트림은 중개 연산을 통해 또 다른 스트림으로 변환됩니다.

이러한 중개 연산은 스트림을 전달받아 스트림을 반환하므로, 중개 연산은 연속으로 연결해서 사용할 수 있습니다.

또한, 스트림의 중개 연산은 필터-맵(filter-map) 기반의 API를 사용함으로 지연(lazy) 연산을 통해 성능을 최적화할 수 있습니다.

스트림 API에서 사용할 수 있는 대표적인 중개 연산과 그에 따른 메소드는 다음과 같습니다.

1. 스트림 필터링 : filter(), distinct()
    
    filter() 메소드는 해당 스트림에서 주어진 조건(predicate)에 맞는 요소만으로 구성된 새로운 스트림을 반환합니다.
    
    또한, distinct() 메소드는 해당 스트림에서 중복된 요소가 제거된 새로운 스트림을 반환합니다.
    
    distinct() 메소드는 내부적으로 Object 클래스의 equals() 메소드를 사용하여 요소의 중복을 비교합니다.
    
    ```java
    IntStream stream1 = IntStream.of(7, 5, 5, 2, 1, 2, 3, 5, 4, 6);
    IntStream stream2 = IntStream.of(7, 5, 5, 2, 1, 2, 3, 5, 4, 6);
    
    // 스트림에서 중복된 요소를 제거함.
    stream1.distinct().forEach(e -> System.out.print(e + " "));
    // 실행결과
    // 7 5 2 1 3 4 6
    System.out.println();
    
    // 스트림에서 홀수만을 골라냄.
    stream2.filter(n -> n % 2 != 0).forEach(e -> System.out.print(e + " "));
    // 실행 결과
    // 7 5 5 1 3 5
    ```
    
2. 스트림 변환 : map(), flatMap()
    
    map() 메소드는 해당 스트림의 요소들을 주어진 함수에 인수로 전달하여, 그 반환값들로 이루어진 새로운 스트림을 반환합니다.
    
    만약 해당 스트림의 요소가 배열이라면, flatMap() 메소드를 사용하여 각 배열의 각 요소의 반환값을 하나로 합친 새로운 스트림을 얻을 수 있습니다.
    
    ```java
    Stream<String> stream = Stream.of("HTML", "CSS", "JAVA", "JAVASCRIPT");
    
    stream.map(s -> s.length()).forEach(System.out::println);
    
    // 실행 결과
    4
    3
    4
    10
    ```
    
3. 스트림 제한 : limit(), skip()
4. 스트림 정렬 : sorted()
5. 스트림 연산 결과 확인 : peek()

### **스트림의 최종 연산(terminal operation)**

스트림 API에서 중개 연산을 통해 변환된 스트림은 마지막으로 최종 연산을 통해 각 요소를 소모하여 결과를 표시합니다.

즉, 지연(lazy)되었던 모든 중개 연산들이 최종 연산 시에 모두 수행되는 것입니다.

이렇게 최종 연산 시에 모든 요소를 소모한 해당 스트림은 더는 사용할 수 없게 됩니다.

스트림 API에서 사용할 수 있는 대표적인 최종 연산과 그에 따른 메소드는 다음과 같습니다.

1. 요소의 출력 : forEach()
    
    반환 타입이 void이므로 보통 스트림의 모든 요소를 출력하는 용도로 많이 사용합니다.
    
    ```java
    Stream<String> stream = Stream.of("넷", "둘", "셋", "하나");
    
    stream.forEach(System.out::println);
    
    // 실행 결과
    넷
    둘
    셋
    하나
    ```
    
2. 요소의 소모 : reduce()
3. 요소의 검색 : findFirst(), findAny()
4. 요소의 검사 : anyMatch(), allMatch(), noneMatch()
5. 요소의 통계 : count(), min(), max()
6. 요소의 연산 : sum(), average()
7. 요소의 수집 : collect()

### **Optional 클래스**

Java 8부터 도입된Optional은 값이 없는 경우를 표현하기 위한 클래스이다.

Optional 클래스는 제네릭을 사용하여 어떤 타입의 객체도 감싸서 담을 수 있다.

Optional 객체는 값이 존재할 수도 있고, 없을 수도 있다.

이는 NullPointerException 예외를 방지할 수 있고, 코드의 안정성을 높이며 가독성을 향상시킨다.

- of() 메서드 : 값이 null이 아닌 경우에만 Optional 객체를 생성
- ofNullable() 메서드 : 값이 null인 경우에도 Optional 객체를 생성
- empty() 메서드 : 값을 갖지 않는 빈(empty) Optional 객체를 생성.

Optional 객체 접근Optional 객체에 접근하기 위해서는 get() 메서드를 사용.

하지만 이 방법은 값이 없는 경우에 예외가 발생할 수 있으므로, isPresent() 메서드를 사용하여값이 존재하는지 여부를 먼저 확인하는 것이 좋다.

또한, Optional 객체에 값이 있을 경우에는orElse()나 orElseGet() 메서드를 사용하여 대체 값을 제공할 수 있다.

```java
import java.util.Optional;

public class OptionalExample {

    public static void main(String[] args) {
        String str = "Hello, World!"; // null이 아닌 값을 가지는 문자열 변수
        Optional<String> optionalStr = Optional.of(str); // Optional 객체 생성, 값이 null이 아니므로 of() 메서드 사용
        System.out.println(optionalStr); // 출력: Optional[Hello, World!]

        String nullStr = null; // null 값을 가지는 문자열 변수
        Optional<String> optionalNullStr = Optional.ofNullable(nullStr); // Optional 객체 생성, 값이 null이므로 ofNullable() 메서드 사용
        System.out.println(optionalNullStr); // 출력: Optional.empty

        Optional<String> emptyOptional = Optional.empty(); // 값을 갖지 않는 빈 Optional 객체 생성
        System.out.println(emptyOptional); // 출력: Optional.empty
    }

}
```

---

### ****입출력(I/O)****

Input & Output
컴퓨터 내부 또는 외부의 장치와 프로그램간에 데이터를 주고받는 것

### ****스트림(stream)****

입출력(I/O)을 하기 위해 필요한 데이터 운반 연결 통로

단방향통신만을 지원하기 때문에 입력과 출력을 동시에 수행해야 하는경우 두 개의 스트림이 필요하다.

*⇒ 입력 스트림(input stream), 출력 스트림(output stream)*


Queue 구조로 되어있다. (FIFO)

| 클래스 | 메소드 | 설명 |
| --- | --- | --- |
| InputStream | abstract int read() | 해당 입력 스트림으로부터 다음 바이트를 읽어들임. |
|  | int read(byte[] b) | 해당 입력 스트림으로부터 특정 바이트를 읽어들인 후, 배열 b에 저장함. |
|  | int read(byte[] b, int off, int len) | 해당 입력 스트림으로부터 len 바이트를 읽어들인 후, 배열 b[off]부터 저장함. |
| OutputStream | abstract void write(int b) | 해당 출력 스트림에 특정 바이트를 저장함. |
|  | void write(byte[] b) | 배열 b의 특정 바이트를 배열 b의 길이만큼 해당 출력 스트림에 저장함. |
|  | void write(byte[] b, int off, int len) | 배열 b[off]부터 len 바이트를 해당 출력 스트림에 저장함. |

read() 메소드는 해당 입력 스트림에서 더 이상 읽어들일 바이트가 없으면, -1을 반환해야 합니다.

그런데 반환 타입을 byte 타입으로 하면, 0부터 255까지의 바이트 정보는 표현할 수 있지만 -1은 표현할 수 없게 됩니다.

따라서 InputStream의 read() 메소드는 반환 타입을 int형으로 선언하고 있습니다.

### 바이트 기반 스트림

자바에서 스트림은 기본적으로 바이트 단위로 데이터를 전송합니다.

| 입력 스트림 | 출력 스트림 | 입출력 대상 |
| --- | --- | --- |
| FileInputStream | FileOutputStream | 파일 |
| ByteArrayInputStream | ByteArrayOutputStream | 메모리 |
| PipedInputStream | PipedOutputStream | 프로세스 |
| AudioInputStream | AudioOutputStream | 오디오 장치 |

### 보조 스트림

자바에서 제공하는 보조 스트림은 실제로 데이터를 주고받을 수는 없지만, 다른 스트림의 기능을 향상시키거나 새로운 기능을 추가해 주는 스트림입니다.

| 입력 스트림 | 출력 스트림 | 설명 |
| --- | --- | --- |
| FilterInputStream | FilterOutputStream | 필터를 이용한 입출력 |
| BufferedInputStream | BufferedOutputStream | 버퍼를 이용한 입출력 |
| DataInputStream | DataOutputStream | 입출력 스트림으로부터 자바의 기본 타입으로 데이터를 읽어올 수 있게 함. |
| ObjectInputStream | ObjectOutputStream | 데이터를 객체 단위로 읽거나, 읽어 들인 객체를 역직렬화시킴. |
| SequenceInputStream | X | 두 개의 입력 스트림을 논리적으로 연결함. |
| PushbackInputStream | X | 다른 입력 스트림에 버퍼를 이용하여 push back이나 unread와 같은 기능을 추가함. |
| X | PrintStream | 다른 출력 스트림에 버퍼를 이용하여 다양한 데이터를 출력하기 위한 기능을 추가함. |

```java
FileInputStream fis = new FileInputStream("test.txt");

BufferedInputStream bis = new BufferedInputStream(fis);
bis.read();
```

### 문자 기반 스트림

자바에서 스트림은 기본적으로 바이트 단위로 데이터를 전송합니다.

하지만 자바에서 가장 작은 타입인 char 형이 2바이트이므로, 1바이트씩 전송되는 바이트 기반 스트림으로는 원활한 처리가 힘든 경우가 있습니다.

따라서 자바에서는 바이트 기반 스트림뿐만 아니라 문자 기반의 스트림도 별도로 제공합니다.

이러한 문자 기반 스트림은 기존의 바이트 기반 스트림에서 InputStream을 Reader로, OutputStream을 Writer로 변경하면 사용할 수 있습니다.

| 입력 스트림 | 출력 스트림 | 입출력 대상 |
| --- | --- | --- |
| FileReader | FileWriter | 파일 |
| CharArrayReader | CharArrayWriter | 메모리 |
| PipedReader | PipedWriter | 프로세스 |
| StringReader | StringWriter | 문자열 |

문자 기반의 보조 스트림

| 입력 스트림 | 출력 스트림 | 설명 |
| --- | --- | --- |
| FilterReader | FilterWriter | 필터를 이용한 입출력 |
| BufferedReader | BufferedWriter | 버퍼를 이용한 입출력 |
| PushbackReader | X | 다른 입력 스트림에 버퍼를 이용하여 push back이나 unread와 같은 기능을 추가함. |
| X | PrintWriter | 다른 출력 스트림에 버퍼를 이용하여 다양한 데이터를 출력하기 위한 기능을 추가함. |

### **표준 입출력(Standard I/O)**

자바에서는 콘솔과 같은 표준 입출력 장치를 위해 System이라는 표준 입출력 클래스를 정의하고 있습니다.

| 클래스 변수 | 입출력 스트림 | 설명 |
| --- | --- | --- |
| System.in | InputStream | 콘솔로부터 데이터를 입력받음. |
| System.out | PrintStream | 콘솔로 데이터를 출력함. |
| System.err | PrintStream | 콘솔로 데이터를 출력함. |

표준 입출력 스트림은 자바가 자동으로 생성하므로, 개발자가 별도로 스트림을 생성하지 않아도 사용할 수 있습니다.
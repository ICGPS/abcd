
# 스트림

## 스트림이란

    1.)데이터 소스가 무엇이든 간에 같은 방식으로 다룰수 있게 데이터를 추상화 하고  
    데이터를 다루는데 자주 사용되는 메서드들을 정의해 놓은것들이다.   
    2.) 데이터 소스에 상관없이 사용되며 코드의 재사용성이 높아진다.   
    3.) 배열이나,컬렉션 뿐 아니라 파일에 서도 다룰수있다.


-----------------------
    
- 스트림은 데이터 소스를 변경하지 않는다.  
    스트림은 데이터 소스를 읽을뿐 변경하지 않는다.

- 스트림은 일회용이다.
    - 스트림은 재사용 할수 없다.
    - 다시 만들어야 쓸수있다.
    
- 스트림은 작업을 내부에서 반복 처리한다.
    - 내부 반복이라는 것은 반복문을 메서드 내부에 숨길 수 있다.

    ```jdava
    void forEach(Consumer<? super T> action) {
        Objects.requireNonNull(action); // 매개변수의 널 체크
        
        for(T t : src) {
            action.accept(T);
        }
    }
    ```
- 스트림의 연산
    - 중간연산  
        - 연산 결과가 스트림인 연산, 스트림에 연속해서 중간 연산할 수 있다.   
    - 최종연산
        - 연산 결과가 스트림이 아닌 연산. 스트림의 요소를 소모하므로 단 한번만 가능하다.
            - forEach()
            ```
            forEach() 는 Peek()와 달리 스트림의 요소를 소모하는 최종 연산이다.   
            반환 타입이 void이므로 스트림의 요소를 출력하는 용도로 사용된다.   
            ```

    ```java
    stream.distinct().limit(5).sorted().forEach(x -> System.out.println(x));
      
    distinct(), limit(5), sorted() - 중간연산
    forEach - 최종연산
    ```
    - 지연된연산 이 있다.
        - 최종 연산이 수행되기 전 까지는 중간 연산이 수행되지 않는다.   
        - 중간 연산을 호출하는 것은 단순히 어떤 작업이 수행 되어야 하는 지를 징정해주는 것이다.   
        - 최종 연산이 수행 되어야 스트림의 요소들이 중간 연산을 거처 최종 연산에서 소모된다. 

    - 기본 자료형을 다루는 스트림
        - 요소의 타입이 T 인 스트림은 기본적으로 Stream인데, 기본 자료형을 하기 위해서는 오토박싱&언박싱이 발생하여 비효율성이 증가한다. (Integer <-> int)
        - 비효율성을 줄이기 위해 데이터 소스의 요소를 기본형으로 다루는 스트림임 있다.
            - IntStream, LongStream, DoubleStream
        - 기본자료형에 유용하게 사용할 수 있는 메서드를 제공한다.

    - 스트림 종류
        - 컬렉션  
        ```java
        Stream<T> Collection.stream()
        ```
        ```java
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        Stream<Integer> intStream = list.stream();

        intStream.forEach(i -> System.out.println(i));    
        // 또는 메서드 참조 방식으로 람다식 정의 intStream.forEach(System.out::println);
        ```
        - 배열
        ``` java
        Stream<T> Stream.of(T... values) // 가변인자
        Stream<T> Stream.of(T[])
        Stream<T> Arrays.stream(T[])
        Stream<T> Arrays.stream(T[] array, int startInclusive, int endExclusive)  // startInclusive이상 endExclusive 미만 범위의 스트림 생성


        ```
        - 임의의수
            - 랜덤 클래스 난수들로 이루어짐
        - 람다식 iterate(), generate()
            - 람다식을 매개변수로 받아서 람다식에 의해 계산되는 값들을 요소로 하는 무한 스트림을 생성
        - 빈 스트림
            - 요소가 하나도 없는 비어있는 스트림
            - 스트림에서 연산을 수행한 결과가 하나도 없을 때, null 보다 빈 스트림을 반환하는 것이 좋다.
    
        - 두 스트림의 연결 - (concat())
        - 스트림의 중간 연산
            - 스트림 자르기 skip(), limit()
            - 스트림 요소 걸러내기 filter(), distinct()
            - 졍렬 sorted()
            - 변경 map()
            - 조회 peek()
        - mapToInt(),mapToLong(),mapToDouble()
            - map() 은 연산결과로 스트림 을 반환하지만 기본 자료형인 int,long,double 기본 스트림을 반환 한다.

        - flatMap() - Stream<T[]> 스트림 변환
            - 스트림의 타입이  <T[]> 인 경우 스트림로 변환해 준다.
        
    - Optional와 OptionalInt
        - Optional 은 제네릭 클래스 T 타입 래퍼 클래스다.
        - OPtionl 타입의 객체에는 모든 타입의 참조변수를 담을수 있다.
            - 스트림에서 최종 연산결과를 Optional 객체에 담아서 반환
            - 객체에 담아서 반환을 하므로 결과가 null 인지 간단한 체크하는 메서드 제공
            - if로 따로 체크하지 않아도 NullPointException이 발생하지 않는 보다 간결하고 안전한 코드 작성이 가능해 진다.  


---------------
# 입출력  IO

    - 입출력이란? (I/O)
    Input과 Output의 으로 간단히 입출력이라고 한다.
    - 입출력은 컴퓨터 내부 또는 외부와 프로그램간의 데이터를 주고 받는것을 말한다.
    - 키보드로부터 데이터를 입/출력을 한다는 것이다.

## 입출력 스트림
  - 자바에서 입출력 스트림이란?
      - 두 개의 대상을 연결하고 데이터를 전송할수 있는 무언가, 이것을 스트림이라고 정의한다.
    - 스트림은 데이터를 연결하는 연결 통로이다.
        - 스트림은 단방향 통신만 가능하다. 입력 또는 출력 한가지!
        - 입출력 동시에 하기위해서는 2개의 스트림을 필요로 한다.
    - 바이트기반 스트림
        바이트 단위로 데이터를 전송하며 입출력 대상에 따라 다음과 같은 입출력 스트림이 있다.   
        종류
        입력 스트림
        ```
        FileInputStream,ByteArrayInputStream,PipedInputStream,AudioInputStream
        ```
        출력 스트림
        ```
        FileOutputStream,ByteArrayOutputStream,PipedOutputStream,AudioOutputStream
        ```
        - 모두 InputStream 과 OutputStream의 하위 클래스이며, 각각 읽고 쓰는데 필요한 추상 메서드를 자신에 맞게 구현했다.
        - java.io 패키지를 통해서 많은 종류의 입출력 클래스들을 제공하고 있고    
        입출력 대상이 달라져도 동일한 방법으로 입출력이 가능하기 떄문에 편하다.

    ## 보조 스트림
        - 보조 스트림은 실제 데이터를 주고 받는 스트림이 이닌 스트림 기능을 향상 시키거나 기능을 추가 하는 것.
        - 보조 스트림만으로는 입출력을 할수 없다.
    
    ## 문자기반 스트림 ##
     문자데이터를 입출력할 때는 바이트기반 스트림대신 문자기반 스트림을 사용한다.
    - inputStream -> Reader
    - OutputStream -> Writer
        
    ## 표준 입출력과 File ##
    - 표준 입출력은 콘솔을 통한 입출력을 말한다.
    - 자바 표준 입출력System.in,System.out,System.err가 제공된다.

    # 직렬화 #
    객체를 컴퓨터에 저장했다가 다음에 꺼낼수 없는지, 네트워크를 통해 컴퓨터간에 서로 객체를 주고받을 일을 해주는 것이 직렬화다.

    - 직렬화란?
        - 객체를 데이터 스트림으로 만드는 것을 뜻한다.
        - 객체에 저장된 데이터를쓰기 위해 연속적인 데이터로 변환하는 것이다.
        - 스트림으로부터 데이터를 읽어 객체를 만드는것 역 직렬화.
        - 객체는 클래스에 정의된 인스턴스 변수 집합이다.
        - 객체에는 클래스변, 메소드가 포함되지 않는다.
        - 객체를 저장한다는 것은 객체의 인스턴스변수 값을 저장하는것이다.
        - 저장된 객체를 다시 생성하려면, 객체를 생성후 값을 읽어 인스턴스 변수에 저장하는 것이다.

        - 객체를  직렬화/역진렬화는 객체의 모든 인스턴스를 참조하기 떄문에 오래 걸린다.
        - 직렬화 할때에 버전이 다르다면 이름이 같더라고 에러 예외가 발생할수 있다.




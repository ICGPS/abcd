# 컬렉션 프레임워크(Collection Framework)

<details>
 
## 컬렉션 프레임워크(Collection Framework) ##  
 컬렉션(Collection) : 데이터 군집, 자료의 모음
 프레임워크(Framework) : 표준화된 설계
 따라서 컬렉션 프레임워크는 데이터 군을 저장하는 클래스등을 표준화 한 설계이다.  
  - 컬렉션(다수의 데이터)를 다루는데 필요한 클래스들을 제공    

#### Iterator(반복자) 인터페이스 ####  
 데이터 군집을 순회하는 역할  
 컬렉션 내의 요소에 순차적으로 접근하는 방법 제공  
 <br>

 주요 메소드  
 - iterator() : 컬렉션의 처음 요소부터 끝 요소까지 접근 가능
<pre>
<code>
Collection<String> myCollection = new ArrayList<>();
Iterator<String> iterator = myCollection.iterator();
</code>
</pre>  
<br>

 - boolean hasNext() : 현재 위치에서 다음 요소가 있는지 확인, 있으면 true 없으면 false 반환  
 <pre>
 <code>
 while (iterator.hasNext()) {
    // 다음 요소가 있는 경우에만 아래로 이동(없으면 이동하지 않음)
    // ...
}
 </code>
 </pre> 
 <br>

 - E next() : 다음 요소를 반환, boolean hasNext를 통해 다음 요소가 있는지 확인 하는 것이 안전  
 <pre>
 <code>
 while (iterator.hasNext()) {
    String element = iterator.next();
    // 현재 요소(element)에 대한 작업 수행
    // ...
}
 </code>
 </pre>
 <br>

 #### Iterator의 이러한 메소드들을 통해 컬렉션의 모든 요소에 접근하고 조작 가능  
 #### 또한, 컬렉션의 내부 구조에 독립적으로 접근할 수 있게 해주어 코드의 일관성과 재사용성을 높인다.  

### 컬렉션 프레임워크의 핵심 인터페이스 ###  
 크게 List, Set, Map 3가지로 구분되고, 이 중 List와 Set을 많이 사용한다.

 ### List  
 #### 순서가 있는 데이터의 집합, 데이터의 중복을 허용  
 - 구현클래스 : ArrayList, LinkedList, Stack, Vector 등  

 ### Set  
 #### 순서를 유지하지 않는 데이터의 집합, 데이터의 중복을 허용하지 않음  
 - 구현클래스 : HashSet, TreeSet 등  

 ### Map  
 #### 키(key)와 값(value)의 쌍으로 이루어진 데이터의 집합  
 - 키란, 데이터 집합 중에서 어떤 값을 찾는데 열쇠가 된다는 의미에서 붙여진 이름이기 때문에 중복을 허용하지 않는다.  
 - 구현클래스 : HashMap, TreeMap, Hashtable, properties

![상속계층도](./images/3-1.jpg)  

인터페이스의 List와 Set을 구현한 컬렉션 클래스들은 서로 많은 공통 부분이 있어 다시 뽑아 Collection 인터페이스를 정의할 수 있었지만 Map은 전혀 다른 형태라 같은 계층도에 포함되지 못함  

### Collection 인터페이스  
 - List와 Set의 상위 인터페이스  
 - 컬렉션 클래스에 저장된 데이터를 읽고, 추가하고, 삭제하는 등 컬렉션을 다루는 기본적인 메소드를 정의한다.  

 ![Collection Method](./images/3-2.jpg)

 ### 실제 사용 예시

#### List 활용
 <pre>
 <code>
import java.util.ArrayList;
import java.util.List;

public class ListExample {
    public static void main(String[] args) {
        - List 생성
        List< String > myList = new ArrayList<>();

        - 데이터 추가
        myList.add("Apple");
        myList.add("Banana");
        myList.add("Orange");

        - 데이터 순회 및 출력
        System.out.println("List Elements:");
        for (String fruit : myList) {
            System.out.println(fruit);

        - 출력 결과 : Apple, Banana, Orange
        }
    }
}
 </code>
 </pre>
 <br>

#### Set 활용
 <pre>
 <code>
import java.util.HashSet;
import java.util.Set;

public class SetExample {
    public static void main(String[] args) {
        - Set 생성
        Set< String > mySet = new HashSet<>();

        - 데이터 추가
        mySet.add("Red");
        mySet.add("Green");
        mySet.add("Blue");

        - 데이터 순회 및 출력
        System.out.println("Set Elements:");
        for (String color : mySet) {
            System.out.println(color);

        - 출력 결과 : Set Elements:, Red, Green, Blue
        }
    }
}
 </code>
 </pre>
 <br>

#### Map 활용
 <pre>
 <code>
import java.util.HashMap;
import java.util.Map;

public class MapExample {
    public static void main(String[] args) {
        - Map 생성
        Map< String, Integer > myMap = new HashMap<>();

        - 데이터 추가
        myMap.put("One", 1);
        myMap.put("Two", 2);
        myMap.put("Three", 3);

        - 데이터 순회 및 출력
        System.out.println("Map Elements:");
        for (Map.Entry< String, Integer > entry : myMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());

        - 출력 결과 : Map Elements:, One:1, Two:2, Three:3
        }
    }
}
 </code>
 </pre>
 <br>

#### Collection 활용
 <pre>
 <code>
import java.util.ArrayList;
import java.util.Collection;

public class CollectionExample {
    public static void main(String[] args) {
        - Collection 생성
        Collection< Integer > myCollection = new ArrayList<>();

        - 데이터 추가
        myCollection.add(10);
        myCollection.add(20);
        myCollection.add(30);

        - 데이터 순회 및 출력
        System.out.println("Collection Elements:");
        for (int number : myCollection) {
            System.out.println(number);

        - 출력 결과 : 10, 20, 30
        }
    }
}
 </code>
 </pre>
 <br>





 

</details>
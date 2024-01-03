# 지네릭스(Generics) #

<details>

## 지네릭스(Generics) ##  
 지네릭스는 다양한 타입의 객체들을 다루는 메소드나 컬렉션 클래스가 컴파일 시 타입 체크를 해주는 기능이다.  
 
 객체의 타입을 컴파일 시에 체크하기 때문에, 객체의 타입 안정성을 높이고 형변환의 번거로움이 줄어든다.  
  - 다룰 객체의 타입을 미리 명시하여 번거로운 형변환을 줄인다.  

 

#### 즉, 제네릭스를 사용한다고 하면, 컬렉션 생성 시 어떤 타입의 객체를 다룰 것인지 명시한다는 것과 동일하다. ####


#### 지네릭스의 간단한 사용 예시 ####  

<pre>
<code>
- 제네릭스를 사용하지 않은 경우
List list = new ArrayList();
list.add("Hello");

String value = (String) list.get(0); 
- 데이터를 꺼낼 때 형변환이 필요하다.

- 제네릭스를 사용한 경우
- 제네릭스 타입을 String으로 지정
List < String > stringList = new ArrayList<>();
stringList.add("Hello");

String value = stringList.get(0); 
- 데이터를 꺼낼 때 형변환이 필요없다.
</code>
</pre>  

<br>

<pre>
<code>
- 일반적인 클래스(Box)의 선언

class Box {
	Object item;
	
	void setItem(Object item) { 
		this.item = item; 
	}
	
	Object getItem() { 
		return item;
	}
}
</code>
</pre>

<br>

<pre>
<code>
- 지네릭스를 사용한 클래스의 선언
- T는 타입 매개변수(실제 타입을 나중에 지정)
 
class Box< T > { 
	T item;
	
	void setItem(T item) { 
		this.item = item; 
	}
	
	T getItem() {
		return item;
	}
}

- T(Type)는 Box 클래스를 사용할 때 설정하는 사용하는 실제 타입(String, Integer 등)으로 대체된다.  
- 타입변수는 다른 것을 사용해도 되며 관례적으로 의미에 맞춰 E(Element), K(Key), V(value)등을 많이 사용한다. 
- 기호만 다르고 임의의 참조형 타입을 지정한다는 것은 동일하다.
</code>
</pre>

<br>

### 지네릭스의 사용 이유 ###  
#### 타입 안정성 ####
 - 가장 주요한 이유
 - 제네릭스를 사용하면 컴파일 시 타입 체크가 이루어지므로, 런타임에 발생할 수 있는 형변환 오류를 방지할 수 있다.  

#### 코드 재사용성 ####  
 - 동일한 로직을 여러 데이터 타입에 사용 가능하다.  
 - 따라서 다양한 타입에 대해 동작하는 유연한 코드를 구현할 수 있다.  

#### 컴파일 타임 체크 ####  
 - 컴파일 시에 타입 관련 오류를 잡아 낼 수 있어 런타임에 발생하는 오류를 줄이고 디버깅을 용이하게 만든다.  

#### 가독성 향상 ####  
 - 코드에 타입 매개변수를 사용하면 코드의 의도가 명확히 표현되고, 지네릭스를 통해 메소드나 클래스 정의 시 어떤 타입을 다루는 지 정확히 명시하므로 보기 쉽다.  

 ### 컴파일 이후 지네릭스의 소거 ###  
  - 지네릭스의 도입(java 5 이후) 이전 버전과의 하위 호환성을 유지하기 위해 타입 소거라는 방식을 도입했다.  

  - 컴파일 이후에는 필요하지 않으므로 소거하여 런타임 효율성(메모리 사용량 하락)을 높일 수 있다.  

#### 컴파일 이후 소거의 예시 ####  

<pre>
<code>
- 지네릭스 클래스 Box< T >를 사용하는 코드
Box< String > stringBox = new Box<>();
stringBox.setItem("Hello");
String value = stringBox.getItem();
</code>
</pre>

<br>

<pre>
<code>
- 컴파일 이후의 코드 (타입 소거)
public class Box {
    private Object item;

    public void setItem(Object item) {
        this.item = item;
    }

    public Object getItem() {
        return item;
    }
}

컴파일 이후 T가 Object로 변환되고, 모든 지네릭스 타입에 관한 정보가 사라진다.
</code>
</pre>

<br>

### 지네릭스의 제한 ###
 - 타입 매개변수를 가지는 참조 변수와 인스턴스는 타입 매개변수에 동일한 자료형을 입력해야 한다.  

 <pre>
 <code>
Box<Apple> appleBox = new Box<Apple>(); 
- Apple 객체만 저장가능

Box<Grape> grapeBox = new Box<Grape>(); - Grape 객체만 저장가능
 </code>
 </pre>

 - 이 외에도 메소드 호출 시에도 타입이 일치해야 하고, 클래스 상속 시 부모가 지네릭스 타입을 가지고 있으면 자식 클래스에서도 같은 지네릭스 타입을 유지해야 한다.

<pre>
<code>
public class TypeMatchExample {

    - 지네릭스 메소드 사용
    public static < T > void printValue(T value) {
        System.out.println(value);
    }

    public static void main(String[] args) {
        - 올바른 사용 예시
        printValue("Hello");
        printValue(42);

        - 잘못된 사용 예시 (컴파일 에러)
        printValue(3.14);

        - 추론한 타입과 다르기 때문에 에러 발생
    }
}
</code>
</pre>

<br>

<pre>
<code>
- 부모 클래스에 제네릭 타입을 가지고 있는 Box 클래스
public class Box<T> {
    private T item;

    public void setItem(T item) {
        this.item = item;
    }

    public T getItem() {
        return item;
    }
}

- 부모 클래스를 상속 받은 자식 클래스
public class StringBox extends Box< String > {

- 여기에 추가적인 기능이나 멤버 변수를 정의할 수 있음

}

- 컴파일 에러: 자식 클래스에서는 부모 클래스의 제네릭 타입을 변경할 수 없음
 public class IntBox extends Box<Integer> {
      ...
 }
</code>
</pre>



</details>
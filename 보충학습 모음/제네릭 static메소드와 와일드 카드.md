https://st-lab.tistory.com/153


 static 은 무엇인가? 정적이라는 뜻이다. static 변수, static 함수 등 static이 붙은 것들은 기본적으로 프로그램 실행시 메모리에 이미 올라가있다.

 

이 말은 객체 생성을 통해 접근할 필요 없이 이미 메모리에 올라가 있기 때문에 클래스 이름을 통해 바로 쓸 수 있다는 것이다.

 

 

근데, 거꾸로 생각해보자면 static 메소드는 객체가 생성되기 전에 이미 메모리에 올라가는데 타입을 어디서 얻어올 수 있을까? 

 ```java

class ClassName<E> {
 
	/*
	 * 클래스와 같은 E 타입이더라도
	 * static 메소드는 객체가 생성되기 이전 시점에
	 * 메모리에 먼저 올라가기 때문에
	 * E 유형을 클래스로부터 얻어올 방법이 없다.
	 */
	static E genericMethod(E o) {	// error!
		return o;
	}
	
}
 
class Main {
 
	public static void main(String[] args) {
 
		// ClassName 객체가 생성되기 전에 접근할 수 있으나 유형을 지정할 방법이 없어 에러남
		ClassName.getnerMethod(3);
 
	}
}
 ```

위 내용을 보면 이해가 갈 것이다.

 

그렇기 때문에 제네릭이 사용되는 메소드를 정적메소드로 두고 싶은 경우 제네릭 클래스와 별도로 독립적인 제네릭이 사용되어야 한다는 것이다.

 

 

 

 ```java

// 제네릭 클래스
class ClassName<E> {
 
	private E element; // 제네릭 타입 변수
 
	void set(E element) { // 제네릭 파라미터 메소드
		this.element = element;
	}
 
	E get() { // 제네릭 타입 반환 메소드
		return element;
	}
 
	// 아래 메소드의 E타입은 제네릭 클래스의 E타입과 다른 독립적인 타입이다.
	static <E> E genericMethod1(E o) { // 제네릭 메소드
		return o;
	}
 
	static <T> T genericMethod2(T o) { // 제네릭 메소드
		return o;
	}
 
}
 
public class Main {
	public static void main(String[] args) {
 
		ClassName<String> a = new ClassName<String>();
		ClassName<Integer> b = new ClassName<Integer>();
 
		a.set("10");
		b.set(10);
 
		System.out.println("a data : " + a.get());
		// 반환된 변수의 타입 출력
		System.out.println("a E Type : " + a.get().getClass().getName());
 
		System.out.println();
		System.out.println("b data : " + b.get());
		// 반환된 변수의 타입 출력
		System.out.println("b E Type : " + b.get().getClass().getName());
		System.out.println();
 
		// 제네릭 메소드1 Integer
		System.out.println("<E> returnType : " + ClassName.genericMethod1(3).getClass().getName());
 
		// 제네릭 메소드1 String
		System.out.println("<E> returnType : " + ClassName.genericMethod1("ABCD").getClass().getName());
 
		// 제네릭 메소드2 ClassName a
		System.out.println("<T> returnType : " + ClassName.genericMethod1(a).getClass().getName());
 
		// 제네릭 메소드2 Double
		System.out.println("<T> returnType : " + ClassName.genericMethod1(3.0).getClass().getName());
	}
}


 결과값
a data : 10
a E Type : java.lang.String

b data : 10
b E Type : java.lang.Integer

<E> returnType : java.lang.Integer
<E> returnType : java.lang.String
<T> returnType : ClassName
<T> returnType : java.lang.Double
 ```


 


 

보다시피 제네릭 메소드는 제네릭 클래스 타입과 별도로 지정된다는 것을 볼 수 있다.

<> 괄호 안에 타입을 파라미터로 보내 제네릭 타입을 지정해주는 것. 이 것이 바로 제네릭 프로그래밍이다.



# 와일드 카드 
만약 특정 범위 내로 좁혀서 제한하고 싶다면 어떻게 해야할까? 

 

 

이 때 필요한 것이 바로 extends 와 super, 그리고 ?(물음표)다. ? 는 와일드 카드라고 해서 쉽게 말해 '알 수 없는 타입'이라는 의미다.

 

일단 먼저 예시를 보면서 말해보자면 이용할 때 크게 세 가지 방식이 있다. 바로 super 키워드와 extends 키워드, 마지막으로 ? 하나만 오는 경우다. 코드로 보자면 다음과 같다. 
 ```java
<K extends T>	// T와 T의 자손 타입만 가능 (K는 들어오는 타입으로 지정 됨)
<K super T>	// T와 T의 부모(조상) 타입만 가능 (K는 들어오는 타입으로 지정 됨)
 
<? extends T>	// T와 T의 자손 타입만 가능
<? super T>	// T와 T의 부모(조상) 타입만 가능
<?>		// 모든 타입 가능. <? extends Object>랑 같은 의미
 ```

보통 이해하기 쉽게 다음과 같이 부른다.

 extends T : 상한 경계

? super T : 하한 경계

 

< ? > : 와일드 카드(Wild card)

이 때 주의해야 할 게 있다. K extends T와 ? extends T는 비슷한 구조지만 차이점이 있다.

'유형 경계를 지정'하는 것은 같으나 경계가 지정되고 K는 특정 타입으로 지정이 되지만, ?는 타입이 지정되지 않는다는 의미다.

 

가장 쉬운 예시로 다음과 같은 예시가 있다.

```java
/*
 * Number와 이를 상속하는 Integer, Short, Double, Long 등의
 * 타입이 지정될 수 있으며, 객체 혹은 메소드를 호출 할 경우 K는
 * 지정된 타입으로 변환이 된다.
 */
<K extends Number>
 
 
/*
 * Number와 이를 상속하는 Integer, Short, Double, Long 등의
 * 타입이 지정될 수 있으며, 객체 혹은 메소드를 호출 할 경우 지정 되는 타입이 없어
 * 타입 참조를 할 수는 없다.
 */
<? extends T>	// T와 T의 자손 타입만 가능
```

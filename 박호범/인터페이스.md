
# 인터페이스(Interface)

<details>

## 인터페이스
 - 클래스들이 필수로 구현해야 하는 추상적인 자료형  
 - 객체 사용 방법의 가이드라인이라고 생각하면 편하다.  
 - interface라는 키워드를 붙여서 사용한다. 

### 인터페이스의 특징  
인터페이스는 추상 메소드만을 가진다.  
 - 추상 메소드 : 선언만 존재하고 구현은 없는 메소드    
 - 클래스에서 인터페이스를 구현할 때, 해당 클래스는 반드시 인터페이스의 모든 추상 메소드를 구현해야 한다.  
 - 추상 클래스와는 다르게 일반 메소드를 갖지 못한다.  
 - abstract를 생략해도 컴파일 시점에 자동으로 추가된다.

인터페이스는 상수를 가진다.  
 - 모든 변수는 상수로 선언된다.  
 - 인터페이스에서 선언한 변수는 컴파일 과정에서 자동으로 상수로 변환된다.

다중 상속 지원  
 - 자바에서 클래스는 단일 상속만 가능하지만, 인터페이스는 다중 상속을 지원한다.  
 - 따라서 하나의 클래스가 여러 인터페이스를 구현할 수 있다.  

default 메소드와 static(정적) 메소드(Java 8부터)  
 - default 메소드는 인터페이스에서 메소드의 기본 구현을 제공하는 것, 필요에 따라 재정의 가능하다.    
 - static(정적) 메소드는 인터페이스 자체에서 정의되는 메소드로 인터페이스를 통해 호출되는 것  

extends를 통한 상속  
 - 인터페이스는 다른 인터페이스를 상속 할 수 있는데 이 때, extends키워드를 사용한다.  
 - 클래스 끼리는 extends, 인터페이스와 클래스의 상속에는 implements를 사용한다.  

 ![인터페이스](./images/2-1.jpg)

### 인터페이스 사용 예제  

<pre>
<code>
package Example;

// 인터페이스는 static, final로 생성할 수 있는 상수와 abstract로 생성할 수 있는 추상메소드만 가질 수 있다. 

public interface Animal {
    public static final String name = "동물";
    
    public abstract void move();
    public abstract void eat();
    public abstract void bark();
}
</code>
</pre>

<pre>
<code>

// 강아지 클래스를 생성 후 Animal 인터페이스를 implements 키워드로 구현하고 인터페이스의 추상 메소드를 모두 재정의한다. 

package Example;

public class Dog implements Animal{
    
    @Override
    public void move() {
        System.out.println("슥슥");
    }
    
    @Override
    public void bark() {
        System.out.println("멍멍!");
    }
}
</code>
</pre>

<pre>
<code>
// 마찬가지로 고양이 클래스 생성 후 메소드 오버라이딩 실행

package Example;

public class Cat implements Animal{

    @Override
    public void move() {
        System.out.println("사뿐사뿐");
    }
    
    @Override
    public void bark() {
        System.out.println("야옹");
    }
}
</code>
</pre>

<pre>
<code>

// 인스턴스 생성 후 동작 호출

package Example;

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog();
        Cat cat = new Cat();
        
        dog.move();
        dog.bark();
        
        cat.move();
        cat.bark();
    }
}

</code>
</pre>


</details>































































































</details>
# 추상 클래스(Abstract Class)

<details>

## 추상 클래스  
 - 다른 클래스들에게 공통된 특성을 제공하기 위해 사용되는 클래스  
 - 클래스 선언 시 abstract라는 키워드를 붙여 추상 클래스를 만든다.  


![추상 클래스](./images/1-1.jpg)



### 추상 클래스의 특징  
 #### 추상 클래스는 인스턴스, 즉 객체를 만들 수 없다.
  - 따라서 new 생성자를 사용할 수 없다.  

 #### 추상 메소드를 가진 추상 클래스는 하위 클래스(상속 받는 자식 클래스)에게 메소드의 구현을 강제한다.  
  - 오버라이딩(메소드 재정의)를 통해 구현한다.  
  
 #### 추상 클래스는 추상 메소드를 꼭 포함하는 것은 아니지만, 추상 메소드를 하나라도 가지고 있다면 추상 클래스가 된다.    


### 추상 클래스의 사용 이유
 객체들의 필드와 메소드의 이름을 통일하여 소스의 가독성을 높일 수 있기 때문이다.  
  - 짖다라는 메소드가 늑대는 Howl(), 개는 Bark()이면 소스의 가독성이 떨어지기 때문에 통일한다.  

 개발의 효율성을 증대시키기 위해 사용한다.  
  - 많은 객체를 생성 시 모든 객체들에 공통적으로 포함되는 필드나 메소드를 추상클래스에서 만들기 때문에 효율성이 높아진다.  

 ### 추상 클래스 예시  

#### 추상 클래스 Animal 생성

<pre>
<code>
package Example;

public abstract class Animal {
    // 필드
    String name;
    int age;
    
    // 생성자
    // 자식 객체가 생성될 때, super를 호출하므로 생성자가 있어야 한다.
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    // 일반 메소드
    public void move() {System.out.println("이동한다");}

    // 일반 메소드
    public void eat() {System.out.println("먹는다");}

    // 짖는 소리는 동물마다 다르므로 추상메소드로 생성
    public abstract void bark(); 
    
}
</code>
</pre>  

#### 개 클래스 생성 후 메소드 오버라이딩

<pre>
<code>
package Example;

public class Dog extends Animal{

    public Dog(String name, int age) {
        // 객체 생성 시 super 호출
        // 상위 클래스의 생성자 호출 후 상위 클래스의 초기화 코드 실행
        super(name, age);
         
    }
    
    // 메소드 오버라이딩
    // bark()는 추상 메소드이므로 재정의 해야한다.
    @Override
    public void bark() {
        System.out.println("멍멍!!");
    }; 
}
</code>
</pre>  

#### 고양이 클래스 생성 후 메소드 오버라이딩

<pre>
<code>
package Example;

public class Cat extends Animal{
    public Cat(String name, int age) {
        super(name, age);
    }
    
    // 메소드 오버라이딩
    // bark()는 추상 메소드이므로 재정의 해야한다.
    @Override
    public void bark() { 
        System.out.println("야옹~~");
    }; 
}
</code>
</pre>

#### 인스턴스 생성 후 동작 호출

<pre>
<code>
package Example;

public class Main {
    public static void main(String[] args) {
        Dog dog = new Dog("강아지",3);
        Cat cat = new Cat("고양이",3);
        
        dog.move();
        dog.bark();
        
        cat.move();
        cat.bark();
    }
}
</code>
</pre>

</details>
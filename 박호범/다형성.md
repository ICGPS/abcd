# 다형성

<details>

## 다형성  
#### 여러 가지 형태를 가질 수 있는 능력
 - 자바에서는 하나의 타입이 여러 가지 타입으로 나타날 수 있는 성질을 의미  
   
#### 상속과 인터페이스를 기반으로 한다.  

## 업캐스팅과 다운캐스팅  

#### 업캐스팅(Upcasting)  
 - 자식 클래스의 객체가 부모 클래스 타입으로 형변환 되는 것  

 - 업캐스팅은 항상 가능하다.

#### 다운캐스팅(Downcasting)  
 - 업캐스팅된 상태(부모클래스 객체를 자식클래스 타입으로)를 다시 원상태로 돌리는 것  
  
 - 다운캐스팅은 가능 여부 확인(instanceOf 사용) 후 가능하다.  

 - instanceOf : 특정 객체가 특정 클래스나 인터페이스의 인스턴스인지 확인하는 데에 사용, true or false의 값을 반환

 <pre>
 <code>
 class Animal {
    // Animal의 내용 생략
}

class Dog extends Animal {
    // Dog의 내용 생략
}

// 다운캐스팅
Animal myAnimal = new Dog();

// 다운캐스팅 가능 여부 확인
// myAnimal의 객체가 Dog 클래스의 인스턴스인지 확인
if (myAnimal instanceof Dog) {

    // 다운캐스팅
    Dog myDog = (Dog) myAnimal;  

    // 다운캐스팅이 성공했을 때 실행되는 코드
    System.out.println("다운캐스팅에 성공했습니다.")

} else {
    // 다운캐스팅이 실패했을 때 실행되는 코드
    System.out.println("다운캐스팅에 실패했습니다.")
}

// 출력결과 : 다운캐스팅에 성공했습니다.
 </code>
 </pre>



## 다형성의 장점    
 - 코드의 재사용성  
 - 유연성과 확장성  
 - 인터페이스와 구현의 분리  
 - 가독성과 유지보수성  
 - 다형성을 통한 다양한 동작  


<pre>
<code>

// 도형 클래스
class Shape {
    void draw() {
        System.out.println("도형 그리기");
    }
}

// 도형을 상속받은 원
class Circle extends Shape {
    @Override
    void draw() {
        System.out.println("원 그리기");
    }
}

// 도형을 상속받은 사각형
class Rectangle extends Shape {
    @Override
    void draw() {
        System.out.println("사각형 그리기");
    }
}
</code>
</pre>

<pre>
<code>
Shape shape1 = new Circle();
Shape shape2 = new Rectangle();

// Circle 클래스의 draw() 메소드 호출
shape1.draw();  

// Rectangle 클래스의 draw() 메소드 호출
shape2.draw();  

// 출력 결과 : 원 그리기, 사각형 그리기
</code>
</pre>

</details>
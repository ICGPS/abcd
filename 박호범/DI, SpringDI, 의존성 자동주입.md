# DI, Spring DI, 의존성 자동주입 #  

<details>

## DI(Dependency Injection) ##  
의존성 주입 : 하나의 객체에 다른 객체의 의존성을 제공하는 기술  
 - 즉, 의존관계에 있는 클래스들을 주입하는 것    

즉, 객체를 직접 생성하는 것이 아니라 외부에서 생성 후 주입시켜주는 방식  
 - 기존의 일반적 의존 형태 : A 객체에서 B 객체 생성  
 - 의존성 주입의 형태 : A 객체의 외부에서 B 객체 생성 후 주입

의존 : 외부에서 객체 간의 관계를 결정해주는 것

의존 관계 : 어떤 객체가 다른 객체를 사용하는 것  
 - 의존 객체가 다른 객체에 의존하여 기능을 사용하는 것과 동일하다.  
 - 다른 객체의 기능을 사용하려면 필수적으로 선행되어야 한다.  
 - 변경에 따른 영향이 전파되는 관계  

<pre>
<code>
- 의존 관계 예시 코드

- chef 클래스의 필드 선언
- Recipe 객체 참조 하는 recipe 필드
- private이므로 chef 클래스 내에서만 접근 가능

class Chef {
    private Recipe Recipe;

- chef 클래스의 생성자
- 객체가 생성될 때 호출되는 메소드
    public Chef() {

- 생성자의 실행부에서 Recipe 필드를 새로운 Recipe 객체로 초기화        
        Recipe = new Recipe();

- chef 객체가 생성될 때마다 Recipe 생성자가 호출되어 Racipe 필드는 새로운 Recipe 객체를 참조한다.                

- chef의 생성자는 객체가 생성될 때마다 Recipe 필드를 새로운 Recipe 객체로 초기화한다.
- 셰프가 바뀔 때마다 레시피가 바뀐다.
    }
}
</code>
</pre>

<pre>
<code>
- DI를 사용한 코드 예시

- BurgerRecipe를 구현한 객체를 가지는 클래스
- 생성자를 통해 레시피 설정 가능
- 외부의 BurgerRecipe 객체로 초기화되는 구조

class BurgerChef {
    private BurgerRecipe burgerRecipe;

    - 생성자 : 외부에서 레시피를 주입받아 초기화

    public BurgerChef(BurgerRecipe burgerRecipe) {
        this.burgerRecipe = burgerRecipe;
    }
}

- BurgerChef 객체를 가지는 클래스
- 처음에는 HamburgerRecipe를 사용하는 burgerCher 객체 생성 후 초기화
- 이후 changeMenu를 통해 새로운 레시피를 사용하는 BurgerChef 객체로 교체할 수 있음

class BurgerRestaurantOwner {
    private BurgerChef burgerChef = new BurgerChef(new HamburgerRecipe());

    public void changeMenu() {
        burgerChef = new BurgerChef(new CheeseBurgerRecipe());
    }
}
</code>
</pre>

### DI(의존성 주입)의 사용 이유 ###  
코드의 재사용성, 유연성이 높아진다.  
 - 하나의 작업만 수행하는 작은 객체는 많은 상황에서 재결합하고 재사용하기 쉽기 때문이다.  

객체 간 결합도가 낮기 때문에 한 클래스를 수정했을 때, 다른 클래스를 수정하는 상황을 막는다.  

유지보수 및 테스트가 쉽다.  

확장성을 가진다.  

## Spring DI(Dependency Injection) ##
- 객체 지향 프로그래밍에서 사용되는 설계 패턴 중 하나로, 객체 간의 의존성을 외부에서 주입하는 방법이다.  
- DI를 통해 객체는 직접 자신이 필요로 하는 의존 객체를 생성하지 않고, 외부에서 주입 받아 사용한다.  
- 즉, new 연산자를 통해 객체를 직접 생성하는 것이 아니라 외부에서 생성된 객체를 주입받아 이용하는 것이다.  
- 주로 생성자를 통해서 혹은 setter 메소드를 통해서 의존성을 주입한다.  

### 생성자 주입 ###
- 클래스에 생성자를 만든 후 @Autowired를 붙여 의존성을 주입받는 방법

- Spring 4.3 이후 클래스 내 생성자가 하나이고, 그 생성자로 주입받을 객체가 빈으로 등록되어 있다면 @Autowired를 생략 가능하다.

- 주로 클래스 이름의 첫 글자를 소문자로 변환한 이름을 기본적으로 사용하지만 객체의 성격에 맞게, 빈의 이름(Service, Component)이 달라질 수 있다.

- 생성자 주입은 인스턴스 생성 시 1회 호출되는 것이 보장되기 때문에, 주입 받은 객체가 변하지 않거나 반드시 객체 주입이 필요한 경우 강제하기 위해 사용된다.

- 생성자를 2회 이상 정의할 수 없다(Java와 동일).  

- @Qualifier("주입 받을 빈의 이름") 어노테이션을 통해 여러 빈 중 특정 빈을 선택하여 주입 받을 수 있다.  

- @Autowired(required = false)로 설정하면 해당 타입의 빈이 없어도 예외가 발생하지 않고 null 값으로 주입된다.

<pre>
<code>
- 생성자 주입의 간단한 예시

-  Service 클래스 선언
public class Service {
    - Repository 타입의 private 멤버 변수 선언
    private Repository Repository;

    - 생성자 주입
    - Repository 타입의 객체가 Service 객체를 생성할 때 주입되는 생성자
    public Service(Repository Repository) {
        - 주입받은 Repository를 Service 클래스의 private 변수에 할당
        this.Repository = Repository;
    }

    - 나머지 코드(생략)
}


- 생성자 주입은 객체를 생성할 때 의존성이 모두 주입되기 때문에 객체가 완전하게 초기화된 상태로 사용할 수 있다. 
</code>
</pre>

### setter 주입
- setter 혹은 사용자 정의 메소드를 통해 의존 관계를 주입한다.

- setter의 경우 객체가 변경될 필요성(주입하는 객체를 변경하는 경우는 드묾)이 있을 때만 사용한다.  

- bean의 주입이 유동적이어야 하는 경우 유용하다.(객체 생성 이후 언제든 호출할 수 있기 때문에, 생성 시점이나 초기화가 중요하지 않은 경우라면 바로 사용할 수 있다)


<pre>
<code>
- setter 주입의 간단한 예시

- Service 클래스 선언
public class Service {
    - Repository 타입의 private 멤버 변수 선언
    private Repository Repository;

    - setter 주입
    - Repository 타입의 객체를 받아서 Repository 멤버 변수에 할당하는 메소드
    public void setRepository(Repository Repository) {
        this.Repository = Repository;
    }

    // 나머지 코드(생략)
}
</code>
</pre>

### Spring에서는 생성자 주입을 권장 ###

#### 객체 불변성 확보 ####
- 객체의 생성자는 객체 생성 시 최초 1회만 호출되기 때문에 주입 받은 객체가 불변 객체(메소드 변화 X)여야 하거나 반드시 해당 객체의 주입이 필요한 경우 사용한다.
- 생성자로 한번 의존 관계를 주입하면 생성자는 최초 1회 이후 다시 생성될 일이 없기 때문에 불변객체가 보장된다.

#### 테스트 용이 ####
- 필드 주입으로 작성된 경우, 순수 자바 코드로 단위테스트를 실행하는 것이 불가능하다.
- 메인 코드는 Spring과 같은 DI 프레임워크 위에서 동작하는데 단위테스트 시 단독적으로 실행되므로 의존관계 주입이 NULL 상태여서 NullPoninterException이 발생한다.
- 생성자 주입 시 단독으로 실행할 때도 의존관계 주입이 성립된다.

#### 순환참조 에러 방지 ####
- 순환참조 에러 : A객체는 B객체를 참조하고, B객체는 A객체를 참조해 두 객체가 서로를 동시에 참조할 때 발생한다.

#### 의존성을 생성자로 주입했을 때는 컴파일 에러 발생으로 프로그램 실행 자체가 되지 않으므로 서비스 전 에러를 해결 할 수 있다. ####


## 의존성 자동 주입 ##

### 의존성 자동 주입 ###
 - bean들 간의 의존성을 자동으로 해결하게 해주는 기능  
 - 객체 간의 결합도를 낮추고 코드를 더 유연하게 만들 수 있다.

### IOC(Inversion of Control) : 제어의 역전 ###  

- 메소드나 객체의 호출작업을 개발자가 직접 하는 것이 아닌, 스프링 프레임워크에게 제어권을 넘기는 것이다. 
- 대부분의 프레임워크에서 IoC를 적용하고 있고 스프링 또한 여러 프레임워크 중 하나이다.
- @componentScan, @autowired 등을 예시로 들 수 있다.

### 의존성 자동 주입의 특징 ###
컴포넌트 스캔(Component Scanning)  
 - 스프링은 클래스 경로 내에서 어노테이션을 기반으로 한 빈을 자동으로 찾아 등록하는 컴포넌트 스캔 기능을 제공  
 - @Component, @Service, @Repository, @Controller 등의 어노테이션을 사용하여 빈으로 등록할 클래스를 표시하고, 스프링은 해당 어노테이션이 붙은 클래스들을 찾아서 빈으로 등록  

<pre>
<code>
- @service 어노테이션을 사용하여 빈으로 등록

@Service
public class MyService {
    // ...
}
</code>
<pre>


자동와이어링(Autowiring)
 - 스프링은 @Autowired 어노테이션을 사용하여 자동으로 의존성을 주입한다.
 - @Autowired 어노테이션이 붙은 필드, 생성자, 메소드 등에 대해 스프링은 적절한 빈을 찾아 의존성을 주입한다.

<pre>
<code>
- @Autowired의 사용 예시

@Service
public class MyService {
    @Autowired
    private AnotherService anotherService;
    
    // ...
}
</code>
</pre>

 자동와이어링을 위한 @Qualifiers와 @Primary
  - @Qualifiers : 동일한 타입의 빈이 여러 개 일 때, 어떤 빈을 선택할 지 지정  
  - @Primary : 동일한 타입의 빈이 여러 개 일 때, 우선적으로 주입되어야 할 빈을 지정  

<pre>
<code>
@Service

- special과 normal이 있다면 special 선택
@Qualifier("special")

public class SpecialService implements MyService {
    // ...
}
</code>
</pre>

<pre>
<code>
- 기본적으로 주입될 빈을 나타내는 @Primary 어노테이션
- @Service 중 @primary가 붙어 있는 것이 기본 값

@Primary
@Service
public class PrimaryService implements MyService {
    // 해당 서비스의 구현 내용(생략)
    // 
}
</code>
</pre>



</details>


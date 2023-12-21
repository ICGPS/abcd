# 6번 박범수


## 웹기초 : 응답상태(2xx, 3xx, 4xx, 5xx)
<details>
### **HTTP 상태 코드**
    
1xx (조건부 응답) - 요청받음, 프로세스 계속.   
임시, 실험적인 상태. → 클라이언트로 보내면 안된다.
    
2xx (요청 수신, 처리 성공) 
    
    200 : 정상 응답 (요청 -> 서버 -> 응답)
    201 : 작성됨(CREATED)
    
3xx (리다이렉션 완료 : 다른 페이지로 이동)

    301 : 영구 이동 - get 또는 haed 요청에 대한 응답
    302 : 임시 이동 - 현재 다른 위치이지만 원래 위치를 사용할 때 (redirect)
    304 : 캐시됨 - 마지막 요청 이후 페이지가 수정되지 않았다. (요청의 응답으로 캐시를 사용했다는 뜻)
    
4xx : 요청 오류

 	400 : 잘못된 요청(BAD REQUEST) - 서버가 구문 인식 못함.
 	401 : 권한 없음(UNAUTHORIZED) - 인증 안됨(로그인이 필요한 페이지)
 	403 : 금지됨(FORBIDDEN) - 권한 없음
 	404 : 페이지 없음(NOT FOUND) 
 	405 : Method Not Allowed - POST 요청받는 서버에 GET으로 요청/ GET 요청받는 서버에 POST로 요청

5xx : 서버 오류

    500 - 내부 서버 오류(Internal Server Error) - 코드 오류, 서버 물리적 오류
    501, 502 (불량 게이트웨이)
    503 (서비스를 사용할 수 없음)
=======
2XX : 성공을 알리는 상태코드 입니다. 
          200(성공), 
          201(작성됨)이 많이 사용됩니다.
3XX : 리다이렉션(다른 페이지로 이동)을 알리는 상태 코드 입니다. 어떤 주소를 입력했는데 다른 주소의 페이지로 넘어갈 때 이 코드가 사용됩니다. 
          301(영구이동), 
          302(임시 이동) 
          304(수정되지 않음)는 요청의 응답으로 캐시를 사용했다는 뜻 입니다.
4XX : 요청 오류를 나타냅니다. 요청 자체에 오류가 있을 때 표시됩니다. 
          400(잘못된 요청), 
          401(권한없음), 
          403(금지됨), 
          404(찾을 수 없음), 
          405(메서드 처리 불가 Method Not Allowed)가 있습니다.
5XX : 서버오류를 나타냅니다. 특히 코드 오류나 물리적 서버 오류 때 발생하며 요청은 제대로 왔지만 서버에 오류가 생겼을 때 발생합니다. 이 오류가 뜨지 않게 주의해서 프로그래밍 해야 합니다. 
          500(코드 오류, 내부 서버 오류), 
          501&502(불량 게이트웨이), 
          503(서비스를 사용할 수 없음)이 자주 사용됩니다.
</details>
          
## 스프링 : AOP
<details>
### **AOP**

@EnableApectJAutoProxy : 프록시 설정 활성

```java
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true) // 하위클래스 기반의 프록시
public class AppCtx {
    @Bean
    public Calculator calculator() {
        return new RecCalculator();
    }

    @Bean
    public ProxyCache proxyCache() {
        return new ProxyCache();
    }

    @Bean
    public ProxyCalculator proxyCalculator() {
        return new ProxyCalculator();
    }
}
```

1)**@Aspect** : **공통 기능**이 포함된 **클래스**

```java
import aopex.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Aspect     // aop
@Order(2)
public class ProxyCalculator {

    @Around("CommonPointcut.publicTarget()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {

        long stime = System.nanoTime(); // 공통 기능

        try {
            Object result = joinPoint.proceed(); // 핵심 기능을 대신 수행하는 메서드
            // factorial(...)

            return result;
        } finally {
            long etime = System.nanoTime(); // 공통 기능
            System.out.printf("걸린시간 : %d%n", etime - stime);
        }
    }
}
```

```java
@Aspect
@Order(1)
public class ProxyCache {

    private Map<Long, Object> cacheData = new HashMap<>();

    @Around("CommonPointcut.publicTarget()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs(); // 매개변수로 투입된 인자 값( 예 - 10L)
        Long num = (Long)args[0];
        if (cacheData.containsKey(num)) {
            System.out.println("캐시값 사용!");
            return cacheData.get(num);
        }

        Object result = joinPoint.proceed(); // ProxyCalculator::proceed()

        // 캐시 저장
        cacheData.put(num, result);
        System.out.println("캐시 저장!");

        return result;
    }
}
```

2) **@Pointcut** : 공통 기능이 적용될 **범위**

- execution 명시자

```java
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcut {
    @Pointcut("execution(* aopex..*(long))")  // A
    public void publicTarget() {}
}
```

3) **@Around** : 공통 기능을 수행하고 핵심 기능을 대신 수행 주는 **메서드**

ProceedingJoinPoint joinPoint

Signature getSignature()

getName() : 메서드명
toLongString() : 자세한 메서드 이름 정보

 Object proceed() : 핵심 기능 대신 수행해 주는 메서드(범용 기능)
 Object[] getArgs()
 Object getTarget() : 핵심 기능 메서드가 속해 있는 객체의 참조 변수

1. @Order
    
    ProxyCache -> ProxyCalculator :  정상
    
    ProxyCaluclator -> ProxyCache : 캐시 동작 X
    
    프록시의 실행 순서가 매우 중요한 경우 @Order -> 순서를 직접 지정
    
    ```java
    @Aspect
    @Order(2)
    public class ProxyCalculator {
    	(...)
    }
    ```
    
    ```java
    @Aspect
    @Order(1)  // 우선 순위
    public class ProxyCache {
    	(...)
    }
    ```
    
2. 프록시 생성방식
    - 반복
        
        ```java
        public static void main(String[] args) {
                long stime = System.nanoTime();     // 공통 기능
        
                // 핵심 기능
                ImplCalculator cal1 = new ImplCalculator(); // 반복문
                long result1 = cal1.factorial(10L);
                System.out.printf("cal1 : %d%n", result1);
        
                long etime = System.nanoTime();     // 공통 기능
                System.out.printf("반복문으로 걸린 시간 : %d%n", etime - stime);
        
                stime = System.nanoTime();          // 공통 기능
        
                // 핵심 기능
                RecCalculator cal2 = new RecCalculator();   // 재귀 : 스택이 쌓여서 성능에 좋지 않다.
                long result2 = cal2.factorial(10L);
                System.out.printf("cal2 : %d%n", result2);
        
                etime = System.nanoTime();          //공통 기능
                System.out.printf("재귀문으로 걸린 시간 : %d%n", etime - stime);
            }   // 왜 재귀문이 더 짧지?
        }       // 반복되는 코드를 프록시로 묶어서 처리하려고 한다. -> Ex02
        ```
        
    - 프록시로
        
        ```java
        public class ProxyCalculator implements Calculator{
            private Calculator calculator;
        
            public ProxyCalculator(Calculator calculator) {
                this.calculator = calculator;
            }
            @Override
            public long factorial(long num) {
                long stime = System.nanoTime();     // 공통 기눙
        
                try {
        
                    long result = calculator.factorial(num);     // 핵심 기능
        
                    return result;
                } finally {
                    long etime = System.nanoTime();     // 공통 기능
                    System.out.printf("걸린시간 : %d%n", etime - stime);
                }
            }
        }
        ```
        
        ```java
        public static void main(String[] args) {
                // 데코레이션 패턴. 추가적인 기능을 더한다.
                // 기능을 대신 수행하기 때문에 프록시라고 부른다.
                ProxyCalculator cal1 = new ProxyCalculator(new ImplCalculator());
                long result1 = cal1.factorial(10L);
                System.out.printf("cal1 : %d%n", result1);
        
                ProxyCalculator cal2 = new ProxyCalculator(new RecCalculator());
                long result2 = cal2.factorial(10L);
                System.out.printf("cal2 : %d%n", result2);
            }
        }
        ```
        
    
3. @Around의 Pointcut 설정과 @Pointcut 재사용
    
    ```java
    @Aspect
    public class CommonPointcut {
        @Pointcut("execution(* aopex..*(long))")
        public void publicTarget() {}
    }
    ```
    
    위 pointcut을 아래에서 재사용
    
    ```java
    @Around("CommonPointcut.publicTarget()")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
    	(...)
    }
    ```
</details> 

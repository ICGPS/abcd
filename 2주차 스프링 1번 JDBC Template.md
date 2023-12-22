# JDBC Template  

## JDBC와 Template이란?  

### JDBC  
 - JDBC(Java DataBase Connectivity)는 Java 언어로 작성된 응용 프로그램에서 데이터베이스에 접속하고 SQL 쿼리를 실행하는 데 사용되는 자바 API이다.  
    - API(Application Programming Interface) : 소프트웨어 어플리케이션들이 서로 상호작용 할 수 있도록 도와주는 인터페이스  

 - java.sql.Connection : 연결
 - java.sql.Statement : SQL을 담은 내용
 - java.sql.ResultSet : SQL 요청 응답  
    - 위 3가지 기능을 표준 인터페이스로 정의하여 제공한다.      



### Template
 - 코드의 작성을 편리하게 하기 위해 미리 정의된 코드 구조나 일부 코드 블록
 - 개발환경이나 IDE(Integrated Development Environment)에서 제공하는 기능으로 특정 코드 패턴을 자동으로 생성할 수 있다.  
    - IDE(Integrated Development Environment): 통합 개발 환경, 소프트웨어 개발을 위한 여러 도구와 기능을 한 곳에서 통합하여 제공하는 소프트웨어 환경
    - 개발자들이 코드를 작성하고 테스트하며 프로젝트를 관리할 때 도움을 준다.  
    - 대표적 IDE : Intelli j, Visual Studio Code, Eclipse  

## JDBC Template  
 - 데이터베이스와의 상호작용을 단순화 하고 편리하게 만들어주는 클래스  
 - 일반적인 JDBC 코드보다 간결하고 안전한 데이터베이스 코드를 작성할 수 있다.  
 - 예외 처리, 연결관리, SQL 실행 및 결과 처리를 자동으로 해주어 효율적으로 데이터베이스와 상호작용할 수 있게 해준다.  

## JDBC Template의 장점  
 - 스프링으로 JDBC를 사용할 때 자동으로 포함되는 spring-jdbc 라이브러리에 속해있기 때문에 별다른 설정 없이 사용할 수 있다.  
 - 템플릿 콜백 패턴을 사용해 JDBC를 직접 사용할 때 발생하는 대부분의 반복 작업을 대신 처리해 준다.  
 - 개발자는 SQL을 작성하고 전달할 파라미터를 정의하여 응답 값을 매핑하기만 하면 된다.  
 - 트랜잭션을 위한 커넥션 동기화, 스프링 예외 변환기를 자동으로 실행한다.  

### 그러나 동적 SQL를 작성하기 어렵다는 단점이 있어, 복잡하지 않은 SQL문을 처리할 때 사용하는 것이 좋다.  

## JDBC Template 사용 설정  
 - 의존성 설정  
 - build.gradle에 의존성을 추가한다.

<pre>
<code>
dependencies {
    // Spring JDBC Template
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
}
</code>
</pre>

 - 데이터베이스 설정  
 - application.properties 또는 application.yml 파일에 데이터베이스 연결 정보를 설정한다.

<pre>
<code>
spring.datasource.url=jdbc:mysql://localhost:3306/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
</code>
</pre>  

 - JDBC Template 사용  
 - JDBC Template를 사용하려면 먼저 'JdbcTemplate' 빈을 설정하고, 이를 활용하는 클래스를 만든다.
 <pre>
 <code>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MyRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public MyRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<String> getAllNames() {
        String sql = "SELECT name FROM your_table";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public void insertData(String name, int age) {
        String sql = "INSERT INTO your_table (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, name, age);
    }
}
 </code>
 </pre>  

- 위의 예시에서 JdbcTemplate를 주입 받은 MyRepository 클래스는 데이터베이스 엑세스에 사용된다.  
-  queryForList() 메소드는 결과를 리스트로 반환하고, update() 메소드는 업데이트나 삽입과 같은 변경 작업을 수행한다.  
- 이후에는 MyRepository 클래스를 서비스나 컨트롤러에서 호출하여 데이터베이스 엑세스를 수행하고, Spring Boot에서는 보통 @Service, @Controller를 사용하여 클래스를 선언한다.  

### Bean을 사용하지 않고, 생성자에서 데이터소스를 주입받고 생성자 내부에서 JDBC Template를 생성하는 방법  
 - 컴포넌트 스캔이나 자동 빈 등록을 사용하지 않고, 직접 생성자를 통해 의존성을 주입하는 방식 사용  

<pre>
<code>
import org.springframework.jdbc.core.JdbcTemplate;
import javax.sql.DataSource;

public class MyRepository {

    private final JdbcTemplate jdbcTemplate;

    public MyRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<String> getAllNames() {
        String sql = "SELECT name FROM your_table";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public void insertData(String name, int age) {
        String sql = "INSERT INTO your_table (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, name, age);
    }
}
</code>
</pre>  

 - 위의 예시는 MyRepository 클래스를 빈으로 등록하지 않고, 직접 생성자를 통해 데이터 소스를 주입 받아 내부에서 JDBC Template 생성  
 - 이 클래스를 사용할 때는 직접 생성자를 호출하여 인스턴스를 생성하거나, 다른 클래스에서 주입하여 사용할 수 있다.  

### 예시
 <pre>
 <code>
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class AppConfig {

    @Bean
    public MyRepository myRepository(DataSource dataSource) {
        return new MyRepository(dataSource);
    }
}
 </code>
 </pre>  

이 방법은 주로 빈 등록이나 컴포넌트 스캔을 사용하지 않을 때 또는 특정 클래스를 빈으로 등록하지 않고 간단한 의존성 주입이 필요한 경우에 활용된다.



        

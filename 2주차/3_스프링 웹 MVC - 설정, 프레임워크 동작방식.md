<h2>스프링 웹 MVC - 설정, 프레임워크 동작방식</h2>

MVC 패턴은 애플리케이션을 개발할 때 사용하는 디자인 패턴이다. 애플리케이션의 개발 영역을 MVC(Model, View, Controller)로 구분하여 각 역할에 맞게 코드를 작성하는 개발 방식이다.
<br>
<br>
**스프링 MVC 설정**

src/main/java/config/MvcConfig.java
```java
package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc; 
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**")
			.addResourceLocations("classpath:/static/");
	}
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp");
	}
}

```

스프링 MVC를 사용하려면 다양한 구성요소를 설정해야 한다.

-   **@EnableWebMvc 애노테이션**은 스프링 MVC 설정을 활성화한다. 스프링 MVC를 사용하는데 필요한 다양한 설정을 생성한다.
    -   @EnableWebMvc 애노테이션이 스프링 MVC를 사용하는데 필요한 기본적인 구성을 설정해준다면 **WebMvcConfigurer 인터페이스**는 스프링 MVC의 개별 설정을 조정할 떄 사용한다.
-   **configureDefaultServletHandling** : DispatcherServlet의 매핑 경로를 '/'로 주었을 때, JSP/HTML/CSS 등을 올바르게 처리하기 위한 설정을 추가한다.
-   **configureViewResolvers** : JSP를 이용해서 컨트롤러 실행 결과를 보여주기 위한 설정을 추가한다.
    -   configureDefaultServletHandling() 메서드와 configureViewResolvers() 메서드는 WebMvcConfigurer 인터페이스에 정의된 메서드로 각각 디폴트 서블릿과 ViewResolver와 관련된 설정을 조정한다. 이 두 메서드가 내부적으로 생성한 설정의 경우에도 관련 빈을 직접 설정하면 20~30여 줄의 코드를 작성해야 한다.
-   **addResourceHandlers** : CSS, JS, 이미지 등등 정적인 지원들을 저장할 경로를 지정한다(src/main/resources/static 경로 생성).
<br>

**web.xml 파일에 DispatcherServlet 설정**

-   스프링 MVC가 웹 요청을 처리하려면 DispatcherServlet을 통해서 웹 요청을 받아야 한다.    
-   이를 위해 web.xml 파일에 DispatcherServlet을 등록한다. src/main/webapp/WEB-INF 폴더에 web.xml 파일을 작성하면 된다.
   
    **src/main/webapp/WEB-INF/web.xml**
    
       ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app version="4.0" xmlns="<http://xmlns.jcp.org/xml/ns/javaee>" xmlns:xsi="<http://www.w3.org/2001/XMLSchema-instance>" xsi:schemaLocation="<http://xmlns.jcp.org/xml/ns/javaee>                       <http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd>">
        <servlet>
    		<!-- DispatcherServlet을 dispatcher라는 이름으로 등록한다. -->
            <servlet-name>dispatcher</servlet-name>
            <servlet-class>
                org.springframework.web.servlet.DispatcherServlet
            </servlet-class>
            <init-param>
    			<!-- contextClass 초기화 파라미터를 설정한다. 
    			자바 설정을 사용하는 경우 AnnotationConfigWebApplicationContext 클래스를 사용한다. 
    			이 클래스는 자바 설정을 이용하는데 웹 어플리케이션 용 스프링 컨테이너 클래스이다. -->
                <param-name>contextClass</param-name>
                <param-value>
                    org.springframework.web.context.support.AnnotationConfigWebApplicationContext
                </param-value>
            </init-param>
            <init-param>
				<!-- contextConfigLocation 초기화 파라미터값을 지정한다.
	    		이 파라미터에는 스프링 설정 클래스 목록을 지정한다.
	    		각 설정 파일의 경로는 줄바꿈이나 콤마로 구분한다. -->
                <param-name>contextConfigLocation</param-name>
                <param-value>
                    config.MvcConfig
                    config.ControllerConfig
                </param-value>
            </init-param>
    		<!-- 톰캣과 같은 컨테이너가 웹 어플리케이션을 구동할 때 이 서블릿을 함께 실행하도록 설정한다. -->
            <load-on-startup>1</load-on-startup>
        </servlet>
        
    	<!-- 모든 요청을 DispatcherServlet이 처리하도록 서블릿 매핑을 설정했다. -->
        <servlet-mapping>
            <servlet-name>dispatcher</servlet-name>
            <url-pattern>/</url-pattern>
        </servlet-mapping>
        <!-- HTTP 요청 파라미터의 인코딩 처리를 위한 서블릿 필터를 등록한다.
    	스프링은 인코딩 처리를 위한 필터인 CharacterEncodingFilter 클래스를 제공한다.
    	encoding 초기화 파라미터를 설정해서 HTTP 요청 파라미터를 읽어올 때 사용할 인코딩을 지정한다. -->
        <filter>
            <filter-name>encodingFilter</filter-name>
            <filter-class>
                org.springframework.web.filter.CharacterEncodingFilter
            </filter-class>
            <init-param>
                <param-name>encoding</param-name>
                <param-value>UTF-8</param-value>
            </init-param>
        </filter>
        <filter-mapping>
            <filter-name>encodingFilter</filter-name>
            <url-pattern>/*</url-pattern>
        </filter-mapping>
        
    </web-app>
    
    ```
    
-   DispatcherServlet은 초기화 과정에서 contextConfigLocation 초기화 파라미터에 지정한 설정 파일을 이용해서 스프링 컨테이너를 초기화 한다.
    
-   즉, 위의 설정은 MvcConfig 클래스와 ControllerConfig 클래스를 이용해서 스프링 컨테이너를 생성한다.
<br>    
<br>

**스프링 MVC 동작방식**

여러 가지 구조를 가진 MVC 패턴 중 아래 그림의 구조를 그대로 사용하고 있는 것이 Spring MVC이다. 일반 MVC 패턴도 유지보수하기에 좋은 패턴임은 확실하나, 구조가 복잡해지는 한계가 있다. 이러한 한계를 극복하게 해주는 것이 Spring MVC이다.

![enter image description here](https://velog.velcdn.com/images/sgwon1996/post/0d59ff3e-1ccd-4a99-bd31-f550e6223f77/image.png)

**DispatcherServlet**은 웹 브라우저의 요청을 받기 위한 창구 역할을 하고, 다른 주요 구성요소들을 이용해서 요청 흐름을 제어한다.

**HandlerMapping**은 클라이언트의 요청을 처리할 핸들러 객체를 찾아준다. 클라이언트의 요청을 실제로 처리한 뒤 뷰 정보와 모델을 찾아준다.

**HandlerAdapter**는 DispatcherServlet과 핸들러 객체 사이의 변환을 처리해준다.

**ViewResolver**는 요청 처리 결과를 생성할 View를 찾아주고

**View**는 최종적으로 클라이언트 응답을 생성해서 전달한다.

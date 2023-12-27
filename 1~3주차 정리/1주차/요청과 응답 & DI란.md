# 1.고원일

## 웹기초 : 요청과 응답 이해하기
<details>
둘다 내장 객체의 종류 : 내장객체는 별도의 객체 생성 없이 각 내장 객체의 메서드를 사용할 수 있음 메이븐 레파지토리 API 문서가 이에 해당

요청은 request : 클라이언트의 요청 정보를 저장하는 역할, 웹 브라우저가 웹 서버에 웹 페이지를 달라고 하는 것

응답은 response : 클라이언트의 요청에 대한 응답 정보를 저장하는 역할, 웹 브라우저가 요청한 웹 페이지를 웹 브라우저에 제공하는 것

요청(request )은 JSP에서 가장 많이 사용됨 클라이언트가 전송한 요청 정보를 담고 있음

	<li>데이터 정송 방식 : <%=request.getMethod() %></li>
	<li>URL : <%=request.getRequestURL() %></li>
	<li>프로토콜 : <%=request.getProtocol() %></li>
	<li>서버이름 : <%=request.getServerName() %></li>
	<li>포트번호 : <%=request.getServerPort() %></li>
	<li>클라이언트 IP : <%=request.getRemoteAddr() %></li>
	<li>쿼리스트링 : <%=request.getQueryString() %></li>
	<li>파마미터 : <%=request.getParameter("eng") %></li>
	<li>파마미터 : <%=request.getParameter("han") %></li>
​
응답(response)는 요청에 의한 매개변수를 얻어와 내용을 처리하고 응답하는 역할 만약 클라이언트가 아이디와 비밀번호를 입력하여 정보를 받아 정보 일치 여부에 따른 뷰(JSP)로 이동하여 클라이언트에게 정보를 전달 또는 클라이언트의 정보를 저장하는 역할이라 보면된다.


	
	<% String id = request.getParameter("id");
	String pw = request.getParameter("pw");

     if("momo".equals(id) && "1234".equals(pw)){
	response.sendRedirect	("Welcome.jsp?id" + request.getParameter("id"));
​</details>
## 스프링 : DI란?
<details>
DI (Dependency 의존 Injection 주입)
이 단어를 이해하기 위해 의존을 먼저 이해해야한다.

쉽게 말해 꼬꼬무라고 이해하면 될 듯함. 

학원이 있으면 학원의 직원이 있고 그 직원과 강사 그리고 학생과 연결되어 있는 것 처럼 학원이라는 큰 컨테이너 안에 객체가 있고 서로 의존하고 있다고 생각하면 될 것 같음.

@Autowired 어노테이션을 이용해서 의존성을 주입함.

DI를 통해 의존성을 외부에서 주입하여 다형성을 이용하여 유연하게 객체들을 사용할 수 있음 또한 코드의 결합도를 낮추어 유연하게 변경이 가능하게 만들어줌 

하나의 코드를 보면
```
public class MemberRegisterService { 
//의존 객체를 직접 생성
private MemberDao memberDao = new MemberDao();
//의존하는 MemberDao의 객체도 함께 생성
MemberRegisterService svc = new MemberRegisterService();
```
위 코드는 클래스 내부에 직접 의존하는 객체를 생성해서 사용하려고 new를 써서 의존하는 클래스를 불러오는 코드이다.

이는 생성은 쉽지만 유지보수 측면에서 문제점을 유발한다.

이를 위해 DI를 사용하여 객체를 직접 생성하는 대신 의존 객체를 전달 받는 방식으로 사용한다.

아래의  DI 코드를 보면 직접 객체를 생성하지 않고 생성자를 통해서 의존 객체를 전달 받는다.

즉 MemberRegisterService가 MemberDao 의 객체를 주입 받은 것.

```
2.
public MemberRegisterService(MemberDao memberDao){
	this.memberDao = memberDao;
}
```

Di는 변경의 유연함 때문에 사용한다. 만약 이미 만들어진 MemberDao의 객체 정보에 기능을 제공하는 changeMemberDao 클래스를 하나 더 만들어야 한다고 가정해보자.

```
3.
public class changeMemberDao extends MemberDao{
}
```

MemberDao 의 정보를 changeMemberDao 가 상속 받고 new 를 써서 만든 1번 코드 MemberDao  객체와 관련된 클래스 하나하나의 소스를 changeMemberDao  다 바꿔줘야한다. 

그게 수량이 많다면 변경에 있어서 유연함이 없다고 하는 것이다.  

하지만 DI를 사용하면 한번만 변경하면 끝
```
2번과 같이 MemberRegisterService안에 MemberDao를 주입
객체 생성
MemberDao memberDao = new MemberDao();
MemberRegisterService regSvc = new MemberRegisterService(memberDao);
3번 코드에 다형성을 이용한 객체 생성 코드를 한번만 변경하면 사용가능
MemberDao memberDao = new changeMemberDao ();
MemberRegisterService regSvc = new MemberRegisterService(memberDao);
```

DI의 실제 객체는 main 메서드에 생성하는데 이 방법 보다는 객체를 생성하고 의존 객체를 주입해주는 클래스를 따로 작성하는 것이 좋다.

즉 의존성 전용 클래스를 만드는 것. 레고의 조립 클래스 느낌으로 프로팰러, 바퀴, 몸통을 연결해주는 느낌으로. 
```
public class Lego
private MemberDao memberDao;
private MemberRegisterService regSvc;

public Lego(){
memberDao = new MemberDao();
regSvc = new MemberRegisterService(memberDao);

여기서 만약 changeMemberDao 를 써야한다면

public Lego(){
memberDao = new changeMemberDao ();
regSvc = new MemberRegisterService(memberDao);
```


참고사항: 그래들의 의존성도 찾아보니 지정된 의존들의 프로그램을 가져오는 건 맞지만 DI의 패턴과는 다른 점이 있음, DI는 객체 간의 의존성을 런타임에 주입하는 것이지만 그래들의 디펜던시스는 빌드 타임에 필요한 라이브러리를 제공해줌.

즉 서로의 의존성 목적이 다르다고 볼 수 있다.
한마디로 그래들이나 메이븐은 빌드타임의 의존 라이브러리를 받는 목적으로 DI 패턴을 사용하진 않는다.

DI 패턴은 주로 생성자 주입, 세터 주입, 인터페이스 주입 등을 통해 의존성을 주입하는데, 스프링 프레임워크에서는 이를 구현하기 위해 @Autowired 어노테이션을 제공한다
</details>

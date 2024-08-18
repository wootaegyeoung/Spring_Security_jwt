# Spring_Security_jwt
각 파트별로 나눠서 spring Security와 jwt를 이용한 로그인과 회웝가입 api 서버에 대한 내용을 정리했습니다.<br>

##stateless
개발방식은 stateless로 작성하였다. 이해를 돕기위해 간단하게 첨언하자면, "Stateless 구조에서 server는 단순히 요청이 오면 응답을 보내는 역할만 수행하며, 세션 관리는 클라이언트에게 책임이 있다." 즉, 서버가 클라이언트의 상태정보를 보존하지 않는 거이다. <br>

- 웹서버 통신(http) 특성상 사용자(브라우저)의 이전 상태 client(쿠키) or server(세션) 정보를 기록하지 않는 접속이란 의미다.
- 브라우저가 데이터를 전송할 때마다 연결하고 바로 끊어버리는 방식이다.
- 장점 : 서버의 확장성이 높기 때문에 대량의 트래픽 발생 시에도 대처를 수월하게 할 수 있다.
- 단점 : 클라이언트의 요청에 상대적으로 Stateful 보다 더 많은 데이터가 소모된다.
---
## jwt 토큰
1. access token
2. refresh toekn
-> 참조자료에서 추가적으로 refresh toekn을 같이 다룸
### 버전 및 의존성
- Spring Boot 3.2.1
- Security 6.2.1
- Lombok
- Spring Data JPA - MySQL
- Gradle - Groovy
- IntelliJ Ultimate




참조 url : https://www.youtube.com/watch?v=NPRh2v7PTZg&list=PLJkjrxxiBSFCcOjy0AAVGNtIa08VLk1EJ

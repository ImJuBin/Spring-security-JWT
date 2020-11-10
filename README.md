# Spring-security-JWT
Skill : Spring Boot, Spring Security, JWT /  Login, Join, info implementation



회원 가입, 로그인 및 조회 서비스를 위한 Back-End API를 정의하고, Java ( Spring Boot ) 프로
젝트로 구현해주세요.

기능요건 
(1) : 회원 가입 API를 구현해 주세요.
1. 회원가입 URI는 다음과 같습니다. : POST /v1/member/join
2. 회원가입 시 필요한 정보는 ID, 비밀번호, 사용자이름 입니다.
3. ID는 반드시 email 형식이어야 합니다.
4. 비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상
의 문자열로 생성해야 합니다.
5. 비밀번호는 서버에 저장될 때에는 반드시 단방향 해시 처리가 되어야 합니다.

기능요건 (2) : 로그인 API를 구현해 주세요.
1. 로그인 URI는 다음과 같습니다. : POST /v1/member/login
2. 사용자로부터 ID, 비밀번호를 입력받아 로그인을 처리합니다.
3. ID와 비밀번호가 이미 가입되어 있는 회원의 정보와 일치하면 로그인이 되었다는 응답
으로 AccessToken을 제공합니다.

기능요건 (3) : 회원정보 조회 API를 구현해 주세요.
1. 회원정보 조회 URI는 다음과 같습니다. : GET /v1/member/info
2. 로그인이 된 사용자에 대해서는 사용자이름, Email, 직전 로그인 일시를 제공합니다.
(화면에는 다음과 같이 표시됩니다.)
ex) 홍길동(kildong@bithumbcorp.com) 님, 환영합니다.
(직전로그인 : 2020/07/01 00:00:00.161124)
3. 로그인이 안된 사용자는 HTTP Status Code를 401 (Unauthorized)로 응답합니다



비기능요건
1. 데이터는 반드시 DB에 저장해주세요.
2. Junit Test 코드는 반드시 작성해주세요.
3. AccessToken은 특별한 사유가 없다면 JWT를 적용해주세요.
4. Front-End 개발자를 위한 API 명세서를 어떻게 제공할 것인지 제시해주세요.
5. 보안코딩(Secure Coding)에 신경써주세요.
6. 향후 서비스 확대 및 요구사항 변경을 고려하여 시스템을 설계해주세요.

기타
1. 혹시 Back-End에서 구현할 수 없는 기능요건이 있다면 어떤 방식으로 구현하면 되는지
방법을 제시해주세요.

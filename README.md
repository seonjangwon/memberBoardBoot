# memberBoardBoot
스프링 부트 memberboard 제출용

#주요 기능
## index
비 로그인시 로그인과 회원가입만 출력
로그인시 로그아웃 글작성 글목록 마이페이지 출력
admin 계정 로그인시 로그아웃 관리자 페이지 출력
## member

### 회원가입
각 항목 필수입력 체크
이메일 체크
사진

### 로그인

### 마이페이지
회원정보 출력
수정 페이지 링크 출력

#### 수정
수정 시 비밀번호 체크
비밀번호 틀리면 글로벌 오류
사진 수정시 사진 변경후 사진 삭제

### 관리자 페이지
인터셉터로 관리자만 접속가능
#### 회원 조회
전체 회원 조회 후 삭제 가능

## board
### 글작성
사진 작성가능 작성자는 세션값으로 자동으로 가져옴

### 전체 조회
페이징 기능 가능
셀렉트 선택후 변경 버튼 누르면 원하는 글 갯수 변경 가능


### 검색
검색 타입과 검색어를 입력 후 검색을 누르면 검색 가능
검색 결과도 페이징과 글 갯수 변경 가능


### 수정
세션이메일이 글작성자와 일치 해야 글 수정 가능

### 삭제
관리자와 글 작성자만 삭제가능

### 조회
글 내용과 댓글 출력

댓글 입력 가능
댓글 입력시 ajax로 바로 출력
관리자와 댓글 작성자만 댓글 삭제 가능


## 인터셉터

### 로그인 인터셉터
미 로그인시 회원가입 로그인 인덱스 이메일체크만 가능


### 관리자 인터셉터
관리자 페이지와 회원 전체 목록 페이지는 관리자 계정만 가능


# Personal Project - Restaurants in 'Kodoku no gurume'

## Technology stack to be used (planned)

### Server side

* JAVA 1.8
* Spring Boot 2.1.6.RELEASE
  * spring-boot-starter-web
  * spring-boot-starter-data-jpa
  * spring-boot-starter-security
  * spring-boot-starter-oauth2-client
  * spring-boot-starter-websocket
  * spring-boot-devtools
* etc. Dependencies
  * Lombok
  * Querydsl
* MySQL 5.6

### Client side

* First step using HTML / CSS / jQuery for web.
* Second step using React Native or Vue Native for iOS / Android apps. 

### Development Environment

* IDE
  * IntelliJ IDEA Ultimate
  * Visual Studio Code
  * Eclipse 2019-06 & STS 4.3.0 (Spare)
* OS
  * Local dev laptop : Windows 10 
  * Stage server : Amazon Linux AMI 2018.03.0.20190611 x86_64 HVM gp2

## Daily log of Development

### 2019-07-22

* Bootstrap 4.3, SweetAlert2 본격 적용 시작.  
  * jQuery로 어디까지 만들고 React.js로 변경할 것인지 검토 중.

### 2019-07-16

* .gitignore 파일 작동 이상으로 .yml 파일이 remote repo에 push 됨.  
  2번째 git 초기화로 인해 history가 전부 소멸됨.
  .gitignore 파일이 제대로 작동하지 않을 경우에 대한 대책 -  
  https://projooni.tistory.com/entry/gitignore-%EC%A0%81%EC%9A%A9-%EC%95%88%EB%90%A0%EB%95%8C

### 2019-07-11

* Spring Security 설정 및 로그인 로직 작성  
  * successHandler에서 ajax response 로그인 처리

### 2019-07-02

* Spring Data Jpa를 통한 1:N 테이블 정의

``` java
@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
@JoinColumn(name="user_idx")
private List<Role> roles = new ArrayList<>();
```

* Java 8의 Instant type 필드를 활용한 MySQL timestamp 타입 컬럼 정의

``` java
@Column(name = "registration_date", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
private Instant registrationDate;

@Column(name = "update_date", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
private Instant updateDate;
```

* EC2 Linux를 통한 ElastiCache 접속하기
  * 클러스터 노드에 연결하는 법 -  
  https://docs.aws.amazon.com/ko_kr/AmazonElastiCache/latest/red-ug/GettingStarted.ConnectToCacheNode.html

* Git
  * 실수로 인한 AWS Credential info file push - .git remove and rebase
  * 좋은 git 커밋 메시지를 작성하기 위한 8가지 약속 -  
     https://djkeh.github.io/articles/How-to-write-a-git-commit-message-kor/

### 2019-06-27

* Spring-data-jpa basic entity config
  * 로그인 로직을 위한 entity 정의, 자동 테이블 생성
    * 테이블의 PK 컬럼에 기본 값을 랜덤 varchar type으로 입력하도록 구성.  
      자동 채번 기능인 MySQL의 auto increment를 사용하지 않고  
      hibernate의 기능을 활용하여 varchar Type의 UUID로 발행.  
    * 차후 다른 DB와 유저/멤버 테이블을 병합할 경우 확장성 용이.  
    * 시간에 따른 정렬 기준은 등록일 / 변경일을 기준으로 할 것.
  * schema 데이터 정렬 방식 utf8-general-ci로 임시 적용.  
    MySQL 데이터 정렬 방식을 utf8mb4-unicode-ci로 할 경우  
    Specified key was too long; max key length is 767 bytes 에러 발생  
    해결법을 검색해서 적용했으나 변화 없음.
  * User Entity, Role Entity 적용.  
    회원가입, 로그인 테스트 필요.

### 2019-06-23

* Local laptop installed MySQL 5.6
  * scoop을 사용하여 MySQL 5.6 설치  
  
  ``` powershell
    KangSH@KSH-DELL7586 >
    > scoop update
    > scoop status
    > scoop install mysql56 mysql57
    > sudo mysqld --install ## 반드시 관리자 권한으로 실행해야 함.
  ```
  
  * MySQL 5.6 기준 DATABASE, TABLE, USER 설정  
  
  ``` mysql
    -- [USER PASSWORD UPDATE]    
    GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' IDENTIFIED BY '1111'; -- 모든 권한
    GRANT USAGE ON *.* TO '[ID]'@'[ACCESS HOST]' IDENTIFIED BY '[PASSWORD]'; -- 사용 권한

    -- [CREATE DATABASE]
    CREATE DATABASE [NEW DATABASE NAME]
    DEFAULT CHARACTER SET UTF8
    DEFAULT COLLATE UTF8_GENERAL_CI;
  
    -- [CREATE USER]
    CREATE USER '[ID]'@'[ACCESS HOST]' IDENTIFIED BY '[PASSWORD]';

    -- [권한 설정]
    GRANT ALL ON [DATABASE NAME].'[TABLE NAME]' TO '[USER NAME]'@'[ACCESS HOST]';  -- 모든 권한
    GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, DROP, INDEX, ALTER 
    ON [DATABASE NAME].'[TABLE NAME]' 
    TO '[USER NAME]'@'[ACCESS HOST]'; -- 특정 권한


  ```

  * 참조 : MySQL Windows에 수동 설치하기 -  
    http://www.itpaper.co.kr/window%EC%9A%A9-mysql-%EC%88%98%EB%8F%99-%EC%84%A4%EC%B9%98%ED%95%98%EA%B8%B0/  

  * 수동 설치 시 [Microsoft Visual C++ 2010 재배포 가능 패키지(x64)](https://www.microsoft.com/ko-kr/download/details.aspx?id=14632)를  
    반드시 설치해야 mysqld를 실행 할 수 있다.  

### 2019-06-22

* Amazon Linux AMI 인스턴스 생성  
* Windows 10 Power Shell SSH 접속  
  * Windows 10 에서 pem 파일을 사용하기 – https://swiftcoding.org/lightsail-from-window10
  * 사용 권한 변경 중에 부모 권한 상속 에러 발생 해결 -  
    https://m.blog.naver.com/PostView.nhn?blogId=crehacktive3&logNo=220991428540&proxyReferer=https%3A%2F%2Fwww.google.com%2F 
* Amazon Linux AMI를 사용하여 LAMP 웹 서버 설치
https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/install-LAMP.html
# spring_db_jdbc

## 1. 테스트 환경 - h2 db : [download](/h2-2022-06-13.zip)

### [환경 Setting] 데이터베이스 파일 생성 방법

사용자명은 sa 입력

JDBC URL에 다음 입력,

jdbc:h2:~/test (최초 한번) => 이 경우 [연결 시험] 을 호출하면 오류가 발생한다. 

[연결]을 직접 눌러주어야 한다.

~/test.mv.db 파일 생성 확인 (Home Folder에서 확인)

이후부터는 jdbc:h2:tcp://localhost/~/test 이렇게 접속
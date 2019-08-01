> 2016 - 고등학교 1학년 1학기 자유프로젝트 수행평가

# Messenger 
서버와 클라이언트를 따로 개발한 평범한 소켓통신 프로그램  
수행평가 의도에 맞게 개발하기위해 정당한 이름을 붙이고 몇몇추가기능을 더함.

## 실행 과정
회원가입시 정규식으로 비밀번호 길이와 사용문자를 제한하고 정해진 메일주소로 회원가입한 사람의 이메일에 인증코드를 만들어 인증메일을 보내고 인증코드를 저장하고 있는다.   
맞는 코드가 입력되었을경후 회원가입이 완료되고 DB로 회원정보가 저장된다 로그인후엔 단체 메신저방에 들어올수 있고 socket을 사용하여 통신을하며 방을 나가거나 들어올시 OOO님이 입장하셨습니다와 같은 메세지가 뿌려진다.  

## 평가조건
  - JDBC 사용
  - Swing 사용
## 제출시간
  - 16-11-29 PM 11:57
## 기능 & 기술
  - 로그인 & 회원가입
    - SMTP 메일인증
    - 정규식
    - JDBC
  - Swing JFrame
  - Socket
  - Thread

## 구동 이미지
<img src='doc_img/img_0.png'><br>
<img src='doc_img/img_5.png' height='500'><img src='doc_img/img_1.png' height='500'><br>
<img src='doc_img/img_2.png' height='500'><img src='doc_img/img_3.png' height='500'><br>
<img src='doc_img/img_4.png'>

> PPT는 약간의 일반인 패치가 덜풀려서 이과감성이 아니다 ( 오글거린다 )

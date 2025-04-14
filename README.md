# 📰 뉴스피드 프로젝트

친구들의 가장 최근에 업데이트된 게시물들을 볼 수 있는 페이지 구성

---

## 개발자
윤경모 : 팀장, 친구 관리 기능 및 댓글 기능 구현

배재훈 : 게시물 관리 기능과 페이지네이션(게시물 10개씩 조회) 구현

이귀현 : 회원가입 기능 구현

임민지 : 회원 관리 기능 및 로그인 필터, 좋아요, 공통 응답 기능 구현

## 폴더 구조
```
src                                                   
├─ main                                               
│  ├─ java                                            
│  │  └─ com                                          
│  │     └─ example                                   
│  │        └─ news                                   
│  │           ├─ common                              
│  │           │  ├─ ApiResponse.java                 
│  │           │  ├─ CommonResponse.java              
│  │           │  ├─ CustomException.java             
│  │           │  ├─ Error.java                       
│  │           │  ├─ GlobalExceptionHandler.java      
│  │           │  └─ SuccessCode.java                 
│  │           ├─ config                              
│  │           │  ├─ LoginFilter.java                 
│  │           │  ├─ PasswordEncoder.java             
│  │           │  └─ WebConfig.java                   
│  │           ├─ controller                          
│  │           │  ├─ BoardController.java             
│  │           │  ├─ CommentController.java           
│  │           │  ├─ FriendshipController.java        
│  │           │  ├─ UserBoardController.java         
│  │           │  └─ UserController.java              
│  │           ├─ dto                                 
│  │           │  ├─ boardDto                         
│  │           │  │  ├─ BoardRequestDto.java          
│  │           │  │  └─ BoardResponseDto.java         
│  │           │  ├─ commentDto                       
│  │           │  │  ├─ CommentRequestDto.java        
│  │           │  │  └─ CommentResponseDto.java       
│  │           │  ├─ friendDto                        
│  │           │  │  ├─ FriendBoardResponseDto.java   
│  │           │  │  ├─ FriendResponseDto.java        
│  │           │  │  ├─ FriendshipRequestDto.java     
│  │           │  │  └─ FriendshipResponseDto.java    
│  │           │  └─ userDto                          
│  │           │     ├─ UpdatePwResponseDto.java      
│  │           │     ├─ UserRequestDto.java           
│  │           │     ├─ UserResponseDto.java          
│  │           │     └─ UserWithdrawResponseDto.java  
│  │           ├─ entity                              
│  │           │  ├─ BaseEntity.java                  
│  │           │  ├─ Board.java                       
│  │           │  ├─ Comment.java                     
│  │           │  ├─ Friendship.java                  
│  │           │  └─ User.java                        
│  │           ├─ repository                          
│  │           │  ├─ BoardRepository.java             
│  │           │  ├─ CommentRepository.java           
│  │           │  ├─ FriendshipRepository.java        
│  │           │  └─ UserRepository.java              
│  │           ├─ service                             
│  │           │  ├─ BoardService.java                
│  │           │  ├─ BoardServiceImpl.java            
│  │           │  ├─ CommentService.java              
│  │           │  ├─ CommentServiceImpl.java          
│  │           │  ├─ FriendshipService.java           
│  │           │  ├─ FriendshipServiceImpl.java       
│  │           │  ├─ UserService.java                 
│  │           │  └─ UserServiceImpl.java             
│  │           ├─ NewsApplication.java                
│  │           └─ Test.java                           
│  └─ resources                                       
│     └─ application.properties                       
└─ test                                               
   └─ java                                            
      └─ com                                          
         └─ example                                   
            └─ news                                   
               └─ NewsApplicationTests.java
```


## 🛠️ 기술 스택

- **Backend**: Spring Boot, Spring Web, Spring Data JPA, Lombok 
- **DB**: MySQL
- **보안**: Bcrypt
- **빌드 도구**: Gradle  

---

## 📌 주요 기능 : 사용자 인증 / 프로필 관리 / 뉴스피드 관리 / 친구 관리

### 🔐 사용자 인증
- **회원가입**
  - 이메일, 사용자 이름, mbti, 비밀번호 등을 입력하여 회원가입  
  - 비밀번호는 Bcrypt 암호화

- **회원탈퇴**
  - 비밀번호 확인 후 탈퇴 처리
  - 탈퇴 후 이메일은 재사용 및 복구 불가
    
---

### 👤 프로필 관리
- **조회**
  - 타인 프로필 조회 시 민감 정보 제외
- **수정**
  - 본인만 수정 가능
  - 비밀번호 수정 시 현재 비밀번호 확인 필요

---

### 📝 뉴스피드 관리
- **CRUD 기능**
  - 게시물 작성, 조회, 수정, 삭제
  - 수정/삭제는 작성자만 가능

- **조회 정렬 및 페이징**
  - 최신순 정렬(생성일 기준 내림차순 정렬)
  - 페이지당 10개

---

### 🤝 친구 관리
- 친구 추가 및 삭제 가능
- 친구의 최신 게시물 확인(생성일 기준 내림차순 정렬)
- 수락형 친구 관계 (친구 수락이 필요함)

---

### 📊 ERD
![image](https://github.com/user-attachments/assets/2866695c-e53a-4b62-9876-1f377522a160)

### 🧾 API 명세서
![image](https://github.com/user-attachments/assets/986f0cdc-bb52-4520-8375-1bb98bf972a9)



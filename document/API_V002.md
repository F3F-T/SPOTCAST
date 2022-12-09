| FUCTION | METHOD | API  | QueryString | RequestBody | ResponseBody | 비고 |
| --- | --- | --- | --- | --- | --- | --- |
| 회원가입 | POST | /user |  | user data |  |  |
| 회원탈퇴 | DELETE | /user/{userId} |  | 로그인여부 , 2차 인증 |  |  |
| 회원정보수정 | UPDATE | /user/{userId} |  | user data |  |  |
| 회원조회 | GET | /user/{userId} |  | 로그인 여부 |  |  |
| 로그인 | POST | /user/login |  | login info |  |  |
| 로그아웃 | DELETE | /user/logout |  | login info |  |  |
| 비밀번호 찾기 | POST | /user/find/password/{email} |  |  |  |  |
| 이메일 찾기 | POST | /user/find/email |  | 인증여부,전화번호 |  | 재정의 |
| 본인인증(소셜로그인이 아닌 경우) | POST | /user/certification/{email} |  | 인증에 필요한 값  |  |  |
| 게시글 작성 | POST | /board |  | board data,로그인 여부 |  |  |
| 게시글 수정 | UPDATE | /board/{boardId} |  | board data, 로그인 여부 |  |  |
| 게시글 삭제 | DELETE | /board/{boardId} |  | 로그인 여부 체크 |  |  |
| 게시글 조회 | GET | /board/{boardId} |  |  |  |  |
| 게시글 리스트 조회 | GET | /board/list | ?pageNum=&pageSize= |  |  |  |
| 포트폴리오 작성 | POST | /portfolio |  | 로그인여부 체크 |  |  |
| 포트폴리오 수정 | UPDATE | /portfolio/{portfolioId} |  | 로그인여부 체크 |  |  |
| 포트폴리오 삭제 | DELETE | /portfolio/{portfolioId} |  | 로그인여부 체크 |  |  |
| 포트폴리오 조회 | GET | /portfolio/{portfolioId} |  | 로그인여부 체크 |  |  |
| 즐겨찾기 추가 | POST | /bookmard/portfolio |  | 유저정보와 포트폴리오 정보 |  |  |
| 즐겨찾기 제거 | DELETE | /bookmard/portfolio |  | 유저정보와 포트폴리오 정보 |  |  |
| 게시글 좋아요 | POST | /board/like/{boardId} |  | 유저정보 |  |  |
| 게시글 좋아요 취소 | DELETE | /board/unlike/{boardId} |  | 유저정보 |  |  |
| 게시글 댓글 작성 | POST | /comment |  | 게시글 정보와 내용 |  |  |
| 게시글 댓글 수정 | UPDATE | /comment/{commentId} |  | 댓글 내용 + 로그인여부 |  |  |
| 게시글 댓글 삭제 | DELETE | /comment/{commentId} |  |  |  |  |
| 게시글 스크랩 박스에 추가 | POST | /scrap/{scrapId}/board/{boardId} |  | 게시글 정보와 스크랩 정보 |  |  |
| 게시글 스크랩 박스 조회 | GET | /user/{userId}/scrap |  | 로그인 여부 |  |  |
| 스크랩 내부 게시글 리스트 조회 | GET | /user/scrap/{scrapId} |  | 로그인 여부 |  |  |
| 스크랩박스 삭제 | DELETE | /scrap/{scrapId} |  | 로그인 여부 |  |  |
| 구인글 지원하기 | POST | /apply |  | 지원 정보 |  |  |
| 구인글 지원 취소 | DELETE | /apply/{applyId} |  | 로그인여부 |  |  |
| 지원한 사람 조회 | GET | /applylist/{userId} |  | 로그인여부 |  |  |
| 쪽지 보내기 | POST | /sendMessage |  | 로그인 여부, 발신자 수신자 정보 |  |  |
| 받은 쪽지 조회하기 | GET | /message/user/{userId} |  | 로그인 여부 |  |  |
| 카테고리 생성 | POST | /category |  |  |  | 어드민 |
| 카테고리에 포함된 게시글 조회 | GET | /category/boardList | ?pageNum=&pageSize= |  |  |  |
| 카테고리 삭제 | DELETE | /category/{categoryId} |  |  |  | 어드민만 삭제 가능,아무 게시글이 없는지 확인 |
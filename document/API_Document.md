| ACTION | Method | EndPoint | Query String | Request Body |
| --- | --- | --- | --- | --- |
| 회원가입 | POST | /user |  | email, password, phone, name |
| 회원 탈퇴 | DELETE | /user/{user_id} |  | password |
| 회원정보수정 | PATCH | /user/{user_id} |  |  |
| 회원정보 조회 | GET | /user/{user_id} |  |  |
| 로그인 | POST | /user/login | email, password |  |
| 로그아웃 | DELETE | /user/logout |  |  |
| 아이디 찾기 | GET | /user/find |  |  |
| 비밀번호 찾기 | GET | /user/find/{email} |  |  |
| 즐겨찾기 추가 | POST | /user/favorite |  | user1_id, user2_id(user1 은 누른사람) |
| 즐겨찾기 삭제 | DELETE | /user/favorite |  | user1_id, user2_id(user1 은 누른사람) |
| 게시글 조회 | GET | /board | board_type, category(업종) |  |
| 게시글 작성 | POST | /board |  | 상세 내용들 |
| 게시글 정보 조회 | GET | /borad/{board_id} |  |  |
| 게시글 수정 | PATCH | /board/{board_id} |  |  |
| 게시글 삭제 | DELETE | /board/{board_id} |  |  |
| 게시글 좋아요 개수  | GET | /board/{board_id}/like |  |  |
| 게시글 좋아요 추가 | POST | /board/{board_id}/like |  | user_id |
| 개시글 좋아요 삭제 | DELETE | /board/{board_id}/like |  | user_id |
| 게시글 스크랩 | POST | /board/{board_id}/scrap/{scrap_id} |   |  |
| 게시글 지원하기 | POST | /board/{board_id}/apply |  | user_id |
| 게시글 댓글 | POST | /board/{board_id}/{user_id}/comment |  | content |
| 게시글 댓글 수정 | PATCH | /board/comment/{comment_id} |  | content |
| 게시글 댓글 삭제 | DELETE | /board/comment/{comment_id} |  |  |
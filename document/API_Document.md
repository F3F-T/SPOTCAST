| ACTION | Method | EndPoint | Query String | Request Body |
| --- | --- | --- | --- | --- |
| 회원가입 | POST | /user |  | email, password, phone, name |
| 회원 탈퇴 | DELETE | /user/{user_id} |  | password,token |
| 회원정보수정 | PATCH | /user/{user_id} |  | token |
| 회원정보 조회 | GET | /user/{user_id} |  | token |
| 로그인 | POST | /user/login |  | email, password |
| 로그아웃 | DELETE | /user/logout |  |  |
| 아이디 찾기 | POST | /user/find |  | name,phone |
| 비밀번호 찾기 | GET | /user/find/{email} |  |  |
| 비밀번호 변경 | PATCH | /user/password |  | email, beforePassword, afterPassword |
| 즐겨찾기 추가 | POST | /user/favorite/{user1_id}/{user2_id} |  | token |
| 즐겨찾기 삭제 | DELETE | /user/favorite/{user1_id}/{user2_id} |  | token |
| 즐겨찾기 리스트 조회 | GET | /user/{user_id}/favorite |  | token |
| 스크랩 박스 조회 | GET | /user/{user_id}/scrap |  | token |
| 스크랩 리스트 조회 | GET | /user/{user_id}/scrap/{scrap_id} |  |  |
| 게시글 조회 | GET | /board | board_type, category(업종) |  |
| 게시글 작성 | POST | /board |  | 상세 내용들,token |
| 게시글 정보 조회 | GET | /borad/{board_id} |  |  |
| 게시글 수정 | PATCH | /board/{board_id} |  | 수정 내용들,token |
| 게시글 삭제 | DELETE | /board/{board_id} |  | token |
| 게시글 좋아요 개수  | GET | /board/{board_id}/like |  | token |
| 게시글 좋아요 추가 | POST | /board/{board_id}/like |  | user_id,token |
| 개시글 좋아요 삭제 | DELETE | /board/{board_id}/like |  | user_id,token |
| 게시글 스크랩 | POST | /board/{board_id}/scrap/{scrap_id} |   | token |
| 게시글 지원하기 | POST | /board/{board_id}/apply |  | user_id,token |
| 게시글 지원 취소 | DELETE | /board/{board_id}/apply/{apply_id} |  | token |
| 게시글 댓글 | POST | /board/{board_id}/{user_id}/comment |  | content,token |
| 게시글 댓글 수정 | PATCH | /board/comment/{comment_id} |  | content,token |
| 게시글 댓글 삭제 | DELETE | /board/comment/{comment_id} |  | token |
| 포트폴리오 정보 조회 | GET | /portfolio/{portfolio_id} |  | toekn |
| 포트폴리오 작성 | POST | /portfolio |  | 상세내용들, token |
| 포트폴리오 수정 | PATCH | /portfolio/{portfolio_id} |  | 수정 내용들, token |
| 포트폴리오 삭제 | DELETE | /portfolio/{portfolio_id} |  | token |
|  |  |  |  |  |
| ACTION | Method | EndPoint | Path variable | Request Body | cookie | Response Body |
| --- | --- | --- | --- | --- | --- | --- |
| 회원가입 | POST | /auth/signup |  | email,  phone, name,, loginMemberType, loginType, authority, information |  |  |
| 회원 탈퇴 | DELETE | /member/{memberId} | memberId | email, password | token |  |
| 회원정보수정 | PATCH | /member/{memberId} |  |  | token |  |
| 회원정보 조회 | GET | /member/{memberId} | memberId |  |  | id, email, name , twitter, instagram, other sns, loginMemberType, loginType authority, information |
| 내 정보 조회 | GET | /member/myInfo |  |  | token | id, email, name , twitter, instagram, other sns, loginMemberType, loginType authority, information |
| 로그인 | POST | /auth/login |  | email, password |  | id, email, name ,loginMemberType,authority, grantType, accessToken, accessTokenExpireIn |
| 로그아웃 | DELETE | /authlogout |  |  | token |  |
| 토큰 재발행 | POST | /auth/reissue |  |  | token |  |
| 이메일 인증번호 전송 | POST | /auth/email-certification/sends |  | email |  |  |
| 이메일 인증번호 확인 | POST | /auth/email-certification/confirms |  | email, certificationNumber |  |  |
| 비밀번호 변경(로그인 X) | POST | /member/find/password |  | email, afterPassword |  |  |
| 비밀번호 변경(로그인 O) | POST | /member/{memberId}/change/password | memberId | email, beforePassword, afterPassword | token |  |
| 정보 변경 | POST | /member/{memberId}/change/information | memberId | information, twitter, instagram, otherSns | token |  |
| 이메일 중복 검사 | GET | /auth/member-emails/{email}/exists | email |  |  | boolean(true or false) |
| 메세지 전송 | POST | /message/send |  | content, sender,recipient | token |  |
| 메세지 삭제(숨기기) | PATCH | /message/{messageId} | messageId |  | token |  |
| 메세지 정보 조회 | GET | /message/{messageId} | messageId |  | token |  |
| 발신 메세지 리스트 조회 | GET | /message/sender |  |  | token | List<id,content,sender,recipient |
| 수신 메세지 리스트 조회 | GET | /message/recipient |  |  | token | List<id,content,sender,recipient |
| 즐겨찾기 추가 | POST | /user/favorite/{user1_id}/{user2_id} |  |  |  |  |
| 즐겨찾기 삭제 | DELETE | /user/favorite/{user1_id}/{user2_id} |  |  |  |  |
| 즐겨찾기 리스트 조회 | GET | /user/{user_id}/favorite |  |  |  |  |
| 스크랩 박스 조회 | GET | /member/{memberId}/scrap | memberId |  | token | List<id, name> |
| 스크랩 박스 생성 | POST | /member/{memberId}/scrap | memberId | name | token |  |
| 스크랩 박스 삭제 | DELETE | /member/{memberId}/scrap | memberId | scrapId | token |  |
| 스크랩 박스 수정 | PATCH | /member/{memberId}/scrap | memberId | name | token |  |
| 스크랩 리스트 조회 | GET | /member/{memberId}/scrap/{scrap_id} | memberId,scrapId |  | token | List<{title, content,viewCount,boardType,category,member{id,emaiil,name}}> |
| 스크랩 추가 | POST | /member/{memberId}/scrap/{scrapId} | memberId,scrapId | board_id | token |  |
| 스크랩 삭제 | DELETE | /member/{memberId}/scrap/{scrapId} | memberId,scrapId | board_id,scrap_Post_id | token |  |
| 게시글 조회 | GET | /board | board_type, category(업종) |  |  |  |
| 게시글 작성 | POST | /board |  | 상세 내용들,token |  |  |
| 게시글 정보 조회 | GET | /borad/{board_id} |  |  |  |  |
| 게시글 수정 | PATCH | /board/{board_id} |  | 수정 내용들,token |  |  |
| 게시글 삭제 | DELETE | /board/{board_id} |  | token |  |  |
| 게시글 좋아요 개수  | GET | /board/{board_id}/like |  | token |  |  |
| 게시글 좋아요 추가 | POST | /board/{board_id}/like |  | user_id,token |  |  |
| 개시글 좋아요 삭제 | DELETE | /board/{board_id}/like |  | user_id,token |  |  |
| 게시글 스크랩 | POST | /board/{board_id}/scrap/{scrap_id} |   | token |  |  |
| 게시글 지원하기 | POST | /board/{board_id}/apply |  | user_id,token |  |  |
| 게시글 지원 취소 | DELETE | /board/{board_id}/apply/{apply_id} |  | token |  |  |
| 게시글 댓글 | POST | /board/{board_id}/{user_id}/comment |  | content,token |  |  |
| 게시글 댓글 수정 | PATCH | /board/comment/{comment_id} |  | content,token |  |  |
| 게시글 댓글 삭제 | DELETE | /board/comment/{comment_id} |  | token |  |  |
| 포트폴리오 정보 조회 | GET | /portfolio/{portfolio_id} |  | toekn |  |  |
| 포트폴리오 작성 | POST | /portfolio |  | 상세내용들, token |  |  |
| 포트폴리오 수정 | PATCH | /portfolio/{portfolio_id} |  | 수정 내용들, token |  |  |
| 포트폴리오 삭제 | DELETE | /portfolio/{portfolio_id} |  | token |  |  |
|  |  |  |  |  |  |  |
| ACTION         | Method | EndPoint | Query String | Request Body | header |
|----------------| --- | --- | --- | --- | --- |
| 회원가입           | POST | /auth/signup |  | email, password, phone, name, nickname, loginMemberType, loginType, authority, information |  |
| 회원 탈퇴          | DELETE | /member/{memberId} |  | email, password | accessToken |
| 회원정보수정         | PATCH | /member/{memberId} |  |  | accessToken |
| 회원정보 조회        | GET | /member/{memberId} |  |  | accessToken |
| 내 정보 조회        | GET | /member/{memberId}/myInfo |  |  | accessToken |
| 일반 로그인         | POST | /auth/login |  | email, password |  |
| 로그아웃           | DELETE | /authlogout |  |  | accessToken |
| 토큰 재발행         | POST | /auth/reissue |  | accessToken |  |
| 이메일 인증번호 전송    | POST | /auth/email-certification/sends |  | email |  |
| 이메일 인증번호 확인    | POST | /auth/email-certification/confirms |  | email, certificationNumber |  |
| 비밀번호 변경(로그인 X) | POST | /member/password/{email} |  | email, afterPassword |  |
| 비밀번호 변경(로그인 O) | POST | /member/{memberId}/change/password |  | email, beforePassword, afterPassword | accessToken |
| 닉네임 변경         | POST | /member/{memberId}/change/nickname |  | nickname | accessToken |
| 휴대폰 번호 변경      | POST | /member/{memberId}/change/phone |  | phone | accessToken |
| 정보 변경          | POST | /member/{memberId}/change/information |  | information | accessToken |
| 이메일 중복 검사      | GET | /auth/member-emails/{email}/exists |  | email |  |
| 닉네임 중복 검사      | GET | /auth/member-nicknames/{nickname}/exists |  | nickname |  |
| 휴대폰 번호 중복 검사   | GET | /auth/member-phones/{phone}/exists |  | phone |  |
| 즐겨찾기 추가        | POST | /user/favorite/{user1_id}/{user2_id} |  |  |  |
| 즐겨찾기 삭제        | DELETE | /user/favorite/{user1_id}/{user2_id} |  |  |  |
| 즐겨찾기 리스트 조회    | GET | /user/{user_id}/favorite |  |  |  |
| 스크랩 박스 조회      | GET | /user/{user_id}/scrap |  |  |  |
| 스크랩 리스트 조회     | GET | /user/{user_id}/scrap/{scrap_id} |  |  |  |
| 게시글 조회         | GET | /board | board_type, category(업종) |  |  |
| 게시글 작성         | POST | /board |  | 상세 내용들,token |  |
| 게시글 정보 조회      | GET | /borad/{board_id} |  |  |  |
| 게시글 수정         | PATCH | /board/{board_id} |  | 수정 내용들,token |  |
| 게시글 삭제         | DELETE | /board/{board_id} |  | token |  |
| 게시글 좋아요 개수     | GET | /board/{board_id}/like |  | token |  |
| 게시글 좋아요 추가     | POST | /board/{board_id}/like |  | user_id,token |  |
| 개시글 좋아요 삭제     | DELETE | /board/{board_id}/like |  | user_id,token |  |
| 게시글 스크랩        | POST | /board/{board_id}/scrap/{scrap_id} |   | token |  |
| 게시글 지원하기       | POST | /board/{board_id}/apply |  | user_id,token |  |
| 게시글 지원 취소      | DELETE | /board/{board_id}/apply/{apply_id} |  | token |  |
| 게시글 댓글         | POST | /board/{board_id}/{user_id}/comment |  | content,token |  |
| 게시글 댓글 수정      | PATCH | /board/comment/{comment_id} |  | content,token |  |
| 게시글 댓글 삭제      | DELETE | /board/comment/{comment_id} |  | token |  |
| 포트폴리오 정보 조회    | GET | /portfolio/{portfolio_id} |  | toekn |  |
| 포트폴리오 작성       | POST | /portfolio |  | 상세내용들, token |  |
| 포트폴리오 수정       | PATCH | /portfolio/{portfolio_id} |  | 수정 내용들, token |  |
| 포트폴리오 삭제       | DELETE | /portfolio/{portfolio_id} |  | token |  |
|                |  |  |  |  |  |
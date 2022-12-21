package f3f.domain.comment.dto;

import f3f.domain.comment.domain.Comment;

public class CommentResponseDto {

    private Long id;
    private String content;
    private String nickname;
    private Long boardId;

    /*댓글정보를 return할 응단 클래스
    -> Entity 클래스를 생성자 파라미터로 받아 데이터를 Dto로 변환하여 응답
     별도의 전달 객체를 활용해 연관관계를 맺은 엔티티간의 무한참조를 방지 */

    /* Entity -> Dto*/
    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.nickname = comment.getAuthor().getNickname();
        this.boardId = comment.getBoard().getId();
    }
}

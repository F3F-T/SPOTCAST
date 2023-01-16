package f3f.domain.BoardImage.domain;

import f3f.domain.board.domain.Board;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class BoardImage {

    @Id
    @GeneratedValue
    private String id;

    private String s3Url;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}

package f3f.domain.BoardImage.domain;

import f3f.domain.board.domain.Board;
import lombok.*;
import javax.persistence.*;
import f3f.global.util.ImageUtil;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class BoardImage {

    @Id
    @GeneratedValue

    private Long id;

    private String s3Url;
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}

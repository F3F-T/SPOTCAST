package f3f.domain.BoardImage.domain;

import f3f.domain.board.domain.Board;
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
    private String id;
    private String fileldId;
    private String name;
    private String format;
    private String path;
    private long bytes;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    public static BoardImage multipartOf(MultipartFile multipartFile) {
        final String fileId = ImageUtil.createFileId();
        final String format = ImageUtil.getFormat(multipartFile.getContentType());
        return BoardImage.builder()
                .fileldId(fileId)
                .name(multipartFile.getOriginalFilename())
                .format(format)
                .path(ImageUtil.createPath(fileId, format))
                .bytes(multipartFile.getSize())
                .build();
    }
}

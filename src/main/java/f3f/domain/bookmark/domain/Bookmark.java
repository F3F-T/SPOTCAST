package f3f.domain.bookmark.domain;

import f3f.domain.publicModel.BaseTimeEntity;
import f3f.domain.user.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Bookmark extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    //팔로우 하는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", updatable = false, referencedColumnName = "member_id")
    private Member follower;

    //팔로우 당하는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", updatable = false, referencedColumnName = "member_id")
    private Member following;

    @Builder
    public Bookmark(Long id, Member follower, Member following) {
        this.id = id;
        this.follower = follower;
        this.following = following;
    }
}

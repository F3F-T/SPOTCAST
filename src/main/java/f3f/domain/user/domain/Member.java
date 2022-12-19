package f3f.domain.user.domain;

import f3f.domain.board.domain.Board;
import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.comment.domain.Comment;
import f3f.domain.likes.domain.Likes;
import f3f.domain.model.LoginType;
import f3f.domain.model.LoginMemberType;
import f3f.domain.model.MemberBase;
import f3f.domain.model.Authority;
import f3f.domain.portfolio.domain.Portfolio;
import f3f.domain.teamApply.domain.Apply;
import f3f.domain.user.dto.MemberDTO.MemberInfoResponseDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends MemberBase {

    private String phone;

    private String nickname;

    @OneToMany(mappedBy = "member")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> commentList = new ArrayList<>();

    //팀 지원 목록
    @OneToMany(mappedBy = "volunteer")
    private List<Apply> volunteerList = new ArrayList<>();

    //팀 지원자 목록
    @OneToMany(mappedBy = "recruiter")
    private List<Apply> recruiterList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , mappedBy = "member")
    @JoinColumn(name = "portfolio_id" )
    private Portfolio portfolio;



    @Builder
    public Member(Long id, String email, String name, String password, LoginMemberType loginMemberType, LoginType loginType, Authority authority, String information, String phone, String nickname, List<Board> boardList, List<Likes> likesList, List<Comment> commentList, List<Apply> volunteerList, List<Apply> recruiterList, List<Bookmark> bookmarkList, Portfolio portfolio) {
        super(id, email, name, password, loginMemberType, loginType, authority, information);
        this.phone = phone;
        this.nickname = nickname;

    }

    public MemberInfoResponseDto toFindMemberDto(){
        return MemberInfoResponseDto.builder()
                .email(this.getEmail())
                .name(this.getName())
                .nickname(this.getNickname())
                .loginMemberType(this.getLoginMemberType())
                .loginType(this.getLoginType())
                .authority(this.getAuthority())
                .information(this.getInformation())
                .phone(this.getPhone())
                .build();

    }


    public void updatePassword(String password){
        this.password = password;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }
    public void updateInformation(String information){
        this.information = information;
    }

    public void updatePhone(String phone){
        this.phone = phone;
    }

}

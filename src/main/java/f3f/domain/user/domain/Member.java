package f3f.domain.user.domain;

import f3f.domain.board.domain.Board;
import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.comment.domain.Comment;
import f3f.domain.likes.domain.Likes;
import f3f.domain.model.LoginType;
import f3f.domain.model.LoginMemberType;
import f3f.domain.model.MemberBase;
import f3f.domain.model.MemberType;
import f3f.domain.portfolio.domain.Portfolio;
import f3f.domain.teamApply.domain.Apply;
import f3f.domain.user.dto.MemberDTO.MemberInfoDTO;
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

    @OneToMany(mappedBy = "user")
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "author")
    private List<Comment> commentList = new ArrayList<>();

    //팀 지원 목록
    @OneToMany(mappedBy = "volunteer")
    private List<Apply> volunteerList = new ArrayList<>();

    //팀 지원자 목록
    @OneToMany(mappedBy = "recruiter")
    private List<Apply> recruiterList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , mappedBy = "user")
    @JoinColumn(name = "portfolio_id" )
    private Portfolio portfolio;



    @Builder
    public Member(Long id, String email, String name, String password, LoginMemberType loginMemberType, LoginType loginType, MemberType memberType, String information, String phone, String nickname, List<Board> boardList, List<Likes> likesList, List<Comment> commentList, List<Apply> volunteerList, List<Apply> recruiterList, List<Bookmark> bookmarkList, Portfolio portfolio) {
        super(id, email, name, password, loginMemberType, loginType, memberType, information);
        this.phone = phone;
        this.nickname = nickname;

    }

    public MemberInfoDTO toFindUserDto(){
        return MemberInfoDTO.builder()
                .email(this.getEmail())
                .name(this.getName())
                .nickname(this.getNickname())
                .loginUserType(this.getLoginMemberType())
                .loginType(this.getLoginType())
                .userType(this.getMemberType())
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

}

package f3f.domain.user.domain;

import f3f.domain.board.domain.Board;
import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.comment.domain.Comment;
import f3f.domain.likes.domain.Likes;
import f3f.domain.model.LoginType;
import f3f.domain.model.UserBase;
import f3f.domain.model.UserType;
import f3f.domain.portfolio.domain.Portfolio;
import f3f.domain.teamApply.domain.Apply;
import f3f.domain.user.dto.UserDTO.UserInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User extends UserBase {

    private String phone;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Likes> likesList = new ArrayList<>();

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<>();

    //팀 지원 목록
    @OneToMany(mappedBy = "volunteer", fetch = FetchType.LAZY)
    private List<Apply> volunteerList = new ArrayList<>();

    //팀 지원자 목록
    @OneToMany(mappedBy = "recruiter", fetch = FetchType.LAZY)
    private List<Apply> recruiterList = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Bookmark> bookmarkList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY , mappedBy = "user")
    @JoinColumn(name = "portfolio_id" )
    private Portfolio portfolio;

    @Builder
    public User(Long id, String email, String password, LoginType loginType, UserType userType, String information, String phone) {
        super(id, email, password, loginType, userType, information);
        this.phone = phone;
    }

    public UserInfoDTO toFindUserDto(){
        return UserInfoDTO.builder()
                .email(this.getEmail())
                .password(this.getPassword())
                .loginType(this.getLoginType())
                .userType(this.getUserType())
                .information(this.getInformation())
                .phone(this.getPhone())
                .build();

    }

}

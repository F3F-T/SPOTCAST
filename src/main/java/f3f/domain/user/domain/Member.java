package f3f.domain.user.domain;

import f3f.domain.board.domain.Board;
import f3f.domain.bookmark.domain.Bookmark;
import f3f.domain.category.domain.Category;
import f3f.domain.comment.domain.Comment;
import f3f.domain.likes.domain.Likes;
import f3f.domain.memberCategory.domain.MemberCategory;
import f3f.domain.message.domain.Message;
import f3f.domain.publicModel.LoginType;
import f3f.domain.publicModel.LoginMemberType;
import f3f.domain.publicModel.MemberBase;
import f3f.domain.publicModel.Authority;
import f3f.domain.portfolio.domain.Portfolio;
import f3f.domain.scrap.domain.Scrap;
import f3f.domain.apply.domain.Apply;
import f3f.domain.user.dto.MemberDTO;
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

    private String twitter;

    private String instagram;

    private String otherSns;

    private String information;

    private String profile;
    private String egName;

    @OneToMany(mappedBy = "member")
    private List<Scrap> scrapList = new ArrayList<>();

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

    @OneToMany(mappedBy = "follower")
    private List<Bookmark> followerList = new ArrayList<>();

    @OneToMany(mappedBy = "following")
    private List<Bookmark> followingList = new ArrayList<>();


    @OneToOne(fetch = FetchType.LAZY , mappedBy = "member")
    @JoinColumn(name = "portfolio_id" )
    private Portfolio portfolio;

    @OneToMany(mappedBy = "sender")
    private List<Message> sendMessageList = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<Message> receptionMessageList = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberCategory> memberCategories = new ArrayList<>();


    @Builder
    public Member(Long id, String email, String name, String password, LoginMemberType loginMemberType, LoginType loginType, Authority authority, String field, String profile) {
        super(id, email, name, password, loginMemberType, loginType, authority, field);
        this.profile = profile;
    }







    public MemberInfoResponseDto toFindMemberDto(){
        return MemberInfoResponseDto.builder()
                .id(this.getId())
                .email(this.getEmail())
                .name(this.getName())
                .loginMemberType(this.getLoginMemberType())
                .loginType(this.getLoginType())
                .authority(this.getAuthority())
                .information(this.getInformation())
                .instagram(instagram)
                .twitter(twitter)
                .otherSns(otherSns)
                .profile(profile)
                .egName(this.getEgName())
                .build();
    }

    public MemberDTO.MemberBoardInfoResponseDto toMessageMemberDTO(){
        return MemberDTO.MemberBoardInfoResponseDto.builder()
                .id(this.getId())
                .email(this.getEmail())
                .name(this.getName())
                .build();
    }

    public MemberDTO.MemberBoardInfoResponseDto toBoardMemberDTO(){
        return MemberDTO.MemberBoardInfoResponseDto.builder()
                .id(this.getId())
                .email(this.getEmail())
                .name(this.getName())
                .build();
    }
    public void updatePassword(String password){
        this.password = password;
    }

    public void updateInformation(MemberDTO.MemberUpdateInformationRequestDto updateInformationRequest){
        this.information = updateInformationRequest.getInformation();
        this.instagram = updateInformationRequest.getInstagram();
        this.twitter = updateInformationRequest.getTwitter();
        this.otherSns = updateInformationRequest.getOtherSns();
        this.field = updateInformationRequest.getField();
        this.egName = updateInformationRequest.getEgName();
    }


    public void addScrapList(Scrap scrap){
        this.scrapList.add(scrap);

    }

}

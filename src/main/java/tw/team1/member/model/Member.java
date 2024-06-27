package tw.team1.member.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import tw.team1.trail.model.Trail;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "MEMBERS")
@Component
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberid;

    @Column
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "BIRTHDAY")
    private Date birthday;

    @Column(name = "AVATAR")
    private String avatar;

    @Column(name = "signature")
    private String signature;

    @Column(name = "phone")
    private String phone;

    @Column(name = "ADMIN")
    private boolean admin;

    @Column(name = "DELETED")
    private boolean deleted;

    // 空建構子
    public Member() {
    }


    // 帶參數的建構子

    public Member(String userName, String password, String nickName , Date birthday,String avatar, boolean admin, boolean deleted,String signature,String phone) {
        this.username = userName;
        this.password = password;
        this.nickname = nickName;
        this.birthday = birthday;
        this.avatar = avatar;
        this.signature = signature;
        this.phone = phone;
        this.admin = admin;
        this.deleted = deleted;
    }




//    @JsonManagedReference
    @ManyToMany(cascade = {})
    @JoinTable(
            name = "likes",
            joinColumns = {@JoinColumn(name = "mid", referencedColumnName = "memberid")},
            inverseJoinColumns = {@JoinColumn(name = "tid", referencedColumnName = "tid")}
    )
    private Set<Trail> likedTrails = new HashSet<>();

    // getter/setter 方法

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getMemberid() {
        return memberid;
    }

    public void setMemberid(Integer mid) {
        this.memberid = mid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String mname) {
        this.username = mname;
    }

    public Set<Trail> getLikedTrails() {
        return likedTrails;
    }

    public void setLikedTrails(Set<Trail> likedTrails) {
        this.likedTrails = likedTrails;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}


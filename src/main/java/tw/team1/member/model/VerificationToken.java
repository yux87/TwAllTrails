package tw.team1.member.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Entity @Table(name = "VERIFICATIONTOKENS")
@Component
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "VTOKENID")
    private Integer vtokenid;
    @Column(name = "TOKEN")
    private String token;
    @Column(name = "EXPIRYDATE")
    private LocalDateTime expirydate;
    @ManyToOne
    @JoinColumn(name = "MEMBERID")
    private Member member;

    public VerificationToken() {
    }

    public VerificationToken(Integer vtokenid, String token, LocalDateTime expirydate, Member member) {
        this.vtokenid = vtokenid;
        this.token = token;
        this.expirydate = expirydate;
        this.member = member;
    }

    public Integer getVtokenid() {
        return vtokenid;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpirydate() {
        return expirydate;
    }

    public Member getMember() {
        return member;
    }

    public void setVtokenid(Integer vtokenid) {
        this.vtokenid = vtokenid;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setExpirydate(LocalDateTime expirydate) {
        this.expirydate = expirydate;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

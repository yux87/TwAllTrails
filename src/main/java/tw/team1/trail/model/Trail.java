package tw.team1.trail.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import tw.team1.member.model.Member;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;


@Entity @Table(name = "Trails")
//@NamedEntityGraph(name = "TrailsEntityGraph", attributeNodes = @NamedAttributeNode("likedByMembers"))
public class Trail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tid;
    @Column
    private String tname;
    @Column
    private byte[] tphoto;
    @Column
    private String tphotobase64;
    @JsonIgnore
//    @JsonBackReference
    @ManyToMany(mappedBy = "likedTrails")
    private Set<Member> likedByMembers = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "trails", fetch = FetchType.LAZY)
    private Set<TrailPhoto> trailPhotos = new HashSet<>();







    public Trail() {
    }

    public Trail(String tname) {
        this.tname = tname;
    }

    public Trail(String tname, byte[] tphoto) {
        this.tname = tname;
        this.tphoto = tphoto;
    }
    public Trail(Long tid, byte[] tphoto) {
        this.tid = tid;
        this.tphoto = tphoto;
    }

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public byte[] getTphoto() {
        return tphoto;
    }

    public void setTphoto(byte[] tphoto) {
        try {
            String base64String = Base64.getEncoder().encodeToString(tphoto);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(tphoto);
            String mimeType = URLConnection.guessContentTypeFromStream(byteArrayInputStream);

            String photoBase64 = "data:%s;base64,".formatted(mimeType) + base64String;

            this.tphotobase64 = photoBase64;
            System.out.println("setTphoto is working!");

        } catch (IOException e) {
            e.printStackTrace();
        }
        this.tphoto = tphoto;
    }

    public String getTphotobase64() {
        return tphotobase64;
    }

    public void setTphotobase64(String tphotobase64) {
        this.tphotobase64 = tphotobase64;
    }

    public Set<Member> getLikedByMembers() {
        return likedByMembers;
    }

    public void setLikedByMembers(Set<Member> likedByMembers) {
        this.likedByMembers = likedByMembers;
    }




    public Set<TrailPhoto> getTrailPhotos() {
        return trailPhotos;
    }

    public void setTrailPhotos(Set<TrailPhoto> trailPhotos) {
        this.trailPhotos = trailPhotos;
    }








//    extra properties


    @Column
    private String tclass;
    @Column
    private String tmain;
    @Column
    private String tlength;
    @Column
    private String tpave;
    @Column
    private String tlevel;
    @Column
    private String ttour;
    @Column
    private String tseason;
    @Column
    private String tspecial;
    @Column
    private Double tlat;
    @Column
    private Double tlng;


    public String getTclass() {
        return tclass;
    }

    public void setTclass(String tclass) {
        this.tclass = tclass;
    }

    public String getTmain() {
        return tmain;
    }

    public void setTmain(String tmain) {
        this.tmain = tmain;
    }

    public String getTlength() {
        return tlength;
    }

    public void setTlength(String tlength) {
        this.tlength = tlength;
    }

    public String getTpave() {
        return tpave;
    }

    public void setTpave(String tpave) {
        this.tpave = tpave;
    }

    public String getTlevel() {
        return tlevel;
    }

    public void setTlevel(String tlevel) {
        this.tlevel = tlevel;
    }

    public String getTtour() {
        return ttour;
    }

    public void setTtour(String ttour) {
        this.ttour = ttour;
    }

    public String getTseason() {
        return tseason;
    }

    public void setTseason(String tseason) {
        this.tseason = tseason;
    }

    public String getTspecial() {
        return tspecial;
    }

    public void setTspecial(String tspecial) {
        this.tspecial = tspecial;
    }

    public Double getTlat() {
        return tlat;
    }
    public void setTlat(Double tlat) {
        this.tlat = tlat;
    }

    public Double getTlng() {
        return tlng;
    }

    public void setTlng(Double tlng) {
        this.tlng = tlng;
    }
}

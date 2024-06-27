package tw.team1.trail.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "trailphotos")
public class TrailPhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int pid;

    @Column
    private String pname;
    @Column
    private byte[] photo;
    @Column
    private String base64;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "tid")
    private Trail trails;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public Trail getTrails() {
        return trails;
    }

    public void setTrails(Trail trails) {
        this.trails = trails;
    }
}

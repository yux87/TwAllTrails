package tw.team1.trail.dto;

import tw.team1.trail.model.Trail;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class TrailDTO {
    private Long id;
    private String name;
    private String photo;

    private String trailClass;
    private String main;
    private String length;
    private String pave;
    private String level;
    private String tour;
    private String season;
    private String special;
    private Double lat;
    private Double lng;


//    private Set<String> likedByMembersNames;
    private Set<Map<String, Object>> likedByMembersDetails;





    private Set<Map<String, Object>> trailPhotos;




    private Set<Long> likedBymid;

//    public TrailDTO(Trail trail) {
//        this.id = trail.getTid();
//        this.name = trail.getTname();
//        this.likedByMembersNames = trail.getLikedByMembers().stream()
//                .map(Member::getMname)
//                .collect(Collectors.toSet());
//        this.likedBymid = trail.getLikedByMembers().stream()
//                .map(Member::getMid)
//                .collect(Collectors.toSet());
//    }



    //把likedByMembersNames改成子集合 (json傳輸
    public TrailDTO(Trail trail) {
        this.id = trail.getTid();
        this.name = trail.getTname();
        this.likedByMembersDetails = trail.getLikedByMembers().stream()
                .map(member -> {
                    Map<String, Object> details = new HashMap<>();
                    details.put("memberNames", member.getUsername());
//                    if (member.getMid()==1){
//                    }
                    details.put("mid", member.getMemberid());
                    return details;
                })
                .collect(Collectors.toSet());
        this.photo = trail.getTphotobase64();



        this.trailPhotos = trail.getTrailPhotos().stream()
                .map(trailPhoto -> {
                    Map<String, Object> photoDetails = new HashMap<>();
                    photoDetails.put("base64", trailPhoto.getBase64());
                    photoDetails.put("photoName", trailPhoto.getPname());
                    // You can add more properties here if needed
                    return photoDetails;
                })
                .collect(Collectors.toSet());
//        this.trailPhotos = trail.getTrailPhotos().stream()
//                .map(TrailPhoto::getBase64)
//                .collect(Collectors.toSet());


        // rest of properties
        this.trailClass = trail.getTclass();
        this.main = trail.getTmain();
        this.length = trail.getTlength();
        this.pave = trail.getTpave();
        this.level = trail.getTlevel();
        this.tour = trail.getTtour();
        this.season = trail.getTseason();
        this.special = trail.getTspecial();
        this.lat = trail.getTlat();
        this.lng = trail.getTlng();

    }

    // Default constructor
    public TrailDTO() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<String> getLikedByMembersNames() {
//        return likedByMembersNames;
//    }
//
//    public void setLikedByMembersNames(Set<String> likedByMembersNames) {
//        this.likedByMembersNames = likedByMembersNames;
//    }


    public Set<Map<String, Object>> getLikedByMembersDetails() {
        return likedByMembersDetails;
    }

    public void setLikedByMembersDetails(Set<Map<String, Object>> likedByMembersDetails) {
        this.likedByMembersDetails = likedByMembersDetails;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }





    public Set<Map<String, Object>> getTrailPhotos() {
        return trailPhotos;
    }

    public void setTrailPhotos(Set<Map<String, Object>> trailPhotos) {
        this.trailPhotos = trailPhotos;
    }



    // rest of properties

    public String getTrailClass() {
        return trailClass;
    }

    public void setTrailClass(String trailClass) {
        this.trailClass = trailClass;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getPave() {
        return pave;
    }

    public void setPave(String pave) {
        this.pave = pave;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTour() {
        return tour;
    }

    public void setTour(String tour) {
        this.tour = tour;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
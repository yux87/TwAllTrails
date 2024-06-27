package tw.team1.trail.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.team1.member.model.Member;
import tw.team1.member.model.MembersRepository;
import tw.team1.trail.dao.TrailLikeRequest;
import tw.team1.trail.dao.TrailRepository;
import tw.team1.trail.model.Trail;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class TrailService {

    @Autowired
    private TrailRepository tRepo;

    @Autowired
    private MembersRepository mRepo;
    @PersistenceContext
    private EntityManager entityManager;



    public Optional<Boolean> manageLikedTrail(TrailLikeRequest request) {
        Optional<Member> memberOpt = mRepo.findById(request.getMid());
        if (!memberOpt.isPresent()) {
            return Optional.empty();
        }

        Member member = memberOpt.get();
        Set<Trail> likedTrails = member.getLikedTrails();

        Optional<Trail> specificTrail = likedTrails.stream()
                .filter(trail -> trail.getTid().equals(request.getTid()))
                .findFirst();

        boolean liked;
        if (specificTrail.isPresent()) {
            // Unlike the trail
            Trail trail = specificTrail.get();
            trail.getLikedByMembers().remove(member);
            member.getLikedTrails().remove(trail);
            liked = false; // Trail was removed from likes
        } else {
            // Like the trail
            Optional<Trail> trailOpt = tRepo.findById(request.getTid());
            if (trailOpt.isPresent()) {
                Trail trail = trailOpt.get();
                trail.getLikedByMembers().add(member);
                member.getLikedTrails().add(trail);
                liked = true; // Trail was added to likes
            } else {
                return Optional.empty(); // Trail ID not found
            }
        }

        // Save changes
        tRepo.saveAll(likedTrails);
        mRepo.save(member);

        return Optional.of(liked);
    }



//    Pageable test
    public Page<Trail> findAllItems(Pageable pageable) {
        return tRepo.findAll(pageable);
    }


        //SQL 語法測試
//    public List<Trail> findAllSql(){
//        return tRepo.findAllSql();
//    }

    public List<Trail> findAll(){
        return tRepo.findAll();
    }

    public List<Trail> getAllTrails() {
        Query query = entityManager.createQuery("SELECT t FROM Trail t", Trail.class);
        return query.getResultList();
    }

    public Page<Trail> findAllByPage(Pageable pageable){
        return tRepo.findAll(pageable);
    }
    public Trail insert(Trail t){
        return tRepo.save(t);
    }
//    public Trail insert(String tname){
//        return tRepo.save(tname);
//    }

    public Trail findById(Long tid){
        Optional<Trail> op1 = tRepo.findById(tid);
        if(op1.isPresent()) {
            return op1.get();
        }
        return null;
    }

    public Trail update(Trail t){
        return tRepo.save(t);
    }

    public void delete(Trail t){
        tRepo.delete(t);
    }

    //base64 test
    public Trail saveOrUpdateTrail(Trail trail) {
        if (trail.getTphoto() != null) {
            String tphotobase64 = convertToBase64(trail.getTphoto());
            trail.setTphotobase64(tphotobase64);
        }
        return tRepo.save(trail);
    }

//    method: base64 Converter
    private String convertToBase64(byte[] tphoto) {
        String base64String = Base64.getEncoder().encodeToString(tphoto);
        String mimeType = "image/jpeg"; // 假設是JPEG格式，根據實際情況調整
        String photoBase64 = "data:%s;base64,".formatted(mimeType) + base64String;
        return photoBase64;
    }
}

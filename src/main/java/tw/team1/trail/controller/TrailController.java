package tw.team1.trail.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tw.team1.member.model.Member;
import tw.team1.member.model.MembersRepository;
import tw.team1.trail.dao.TrailLikeRequest;
import tw.team1.trail.dao.TrailRepository;
import tw.team1.trail.dto.TrailDTO;
import tw.team1.trail.model.Trail;
import tw.team1.trail.service.TrailService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

//@RestController
@Controller
@CrossOrigin
//@SessionAttributes(names = {"totalPages","totalElements"})
public class TrailController {
    @Autowired
    private TrailService tService;
    @Autowired
    private TrailRepository tRepo;
    @Autowired
    private MembersRepository mRepo;


    @ResponseBody
    @PostMapping("/manageLikedTrails")
    public ResponseEntity<?> manageLikedTrails(@RequestBody TrailLikeRequest request) {
        Optional<Boolean> actionResult = tService.manageLikedTrail(request);

        if (actionResult.isPresent()) {
            boolean liked = actionResult.get();
            return ResponseEntity.ok(liked);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @ResponseBody
    @GetMapping("/findLikedTrailByMid")
    public ResponseEntity<?> findLikedTrails(@RequestParam("mid") int memberId) {
        Optional<Member> member = mRepo.findById(memberId);

        if (member.isPresent()) {
            Member currentMember = member.get();
            // Assuming getLikedTrails() returns a collection of Trail objects that this member has liked.
            Set<Trail> likedTrails = currentMember.getLikedTrails();

            // If you want to return a more specific DTO or a list of IDs instead of the entire Trail objects,
            // you might need to transform this set into the desired format here.

            return ResponseEntity.ok(likedTrails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @ResponseBody
    @PostMapping("/findLikedTrails")
    public ResponseEntity<?> findLikedTrails(@RequestBody TrailLikeRequest request) {
        Optional<Member> member = mRepo.findById(request.getMid());



        if (member.isPresent()) {
            Member currentMember = member.get();
            // Assuming getLikedTrails() returns a collection of Trail objects that this member has liked.
            Set<Trail> likedTrails = currentMember.getLikedTrails();



            Optional<Trail> specificTrail = likedTrails.stream()
                    .filter(trail -> trail.getTid().equals(request.getTid())) // Assuming the trail ID field is named 'id' and is of type Long
                    .findFirst(); // This returns th(e first match as an Optional

            if (specificTrail.isPresent()) {
                //取消按讚路徑


                Trail trail = specificTrail.get();

                trail.getLikedByMembers().remove(currentMember);
                currentMember.getLikedTrails().remove(trail);

                // Save the updated entities
                tRepo.save(trail);
                mRepo.save(currentMember);



                return ResponseEntity.ok(member);
                // You can now use the trail object as needed
                // For example, return it or manipulate it further
            } else {
                // Handle the case where no trail with trailId = 1 was found in likedTrails
                //寫入按讚路徑

                Optional<Trail> likedTrail = tRepo.findById(request.getTid());

                Trail trailToInsert = likedTrail.get();
                Member memberToInsert = member.get();

                trailToInsert.getLikedByMembers().add(memberToInsert);
                memberToInsert.getLikedTrails().add(trailToInsert);

                // Save the entities
                tRepo.save(trailToInsert);
                mRepo.save(memberToInsert);


                return ResponseEntity.ok(member);
            }

            // If you want to return a more specific DTO or a list of IDs instead of the entire Trail objects,
            // you might need to transform this set into the desired format here.

//            return ResponseEntity.ok(likedTrails);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ResponseBody
    @PostMapping("/deleteLikedTrail")
    public ResponseEntity<?> deleteLikedTrail(@RequestBody TrailLikeRequest request){

        Optional<Trail> likedTrail = tRepo.findById(request.getTid());
        Optional<Member> member = mRepo.findById(request.getMid());

        if (likedTrail.isPresent() && member.isPresent()) {
            Trail trailToRemove = likedTrail.get();
            Member memberToRemove = member.get();

            // Check if the member actually liked the trail before attempting to remove
            if (trailToRemove.getLikedByMembers().contains(memberToRemove) &&
                    memberToRemove.getLikedTrails().contains(trailToRemove)) {

                trailToRemove.getLikedByMembers().remove(memberToRemove);
                memberToRemove.getLikedTrails().remove(trailToRemove);

                // Save the updated entities
                tRepo.save(trailToRemove);
                mRepo.save(memberToRemove);

                // Return a successful response
                return ResponseEntity.ok(member);
            } else {
                // If the association doesn't exist, return a not found or similar
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Member did not like this trail or vice versa");
            }
        }
        return ResponseEntity.notFound().build();
    }



    @ResponseBody
    @PostMapping("/insertLikedTrail")
    public ResponseEntity<?> insertLikedTrail(@RequestBody TrailLikeRequest request){

        Optional<Trail> likedTrail = tRepo.findById(request.getTid());
        Optional<Member> member = mRepo.findById(request.getMid());
//        System.out.println(tRepo.countAll());

        if (likedTrail.isPresent() && member.isPresent()) {
            Trail trailToInsert = likedTrail.get();
            Member memberToInsert = member.get();

            trailToInsert.getLikedByMembers().add(memberToInsert);
            memberToInsert.getLikedTrails().add(trailToInsert);

            // Save the entities
            tRepo.save(trailToInsert);
            mRepo.save(memberToInsert);
        }
        return member != null ? ResponseEntity.ok(member) : ResponseEntity.notFound().build();
    }

    @ResponseBody
    @GetMapping("/countTrailLikes/{tid}")
    public String countTrailLikes(@PathVariable int tid){

//        System.out.println(tRepo.countAll());
        return tRepo.countTrailLikes(tid);
    }


    @ResponseBody
    @GetMapping("/countAll")
    public String countAll(){

//        System.out.println(tRepo.countAll());
        return tRepo.countAll();
    }






//    Pageable test
    @ResponseBody
    @GetMapping("/trailPage.controller")
    public Page<Trail> listItems(Pageable pageable) {
        return tService.findAllItems(pageable);
    }




    //SQL 語法測試
//    @ResponseBody
//    @GetMapping("/trailSql.controller")
//    public List<Trail> findAllSql(){
//        return tService.findAllSql();
//    }

    //DTO test
    @ResponseBody
    @GetMapping("/trailDto.controller")
    public List<TrailDTO> getAllTrails() {
        return tRepo.findAll().stream()
                .map(TrailDTO::new)
                .collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/trail.controller")
    public List<Trail> processFindAllTrail(){
        return  tService.findAll();
    }

    @ResponseBody
    @GetMapping("/trail.controller2")
    public List<Trail> processFindAllTrail2(){
        return  tService.getAllTrails();
    }

    @GetMapping("/productquerymainaction.controller")
    public String processQueryMainAction() {
        return "productQueryAll";
    }

    @ResponseBody
    @GetMapping("/queryByPage/{pageNo}")
    public Page<Trail> processQueryByPageAction(@PathVariable("pageNo") int pageNo, Model m, HttpServletRequest request){

        int pageSize=2;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Trail> page = tService.findAllByPage(pageable);
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
//        m.addAttribute("totalPages", page.getTotalPages());
//        m.addAttribute("totalElements", page.getTotalElements());


        // Uncomment the line below if you want to return pagination-related information in the response
        // return Arrays.asList(page.getTotalPages(), page.getTotalElements(), page.getContent());

//        return page.getContent();
        return page;
    }

    @ResponseBody
    @PostMapping(path = "/add.controller")
    public Trail processInsertAction(@RequestBody Trail t){
        System.out.println(t.getTname());
        return tService.insert(t);
    }

    @ResponseBody
    @PostMapping(path = "/add.controller2")
    public Trail processInsertAction2(@RequestParam("tname") String tname){
        System.out.println(tname);
        Trail insertBean = new Trail(tname);
        return tService.insert(insertBean);
    }

    //Photo
    @ResponseBody
    @PostMapping(path = "/addPhoto.controller")
    public Trail processInsertAction3(@RequestParam("tname") String tname, @RequestPart("tphoto") MultipartFile tphoto){
        System.out.println(tname);

        try {
            byte[] tphotoBytes = tphoto.getBytes();
            Trail insertBean = new Trail(tname, tphotoBytes);
//            return tService.insert(insertBean);
            return tService.saveOrUpdateTrail(insertBean);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseBody
    @GetMapping(path = "/findById.controller/{tid}")
    public String processFindById(@PathVariable("tid") Long tid){
        Trail resultBean = tService.findById(tid);
        return resultBean.getTphotobase64();
    }


    @ResponseBody
    @PutMapping("/update.controller")
    public Trail processUpdateAction(@RequestBody Trail t){
        return tService.update(t);
    }

//    update photo
    @ResponseBody
    @PutMapping("/updatePhoto.controller")
    public Trail processUpdatePhotoAction(@RequestParam("tid") Long tid, @RequestPart("tphoto") MultipartFile tphoto) throws IOException {
        byte[] tphotoBytes = tphoto.getBytes();
        Trail updateTrail = new Trail(tid, tphotoBytes);
        return tService.saveOrUpdateTrail(updateTrail);
    }

    @ResponseBody
    @DeleteMapping("/delete.controller")
    public void processDeleteAction(@RequestBody Trail t){
        tService.delete(t);
    }


}

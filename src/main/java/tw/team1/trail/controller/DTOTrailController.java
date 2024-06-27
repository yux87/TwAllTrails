package tw.team1.trail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import tw.team1.trail.dao.TrailRepository;
import tw.team1.trail.dto.TrailDTO;
import tw.team1.trail.model.Trail;
import tw.team1.trail.service.TrailService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dto")
@CrossOrigin
public class DTOTrailController {

    @Autowired
    private TrailRepository trailRepository;

//    Pageable
    @Autowired
    private TrailService tService;




    @GetMapping("/getTrailByTname.controller/{tname}")
    public List<TrailDTO> getTrailByTname(@PathVariable String tname) {
        return trailRepository.findByTname(tname).stream()
                .map(TrailDTO::new)
                .collect(Collectors.toList());
    }
    @GetMapping("/getAreaTrails.controller/{tname}")
    public List<TrailDTO> getAreaTrails(@PathVariable String tname) {
        return trailRepository.findByTnameContaining(tname).stream()
                .map(TrailDTO::new)
                .collect(Collectors.toList());
    }





    //    Pageable test
    @ResponseBody
    @GetMapping("/trailPageDto.controller")
    public List<TrailDTO> listItems(Pageable pageable) {
        return tService.findAllItems(pageable).stream()
                .map(TrailDTO::new)
                .collect(Collectors.toList());

    }



    @PostMapping("/addDto")
    public void addTrail(@RequestBody TrailDTO trailDTO){
        Trail trail = new Trail();
        trail.setTname(trailDTO.getName());
        trail.setTphotobase64(trailDTO.getPhoto());
        trailRepository.save(trail);
    }




    @DeleteMapping("/deleteDto/{id}")
    public ResponseEntity<String> deleteTrailDto(@PathVariable Long id) {
        Optional<Trail> trailOptional = trailRepository.findById(id);
        if (trailOptional.isPresent()) {
            Trail trail = trailOptional.get();
            trailRepository.delete(trail);
            return new ResponseEntity<>("Trail successfully deleted", HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trail not found");
        }
    }
//arrow version
//    @DeleteMapping("/deleteDto/{id}")
//    public ResponseEntity<String> deleteTrailDto(@PathVariable Long id) {
//        return trailRepository.findById(id).map(trail -> {
//            trailRepository.delete(trail);
//            return new ResponseEntity<>("Trail successfully deleted", HttpStatus.OK);
//        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trail not found"));
//    }

//    @DeleteMapping("/deleteDto/{id}")
//    public TrailDTO deleteTrailDto(@PathVariable Long id, @RequestBody TrailDTO trailDTO) {
//        return trailRepository.findById(id).map(trail -> {
//            trailRepository.delete(trail);
//            return new TrailDTO(trail); // Assuming you have a constructor in ProductDTO that takes a Product
//        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
//    }




//    @PutMapping("/updateDtoById/{id}")
//    public TrailDTO updateProductById(@PathVariable Long id, @RequestBody TrailDTO trailDTO) {
//        return trailRepository.findById(id).map(trail -> {
//            trail.setTname(trailDTO.getName());
//            trailRepository.save(trail);
//            return new TrailDTO(trail); // Assuming you have a constructor in ProductDTO that takes a Product
//        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
//    }


    @PutMapping("/updateDtoById/{id}")
    public TrailDTO updateProductById(@PathVariable Long id, @RequestBody TrailDTO trailDTO) {
        return trailRepository.findById(id).map(trail -> {
            trail.setTname(trailDTO.getName());
            trail.setTphotobase64(trailDTO.getPhoto());
            trailRepository.save(trail);
            return new TrailDTO(trail); // Assuming you have a constructor in ProductDTO that takes a Product
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }
    @PutMapping("/updateDtoPhotoByTname/{tname}")
    public TrailDTO updateProductByName(@PathVariable String tname, @RequestBody TrailDTO trailDTO) {
        return trailRepository.findByTname(tname).map(trail -> {
            trail.setTphotobase64(trailDTO.getPhoto());
            trailRepository.save(trail);
            return new TrailDTO(trail); // Assuming you have a constructor in ProductDTO that takes a Product
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }





    @GetMapping("/trailDto.controller")
    public List<TrailDTO> getAllTrails() {
        return trailRepository.findAll().stream()
                .map(TrailDTO::new)
                .collect(Collectors.toList());
    }
//    Pageable test
    @GetMapping("/trailDtoPageable.controller")
    public List<TrailDTO> getAllTrailsPage(@PageableDefault(size = 3) Pageable pageable) {
        return tService.findAllItems(pageable).stream()
                .map(TrailDTO::new)
                .collect(Collectors.toList());
    }




//    @GetMapping("/trail3.controller/{tid}")
//    public Trail processFindAllTrail(@PathVariable("tid") Long tid){
//        return  tService.findById(tid);
//    }

    @GetMapping("/findTrailById.controller/{tid}")
    public TrailDTO processFindAllTrail(@PathVariable("tid") Long tid){
        return trailRepository.findById(tid)
                .map(trail -> new TrailDTO(trail)) // Convert to TrailDTO if present
                // .map(TrailDTO::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Trail not found")); // Or throw an exception if not found
    }

}

package tw.team1.trail.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tw.team1.trail.model.TrailPhoto;
import tw.team1.trail.service.TrailPhotoService;

import java.util.List;

@Controller
public class TrailPhotoControlller {

    @Autowired
    private TrailPhotoService tpService;


    @ResponseBody
    @GetMapping("/photo.controller")
    public List<TrailPhoto> processFindAllTrailPhoto(){
        return tpService.findAll();
    }

}

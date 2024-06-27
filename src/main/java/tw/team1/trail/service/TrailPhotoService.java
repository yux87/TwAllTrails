package tw.team1.trail.service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tw.team1.trail.dao.TrailPhotoRepository;
import tw.team1.trail.model.TrailPhoto;

import java.util.List;

@Service
@Transactional
public class TrailPhotoService {

    @Autowired
    private TrailPhotoRepository tpRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public List<TrailPhoto> findAll(){ return tpRepository.findAll();}
}

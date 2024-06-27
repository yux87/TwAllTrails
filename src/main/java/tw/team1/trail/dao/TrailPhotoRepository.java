package tw.team1.trail.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import tw.team1.trail.model.TrailPhoto;

public interface TrailPhotoRepository extends JpaRepository<TrailPhoto, Integer> {
}

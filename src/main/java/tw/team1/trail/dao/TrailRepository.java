package tw.team1.trail.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tw.team1.trail.model.Trail;

import java.util.List;
import java.util.Optional;

public interface TrailRepository extends JpaRepository<Trail, Long> {
//    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "TrailsEntityGraph")
//    List<TrailDTO> getAllTrails();

    //SQL 語法測試
//    @Query(value = "select * from Trails", nativeQuery = true)
//    public List<Trail> findAllSql();

    @Query(value = "INSERT INTO likes (mid, tid) VALUES (?1, ?2)", nativeQuery = true)
    public String insertLikedTrail(int mid, int tid);


    @Query(value = "SELECT COUNT(*) AS TotalRows FROM Trails", nativeQuery = true)
    public String countAll();

    @Query(value = "SELECT COUNT(*) FROM likes WHERE tid = ?1", nativeQuery = true)
    String countTrailLikes(int tid);


    List<Trail> findByTnameContaining(String name);
//    List<Trail> findByTname(String name);
    Optional<Trail> findByTname(String name);


}

package tw.team1.forum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import tw.team1.forum.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

	Category findByCategoryname(String categoryname);

    // 可以新增一些自訂的查詢方法
}


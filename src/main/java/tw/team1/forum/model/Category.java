package tw.team1.forum.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "Categories")
@Component
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryid;
    private String categoryname;
    private String description;

    // 加入對應的 thread 屬性

//    @OneToOne // 声明与Member的关联
//    @JoinColumn(name = "categoryid", referencedColumnName = "categoryid", insertable = false, updatable = false) // 添加这行指定关联列和参考列
//    private Thread thread; // 添加一个成员变量来持有关联的Member对象

	public int getCategoryid() {
		return categoryid;
	}

	public void setCategoryid(int categoryid) {
		this.categoryid = categoryid;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

//	public Thread getThread() {
//		return thread;
//	}
//
//	public void setThread(Thread thread) {
//		this.thread = thread;
//	}

	@Override
	public String toString() {
		return "Category [categoryid=" + categoryid + ", categoryname=" + categoryname + ", description=" + description
				+ ", thread=" + "]";
	}

	public Category() {
		super();
		// TODO Auto-generated constructor stub
	}

    
}

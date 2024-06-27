package tw.team1.forum.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import tw.team1.member.model.Member;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "threads")
@Component
public class Thread {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int threadid;

    @Column(name = "categoryid")
    private int categoryid;

    @Column(name = "memberid") // 添加这行以映射到数据库中的正确列名
    private int memberid;

    private String title;
    private String content;
    private Date createdate;
    private boolean isupdated;
   

	@OneToOne // 声明与Member的关联
    @JoinColumn(name = "memberid", referencedColumnName = "memberid", insertable = false, updatable = false) // 添加这行指定关联列和参考列
    private Member member; // 添加一个成员变量来持有关联的Member对象

    @OneToOne // 声明与Member的关联
    @JoinColumn(name = "categoryid", referencedColumnName = "categoryid", insertable = false, updatable = false) // 添加这行指定关联列和参考列
    private Category category; // 添加一个成员变量来持有关联的Member对象
    
    public int getThreadid() {
        return threadid;
    }

    public void setThreadid(int threadid) {
        this.threadid = threadid;
    }

    public int getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(int categoryid) {
        this.categoryid = categoryid;
    }

    public int getMemberid() {
        return memberid;
    }

    public void setMemberid(int memberid) {
        this.memberid = memberid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
    public boolean isIsupdated() {
		return isupdated;
	}

	public void setIsupdated(boolean isupdated) {
		this.isupdated = isupdated;
	}

    public String getFormattedCreateDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy 年 MM 月 dd 日 HH:mm");
        return formatter.format(createdate);
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

    @Override
	public String toString() {
		return "Thread [threadid=" + threadid + ", categoryid=" + categoryid + ", memberid=" + memberid + ", title="
				+ title + ", content=" + content + ", createdate=" + createdate + ", isupdated=" + isupdated
				+ ", member=" + member + ", category=" + category + "]";
	}

	public Thread() {
        super();
    }
}


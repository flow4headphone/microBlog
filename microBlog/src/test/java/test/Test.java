package test;

import java.util.List;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.microblog.bean.Blog;
import com.microblog.bean.Concern;
import com.microblog.bean.Groups;
import com.microblog.bean.User;
import com.microblog.biz.BlogBiz;
import com.microblog.biz.ConcernBiz;
import com.microblog.biz.GroupsBiz;
import com.microblog.biz.UserBiz;
import com.microblog.web.model.BlogModel;

public class Test extends TestCase {

	public void testApp07() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		BlogBiz ub = (BlogBiz) ac.getBean("blogBizImpl");
		Blog blog = new Blog();
		blog.setText("1111");
		blog.getUser().setUid(5);
		blog.setPic("ziyuan\\image/jpeg\\1472639462156.jpg,split* ");
		ub.saveBlog(blog);
		
		 blog = new Blog();
		blog.setText("1111");
		blog.getUser().setUid(2);
		blog.setPic("ziyuan\\image/jpeg\\1472639462156.jpg,split* ");
		ub.saveBlog(blog);
		
		 blog = new Blog();
		blog.setText("1111");
		blog.getUser().setUid(3);
		blog.setPic("ziyuan\\image/jpeg\\1472639462156.jpg,split* ");
		ub.saveBlog(blog);
		
		 blog = new Blog();
		blog.setText("1111");
		blog.getUser().setUid(4);
		blog.setPic("ziyuan\\image/jpeg\\1472639462156.jpg,split* ");
		ub.saveBlog(blog);
	}

	public void logintest() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		UserBiz ub = (UserBiz) ac.getBean("userBizImpl");

		User user = new User();

		user.setEmail("571880590@qq.com");
		user.setPassword("123123");
		ub.loginByEmail(user);
		System.out.println("登陆成功");
	}

	public void registtest() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		UserBiz ub = (UserBiz) ac.getBean("userBizImpl");

		User user = new User();

		user.setEmail("571880590@qq.com");
		user.setPassword("123123");
		user.setTelephone("123141241212");
		user.setNickname("xiaoer");
		ub.register(user);
		System.out.println("注册成功");
	}

	public void updatetest() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		UserBiz ub = (UserBiz) ac.getBean("userBizImpl");

		User user = new User();
		user.setUid(1);
		user.setNickname("曾二宝");
		user.setSex(1);
		ub.update(user);
		System.out.println("更新成功");

	}

	// 测试查找默认分组 -- 成功
	public void getDefaultGroups() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		GroupsBiz gb = (GroupsBiz) ac.getBean("groupsBizImpl");
		List<Groups> l = gb.findGroups();

		for (Groups g : l) {
			System.out.println(g.getName());
		}
		System.out.println("查询成功");
	}

	// 测试redis点赞数的自增
	public void testApp09() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		BlogBiz ub = (BlogBiz) ac.getBean("blogBizImpl");
		System.out.println("当前点赞数" + ub.parse(2L, 1));
	}

	// 测试redis转发数
	public void testApp10() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		BlogBiz ub = (BlogBiz) ac.getBean("blogBizImpl");
		System.out.println("当前转发数" + ub.relay(2L, 1));
	}
	//查询所有用户ID
	public void testApp11() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		UserBiz ub = (UserBiz) ac.getBean("userBizImpl");
		System.out.println(ub.getUidList());
	}
	//通过粉丝ID查出博主ID
	public void testApp12() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		ConcernBiz ub = (ConcernBiz) ac.getBean("concernBizImpl");
		Concern c=new Concern();
		c.setF_uid(2);
		System.out.println(ub.getBidByFid(c));
	}

	
	public void getwebCounttest() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		UserBiz bb = (UserBiz) ac.getBean("userBizImpl");
		User user = new User();
		user.setUid(1);
			
		System.out.println(bb.findUserBlogCount(user));

	}
	
	
	

	//查询所有关注的人的微博
	public void testApp13() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		BlogBiz ub = (BlogBiz) ac.getBean("blogBizImpl");
		BlogModel b=new BlogModel();
		System.out.println(ub.findAllBlog(b));
		
	}
	
	//查询某人发表的微博
	public void testApp14() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(
				"beans_mybatis.xml");
		BlogBiz ub = (BlogBiz) ac.getBean("blogBizImpl");
		BlogModel b=new BlogModel();
		
		System.out.println(ub.findAllBlog(b));
		
	}
	
	//根据ID查微博
		public void testApp15() {
			ApplicationContext ac = new ClassPathXmlApplicationContext(
					"beans_mybatis.xml");
			BlogBiz ub = (BlogBiz) ac.getBean("blogBizImpl");
			
			System.out.println(ub.findBlogById(1L));
			
		}
}

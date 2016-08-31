package com.microblog.web.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.microblog.bean.Blog;
import com.microblog.bean.User;
import com.microblog.biz.BlogBiz;
import com.microblog.util.YcConstants;
import com.microblog.web.model.BlogModel;
import com.opensymphony.xwork2.ModelDriven;

@Controller
@Namespace("/")
@Scope("prototype")
@ParentPackage("struts-default")
//@InterceptorRefs(value = { @InterceptorRef("modelDriven"),@InterceptorRef("fileUploadStack")}) 
@InterceptorRefs({ 
	@InterceptorRef("modelDriven"),
	@InterceptorRef(value = "fileUpload", params = {"allowedExtensions","jpg,bmp,gif,png,tiff,mp4,avi,rmvb,wmv"}),
	@InterceptorRef(value = "basicStack")  
})
public class BlogAction extends BaseAction implements ModelDriven<BlogModel> {
	private static final long serialVersionUID = 1L;
	private String picAllowed = "jpg,bmp,gif,png,tiff";
	private BlogModel blogModel;
	private BlogBiz blogBiz;
	@Action(value = "/blog_saveBlog")
	public void saveBlog() throws IOException {
		
		String pic = "";
		String video = "";
		String[] pv=	uploadPicAndVideo(pic, video);
		System.out.println("pic-0--->:"+pv[0]+"\nvideo---->:"+pv[1]);
		Blog blog = blogModel.getBlog();
		blog.setPic(pv[0]);
		blog.setVideo(pv[1]);
		//TODO 登录用户
		blog.setUser((User) ServletActionContext.getRequest().getSession().getAttribute(YcConstants.LOGINUSER));
		blogBiz.saveBlog(blog);
		if(blog.getId()>0)
		jsonModel.setCode(1);
		jsonModel.setObj(blog);
		super.printJson(jsonModel, ServletActionContext.getResponse());
	}

	/**
	 * 上传文件
	 * @param pic
	 * @param video
	 * @return
	 */
	private String[] uploadPicAndVideo(String pic, String video) {
		List<File> files = blogModel.getFile();
		if (files != null && files.size() > 0) {
			for (int i = 0; i < files.size(); i++) {
				File f = new File(getSavePath(YcConstants.SAVEPATH + "\\"
						+ blogModel.getFileContentType().get(i)));
				try {
					if (!f.exists()) {
						if (!f.mkdirs()) {
							System.out.println("创建文件失败");
							jsonModel.setCode(0);
							jsonModel.setMsg("system is wrong");
							return null;
						}
					}
					String filename = blogModel.getFileFileName().get(i);
					String newName=rename(filename);
					String pp = getSavePath(YcConstants.SAVEPATH + "\\"
							+ blogModel.getFileContentType().get(i))
							+ "\\" + newName;
					FileOutputStream fos = new FileOutputStream(pp);
					FileInputStream fis = new FileInputStream(files.get(i));
					String type = filename
							.substring(filename.lastIndexOf(".") + 1);
					if (picAllowed.contains(type)) {
						pic += YcConstants.SAVEPATH + "\\"
								+ blogModel.getFileContentType().get(i)
								+ "\\" + newName + YcConstants.URLSPLIT;
					} else {
						video += YcConstants.SAVEPATH + "\\"
								+ blogModel.getFileContentType().get(i)
								+ "\\" + newName + YcConstants.URLSPLIT;
					}
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}
					fis.close();
					fos.close();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					jsonModel.setCode(0);
					jsonModel.setMsg(e.getMessage());
				} catch (IOException e) {
					jsonModel.setCode(0);
					jsonModel.setMsg(e.getMessage());
					e.printStackTrace();
				}
			}
		}
		return new String[]{pic,video};
	}

	
	/**
	 * 上传文件重命名
	 * @param filename
	 * @return
	 */
	private String rename(String filename) {
		String type = filename.substring(filename.lastIndexOf("."));
		String newName = System.currentTimeMillis() + type;
		return newName;

	}

	
	/**
	 * 获取存储路径
	 * @param path
	 * @return
	 */
	private String getSavePath(String path) {
		return ServletActionContext.getServletContext().getRealPath(path);
	}

	@Override
	public BlogModel getModel() {
		blogModel = new BlogModel();
		return blogModel;
	}

	@Resource(name = "blogBizImpl")
	public void setBlogBiz(BlogBiz blogBiz) {
		this.blogBiz = blogBiz;
	}

}

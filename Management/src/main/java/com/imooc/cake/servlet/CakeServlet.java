package com.imooc.cake.servlet;

import com.imooc.cake.entity.Cake;
import com.imooc.cake.entity.Category;
import com.imooc.cake.service.CakeService;
import com.imooc.cake.service.CategoryService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * 蛋糕Servlet
 *
 * @version 1.0
 */
public class CakeServlet extends HttpServlet {

    private CakeService cakeService;

    private CategoryService categoryService;

    @Override
    public void init() throws ServletException {
        super.init();
        cakeService = new CakeService();
        categoryService = new CategoryService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("/cake/list.do".equals(req.getServletPath())) {
            //获取蛋糕分类的id
            String categoryIdStr = req.getParameter("categoryId");
            try {
                //定义一个长整型的蛋糕分类ID
                Long catgoryId = null;
                //如果前台页面传过来的categoryId不为null
                if (null != categoryIdStr) {
                    //就将值的引用赋值给categoryId，此处需要将String类型的变量转换为Long型
                    catgoryId = Long.valueOf(categoryIdStr);
                }
                //通过CateService中的getCakesByCategoryId方法可以获取指定分类下的cakes集合
                List<Cake> cakes = cakeService.getCakesByCategoryId(catgoryId, 1, 5000);
                //将cakes存储在request作用域中
                req.setAttribute("cakes", cakes);
                //通过CategoryService的getCateegories方法可以获取全部的蛋糕分类
                List<Category> categories = categoryService.getCategories();
                //将categories存储在request作用域中
                req.setAttribute("categories", categories);
                //转发到cake_list.jsp页面
                req.getRequestDispatcher("/WEB-INF/views/biz/cake_list.jsp").forward(req, resp);
            } catch (NumberFormatException e) {
                req.getRequestDispatcher("/WEB-INF/views/biz/cake_list.jsp").forward(req, resp);
            }
        } else if ("/cake/addPrompt.do".equals(req.getServletPath())) {
            //通过CategoryService的getCateegories方法可以获取全部的蛋糕分类
            List<Category> categories = categoryService.getCategories();
            //将categories存储在request作用域中
            req.setAttribute("categories", categories);
            //转发到add_cake.jsp页面
            req.getRequestDispatcher("/WEB-INF/views/biz/add_cake.jsp").forward(req, resp);
        } else if ("/cake/add.do".equals(req.getServletPath())) {
            req.setCharacterEncoding("utf-8");
            if (ServletFileUpload.isMultipartContent(req)) {
                try {
                    //创建一个磁盘文件项工厂对象
                    FileItemFactory factory = new DiskFileItemFactory();
                    //首先得到ServletFileUpload，是文件上传的核心组件。它能够将request中的每一个属性字段都封装成FileItem对象
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    //解析请求
                    List<FileItem> items = upload.parseRequest(req);
                    //获取items的迭代对象
                    Iterator<FileItem> ite = items.iterator();
                    //创建Cake对象
                    Cake cake = new Cake();
                    //开始迭代items集合
                    while (ite.hasNext()) {
                        //将获取的每条数据存储到item对象中
                        FileItem item = ite.next();
                        //信息是普通的格式
                        if (item.isFormField()) {
                            //获取每个item对象的名字
                            String fieldName = item.getFieldName();
                            //当fieldName为categoryId
                            if ("categoryId".equals(fieldName)) {
                                //设置cake对象中的categoryId值为request中的categoryId
                                cake.setCategoryId(Long.valueOf(item.getString()));
                            } else if ("level".equals(fieldName)) {//当fieldName为level
                                //设置cake对象中的level值为request中的level
                                cake.setLevel(Integer.valueOf(item.getString()));
                            } else if ("name".equals(fieldName)) {//当fieldName为name
                                //设置cake对象中的level值为request中的name,为了防止出现中文乱码,将name值进行了转码
                                cake.setName(new String(item.getString().getBytes("iso8859-1"), "utf-8"));
                            } else if ("price".equals(fieldName)) {//当fieldName为price
                                //设置cake对象中的price值为request中的price
                                cake.setPrice(Integer.valueOf(item.getString()));
                            }
                        } else {//信息是文件格式
                            //设置cake的图片属性为request中的图片数据
                            cake.setSmallImg(item.get());
                        }
                    }
                    cakeService.addCake(cake);
                    req.getRequestDispatcher("/cake/list.do").forward(req, resp);
                } catch (FileUploadException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        cakeService = null;
        categoryService = null;
    }

}

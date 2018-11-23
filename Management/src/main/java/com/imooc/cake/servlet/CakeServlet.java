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
 * @Author: dtvikey
 * @Date: 23/11/18 上午 11:09
 * @Version 1.0
 * 蛋糕Servlet
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
        if("/cake/list.do".equals(req.getServletPath())){

            String categoryIdStr = req.getParameter("categoryId");

            try{

                Long categoryId = null;

                if(null != categoryIdStr){

                    categoryId = Long.valueOf(categoryIdStr);

                }
                //点击分页后显示第一页内容
                List<Cake> cakes = cakeService.getCakesByCategoryId(categoryId,1,500);
                req.setAttribute("cakes",cakes);

                List<Category> categories = categoryService.getCategories();
                req.setAttribute("categories",categories);
                req.getRequestDispatcher("/WEB-INF/views/biz/cake_list.jsp").forward(req,resp);


            }catch (NumberFormatException e){

                req.getRequestDispatcher("/WEB-INF/views/biz/cake_list.jsp").forward(req,resp);

            }
        }else if("/cake/addPrompt.do".equals(req.getServletPath())){

            List<Category> categories = categoryService.getCategories();
            req.setAttribute("categories",categories);
            req.getRequestDispatcher("/WEB-INF/views/biz/add_list.jsp").forward(req,resp);

        }else if("/cake/add.do".equals(req.getServletPath())){
            req.setCharacterEncoding("utf-8");
            //附件上传的form 就是指表单中包括文件上传的表单项,
            //此时获取表单信息时不是通过getParameter()方法完成.
            if(ServletFileUpload.isMultipartContent(req)){
                try{

                    FileItemFactory factory = new DiskFileItemFactory();
                    ServletFileUpload upload = new ServletFileUpload(factory);
                    List items = upload.parseRequest(req); //解析请求
                    Iterator<FileItem> ite = items.iterator();
                    Cake cake = new Cake();
                    while(ite.hasNext()){
                        FileItem item = ite.next();
                        if(item.isFormField()){//信息是普通格式
                            String fieldName = item.getFieldName();
                            if("categoryId".equals(fieldName)){
                                cake.setCategoryId(Long.valueOf(item.getString()));
                            }else if("level".equals(fieldName)){
                                cake.setLevel(Integer.valueOf(item.getString()));
                            }else if("name".equals(fieldName)){
                                cake.setName(item.getString());
                            }else if("price".equals(fieldName)){
                                cake.setPrice(Integer.valueOf(item.getString()));
                            }else {  //信息是文件格式的
                                cake.setSmallImg(item.get());
                            }
                        }

                        cakeService.addCake(cake);
                        req.getRequestDispatcher("/cake/list.do").forward(req,resp);
                    }

                }catch (FileUploadException e){
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

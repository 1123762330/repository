package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService service=new CategoryServiceImpl();
    //查询导航的方法
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("findAll方法进入了...");
        //1.调用service查询所有
        List<Category> cs = service.findAll();
        System.out.println("cs的数据为:"+cs.toString());
        //2.序列化json返回
        writeValue(cs,response);
        System.out.println("findAll方法走了...");
    }


}

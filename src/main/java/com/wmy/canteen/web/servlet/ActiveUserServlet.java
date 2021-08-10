package com.wmy.canteen.web.servlet;

import com.wmy.canteen.service.UserService;
import com.wmy.canteen.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取code字段
        String code =request.getParameter("code");
        //如果不为空，开始激活
        if(code!=null){
            //调用service层激活
            UserService service=new UserServiceImpl();
            boolean flag = service.active(code);

            String msg=null;
            if(flag){
                msg="激活成功，请<a href='index.html'>登录</a>";
            }else{
                msg="激活失败，请联系管理员！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}

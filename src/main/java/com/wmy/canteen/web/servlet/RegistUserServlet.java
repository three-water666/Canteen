package com.wmy.canteen.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wmy.canteen.domain.ResultInfo;
import com.wmy.canteen.domain.User;
import com.wmy.canteen.service.UserService;
import com.wmy.canteen.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//注解路径前面的反斜杠一定不能忘了，不然tomcat不能启动
@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        System.out.println("registUserServlet的doPost被执行...");

        //先判断验证码
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo resultInfo=new ResultInfo();
        if(checkcode_server==null||!checkcode_server.equalsIgnoreCase(check)){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误或者已失效");
        }else{
            //1.获得数据
            Map<String, String[]> map = request.getParameterMap();
            //2.封装为user对象
            User user=new User();
            try {
                BeanUtils.populate(user,map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            //3.调用service层对象注册
            UserService userService = new UserServiceImpl();
            boolean flag=userService.regist(user);
            //4.得到结果
            if(flag){
                resultInfo.setFlag(flag);
                resultInfo.setErrorMsg("注册成功，请注意查收邮箱进行激活");
            }else{
                resultInfo.setFlag(flag);
                resultInfo.setErrorMsg("注册失败，账户已存在");
            }
        }
        //5.返回结果
        //将info对象序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultInfo);

        //将json数据写回客户端
        //设置content-type
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }
}

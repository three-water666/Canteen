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
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {


    private UserService userService = new UserServiceImpl();
    /**
     * 注册功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    /**
     * 激活功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取code字段
        String code =request.getParameter("code");
        //如果不为空，开始激活
        if(code!=null){
            //调用service层激活

            boolean flag = userService.active(code);

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

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户名和密码
        Map<String, String[]> map = request.getParameterMap();
        //2.封装为user对象
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //3.调用serve层查询
        User u = userService.login(user);
        //4.判断是否存在
        ResultInfo info=new ResultInfo();
        if(u==null){
            info.setFlag(false);
            info.setErrorMsg("学号或密码错误");
        }
        if(u!=null && !"Y".equals(u.getStatus())){
            info.setFlag(false);
            info.setErrorMsg("用户未激活，请点击邮件激活");
        }
        if(u!=null && "Y".equals(u.getStatus())){
            //成功登录
            info.setFlag(true);
            //设置session，个性化提示
            request.getSession().setAttribute("user",u);
        }
        //5.返回数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),info);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //1.销毁session
        request.getSession().invalidate();
        //2.跳转到登录页面
        response.sendRedirect(request.getContextPath()+"/index.html");
    }
}

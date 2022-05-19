package servlet;


import com.alibaba.fastjson.JSON;
import mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pojo.Favorite;
import pojo.Mydict;
import pojo.User;
import service.FavoriteService;
import service.MydictService;
import service.UserService;
import service.UserServiceImpl;
import utils.LogUtil;
import utils.MyBatisUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Controller
@RequestMapping("/LoginServlet")
//@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Autowired
    @Qualifier("UserServiceImpl")
    UserService userService;
    @Autowired
    @Qualifier("MydictServiceImpl")
    MydictService mydictService;
    @Autowired
    @Qualifier("FavoriteServiceImpl")
    FavoriteService favoriteService;

    @GetMapping("/a")
    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        int code = 0;

        String account = request.getParameter("userAccount");
        String password = request.getParameter("userPassword");
        LogUtil.log("userAccount:" + account + ";userPassword:" + password);


        User user = userService.selectUserByAccount(account);
        String pwd = user.getPassword().toString();
        if (user != null && password.equals(pwd)) {
            LogUtil.log("登录成功");
            code = 200;//200表示登录成功
        } else {
            LogUtil.log("登录失败");
            code = 100;
        }

        response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式

        PrintWriter out = response.getWriter();
        String json = JSON.toJSONString(code);
        out.println(json);
        out.flush();
        out.close();
    }

    @RequestMapping("/RegisterServlet")
        public void registerGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
            int code=0;

            //接受传进来的参数
            String account = request.getParameter("userAccount");
            String password = request.getParameter("userPassword");
            String name=request.getParameter("userName");
            String sex = request.getParameter("sex");
            String phone = request.getParameter("phone");
            //打印接受的参数
            LogUtil.log("userAccount:"+account + ";userPassword:" + password+";userName:"+name+";sex:"+sex+";phone:"+phone);

            User user = userService.selectUserByAccount(account);
            if(user!=null){
                 //能查到该账号，说明已经注册过了
                    code = 300;
                    //"该账号已存在";
                } else {
                userService.InsertUser(new User(0,account,name,password,sex,phone));
                code = 400;
//                        //"注册成功";
            }
            response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式

            PrintWriter out = response.getWriter();
            //把数据转换成JSON格式的字符串传递到APP
            String json=JSON.toJSONString(code);
            out.println(json);
            out.flush();
            out.close();
        }

    @RequestMapping("/update")
    public void updateGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int code=0;

        //接受传进来的参数
        String account = request.getParameter("userAccount");
        String password = request.getParameter("userPassword");
        String name=request.getParameter("userName");
        //打印接受的参数
        LogUtil.log("userAccount:"+account + ";userPassword:" + password+";userName:"+name);

        User user = userService.selectUserByAccount(account);

        if(user!=null){
            user.setPassword(password);
            //能查到该账号，说明已经注册过了
            userService.updateUser(user);
            code = 400;
        } else {



            code = 300;
        }
        response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式

        PrintWriter out = response.getWriter();
        //把数据转换成JSON格式的字符串传递到APP
        String json=JSON.toJSONString(code);
        out.println(json);
        out.flush();
        out.close();
    }

    @RequestMapping("/selectUser")
    public void selectUserGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json = "";

        //接受传进来的参数
        String account = request.getParameter("userAccount");
//        String password = request.getParameter("userPassword");
//        String name=request.getParameter("userName");
        //打印接受的参数
        LogUtil.log("userAccount:"+account);

        User user = userService.selectUserByAccount(account);


        response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式

        PrintWriter out = response.getWriter();
        //把数据转换成JSON格式的字符串传递到APP
        json=JSON.toJSONString(user);
        out.println(json);
        out.flush();
        out.close();
    }

    @RequestMapping("/queryWord")
    public void queryWords(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code="";

        //接受传进来的参数
        String eng = request.getParameter("eng");
        //打印接受的参数
        LogUtil.log("eng"+eng);

        Mydict mydict = mydictService.selectByEng(eng);

        if(mydict!=null){
            code = mydict.getExplain();
        }else {
            code = "null";
        }
        response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式

        PrintWriter out = response.getWriter();
        //把数据转换成JSON格式的字符串传递到APP
        String json=JSON.toJSONString(code);
        out.println(json);
        out.flush();
        out.close();
    }

    @RequestMapping("/queryFavorite")
    public void queryFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String json="";

        //接受传进来的参数
        int id = Integer.parseInt(request.getParameter("id"));
        //打印接受的参数
        LogUtil.log("id"+id);

        List<Favorite> allFavorite = favoriteService.getAllFavorite(id);

        if(allFavorite!=null){
            json=JSON.toJSONString(allFavorite);
        }else {
            json = "null";
        }
        response.setContentType("text/html;charset=utf-8"); // 设置响应报文的编码格式

        PrintWriter out = response.getWriter();
        //把数据转换成JSON格式的字符串传递到APP
//        String json=JSON.toJSONString(code);
        out.println(json);
        out.flush();
        out.close();
    }


}

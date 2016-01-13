package ssh.controller;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ssh.aop.AopTest;
import ssh.model.User;
import ssh.service.UserService;

import com.google.gson.Gson;

@Controller
@RequestMapping("/user")
public class UserController {
	Logger logger = Logger.getLogger(UserController.class);
	@Resource
	private UserService userService;
	@Resource
	private AopTest aopTest;
	
	@RequestMapping(value="/addUser")
	@ResponseBody
	public void addUser(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		try{
	        response.setContentType("text/html;charset=UTF-8");
	        String account = request.getParameter("account");
	        String name = request.getParameter("name");
	        String address = request.getParameter("address"); 
	        User user = new User();
	        user.setAccount(account);
	        user.setAddress(address);
	        user.setName(name);
	        userService.add(user);
			out = response.getWriter();
			out.write(new Gson().toJson("success"));
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			if(out != null)
				out.write(new Gson().toJson("fail"));
		}finally{
			out.flush();
			out.close();
		}
        
	}
	
	@RequestMapping(value="/queryUser")
	@ResponseBody
	public void queryAllUser(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out = null;
		
		aopTest.test1();
		aopTest.test2();
		
		try {

	        response.setContentType("text/html;charset=UTF-8");
	
	        Gson gson = new Gson();
	        List<User> userList= userService.queryAllUser();
	        String gsonStr = gson.toJson(userList);
	        
		    out = response.getWriter();
		    out.write(gsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			if(out != null)
				out.write(new Gson().toJson("fail"));
		}finally{
			out.flush();
			out.close();
		}
	}
}

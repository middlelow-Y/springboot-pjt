package demo.springbootpjt;

//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;

import demo.springbootpjt.controller.HelloController;
import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@SpringBootApplication
public class SpringbootPjtApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SpringbootPjtApplication.class, args);
		System.out.println("main method operation check");
		//Spring Boot 동작 원리

		//Servlet Container인 tomcat 띄우기
		//tomcat 생성을 위한 spring boot 도우미 class (추상화된 인터페이스로 Tomcat 말고 다른 것도 사용 가능함)
		TomcatServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		//웹서버 생성하고 시작
		WebServer webServer = serverFactory.getWebServer(new ServletContextInitializer() {
			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
//				// Survlet 등록 및 Mapping
//				// Survlet 등록
//				servletContext.addServlet("hello", new HttpServlet() {
//					@Override
//					protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//						req.getParameter("name"); // querystring 으로 파라미터 받기
//						resp.setStatus(200); // 응답코드 설정
//						resp.setHeader("Content-Type", "text/plain"); // header 설정
//						resp.getWriter().println("Hello Servlet"); // body
//					}
//				}).addMapping("/hello"); // Survlet Mapping

				//front Controller
				// Servlet의 중복 처리를 위해 중앙화 된 하나의 Controller를 둠.
				// 인증, 보안, 다국어, 공통 기능 등
				// Servlet Container의 mapping 기능을 front controller가 할 수 있어야 함.

				HelloController helloController = new HelloController();

				servletContext.addServlet("frontcontroller", new HttpServlet() {
					@Override
					protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
						//Mapping -> URL 및 method에 따라 어떤 것을 수행할 지
						//Binding -> 웹 요청(form 등)을 가지고 java에서 사용할 수 있는 평범한 데이터로 만드는 것.
						// POST method는 받지 않음, 404
						if(req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())){
							String name = req.getParameter("name");
							String ret = helloController.hello(name);
							resp.setStatus(200); // 응답코드 설정
							resp.setHeader("Content-Type", "text/plain"); // header 설정
							resp.getWriter().println("Hello " + ret); // body
						}
						else if(req.getRequestURI().equals("/users")){

						}
						else{
							resp.setStatus(HttpStatus.NOT_FOUND.value());
						}
					}
				}).addMapping("/*"); // Survlet Mapping - / 하위의 모든 요청을 다 받는다.
			}
		});
		webServer.start();

		//Servlet(Web Component) 추가하기
	}

}

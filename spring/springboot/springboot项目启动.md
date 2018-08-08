# SpringBoot内嵌容器的部署方式

SpringBoot内部默认提供内嵌的tomcat容器，所以可以直接打成jar包，丢到服务器上的任何一个目录，然后在当前目录下执行`java -jar demo.jar`即可运行。



# 使用外部的tomcat部署方式

1. pom.xml文件，dependencies中添加

   ```xml
   <dependency>
           <groupId>org.springframework.boot</groupId>
           <artifactId>spring-boot-starter-tomcat</artifactId>
           <scope>provided</scope>
   </dependency>
   ```

2. 在pom.xml文件中，project下面packaging标签改为

   ```xml
   <packaging>war</packaging>
   ```

3. 将项目的启动类继承SpringBootServletInitializer并重写configure方法

   ```java
   @SpringBootApplication
   @ComponentScan(basePackages= {"com.**"})
   public class DemoBootstrap extends SpringBootServletInitializer{

   	@Override
       protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
   		return application.sources(DemoBootstrap.class);
       }
   	
   	public static void main(String[] args) {
   		SpringApplication.run(DemoBootstrap.class, args);
   	}
   }
   ```

   ​
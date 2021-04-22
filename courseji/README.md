# 课记-后台

## 项目结构

- src
    - main
        - kotlin
            - com.littlecorgi.courseji
                - attendance 考勤信息，包含教师创建这个考勤所需要的信息
                - checkon 签到信息，包含student和attendance对应关系，表示某位学生参与了某个签到
                - common 公共基础库
                - course 课程信息，包含这个课程的基础信息
                - schedule 上课信息，包含student和course对应关系，表示某位学生加入了某门课
                - student 学生信息
                - teacher 教师信息
        - resources 资源文件夹
            - application.yml SpringBoot配置文件
- pom.xml maven配置文件

## 项目依赖

- org.springframework.spring-context   
  Spring上下文
- org.springframework.boot.spring-boot-starter-web  
  SpringMVC框架
- org.springframework.boot.spring-boot-starter-data-jpa Spring-JPA组件
- mysql.mysql-connector-java  
  MySQL数据库连接器
- org.springframework.boot.spring-boot-starter-thymeleaf  
  文件上传插件
- com.github.ulisesbocchio.jasypt-spring-boot-starter  
  数据加密库，主要用于加密application.yml中的密码
- org.springframework.boot.spring-boot-starter-test  
  Spring单测
- org.powermock.powermock-module-junit4  
  PowerMock对JUnit4的支持组件
- org.powermock.powermock-api-mockito2  
  PowerMock核心组件
- org.jetbrains.kotlin.kotlin-stdlib-jdk8  
  KotlinJava8支持组件
- org.jetbrains.kotlin.kotlin-test  
  Kotlin单测支持组件
- org.jetbrains.kotlin.kotlin-reflect  
  Kotlin反射组件
- io.springfox.springfox-swagger2  
  Swagger文档核心组件
- com.github.xiaoymin.swagger-bootstrap-ui  
  SwaggerUI组件
- org.projectlombok.lombok  
  LOMBOK组件
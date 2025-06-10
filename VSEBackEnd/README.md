# [VSE-Back-Manage-System](https://github.com/PrettyMagnolia/VSE-Back-Manage-System)

虚拟仿真实验平台后端

## 注意事项

- 在克隆或拉取仓库代码时请注意src\main\resources路径下是否成功获取到application.yml这个文件，若无，请自己创建该文件并放在上述位置，内容在项目根目录下的application.txt文件中。
- 全局SQL方言为MySQL
- 通过cos上传的任何文件的文件名不要有中文，否则获取报告时会得到乱码
## 快速开始

- 仓库地址：https://github.com/usersx/VSE-Back-Manage-System-Back-End
- API测试：http://localhost:8002/api/swagger-ui/index.html
          http://see-toju.com:8002/api/swagger-ui/index.html#

## 项目结构

- src
  - main
    - java
      - com.backend.vse
        - common: 通用类模块，存放通用工具类或公共的逻辑代码，方便在整个项目中复用。
          - Result: 该类定义了controller中api接口返回值的格式，具体用法详见代码注释。
        - config: 配置类模块
        - controller: 控制器层，负责前后端交互，接受前端请求，调用service层，接收service层返回的数据，最后返回具体的页面和数据到客户端。
        - dto: DTO层是在应用程序的service层和mapper层之间引入的一个中间层，用于在不同层之间传输数据。
          1. 数据封装
             DTO层可以将多个实体类的数据封装成一个DTO对象，使得数据传输更加方便。DTO对象通常只包含需要传输的数据字段，而不包含业务逻辑。这样可以减少数据传输的大小，提高性能。
          2. 解耦合
             DTO层可以将业务逻辑层和数据访问层解耦合。业务逻辑层可以通过DTO对象与数据访问层进行数据交互，而不需要直接操作数据库实体类。这样可以减少代码的耦合度，提高代码的可维护性和可测试性。
          3. 数据格式转换
             DTO层可以进行数据格式的转换。例如，将数据库实体类的数据转换成前端需要的JSON格式数据，或者将前端传递的JSON数据转换成数据库实体类需要的格式。这样可以简化数据转换的代码逻辑，提高开发效率。
          4. 安全性
             DTO层可以用于过滤敏感数据。在数据传输过程中，可以通过DTO层过滤掉不需要传输的敏感数据，保护数据的安全性。
        - entity: 实体类，每个类中的成员变量都与数据库中表的字段一一对应。
        - exception: 全局异常处理即异常类模块
        - interceptor:  拦截器模块。拦截器用于在请求处理之前或之后进行额外的处理，比如验证用户的 JWT token，记录请求日志等。
        - mapper: 数据持久层，对数据库进行持久化操作。主要针对数据库进行SQL语句的编写，之后暴露接口，供其它层调用。
        - service: 业务逻辑层，存放业务逻辑处理，不直接对数据库进行操作，分为接口类和接口实现类，负责调用数据访问层的 mapper 来执行操作，并将结果返回给控制层。
        - tools: 工具类模块，存放通用工具类。
        - VSEApplication: 入口文件
    - resource: 
      - json: 配置路由相关信息。
      - application.yml: Springboot项目核心配置文件。
- pow.xml: Maven 项目的核心配置文件，包含项目的基本信息和依赖项，还列出了项目所需的各种依赖库及其版本，
  它还配置了编译器插件和 Spring Boot Maven 插件，以便于项目的构建和管理。

  

  



  

  ​		

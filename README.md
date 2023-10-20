# 背景
目前java应用中使用SFTP协议进行文件上传和下载大多使用JSCH库来实现，但是原生的JSCH并没有对sftp的连接进行池化，每次文件操作都是与sftp服务器创建一个新的tcp连接（SFTP协议在传输层使用的tcp协议，所以SFTP连接本质上就是TCP连接），文件操作完成后销毁连接，这样就会造成性能损耗，例如在业务高峰期有大量的文件上传下载操作时，会产生大量的tcp连接，并且每次文件操作都是新创建tcp连接，tcp连接都知道需要三次握手之后才开始传输，性能较差。

基于以上原因，自研了sftp-pool的小工具，将sftp的连接对象（即connect对象）通过common-pool组件（apache开源的一个对象池化组件，类似于数据库连接池）进行池化，整体实现思路是项目启动时即创建n个sftp连接，将连接放在缓存池中，当业务代码需要sftp连接时，从缓存池中获取，当连接用完之后，在归还到池中，实现连接的复用，这个实现思路也是借鉴了数据库连接池的实现，熟悉数据库连接池原理的同学，对这个组件肯定不会陌生了。

# sftp-pool
基于Jsch和apache common-pool实现的sftp连接池
## 引入依赖
```shell
# 下载代码
git clone https://github.com/yuzhaopeng/sftp-pool.git
# 进入目录打包
cd sftp-pool/
mvn install 

```

## 配置
添加以下配置即可
```java
@Configuration
public class SftpConfig {

    @Bean
    public ConnectionProvider getProvider(){
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(3);
        ConnectionProvider connectionProvider = new ConnectionProvider("host", port, "username", "password",config);
        return  connectionProvider;
    }
}

```
## 使用Demo
```java
@RunWith(SpringRunner.class)
@SpringBootTest
public class SftpPoolApplicationTests {

    @Autowired
    private ConnectionProvider provider;

    @Test
    public void contextLoads() throws  Exception{
        //获取连接
        ChannelSftp connection = (ChannelSftp) provider.getConnection();
        //TODO 上传下载
        //connection.put();
        //connection.get();
        
        //归还连接
        provider.returnObject(connection);
    }

}
```

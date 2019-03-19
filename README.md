# sftp-pool
基于Jsch和apache common-pool实现的sftp连接池
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

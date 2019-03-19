package pool;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


/**
 * @Author: 俞兆鹏
 * @Date: 2019/3/8 14:47
 * @Email: yu.zhaopeng@foxmail.com
 * @Version 1.0
 */

public class ConnectionProvider {

    private String host;
    private Integer port;
    private String userName;
    private String password;
    private GenericObjectPoolConfig config;
    private GenericObjectPool<Channel> pool;

    public ConnectionProvider(String host,Integer port, String userName , String password){
        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password  = password;
        ConnectionFactory factory = new ConnectionFactory(host, port, userName, password);
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        this.pool = new GenericObjectPool<Channel>(factory,config);
    }

    public ConnectionProvider(String host,Integer port, String userName , String password,GenericObjectPoolConfig config){
        this(host,port,userName,password);
        this.config = config;
        ConnectionFactory factory = new ConnectionFactory(host, port, userName, password);
        this.pool = new GenericObjectPool<Channel>(factory,config);
    }


    public ChannelSftp  getConnection() throws Exception{
        return (ChannelSftp)pool.borrowObject();
    }

    public void returnObject(Channel obj){
        pool.returnObject(obj);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GenericObjectPoolConfig getConfig() {
        return config;
    }

    public void setConfig(GenericObjectPoolConfig config) {
        this.config = config;
    }

    public GenericObjectPool<Channel> getPool() {
        return pool;
    }

    public void setPool(GenericObjectPool<Channel> pool) {
        this.pool = pool;
    }
}

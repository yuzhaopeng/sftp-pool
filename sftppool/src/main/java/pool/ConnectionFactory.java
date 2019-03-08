package pool;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.Session;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import sftp.SftpUtils;


/**
 * @Author: 俞兆鹏
 * @Date: 2019/3/8 14:35
 * @Email: yu.zhaopeng@foxmail.com
 * @Version 1.0
 */

public class ConnectionFactory implements PooledObjectFactory<Channel>  {
    private String host;
    private Integer port;
    private String userName;
    private String password;

    public ConnectionFactory(String host,Integer port, String userName , String password){

        this.host = host;
        this.port = port;
        this.userName = userName;
        this.password  = password;
    }

    @Override
    public PooledObject<Channel> makeObject() throws Exception {
        SftpUtils sftpFactory = new SftpUtils(host, port, userName, password);
        Channel sftpChanel = sftpFactory.getSftpChanel();
        DefaultPooledObject<Channel> defaultPooledObject = new DefaultPooledObject<>(sftpChanel);
        return defaultPooledObject;
    }

    @Override
    public void destroyObject(PooledObject<Channel> pooledObject) throws Exception {
        Channel channel = pooledObject.getObject();
        channel.disconnect();

    }

    @Override
    public boolean validateObject(PooledObject<Channel> pooledObject) {
        return pooledObject.getObject().isConnected();
    }

    /**
     * 重新激活对象
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void activateObject(PooledObject<Channel> pooledObject) throws Exception {
            //pooledObject.getObject().connect();
    }

    /**
     * 钝化对象
     * @param pooledObject
     * @throws Exception
     */
    @Override
    public void passivateObject(PooledObject<Channel> pooledObject) throws Exception {
        Channel sftp = pooledObject.getObject();
        Session session = sftp.getSession();

    }

}

package sftp;

import com.jcraft.jsch.*;

import java.util.Properties;

/**
 * @Author: 俞兆鹏
 * @Date: 2019/3/8 11:43
 * @Email： yu.zhaopeng@foxmail.com
 * @Version 1.0
 */

public class SftpUtils {

    private String ip;
    private Integer port;
    private String userName;
    private String password;

    public SftpUtils(){}

    public SftpUtils(String ip, Integer port , String userName , String password){
        this.ip = ip;
        this.port = port;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 建立sftp连接
     *
     * @return
     */
    public Channel getSftpChanel() {
        JSch jSch = new JSch();
        try {
            Session session = jSch.getSession(userName, ip, port);
            session.setPassword(password);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
            channel.connect();
            return  channel;
        }catch (JSchException e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 断开连接
     *
     * @param channel
     * @return
     */
    public void breakChannle(Channel channel) {
        channel.disconnect();

    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
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
}

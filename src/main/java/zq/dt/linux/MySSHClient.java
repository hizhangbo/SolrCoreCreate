package zq.dt.linux;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.ConnectionException;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.TransportException;
import net.schmizz.sshj.userauth.UserAuthException;
import net.schmizz.sshj.xfer.FileSystemFile;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangbo on 2017/9/4.
 */
public class MySSHClient {
    private String host;
    private Integer port;
    private String user;
    private String password;
    private String fingerprint;

    public MySSHClient(String host, Integer port, String user, String password, String fingerprint){
        this.host = host;
        this.port = port;
        this.user = user;
        this.password = password;
        this.fingerprint = fingerprint;
    }

    //执行命令
    public Integer Execute(String execStr){
        final SSHClient ssh = new SSHClient();
        //ssh.addHostKeyVerifier((s, i, publicKey) -> true);
        ssh.addHostKeyVerifier(this.fingerprint);
        try {
            ssh.connect(this.host, this.port);
            ssh.authPassword(this.user, this.password);
            Session session = ssh.startSession();
            try {
                final Session.Command cmd = session.exec(execStr);
                System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
                cmd.join(5, TimeUnit.SECONDS);

                return cmd.getExitStatus();
            } finally {
                session.close();
            }
        } catch (UserAuthException e) {
            e.printStackTrace();
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ssh.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    //上传文件
    public void Upload(String filePath, String newFilePath){
        final SSHClient ssh = new SSHClient();
        //ssh.addHostKeyVerifier((s, i, publicKey) -> true);
        ssh.addHostKeyVerifier(this.fingerprint);
        try {
            ssh.connect(this.host, this.port);
            ssh.authPassword(this.user, this.password);
            final String src = filePath;
            final SFTPClient sftp = ssh.newSFTPClient();
            try {
                sftp.put(new FileSystemFile(src), newFilePath);
            } finally {
                sftp.close();
            }
        } catch (UserAuthException e) {
            e.printStackTrace();
        } catch (TransportException e) {
            // fingerprint error!
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ssh.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

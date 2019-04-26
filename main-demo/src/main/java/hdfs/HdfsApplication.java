package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;

/**
 * @ClassName: hdfs
 * @Author: BYM
 * @Date: 2019/4/26
 * @Description:
 * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
 * 应的书面授权，承担保密责任和接受相应的法律约束.
 */
public class HdfsApplication {

    private Configuration configuration;
    private FileSystem fileSystem;

    private void init() throws Exception {
        configuration = new Configuration();

        configuration.set("fs.default.name", "hdfs://192.168.2.133:9000/");
        fileSystem = FileSystem.get(new URI("hdfs://192.168.2.133:9000/"), configuration, "root");
        /**
         * 上面的方法有两种：(主要区别就在权限问题上)
         * 1.get(configuration) 用户名默认是本机的用户名即运行此程序的电脑的用户名
         * 2.get(URI,conf,user) 可直接指定用户名
         *
         * 在系统的环境变量或java JVM变量里面添加HADOOP_USER_NAME，这个值具体等于多少看自己的情况，以后会运行HADOOP上的Linux的用户名。
         */

    }

    /**
     * @return void
     * @Author BYM
     * @Function 读取文件
     * @Param [filePath]
     **/
    private void readHDFSFile(String filePath) {
        FSDataInputStream fsDataInputStream = null;

        try {
            Path path = new Path(filePath);
            fsDataInputStream = fileSystem.open(path);
            IOUtils.copyBytes(fsDataInputStream, System.out, 4096, false);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fsDataInputStream != null) {
                IOUtils.closeStream(fsDataInputStream);
            }
        }

    }

    /**
     * @return void
     * @Author BYM
     * @Function 写入文件
     * @Param [localPath, hdfsPath]
     **/
    private void writeHDFS(String localPath, String hdfsPath) {
        FSDataOutputStream outputStream = null;
        FileInputStream fileInputStream = null;

        try {
            Path path = new Path(hdfsPath);
            outputStream = fileSystem.create(path);
            fileInputStream = new FileInputStream(new File(localPath));
            //输入流、输出流、缓冲区大小、是否关闭数据流，如果为false就在 finally里关闭
            IOUtils.copyBytes(fileInputStream, outputStream, 4096, false);


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                IOUtils.closeStream(fileInputStream);
            }
            if (outputStream != null) {
                IOUtils.closeStream(outputStream);
            }
        }

    }

    /**
     * @return void
     * @Author BYM
     * @Function 创建文件或文件夹
     * @Param [dirPath]
     **/
    private void mkdirs(String dirPath) {
        try {
            Path path = new Path(dirPath);
            fileSystem.mkdirs(path, new FsPermission("777"));
//            this.getFiledSystem().mkdirs(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * @return void
     * @Author BYM
     * @Function 删除文件或文件夹
     * @Param [fileOrDirPath]
     **/
    private void delete(String fileOrDirPath) {
        try {
            Path path = new Path(fileOrDirPath);
            //第二个参数表示是否递归删除
            fileSystem.delete(path, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @Author BYM
     * @Function 重命名文件或文件夹
     * @Param [oldPath,newPath]
     **/
    private void rename(String oldPath,String newPath) {
        try {
            Path fromPath = new Path(oldPath);
            Path toPath = new Path(newPath);
            fileSystem.rename(fromPath, toPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return void
     * @Author BYM
     * @Function 查看文件信息
     * @Param [filePath]
     **/
    private void getInfo(String filePath) {
        try {
            Path path = new Path(filePath);

            //是否存在
            boolean exists = fileSystem.exists(path);

            FileStatus fileStatus = fileSystem.getFileStatus(path);
            long blockSize = fileStatus.getBlockSize();
            String name = fileStatus.getPath().getName();
            long modificationTime = fileStatus.getModificationTime();
            String owner = fileStatus.getOwner();
            FsPermission permission = fileStatus.getPermission();
            short replication = fileStatus.getReplication();
            String group = fileStatus.getGroup();
            long accessTime = fileStatus.getAccessTime();

            //文件的节点位置
            BlockLocation[] blkLocations = fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
            int blockLen = blkLocations.length;
            System.out.println(blockLen);
            for(int i = 0; i < blockLen; i++) {
                String[] hosts = blkLocations[i].getHosts();
                System.out.println("block " + i + "location:" + hosts[i]);
            }
            System.out.println("hahaha");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws Exception {
        HdfsApplication hdfsApp = new HdfsApplication();
        hdfsApp.init();

//        String filePath = "/user/hdfs/mapred-site.xml";
//        hdfsApp.readHDFSFile(filePath);

        String localPath = "C:\\Users\\YT\\Desktop\\HDFS部署-bym.txt";
        String hdfsPath = "hdfs://192.168.2.133:9000/A/C/text.txt";
//        hdfsApp.writeHDFS(localPath, hdfsPath);
        hdfsApp.getInfo("/A/C/text.txt");


    }


}

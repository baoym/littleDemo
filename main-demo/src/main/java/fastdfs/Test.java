package fastdfs;

import org.apache.tools.ant.util.ResourceUtils;

import static com.sun.javafx.scene.control.skin.Utils.getResource;

/**
 * @ClassName: fastdfs
 * @Author: BYM
 * @Date: 2019/4/23
 * @Description:
 * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
 * 应的书面授权，承担保密责任和接受相应的法律约束.
 */
public class Test {

    private FastDFSClient fastDFSClient= null;

    public void init() throws Exception {
        // 1、把FastDFS提供的jar包添加到工程中
        // 2、初始化全局配置。加载一个配置文件。
        ClassLoader classLoader = Test.class.getClassLoader();
        String confUrl = classLoader.getResource("fdfs_client.properties").getPath();
//        String confUrl=this.getClass().getClassLoader().getResource("/fdfs_client.properties").getPath();
        fastDFSClient=new FastDFSClient(confUrl);
    }


    public void upload() throws Exception {
        init();
        //上传文件
        String  filePath= fastDFSClient.uploadFile("C:\\Users\\YT\\Pictures\\731a7bc7ly1fniqxy30k9j206y06yt8y.jpg");
        System.out.println("返回路径："+filePath);
    }

    public void delete() throws Exception {
        init();
        int flag=fastDFSClient.delete_file("group1/M00/00/00/wKgCgly98BeACqhFAAAyaDcJOvs755.jpg");
        System.out.println("删除结果：" +(flag==0?"删除成功":"删除失败"));
    }

    public void uploadByGroup() throws Exception {
        init();
        //上传文件
        String  filePath= fastDFSClient.uploadFileByGroup("group1","C:\\Users\\YT\\Pictures\\731a7bc7ly1fniqxy30k9j206y06yt8y.jpg",null,null);
        System.out.println("返回路径："+filePath);
    }


    public static void main(String[] args) throws Exception {
        new Test().upload();

//        new Test().uploadByGroup();
    }


}

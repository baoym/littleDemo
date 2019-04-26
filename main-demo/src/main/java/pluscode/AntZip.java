package pluscode;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @ClassName: AntZip
 * @author: shuifeng dong
 * @Date: 2018年3月18日
 * @Description: 文件夹压缩，支持win和linux
 * @Company: 本软件文档资料是北京悦图遥感科技发展有限公司的资产，任何人阅读和使用本资料必须获得相
 *           应的书面授权，承担保密责任和接受相应的法律约束.
 */
public class AntZip {
	/**
	 * @Function 压缩
	 * @param inputFileName 输入一个文件夹
	 * @param zipFileName   输出一个压缩文件夹，打包后文件名字
	 * @throws Exception
	 */
	public static OutputStream zip(String inputFileName, String zipFileName) throws Exception {
		return zip(zipFileName, new File(inputFileName));
	}

	private static OutputStream zip(String zipFileName, File inputFile) throws Exception {
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				zipFileName));
		out.setEncoding("UTF-8");//解决linux乱码  根据linux系统的实际编码设置,可以使用命令 vi/etc/sysconfig/i18n 查看linux的系统编码
		zip(out, inputFile, "");
		out.close();
		return out;
	}

	/**
	 * @author
	 * @Function 压缩
	 * @param [out, f, base]
	 * @return void
	 **/
	private static void zip(ZipOutputStream out, File f, String base) throws Exception {
		if (f.isDirectory()) {
			//判断是否为目录
			File[] fl = f.listFiles();
			ZipEntry zipEntry = new ZipEntry(base + System.getProperties().getProperty("file.separator"));
			zipEntry.setUnixMode(755);//解决linux乱码
			out.putNextEntry(zipEntry);
			base = base.length() == 0 ? "" : base + System.getProperties().getProperty("file.separator");
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + fl[i].getName());
			}
		} else {
			// 压缩目录中的所有文件
			ZipEntry zipEntry = new ZipEntry(base);
			zipEntry.setUnixMode(644);//解决linux乱码
			out.putNextEntry(zipEntry);
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			in.close();
		}
	}

	/**
	 * @Function 压缩
	 *
	 * @param sourceFile 压缩的源文件 如: c:/upload
	 * @param targetZip  生成的目标文件 如：c:/upload.zip
	 */
	public static String zipT(String sourceFile, String targetZip) {

		Project prj = new Project();

		Zip zip = new Zip();

		zip.setProject(prj);

		zip.setDestFile(new File(targetZip));//设置生成的目标zip文件File对象

		FileSet fileSet = new FileSet();

		fileSet.setProject(prj);

		fileSet.setDir(new File(sourceFile));//设置将要进行压缩的源文件File对象

		//fileSet.setIncludes("**/*.java"); //包括哪些文件或文件夹,只压缩目录中的所有java文件

		//fileSet.setExcludes("**/*.java"); //排除哪些文件或文件夹,压缩所有的文件，排除java文件

		zip.addFileset(fileSet);
		zip.setEncoding("UTF-8");
		zip.execute();
		return "finish";
	}

	/**
	 * @Function 解压缩
	 * @param sourceZip "D:\\测试压缩.zip"
	 * @param destDir "D:\\测试压缩\\123"
	 * @return
	 * @throws Exception
	 */
	public static boolean unzip(String sourceZip, String destDir) throws Exception {

		try {

			Project p = new Project();

			Expand e = new Expand();

			e.setProject(p);

			e.setSrc(new File(sourceZip));
			//是否覆盖
			e.setOverwrite(false);

			e.setDest(new File(destDir));

            /* 

              ant下的zip工具默认压缩编码为UTF-8编码， 
		                而winRAR软件压缩是用的windows默认的GBK或者GB2312编码 
		                所以解压缩时要制定编码格式 

            */
			e.setEncoding("UTF-8");  //根据linux系统的实际编码设置
			e.execute();
		} catch (Exception e) {
			return false;

		}
		return true;
	}



}

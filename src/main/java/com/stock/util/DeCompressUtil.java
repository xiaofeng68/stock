package com.stock.util;  
  
import java.io.File;  
import java.io.FileOutputStream;  
import java.util.regex.Matcher;  
import java.util.regex.Pattern;  
  
import org.apache.tools.ant.Project;  
import org.apache.tools.ant.taskdefs.Expand;  
  
import de.innosystec.unrar.Archive;  
import de.innosystec.unrar.rarfile.FileHeader;  
  
/**  
 * ZIP文件解压(要使用apache ant.jar以处理中文乱码)    
 * rar文件解压(要使用java-unrar-0.3.jar)    
 * @author  
 */  
public class DeCompressUtil {  
     
    /**  
     * 解压缩文件  
     * @param sourceFile 压缩文件绝对路径  
     * @param destDir 解压目录，解压后的文件将会放入此目录中，请使用绝对路径  
     * 只支持zip和rar格式的压缩包！   
     * @throws Exception  
     */  
    public static void deCompress(String sourceFile, String destDir)  
            throws Exception {  
        // 保证文件夹路径最后是"/"或者"\"  
        char lastChar = destDir.charAt(destDir.length() - 1);  
        if (lastChar != '/' && lastChar != '\\') {  
            destDir += File.separator;  
        }  
        // 根据类型，进行相应的解压缩  
        String type = sourceFile.substring(sourceFile.lastIndexOf(".") + 1);  
        if (type.toLowerCase().equals("zip")) {  
            DeCompressUtil.unzip(sourceFile, destDir);  
        } else if (type.toLowerCase().equals("rar")) {  
            DeCompressUtil.unrar(sourceFile, destDir);  
        } else {  
            throw new Exception("只支持zip和rar格式的压缩包！");  
        }  
    }  
      
    /**  
     * 解压zip格式压缩包   
     * 对应的是ant.jar  
     */  
    private static void unzip(String sourceZip, String destDir)  
            throws Exception {  
        try {  
            Project p = new Project();  
            Expand e = new Expand();  
            e.setProject(p);  
            e.setSrc(new File(sourceZip));  
            e.setOverwrite(false);  
            e.setDest(new File(destDir));  
            /*  
             * ant下的zip工具默认压缩编码为UTF-8编码， 而winRAR软件压缩是用的windows默认的GBK或者GB2312编码  
             * 所以解压缩时要制定编码格式  
             */  
            e.setEncoding("gbk");  
            e.execute();  
        } catch (Exception e) {  
            throw e;  
        }  
    }  
  
    /**  
     * 解压rar格式压缩包。  
     * 对应的是java-unrar-0.3.jar  
     */  
    private static void unrar(String sourceRar, String destDir)  
            throws Exception {  
        Archive a = null;  
        FileOutputStream fos = null;  
        try {  
            a = new Archive(new File(sourceRar));  
            FileHeader fh = a.nextFileHeader();  
            while (fh != null) {  
                // 如果解压fh是目录,直接生成目录即可,不用写入  
                if (!fh.isDirectory()) {  
                    // 处理中文乱码  
                    String fileName = fh.getFileNameW().trim();  
                    if(!existZH(fileName)){  
                        fileName = fh.getFileNameString().trim();  
                    }  
                    // 解压文件路径  
                    String destFileName = destDir + fileName;  
                    // 解压目录路径  
                    String destDirName = destFileName.substring(0, destFileName.lastIndexOf(File.separator));  
                    // 2创建文件夹  
                    File dir = new File(destDirName);  
                    if (!dir.exists() || !dir.isDirectory()) {  
                        dir.mkdirs();  
                    }  
                    // 3解压缩文件  
                    fos = new FileOutputStream(new File(destFileName));  
                    a.extractFile(fh, fos);  
                    fos.close();  
                    fos = null;  
                }  
                fh = a.nextFileHeader();  
                continue;  
            }  
            a.close();  
            a = null;  
        } catch (Exception e) {  
            throw e;  
        } finally {  
            if (fos != null) {  
                try {  
                    fos.close();  
                    fos = null;  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
            if (a != null) {  
                try {  
                    a.close();  
                    a = null;  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
      
    /**  
     * 判断是否为中文  
     * @param str  
     * @return  
     */  
    public static boolean existZH(String str) {  
        String regEx = "[\\u4e00-\\u9fa5]";  
        Pattern p = Pattern.compile(regEx);  
        Matcher m = p.matcher(str);  
        while (m.find()) {  
            return true;  
        }  
        return false;  
    }  
}  
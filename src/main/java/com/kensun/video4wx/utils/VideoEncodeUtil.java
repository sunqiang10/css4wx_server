package com.kensun.video4wx.utils;
import java.io.*;
public class VideoEncodeUtil {

    public static void main(String[] args) throws Exception {
//        encrypt("D:\\py交易\\11.mp4","fuckyourself");
//        decrypt("D:\\py交易\\11.mp4","D:\\py交易\\22.mp4",4);
//        System.out.println(readFileLastByte("D:\\py交易\\11.mp4",12));
    }
    /**
     * 文件file进行加密
     * @param fileUrl 文件路径
     * @param key 密码
     * @throws Exception
     */
    public static void encrypt(String fileUrl, String key) throws Exception {
        File file = new File(fileUrl);
        String path = file.getPath();
        if(!file.exists()){
            return;
        }
        int index = path.lastIndexOf("\\");
        String destFile = path.substring(0, index)+"\\"+"abc";
        File dest = new File(destFile);
        //获取待加密文件的输入流
        InputStream in = new FileInputStream(fileUrl);
        //创建中转文件输出流
        OutputStream out = new FileOutputStream(destFile);
        //待加密文件的流
        byte[] buffer = new byte[1024];
        int r;
        //加密之后的文件的流
        byte[] buffer2=new byte[1024];
        while (( r= in.read(buffer)) > 0) {
            for(int i=0;i<r;i++)
            {
                byte b=buffer[i];
                //buffer2[i]=b==255?0:++b;
                //每个字节加2加密
                b+=2;
                buffer2[i] = b;

            }
            out.write(buffer2, 0, r);
            out.flush();
        }
        in.close();
        out.close();
        file.delete();
        dest.renameTo(new File(fileUrl));
        appendMethodA(fileUrl, key);
        System.out.println("加密成功");
    }
    /**
     *
     * @param fileName
     * @param content 密钥
     */
    public static void appendMethodA(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 解密
     * @param fileUrl 源文件
     * @param tempUrl 临时文件
     * @param keyLength 密码长度
     * @return
     * @throws Exception
     */
    public static String decrypt(String fileUrl, String tempUrl, int keyLength) throws Exception{
        File file = new File(fileUrl);
        if (!file.exists()) {
            return null;
        }
        File dest = new File(tempUrl);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        //获取待解密的文件输入流
        InputStream is = new FileInputStream(fileUrl);
        //创建目标文件输出流，用来生成解密后的文件
        OutputStream out = new FileOutputStream(tempUrl);
        byte[] buffer = new byte[1024];
        byte[] buffer2=new byte[1024];
        byte bMax=(byte)255;
        long size = file.length() - keyLength;
        int mod = (int) (size%1024);
        int div = (int) (size>>10);
        int count = mod==0?div:(div+1);
        int k = 1, r;
        while ((k <= count && ( r = is.read(buffer)) > 0)) {
            if(mod != 0 && k==count) {
                r = mod;
            }
            for(int i = 0;i < r;i++)
            {
                byte b=buffer[i];
                //buffer2[i]=b==0?bMax:--b;
                //每个字节减2解码
                b-=2;
                buffer2[i] = b;

            }
            out.write(buffer2, 0, r);
            k++;
        }
        out.close();
        is.close();
        return tempUrl;
    }
    /**
     * 判断文件是否加密
     * @param fileName
     * @return
     *
     * 加密成功返回key
     * 加密失败返回非key的字符串
     */
    public static String readFileLastByte(String fileName, int keyLength) {
        File file = new File(fileName);
        if(!file.exists())return "没有文件";
        StringBuffer str = new StringBuffer();
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            for(int i = keyLength ; i>=1 ; i--){
                randomFile.seek(fileLength-i);
                str.append((char)randomFile.read());
            }
            randomFile.close();
            return str.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "异常";
    }

    /**
     * 加密视频
     * @param fileName
     */
    public  static void encryptVideo(String path , String fileName ,String key){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                file.mkdir();
            }
            /**
             * 加密不可进行多次
             * 判断是否有加密，加密过无需在加密
             */
            if(!VideoEncodeUtil.readFileLastByte(path+ File.separator +fileName,key.length()).equals(key)) {
                VideoEncodeUtil.encrypt(path + File.separator + fileName, key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解密视频
     * @param fileName
     */
    public static String decryptVideo(String path ,String fileName,String key) throws Exception {

        File file = new File(path);
        String tempPath = path+"temp";
        File tempFile = new File(tempPath);
        if(!file.exists()){
            file.mkdir();
        }
        if(!tempFile.exists()){
            tempFile.mkdir();
        }
        return VideoEncodeUtil.decrypt(path+  fileName,
                tempPath +  fileName,
                key.length());
    }

    public static void deleteVideoFile(String tempFilePath) {
        File file = new File(tempFilePath);
        if(file.exists()){
            file.delete();
        }
    }
}

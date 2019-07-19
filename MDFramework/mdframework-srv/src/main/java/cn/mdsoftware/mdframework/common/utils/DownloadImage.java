package cn.mdsoftware.duangongpingtai.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;


public class DownloadImage {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
//        download("http://ui.51bi.com/opt/siteimg/images/fanbei0923/Mid_07.jpg", "51bi.gif", "c:\\image\\");
//        download("https://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKTp5ia8vFt50sMCUrT045EGYhwAkk0VJ9g4GfVRT8OCVFHRIxB7rpLw7vRE4ibAZtItcSSuicFScAzQ/132","s.jpg","D:\\workspace\\DuanGongPingTai\\code\\java\\upload-dir");
        System.out.println(System.getProperty("os.name"));
    }

    public static void download(String urlString, String filename, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }

//        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
        OutputStream os = new FileOutputStream(sf.getPath() + System.getProperty("file.separator").toString() + filename);

        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }

}
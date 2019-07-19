package cn.mdsoftware.mdframework.controller;


import cn.mdsoftware.mdframework.bean.api.HttpResponse;
import cn.mdsoftware.mdframework.common.enums.HttpResponseEnum;
import cn.mdsoftware.mdframework.common.utils.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/file")
public class FileUploadController {

    private static final Logger log = LoggerFactory.getLogger(FileUploadController.class);


    private final ResourceLoader resourceLoader;
    public String getPrePath() {
        return prePath;
    }

    public void setPrePath(String prePath) {
        this.prePath = prePath;
    }

    @Value("${file-upload-path}")
    public  String ROOT;

    @Value("${file-upload-path-url-pre}")
    private String prePath;

    @Autowired
    public FileUploadController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    //显示图片的方法关键 匹配路径像 localhost:8080/b7c76eb3-5a67-4d41-ae5c-1642af3f8746.png
    @RequestMapping(method = RequestMethod.GET, value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {

        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/upload")
    @ResponseBody
    public HttpResponse<String> handleFileUpload(@RequestParam("file") MultipartFile file,
                                                 RedirectAttributes redirectAttributes, HttpServletRequest request) {
        HttpResponse<String> resp = new HttpResponse<String>();
        if (!file.isEmpty()) {
            try {
                Date date = new Date();
                //格式化并转换String类型
                String dateStr = new SimpleDateFormat("yyyy/MM/dd").format(date);
                String path=ROOT.concat("/uploadfiles/").concat(dateStr);
                File f = new File(path);
                if(!f.exists())
                    f.mkdirs();

                String puff = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String name = RandomUtils.randomToken().concat(puff);
                Files.copy(file.getInputStream(), Paths.get(path, name));
                resp.setData(prePath.concat(dateStr).concat("/").concat(name));

            } catch (IOException|RuntimeException e) {
                resp.setHttpResponseEnum(HttpResponseEnum.SYSTEM_ERROR);
            }
        } else {
            resp.setHttpResponseEnum(HttpResponseEnum.PARAM_ERROR);
        }


        return resp;
    }


    //显示图片的方法关键 匹配路径像 localhost:8080/b7c76eb3-5a67-4d41-ae5c-1642af3f8746.png
    @RequestMapping( value = "/ueditor")
    @ResponseBody
    public Object ueditor(HttpServletRequest request, HttpServletResponse response, String action, MultipartFile file) {
        if (StringUtils.isNotBlank(action) && "config".equals(action)) {
            response.setContentType("application/json;charset=utf-8");
            String config = "/* 前后端通信相关的配置,注释只允许使用多行方式 */\n" +
                    "{\n" +
                    "    /* 上传图片配置项 */\n" +
                    "    \"imageActionName\": \"uploadimage\", /* 执行上传图片的action名称 */\n" +
                    "    \"imageFieldName\": \"file\", /* 提交的图片表单名称 */\n" +
                    "    \"imageMaxSize\": 2048000, /* 上传大小限制，单位B */\n" +
                    "    \"imageAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], /* 上传图片格式显示 */\n" +
                    "    \"imageCompressEnable\": true, /* 是否压缩图片,默认是true */\n" +
                    "    \"imageCompressBorder\": 1600, /* 图片压缩最长边限制 */\n" +
                    "    \"imageInsertAlign\": \"none\", /* 插入的图片浮动方式 */\n" +
                    "    \"imageUrlPrefix\": \"\", /* 图片访问路径前缀 */\n" +
                    "    \"imagePathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\n" +
                    "                                /* {filename} 会替换成原文件名,配置这项需要注意中文乱码问题 */\n" +
                    "                                /* {rand:6} 会替换成随机数,后面的数字是随机数的位数 */\n" +
                    "                                /* {time} 会替换成时间戳 */\n" +
                    "                                /* {yyyy} 会替换成四位年份 */\n" +
                    "                                /* {yy} 会替换成两位年份 */\n" +
                    "                                /* {mm} 会替换成两位月份 */\n" +
                    "                                /* {dd} 会替换成两位日期 */\n" +
                    "                                /* {hh} 会替换成两位小时 */\n" +
                    "                                /* {ii} 会替换成两位分钟 */\n" +
                    "                                /* {ss} 会替换成两位秒 */\n" +
                    "                                /* 非法字符 \\ : * ? \" < > | */\n" +
                    "                                /* 具请体看线上文档: fex.baidu.com/ueditor/#use-format_upload_filename */\n" +
                    "\n" +
                    "    /* 涂鸦图片上传配置项 */\n" +
                    "    \"scrawlActionName\": \"uploadimage\", /* 执行上传涂鸦的action名称 */\n" +
                    "    \"scrawlFieldName\": \"file\", /* 提交的图片表单名称 */\n" +
                    "    \"scrawlPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\n" +
                    "    \"scrawlMaxSize\": 2048000, /* 上传大小限制，单位B */\n" +
                    "    \"scrawlUrlPrefix\": \"\", /* 图片访问路径前缀 */\n" +
                    "    \"scrawlInsertAlign\": \"none\",\n" +
                    "\n" +
                    "    /* 截图工具上传 */\n" +
                    "    \"snapscreenActionName\": \"uploadimage\", /* 执行上传截图的action名称 */\n" +
                    "    \"snapscreenPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\n" +
                    "    \"snapscreenUrlPrefix\": \"\", /* 图片访问路径前缀 */\n" +
                    "    \"snapscreenInsertAlign\": \"none\", /* 插入的图片浮动方式 */\n" +
                    "\n" +
                    "    /* 抓取远程图片配置 */\n" +
                    "    \"catcherLocalDomain\": [\"127.0.0.1\", \"localhost\", \"img.baidu.com\"],\n" +
                    "    \"catcherActionName\": \"catchimage\", /* 执行抓取远程图片的action名称 */\n" +
                    "    \"catcherFieldName\": \"source\", /* 提交的图片列表表单名称 */\n" +
                    "    \"catcherPathFormat\": \"/ueditor/jsp/upload/image/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\n" +
                    "    \"catcherUrlPrefix\": \"\", /* 图片访问路径前缀 */\n" +
                    "    \"catcherMaxSize\": 2048000, /* 上传大小限制，单位B */\n" +
                    "    \"catcherAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], /* 抓取图片格式显示 */\n" +
                    "\n" +
                    "    /* 上传视频配置 */\n" +
                    "    \"videoActionName\": \"uploadimage\", /* 执行上传视频的action名称 */\n" +
                    "    \"videoFieldName\": \"file\", /* 提交的视频表单名称 */\n" +
                    "    \"videoPathFormat\": \"/ueditor/jsp/upload/video/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\n" +
                    "    \"videoUrlPrefix\": \"\", /* 视频访问路径前缀 */\n" +
                    "    \"videoMaxSize\": 102400000, /* 上传大小限制，单位B，默认100MB */\n" +
                    "    \"videoAllowFiles\": [\n" +
                    "        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\n" +
                    "        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\"], /* 上传视频格式显示 */\n" +
                    "\n" +
                    "    /* 上传文件配置 */\n" +
                    "    \"fileActionName\": \"uploadfile\", /* controller里,执行上传视频的action名称 */\n" +
                    "    \"fileFieldName\": \"upfile\", /* 提交的文件表单名称 */\n" +
                    "    \"filePathFormat\": \"/ueditor/jsp/upload/file/{yyyy}{mm}{dd}/{time}{rand:6}\", /* 上传保存路径,可以自定义保存路径和文件名格式 */\n" +
                    "    \"fileUrlPrefix\": \"\", /* 文件访问路径前缀 */\n" +
                    "    \"fileMaxSize\": 51200000, /* 上传大小限制，单位B，默认50MB */\n" +
                    "    \"fileAllowFiles\": [\n" +
                    "        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\n" +
                    "        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\n" +
                    "        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\n" +
                    "        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\n" +
                    "        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\n" +
                    "    ], /* 上传文件格式显示 */\n" +
                    "\n" +
                    "    /* 列出指定目录下的图片 */\n" +
                    "    \"imageManagerActionName\": \"listimage\", /* 执行图片管理的action名称 */\n" +
                    "    \"imageManagerListPath\": \"/ueditor/jsp/upload/image/\", /* 指定要列出图片的目录 */\n" +
                    "    \"imageManagerListSize\": 20, /* 每次列出文件数量 */\n" +
                    "    \"imageManagerUrlPrefix\": \"\", /* 图片访问路径前缀 */\n" +
                    "    \"imageManagerInsertAlign\": \"none\", /* 插入的图片浮动方式 */\n" +
                    "    \"imageManagerAllowFiles\": [\".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\"], /* 列出的文件类型 */\n" +
                    "\n" +
                    "    /* 列出指定目录下的文件 */\n" +
                    "    \"fileManagerActionName\": \"listfile\", /* 执行文件管理的action名称 */\n" +
                    "    \"fileManagerListPath\": \"/ueditor/jsp/upload/file/\", /* 指定要列出文件的目录 */\n" +
                    "    \"fileManagerUrlPrefix\": \"\", /* 文件访问路径前缀 */\n" +
                    "    \"fileManagerListSize\": 20, /* 每次列出文件数量 */\n" +
                    "    \"fileManagerAllowFiles\": [\n" +
                    "        \".png\", \".jpg\", \".jpeg\", \".gif\", \".bmp\",\n" +
                    "        \".flv\", \".swf\", \".mkv\", \".avi\", \".rm\", \".rmvb\", \".mpeg\", \".mpg\",\n" +
                    "        \".ogg\", \".ogv\", \".mov\", \".wmv\", \".mp4\", \".webm\", \".mp3\", \".wav\", \".mid\",\n" +
                    "        \".rar\", \".zip\", \".tar\", \".gz\", \".7z\", \".bz2\", \".cab\", \".iso\",\n" +
                    "        \".doc\", \".docx\", \".xls\", \".xlsx\", \".ppt\", \".pptx\", \".pdf\", \".txt\", \".md\", \".xml\"\n" +
                    "    ] /* 列出的文件类型 */\n" +
                    "\n" +
                    "}";
            return config;
        } else if (StringUtils.isNotBlank(action) && "uploadimage".equals(action)) {
            return uploadImage(file);
        }
        return null;
    }


    private Object uploadImage(MultipartFile file) {
        if (null == file) return null;
        String puff = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String name = RandomUtils.randomToken().concat(puff);

        Date date = new Date();
        //格式化并转换String类型
        String dateStr = new SimpleDateFormat("yyyy/MM/dd").format(date);
        String path=ROOT.concat("/uploadfiles/").concat(dateStr);
        File f = new File(path);
        if(!f.exists())
            f.mkdirs();

        try {
            Files.copy(file.getInputStream(), Paths.get(path, name));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String,Object> result = new HashMap<>();
        result.put("url", prePath.concat(dateStr).concat("/").concat(name));
        result.put("size", 1);
//        result.put("type",
//                file.getName().substring(file.getName().lastIndexOf(".")));
        result.put("state", "SUCCESS");
        return result;
    }

}
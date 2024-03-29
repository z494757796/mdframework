package cn.mdsoftware.mdframework.common.utils;


import cn.mdsoftware.mdframework.bean.ColumnDO;
import cn.mdsoftware.mdframework.bean.TableDO;
import cn.mdsoftware.mdframework.common.Constants;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 代码生成器   工具类
 *
 */
public class GenUtils {

    public static List<String> getTemplates(){
        List<String> templates = new ArrayList<String>();
        templates.add("templates/common/generator/domain.java.vm");
        templates.add("templates/common/generator/Dao.java.vm");
        templates.add("templates/common/generator/ApiController.java.vm");
        //templates.add("templates/common/generator/Mapper.java.vm");
		templates.add("templates/common/generator/Mapper.xml.vm");
        templates.add("templates/common/generator/Service.java.vm");
        templates.add("templates/common/generator/ServiceImpl.java.vm");
        templates.add("templates/common/generator/Controller.java.vm");
        templates.add("templates/common/generator/list.html.vm");
        templates.add("templates/common/generator/add.html.vm");
        templates.add("templates/common/generator/edit.html.vm");
        templates.add("templates/common/generator/list.js.vm");
        templates.add("templates/common/generator/add.js.vm");
        templates.add("templates/common/generator/edit.js.vm");
        templates.add("templates/common/generator/menu.sql.vm");
        return templates;
    }

    /**
     * 生成代码
     */
    public static void generatorCode(Map<String, String> table,
                                     List<Map<String, String>> columns, ZipOutputStream zip, String tablePre){
        //配置信息
        Configuration config = getConfig();

        //表信息
        TableDO TableDO = new TableDO();
        TableDO.setTableName(table.get("tableName"));
        TableDO.setComments(table.get("tableComment"));
        //表名转换成Java类名
//        String className = tableToJava(TableDO.getTableName(), config.getString("tablePrefix"));
        String className = tableToJava(TableDO.getTableName(), tablePre);
        TableDO.setClassName(className);
        String classname = StringUtils.uncapitalize(className);
        TableDO.setClassname(classname);

        //列信息
        List<ColumnDO> columsList = new ArrayList<>();
        for(Map<String, String> column : columns){
            ColumnDO ColumnDO = new ColumnDO();
            ColumnDO.setColumnName(column.get("columnName"));
            ColumnDO.setDataType(column.get("dataType"));
            ColumnDO.setComments(column.get("columnComment"));
            ColumnDO.setExtra(column.get("extra"));

            //列名转换成Java属性名
            String attrName = columnToJava(ColumnDO.getColumnName());
            ColumnDO.setAttrName(attrName);
            ColumnDO.setAttrname(StringUtils.uncapitalize(attrName));

            //列的数据类型，转换成Java类型
            String attrType = config.getString(ColumnDO.getDataType(), "unknowType");
            ColumnDO.setAttrType(attrType);

            //是否主键
            if("PRI".equalsIgnoreCase(column.get("columnKey")) && TableDO.getPk() == null){
                TableDO.setPk(ColumnDO);
            }

            columsList.add(ColumnDO);
        }
        TableDO.setColumns(columsList);

        //没主键，则第一个字段为主键
        if(TableDO.getPk() == null){
            TableDO.setPk(TableDO.getColumns().get(0));
        }

        //设置velocity资源加载器
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        Velocity.init(prop);

        //封装模板数据
        Map<String, Object> map = new HashMap<>();
        map.put("tableName", TableDO.getTableName());
        map.put("comments", TableDO.getComments());
        map.put("pk", TableDO.getPk());
        map.put("className", TableDO.getClassName());
        map.put("classname", TableDO.getClassname());
        //map.put("pathName", config.getString("packageName")+"/"+TableDO.getClassname().toLowerCase());
        map.put("pathName", config.getString("packageName"));
        map.put("columns", TableDO.getColumns());
        map.put("package", config.getString("package"));
        map.put("author", config.getString("author"));
        map.put("email", config.getString("email"));
        map.put("datetime", DateUtils.toDate(new Date(), Constants.DatetimePattern.DP19));
        VelocityContext context = new VelocityContext(map);

        //获取模板列表
        List<String> templates = getTemplates();
        for(String template : templates){
            //渲染模板
            StringWriter sw = new StringWriter();
            Template tpl = Velocity.getTemplate(template, "UTF-8");
            tpl.merge(context, sw);

            try {
                //添加到zip
                zip.putNextEntry(new ZipEntry(getFileName(template, TableDO.getClassname(),TableDO.getClassName(), config.getString("package"))));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
                throw new BDException("渲染模板失败，表名：" + TableDO.getTableName(), e);
            }
        }
    }


    /**
     * 列名转换成Java属性名
     */
    public static String columnToJava(String columnName) {
        return WordUtils.capitalizeFully(columnName, new char[]{'_'}).replace("_", "");
    }

    /**
     * 表名转换成Java类名
     */
    public static String tableToJava(String tableName, String tablePrefix) {
        if(StringUtils.isNotBlank(tablePrefix)){
            tableName = tableName.replace(tablePrefix, "");
        }
        return columnToJava(tableName);
    }

    /**
     * 获取配置信息
     */
    public static Configuration getConfig(){
        try {
            return new PropertiesConfiguration("generator.properties");
        } catch (ConfigurationException e) {
            throw new BDException("获取配置文件失败，", e);
        }
    }

    /**
     * 获取文件名
     */
    public static String getFileName(String template, String classname,String className, String packageName){
        String packagePath = "main" + File.separator + "java" + File.separator;
        if(StringUtils.isNotBlank(packageName)){
            packagePath += packageName.replace(".", File.separator) + File.separator;
        }

        if(template.contains("domain.java.vm")){
            return packagePath + File.separator + "bean"+File.separator+"entity" + File.separator + className + "DO.java";
        }

        if (template.contains("Dao.java.vm")) {
            return packagePath + "dao" + File.separator + className + "Dao.java";
        }

        /*if(template.contains("Mapper.java.vm")){
            return packagePath + "dao" + File.separator + classname + File.separator + className + "Mapper.java";
        }*/

        if(template.contains("Service.java.vm")){
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if(template.contains("ServiceImpl.java.vm")){
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if(template.contains("ApiController.java.vm")){
            return packagePath + "controller" + File.separator + "api" + File.separator + className + "Controller.java";
        }

        if(template.contains("Controller.java.vm")){
            return packagePath + "controller" + File.separator + className + "Controller.java";
        }


        if(template.contains("Mapper.xml.vm")){
            return "main" + File.separator + "resources" +  File.separator + "mapper" + File.separator +  className + "Mapper.xml";
        }

        if(template.contains("list.html.vm")){
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    +  File.separator + classname + File.separator + classname + ".html";
            //				+ "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".html";
        }
        if(template.contains("add.html.vm")){
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    + File.separator + classname + File.separator +  "add.html";
        }
        if(template.contains("edit.html.vm")){
            return "main" + File.separator + "resources" + File.separator + "templates" + File.separator
                    +  File.separator + classname + File.separator + "edit.html";
        }

        if(template.contains("list.js.vm")){
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + classname + File.separator + classname+ ".js";
            //		+ "modules" + File.separator + "generator" + File.separator + className.toLowerCase() + ".js";
        }
        if(template.contains("add.js.vm")){
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + classname + File.separator + "add.js";
        }
        if(template.contains("edit.js.vm")){
            return "main" + File.separator + "resources" + File.separator + "static" + File.separator + "js" + File.separator
                    + "appjs" + File.separator + classname + File.separator + "edit.js";
        }

        if(template.contains("menu.sql.vm")){
            return className.toLowerCase() + "_menu.sql";
        }

        return null;
    }
}

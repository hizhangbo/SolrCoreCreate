package zq.dt.solr;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import zq.dt.linux.ExitStatus;
import zq.dt.linux.MySSHClient;
import zq.dt.solr.core.dataConfig.DataSource;
import zq.dt.solr.core.dataConfig.Entity;
import zq.dt.solr.core.dataConfig.Field;
import zq.dt.solr.core.managedSchema.FieldType;
import zq.dt.solr.core.solrConfig.Lib;
import zq.dt.xml.XmlHelper;
import zq.dt.zip.ZipUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CoreConfig {

    // 拷贝 core 配置模板
    public void copyTemplateCore(String coreName) throws IOException {
        XmlHelper.copy("src/main/resources/solr/core/","src/main/resources/solr/" + coreName);
    }

    // 生成 data-config.xml 配置文件
    public void createDataConfig(DataSource dataSource, Entity entity, List<Field> fields, String coreName) throws IOException, DocumentException {

        Document document = XmlHelper.load("src/main/resources/solr/core/conf/data-config.xml");
        if(document != null){
            //dataSource 节点属性修改
            document.getRootElement().element("dataSource").attribute("type").setValue(dataSource.get_type());
            document.getRootElement().element("dataSource").attribute("driver").setValue(dataSource.get_driver());
            document.getRootElement().element("dataSource").attribute("url").setValue(dataSource.get_url());
            document.getRootElement().element("dataSource").attribute("user").setValue(dataSource.get_user());
            document.getRootElement().element("dataSource").attribute("password").setValue(dataSource.get_password());
            document.getRootElement().element("dataSource").attribute("batchSize").setValue(dataSource.get_batchSize());

            //entity 节点属性
            document.getRootElement().element("document").element("entity").attribute("name").setValue(entity.get_name());
            document.getRootElement().element("document").element("entity").attribute("pk").setValue(entity.get_pk());
            document.getRootElement().element("document").element("entity").attribute("query").setValue(entity.get_query());
            document.getRootElement().element("document").element("entity").attribute("deltaImportQuery").setValue(entity.get_deltaImportQuery());
            document.getRootElement().element("document").element("entity").attribute("deltaQuery").setValue(entity.get_deltaQuery());
            document.getRootElement().element("document").element("entity").attribute("transformer").setValue(entity.get_transformer());

            // 创建Field
            for (Field field : fields){
                Element element = document.getRootElement().element("document").element("entity").addElement("field");
                element.addAttribute("column",field.get_column());
                element.addAttribute("name",field.get_name());
            }
        }
        XmlHelper.createXml(document, coreName, "data-config.xml");
    }

    // 生成 managed-schema 配置文件
    public void createManagedSchema(String coreName, String uniqueKey, List<zq.dt.solr.core.managedSchema.Field> fields, List<FieldType> fieldTypes) throws DocumentException, IOException {

        Document document = XmlHelper.load("src/main/resources/solr/core/conf/managed-schema");
        if(document != null){
            document.getRootElement().attribute("name").setValue(coreName);
            document.getRootElement().addElement("uniqueKey").addText(uniqueKey);

            Element element = document.getRootElement();
            for (zq.dt.solr.core.managedSchema.Field field : fields){
                Element fieldtmp = element.addElement("field");

                fieldtmp.addAttribute("name",field.get_name());
                fieldtmp.addAttribute("type",field.get_type());
                fieldtmp.addAttribute("indexed",field.get_indexed());
                fieldtmp.addAttribute("stored",field.get_stored());

                if(field.get_multiValued() != ""){
                    fieldtmp.addAttribute("multiValued",field.get_multiValued());
                }

                if(field.get_required() != ""){
                    fieldtmp.addAttribute("required",field.get_required());
                }

                if(field.get_default() != ""){
                    fieldtmp.addAttribute("default",field.get_default());
                }
            }
        }
        XmlHelper.createXml(document, coreName, "managed-schema");
    }

    // 生成 solrconfig.xml 配置文件
    public void createSolrConfig(List<Lib> libs, String coreName) throws DocumentException, IOException {

        Document document = XmlHelper.load("src/main/resources/solr/core/conf/solrconfig.xml");
        if(document!=null){
            for(Lib lib : libs){
                // 这里存在问题，lib 标签必须在 config 下的最前面，不然 solr 报错
                document.getRootElement().addElement("lib")
                        .addAttribute("dir",lib.getDir())
                        .addAttribute("regex",lib.getRegex());
            }
        }
        XmlHelper.createXml(document, coreName, "solrconfig.xml");
    }

    // 压缩 core 文件
    public void zipSolrCore(String coreName) throws IOException {
        ZipUtils.doCompress("src/main/resources/solr/"+coreName, "src/main/resources/solr/"+coreName +".zip");
    }

    // 上传 core 文件
    public void uploadCore(String coreName) throws IOException {
        Properties pps = new Properties();
        pps.load(new FileInputStream("src/main/resources/server.properties"));
        MySSHClient client = new MySSHClient(
                pps.getProperty("host")
                ,Integer.parseInt(pps.getProperty("port"))
                ,pps.getProperty("user")
                ,pps.getProperty("password")
                ,pps.getProperty("fingerprint"));
        client.Upload("src/main/resources/solr/"+coreName +".zip", pps.getProperty("solrhome"));
    }

    // 解压 core 文件
    public void unzipSolrCore(String coreName) throws IOException {
        if(shutdownTomcat() != 0){
            return;
        }
        Properties pps = new Properties();
        pps.load(new FileInputStream("src/main/resources/server.properties"));
        String solrhome = pps.getProperty("solrhome");
        List<String> cmd = new ArrayList<>();
        cmd.add("cd " + solrhome);
        cmd.add("unzip -o " + coreName+".zip");

        MySSHClient client = new MySSHClient(
                pps.getProperty("host")
                ,Integer.parseInt(pps.getProperty("port"))
                ,pps.getProperty("user")
                ,pps.getProperty("password")
                ,pps.getProperty("fingerprint"));
        int status = client.Execute(String.join(";", cmd));
        executeStatus(status);
        startupTomcat();
    }

    // 安装 zip 命令
    public void installZip() throws IOException {
        Properties pps = new Properties();
        pps.load(new FileInputStream("src/main/resources/server.properties"));
        String cmd = "yum install -y zip unzip";
        MySSHClient client = new MySSHClient(
                pps.getProperty("host")
                ,Integer.parseInt(pps.getProperty("port"))
                ,pps.getProperty("user")
                ,pps.getProperty("password")
                ,pps.getProperty("fingerprint"));
        int status = client.Execute(cmd);
        executeStatus(status);
    }

    // 删除 core
    public void removeSolr(String coreName) throws IOException {
        if(shutdownTomcat() != 0){
            return;
        }
        Properties pps = new Properties();
        pps.load(new FileInputStream("src/main/resources/server.properties"));
        List<String> cmd = new ArrayList<>();
        cmd.add("cd " + pps.getProperty("solrhome"));
        cmd.add("rm -rf " + coreName);

        MySSHClient client = new MySSHClient(
                pps.getProperty("host")
                ,Integer.parseInt(pps.getProperty("port"))
                ,pps.getProperty("user")
                ,pps.getProperty("password")
                ,pps.getProperty("fingerprint"));
        int status = client.Execute(String.join(";", cmd));
        executeStatus(status);
        startupTomcat();
    }

    // 开启 Tomcat
    public Integer startupTomcat() throws IOException {
        Properties pps = new Properties();
        pps.load(new FileInputStream("src/main/resources/server.properties"));
        List<String> cmd = new ArrayList<>();
        cmd.add("cd " + pps.getProperty("tomcat"));
        cmd.add("./startup.sh");

        MySSHClient client = new MySSHClient(
                pps.getProperty("host")
                ,Integer.parseInt(pps.getProperty("port"))
                ,pps.getProperty("user")
                ,pps.getProperty("password")
                ,pps.getProperty("fingerprint"));
        int status = client.Execute(String.join(";", cmd));
        return status;
    }

    // 关闭 Tomcat
    public Integer shutdownTomcat() throws IOException {
        Properties pps = new Properties();
        pps.load(new FileInputStream("src/main/resources/server.properties"));
        List<String> cmd = new ArrayList<>();
        cmd.add("cd " + pps.getProperty("tomcat"));
        cmd.add("./shutdown.sh");

        MySSHClient client = new MySSHClient(
                pps.getProperty("host")
                ,Integer.parseInt(pps.getProperty("port"))
                ,pps.getProperty("user")
                ,pps.getProperty("password")
                ,pps.getProperty("fingerprint"));
        int status = client.Execute(String.join(";", cmd));
        return status;
    }

    // 执行 linux 命令后的状态
    public void executeStatus(Integer status){
        switch (ExitStatus.getStatus(status)){
            case Success:
                System.out.println("执行成功");
                break;
            case Error:
                System.out.println("执行失败");
                break;
            case NotFoundCmd:
                System.out.println("命令未安装");
                break;
            case NotExecutable:
                System.out.println("命令无法执行");
                break;
            case NotKnow:
                System.out.println("未知错误");
                break;
        }
    }
}

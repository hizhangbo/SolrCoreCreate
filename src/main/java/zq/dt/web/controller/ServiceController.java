package zq.dt.web.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import zq.dt.solr.CoreConfig;
import zq.dt.solr.core.dataConfig.DataSource;
import zq.dt.solr.core.dataConfig.Entity;
import zq.dt.solr.core.dataConfig.Field;
import zq.dt.solr.core.solrConfig.Lib;
import zq.dt.web.model.DataConfig;
import zq.dt.web.model.JsonResult;
import zq.dt.web.model.ManagedSchema;
import zq.dt.web.model.ManagedSchemaField;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class ServiceController {

    @RequestMapping(value = "/copy", method = POST)
    public JsonResult copy(@RequestParam(value="name", defaultValue="") String name) {
        CoreConfig coreConfig = new CoreConfig();
        try {
            coreConfig.copyTemplateCore(name);
        } catch (IOException e) {
            e.printStackTrace();
            return new JsonResult(500, String.format("%s 创建失败！错误：%s", name, e.getMessage()));
        }
        return new JsonResult(200, String.format("%s 创建成功！", name));
    }

    @RequestMapping(value = "/createDataConfig", method = POST)
    public JsonResult createDataConfig(@RequestBody DataConfig dataConfig){
        DataSource dataSource = new DataSource();
        dataSource.set_batchSize(dataConfig.get_batchSize());
        dataSource.set_driver(dataConfig.get_driver());
        dataSource.set_type(dataConfig.get_type());
        dataSource.set_url(dataConfig.get_url());
        dataSource.set_user(dataConfig.get_user());
        dataSource.set_password(dataConfig.get_password());

        Entity entity = new Entity();
        entity.set_name(dataConfig.get_coreName());
        entity.set_pk(dataConfig.get_pk());
        entity.set_query(dataConfig.get_query());
        entity.set_deltaQuery(dataConfig.get_deltaQuery());
        entity.set_deltaImportQuery(dataConfig.get_deltaImportQuery());
        entity.set_transformer(dataConfig.get_transformer());

        List<Field> fieldList = new ArrayList<>();
        for (String field : dataConfig.getFields()){
            if(field != null && field != ""){
                fieldList.add(new Field(field.trim().toUpperCase(),field.trim().toLowerCase()));
            }
        }

        CoreConfig coreConfig = new CoreConfig();
        try {
            coreConfig.createDataConfig(dataSource, entity, fieldList, dataConfig.get_coreName());
        } catch (Exception e) {
            return new JsonResult(500, String.format("%s 创建失败！失败原因：%s", "data-config.xml", e.getMessage()));
        }
        return new JsonResult(200, String.format("%s 创建成功！", "data-config.xml"));
    }

    @RequestMapping(value = "/createManagedSchema", method = POST)
    public JsonResult createManagedSchema(@RequestBody ManagedSchema managedScheam){
        String coreName = managedScheam.getCoreName();
        String uniqueKey = managedScheam.getUniqueKey();
        List<zq.dt.solr.core.managedSchema.Field> fields = new ArrayList<>();
        fields.add(new zq.dt.solr.core.managedSchema.Field("_version_","long","true","true", "","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("text","text_general","true","false", "false","",""));
        for(ManagedSchemaField field : managedScheam.getFieldList()){
            fields.add(new zq.dt.solr.core.managedSchema.Field(field.getName(),field.getType(),field.getIndexed(),field.getStored(), field.getMultiValued(),field.getDefaultValue(),field.getRequired()));
        }

        CoreConfig coreConfig = new CoreConfig();
        try {
            coreConfig.createManagedSchema(coreName, uniqueKey, fields, null);
        } catch (Exception e) {
            return new JsonResult(500, String.format("%s 创建失败！失败原因：%s", "managed-schema", e.getMessage()));
        }
        return new JsonResult(200, String.format("%s 创建成功！", "managed-schema"));
    }

    // 暂时未用
    // 配置solrconfig.xml文件，使用默认路径即可
    @RequestMapping(value = "/createSolrConfig", method = POST)
    public JsonResult createSolrConfig(@RequestParam(value="name", defaultValue="") String name){
        List<Lib> libs = new ArrayList<>();
        libs.add(new Lib("/usr/local/solr/solrhome/contrib/extraction/lib",".*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/dist/","solr-cell-\\d.*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/contrib/clustering/lib/",".*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/dist/","solr-clustering-\\d.*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/contrib/langid/lib/",".*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/dist/","solr-langid-\\d.*\\.ja"));
        libs.add(new Lib("/usr/local/solr/solrhome/contrib/velocity/lib","*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/dist/","solr-velocity-\\d.*\\.jar"));

        CoreConfig coreConfig = new CoreConfig();
        try {
            coreConfig.createSolrConfig(libs, name);
        } catch (Exception e) {
            return new JsonResult(500, String.format("%s 创建失败！失败原因：%s", "solrconfig.xml", e.getMessage()));
        }
        return new JsonResult(200, String.format("%s 创建成功！", name));
    }

    @RequestMapping(value = "/uploadCore", method = POST)
    public JsonResult uploadCore(@RequestParam(value="coreName", defaultValue="") String coreName){
        CoreConfig coreConfig = new CoreConfig();
        try {
            coreConfig.zipSolrCore(coreName);
            coreConfig.uploadCore(coreName);
            coreConfig.unzipSolrCore(coreName);
        } catch (IOException e) {
            return new JsonResult(500, String.format("%s 上传失败！", coreName));
        }

        Properties pps = new Properties();
        try {
            pps.load(new FileInputStream("src/main/resources/server.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JsonResult(200,
                String.format("%s 上传成功！", coreName),
                String.format("http://%s:%s/solr/index.html#/~cores/", pps.getProperty("host"), pps.getProperty("port")));
    }

    @RequestMapping(value = "/installCore", method = POST)
    public JsonResult installCore(@RequestParam(value="name", defaultValue="") String name){
        return new JsonResult(200, String.format("%s 安装成功！", name));
    }

    @RequestMapping(value = "/removeCore", method = POST)
    public JsonResult createRemoveCore(@RequestParam(value="name", defaultValue="") String name){
        CoreConfig coreConfig = new CoreConfig();
        try {
            coreConfig.removeSolr("shxh_spxx");
        } catch (IOException e) {
            return new JsonResult(200, String.format("%s 删除失败！失败原因：%s", name, e.getMessage()));
        }
        return new JsonResult(200, String.format("%s 删除成功！", name));
    }
}

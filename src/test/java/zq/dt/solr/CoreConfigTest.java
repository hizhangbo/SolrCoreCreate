package zq.dt.solr;

import org.dom4j.DocumentException;
import org.junit.Test;
import zq.dt.solr.core.dataConfig.DataSource;
import zq.dt.solr.core.dataConfig.Entity;
import zq.dt.solr.core.dataConfig.Field;
import zq.dt.solr.core.solrConfig.Lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CoreConfigTest {
    CoreConfig coreConfig = new CoreConfig();

    @Test
    public void testCopyTemplateCore() throws IOException {
        coreConfig.copyTemplateCore("shxh_spxx");
    }

    @Test
    public void testCreateDataConfig() throws IOException, DocumentException {
        DataSource dataSource = new DataSource();
        dataSource.set_batchSize("-1");
        dataSource.set_driver("com.mysql.jdbc.Driver");
        dataSource.set_type("JdbcDataSource");
        dataSource.set_url("jdbc:mysql://200.1.3.155:3306/shxh_sjfx");
        dataSource.set_user("root");
        dataSource.set_password("91test.com");

        Entity entity = new Entity();
        entity.set_name("shxh_spxx");
        entity.set_pk("id");
        entity.set_query("select id,spxxid,dj,spbh as isbn,pm,LENGTH(pm) as len,csm,zz,sdhrq,DATE_FORMAT(CBNY,'%Y%m') as CBNY,year(CBNY) as CBN,cbsid,bc from jt_j_spxx");
        entity.set_deltaQuery("select id from jt_j_spxx where updatetime > '${dih.last_index_time}'");
        entity.set_deltaImportQuery("select id,spxxid,dj,spbh as isbn,pm,LENGTH(pm) as len,csm,zz,sdhrq,DATE_FORMAT(sp.CBNY,'%Y%m') as CBNY,year(sp.CBNY) as CBN,cbsid,bc from jt_j_spxx where id = '${dih.delta.id}'");
        entity.set_transformer("RegexTransformer");

        List<Field> fieldList = new ArrayList<>();
        fieldList.add(new Field("ID","id"));
        fieldList.add(new Field("PM","pm"));
        fieldList.add(new Field("DJ","dj"));
        fieldList.add(new Field("ISBN","isbn"));
        fieldList.add(new Field("SPXXID","spxxid"));
        fieldList.add(new Field("LEN","len"));
        fieldList.add(new Field("CSM","csm"));
        fieldList.add(new Field("ZZ","zz"));
        fieldList.add(new Field("SDHEQ","sdheq"));
        fieldList.add(new Field("CBNY","cbny"));
        fieldList.add(new Field("CBN","cbn"));
        fieldList.add(new Field("CBSID","cbsid"));
        fieldList.add(new Field("BC","bc"));

        coreConfig.createDataConfig(dataSource, entity, fieldList, "shxh_spxx");
    }

    @Test
    public void testCreateManagedSchema() throws DocumentException, IOException {
        String coreName = "shxh_spxx";
        String uniqueKey = "id";
        List<zq.dt.solr.core.managedSchema.Field> fields = new ArrayList<>();
        fields.add(new zq.dt.solr.core.managedSchema.Field("_version_","long","true","true", "","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("id","long","true","true", "false","","true"));
        fields.add(new zq.dt.solr.core.managedSchema.Field("dj","tdouble","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("pm","string","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("isbn","string","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("spxxid","string","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("len","int","true","true", "false","0",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("csm","string","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("zz","string","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("sdhrq","tdate","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("cbny","tint","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("cbn","tint","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("cbsid","string","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("bc","string","true","true", "false","",""));
        fields.add(new zq.dt.solr.core.managedSchema.Field("text","text_general","true","false", "false","",""));

        coreConfig.createManagedSchema(coreName, uniqueKey, fields, null);
    }

    @Test
    public void testCreateSolrConfig() throws DocumentException, IOException {
        List<Lib> libs = new ArrayList<>();
        libs.add(new Lib("/usr/local/solr/solrhome/contrib/extraction/lib",".*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/dist/","solr-cell-\\d.*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/contrib/clustering/lib/",".*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/dist/","solr-clustering-\\d.*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/contrib/langid/lib/",".*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/dist/","solr-langid-\\d.*\\.ja"));
        libs.add(new Lib("/usr/local/solr/solrhome/contrib/velocity/lib","*\\.jar"));
        libs.add(new Lib("/usr/local/solr/solrhome/dist/","solr-velocity-\\d.*\\.jar"));

        coreConfig.createSolrConfig(libs, "shxh_spxx");
    }

    @Test
    public void testZipSolrCore() throws IOException {
        coreConfig.zipSolrCore("shxh_spxx");
    }

    @Test
    public void testUploadCore() throws IOException {
        coreConfig.uploadCore("shxh_spxx");
    }

    @Test
    public void testUnzipSolrCore() throws IOException {
        coreConfig.unzipSolrCore("shxh_spxx");
    }

    @Test
    public void testInstallCore() throws IOException {
        coreConfig.installZip();
    }

    @Test
    public void testRemoveCore() throws IOException {
        coreConfig.removeSolr("shxh_spxx");
    }
}

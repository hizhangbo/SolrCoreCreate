package zq.dt.web.model;

import java.util.List;

public class DataConfig {
    // core name
    public String get_coreName() {
        return _coreName;
    }

    public void set_coreName(String _coreName) {
        this._coreName = _coreName;
    }

    private String _coreName;

    // fields
    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    private List<String> fields;

    // DataSource
    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_driver() {
        return _driver;
    }

    public void set_driver(String _driver) {
        this._driver = _driver;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public String get_user() {
        return _user;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public String get_password() {
        return _password;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public String get_batchSize() {
        return _batchSize;
    }

    public void set_batchSize(String _batchSize) {
        this._batchSize = _batchSize;
    }

    private String _type;

    private String _driver;

    private String _url;

    private String _user;

    private String _password;

    private String _batchSize;

    // Entity
    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_pk() {
        return _pk;
    }

    public void set_pk(String _pk) {
        this._pk = _pk;
    }

    public String get_query() {
        return _query;
    }

    public void set_query(String _query) {
        this._query = _query;
    }

    public String get_deltaImportQuery() {
        return _deltaImportQuery;
    }

    public void set_deltaImportQuery(String _deltaImportQuery) {
        this._deltaImportQuery = _deltaImportQuery;
    }

    public String get_deltaQuery() {
        return _deltaQuery;
    }

    public void set_deltaQuery(String _deltaQuery) {
        this._deltaQuery = _deltaQuery;
    }

    public String get_transformer() {
        return _transformer;
    }

    public void set_transformer(String _transformer) {
        this._transformer = _transformer;
    }

    private String _name;

    private String _pk;

    private String _query;

    private String _deltaImportQuery;

    private String _deltaQuery;

    private String _transformer;
}

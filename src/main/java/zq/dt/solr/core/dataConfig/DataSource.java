package zq.dt.solr.core.dataConfig;

public class DataSource {
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
}

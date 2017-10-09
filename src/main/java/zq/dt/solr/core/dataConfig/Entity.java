package zq.dt.solr.core.dataConfig;

public class Entity {
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

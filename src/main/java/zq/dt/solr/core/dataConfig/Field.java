package zq.dt.solr.core.dataConfig;

public class Field {
    public String get_column() {
        return _column;
    }

    public void set_column(String _column) {
        this._column = _column;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public Field(String _column, String _name){
        this._column = _column;
        this._name = _name;
    }

    private String _column;
    private String _name;
}

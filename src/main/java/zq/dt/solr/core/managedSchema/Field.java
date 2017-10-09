package zq.dt.solr.core.managedSchema;

public class Field {
    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_type() {
        return _type;
    }

    public void set_type(String _type) {
        this._type = _type;
    }

    public String get_indexed() {
        return _indexed;
    }

    public void set_indexed(String _indexed) {
        this._indexed = _indexed;
    }

    public String get_stored() {
        return _stored;
    }

    public void set_stored(String _stored) {
        this._stored = _stored;
    }

    public String get_multiValued() {
        return _multiValued;
    }

    public void set_multiValued(String _multiValued) {
        this._multiValued = _multiValued;
    }

    public String get_default() {
        return _default;
    }

    public void set_default(String _default) {
        this._default = _default;
    }

    public String get_required() {
        return _required;
    }

    public void set_required(String _required) {
        this._required = _required;
    }

    public Field(String _name, String _type, String _indexed, String _stored, String _multiValued, String _default, String _required){
        this._name = _name;
        this._type = _type;
        this._indexed = _indexed;
        this._stored = _stored;
        this._multiValued = _multiValued;
        this._default = _default;
        this._required = _required;
    }

    private String _name;
    private String _type;
    private String _indexed;
    private String _stored;
    private String _multiValued;
    private String _default;
    private String _required;
}

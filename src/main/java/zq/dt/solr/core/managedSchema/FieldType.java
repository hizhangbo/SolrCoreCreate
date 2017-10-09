package zq.dt.solr.core.managedSchema;

public class FieldType {
    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public String get_class() {
        return _class;
    }

    public void set_class(String _class) {
        this._class = _class;
    }

    public String get_precisionStep() {
        return _precisionStep;
    }

    public void set_precisionStep(String _precisionStep) {
        this._precisionStep = _precisionStep;
    }

    public String get_positionIncrementGap() {
        return _positionIncrementGap;
    }

    public void set_positionIncrementGap(String _positionIncrementGap) {
        this._positionIncrementGap = _positionIncrementGap;
    }

    public String get_sortMissingLast() {
        return _sortMissingLast;
    }

    public void set_sortMissingLast(String _sortMissingLast) {
        this._sortMissingLast = _sortMissingLast;
    }

    public String get_omitNorms() {
        return _omitNorms;
    }

    public void set_omitNorms(String _omitNorms) {
        this._omitNorms = _omitNorms;
    }

    private String _name;
    private String _class;
    private String _precisionStep;
    private String _positionIncrementGap;
    private String _sortMissingLast;
    private String _omitNorms;
}

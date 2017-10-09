package zq.dt.web.model;

public class ManagedSchemaField {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIndexed() {
        return indexed;
    }

    public void setIndexed(String indexed) {
        this.indexed = indexed;
    }

    public String getStored() {
        return stored;
    }

    public void setStored(String stored) {
        this.stored = stored;
    }

    public String getMultiValued() {
        return multiValued;
    }

    public void setMultiValued(String multiValued) {
        this.multiValued = multiValued;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getRequired() {
        return required;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    private String name;
    private String type;
    private String indexed;
    private String stored;
    private String multiValued;
    private String defaultValue;
    private String required;
}

package zq.dt.web.model;

import java.util.List;

public class ManagedSchema {
    public List<ManagedSchemaField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<ManagedSchemaField> fieldList) {
        this.fieldList = fieldList;
    }

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    List<ManagedSchemaField> fieldList;
    String coreName;
    String uniqueKey;
}

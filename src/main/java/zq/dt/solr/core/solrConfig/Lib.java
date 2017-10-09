package zq.dt.solr.core.solrConfig;

public class Lib {
    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    private String dir;

    private String regex;

    public Lib(String dir, String regex){
        this.dir = dir;
        this.regex = regex;
    }
}

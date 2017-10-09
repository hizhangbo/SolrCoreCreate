package zq.dt.web.model;

public class JsonResult {
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    int status;
    String message;
    String url;

    public JsonResult(int status, String message){
        this.status = status;
        this.message = message;
    }

    public JsonResult(int status, String message, String url){
        this.status = status;
        this.message = message;
        this.url = url;
    }

}

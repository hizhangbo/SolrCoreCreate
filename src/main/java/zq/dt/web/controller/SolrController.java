package zq.dt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * Created by zhangbo on 2017/9/4.
 */
@Controller
public class SolrController {
    @RequestMapping("/solr")
    public String server() {
        return "solr";
    }

    @RequestMapping(value="/upload",method = RequestMethod.POST)
    public String upload() throws IOException {

        return "upload";
    }


    @RequestMapping(value = "/connect", method = RequestMethod.GET)
    public String connect(@RequestParam(value="host", required=false, defaultValue="192.168.80.80") String host
            , @RequestParam(value="name", required=false, defaultValue="root") String name
            , @RequestParam(value="password", required=false, defaultValue="123456") String password
            , Model model) throws IOException {


        //model.addAttribute("name", name);
        return "connect";
    }
}

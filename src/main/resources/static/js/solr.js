var solr = (function(){
    var _copy = function(name){
        $.post("/copy",{name: name}, function(data, status){
            if(status == "success"){
                if(data.status == 200){
                    $("#solrCoreName").val(name);
                    $("#step1").css("display", "none");
                    $("#step2").css("display", "block");
                }else{
                    alert(data.message);
                }
            }
        },"json");
    }

    var _build_fields = function(query){
        $("#config").css("display","none");

        var from = query.substring(query.indexOf("from"), query.length);
        var field_str = query.replace("select ", "").replace(from, "");
        var fields = field_str.replace(/\([A-Za-z0-9]+,/g, "").split(',');

        var result = []
        for(var i in fields){
            if(fields[i].indexOf("as") >= 0){
                fields[i] = fields[i].substring(fields[i].indexOf("as") + 3, fields[i].length);
            }
            result.push(fields[i]);
        }

        var import_fields = "";
        for(var i in result){
            import_fields += result[i] + ',';
            $("#fields").append("<div>" + result[i] + "</div>");
        }
        $("#import_fields").val(import_fields);
        $("#fields").append("<input type='button' value='取消' id='cancel_field' onclick='solr.CancelField()' />");
        $("#fields").append("<input type='button' value='确定' id='ok_field' onclick='solr.OKField()' />");
    }

    var _cancel_field = function(){
        $("#fields").html("");
        $("#config").css("display","inline");
    }

    var _ok_field = function(){
        var fields_str = $("#import_fields").val();

        var param = {}
        var _coreName = $("#solrCoreName").val();
        var fields = fields_str.split(',');
        var _type = $("#type").val();
        var _driver = $("#driver").val();
        var _url = $("#url").val();
        var _user = $("#user").val();
        var _password = $("#password").val();
        var _batchSize = $("#batchSize").val();

        var _pk = $("#pk").val();
        var _query = $("#query").val();
        var _deltaImportQuery = $("#deltaImportQuery").val();
        var _deltaQuery = $("#deltaQuery").val();
        var _transformer = $("#transformer").val();

        param["_coreName"] = _coreName;
        param["_type"] = _type;
        param["_driver"] = _driver;
        param["_url"] = _url;
        param["_user"] = _user;
        param["_password"] = _password;
        param["_batchSize"] = _batchSize;

        param["_pk"] = _pk;
        param["_query"] = _query;
        param["_deltaImportQuery"] = _deltaImportQuery;
        param["_deltaQuery"] = _deltaQuery;
        param["_transformer"] = _transformer;

        param["fields"] = fields;

        $.ajax({
            type: "POST",
            contentType : "application/json ; charset=utf-8",
            url:"/createDataConfig",
            data:JSON.stringify(param),
            dataType: "json",
            async: false,
            success:function(data){
                if(data.status == 200){
                    $("#step2").css("display", "none");
                    var html = "<table class='table' id='managedSchemaTable'><tr>"
                                +"<td>字段名称</td>"
                                +"<td>字段类型</td>"
                                +"<td>是否索引</td>"
                                +"<td>stored</td>"
                                +"<td>是否多值</td>"
                                +"<td>默认值</td>"
                                +"<td>是否必要</td>"
                                "</tr>";

                    var type_select = "<select><option>long</option>"+
                                              "<option>tdouble</option>"+
                                              "<option>string</option>"+
                                              "<option>int</option>"+
                                              "<option>tdate</option>"+
                                              "<option>tint</option>"+
                                              "<option>text_general</option></select>"
                    for(var i in fields){
                        if(fields[i] != ""){
                            var tr = "<tr><td>"+fields[i]
                                    +"</td><td>" + type_select
                                    +"</td><td>"+ "<input type='checkbox' checked>"
                                    +"</td><td>"+"<input type='checkbox' checked>"
                                    +"</td><td>"+"<input type='checkbox'>"
                                    +"</td><td>"+"<input type='text' value=''>"
                                    +"</td><td>"+"<input type='checkbox'>"+"</td></tr>";
                            html += tr;
                        }
                    }
                    html += "<tr><td colspan='7' style='text-align:right;'><input type='button' value='确定' id='managedSchema' onclick='solr.ManagedSchema()'></td></tr></table>";

                    $("#step3").html(html);
                }
            }
         });
    }

    var _managed_schema = function(){
        var fields = [];
        var trLength = $("#managedSchemaTable").find("tr").length - 1;
        $("#managedSchemaTable").find("tr").each(function(i, item){
            if(i != 0 && i != trLength){
                var field = {"name":"", "type":"", "indexed":"", "stored":"", "multiValued":"", "defaultValue":"", "required":""};
                var tdArray = $(item).find("td");
                field.name = tdArray.eq(0).text();
                field.type = tdArray.eq(1).find("select").val();
                field.indexed = tdArray.eq(2).find("input").prop("checked");
                field.stored = tdArray.eq(3).find("input").prop("checked");
                field.multiValued = tdArray.eq(4).find("input").prop("checked");
                field.defaultValue = tdArray.eq(5).find("input").val();
                field.required = tdArray.eq(6).find("input").prop("checked");
                fields.push(field);
            }
        });
        var uniqueKey = "id";
        var coreName = $("#solrCoreName").val();
        var param = {};
        param["uniqueKey"] = uniqueKey;
        param["coreName"] = coreName;
        param["fieldList"] = fields;

        $.ajax({
            type: "POST",
            contentType : "application/json ; charset=utf-8",
            url:"/createManagedSchema",
            data:JSON.stringify(param),
            dataType: "json",
            async: false,
            success: function(data){
                if(data.status == 200){
                    $("#step3").hide();
                    $("#step4").show();
                }
            }
        });
    }

    var _uploadSolrCore = function(){
        var coreName = $("#solrCoreName").val();
        $.post("/uploadCore", {coreName: coreName}, function(data, status){
            if(status == "success"){
                if(data.status == 200){
                    window.location.href = data.url;
                }else{
                    alert(data.message);
                }
            }
        }, "json");
    }

    return {
        Copy: _copy,
        BuildFields: _build_fields,
        CancelField: _cancel_field,
        OKField: _ok_field,
        ManagedSchema: _managed_schema,
        UploadSolrCore: _uploadSolrCore
    }
})();


$(function(){
    $("#copy_core").click(function(){
        var name = $("#core_name").val();
        if(name == "" || name == null){
            alert("solr core 名称不能为空！");
            return;
        }
        solr.Copy(name);
    });

    $("#field_build").click(function(){
        var query = $("#query").val();
        solr.BuildFields(query);
    });

    $("#uploadCore").click(function(){
        solr.UploadSolrCore();
    });
})
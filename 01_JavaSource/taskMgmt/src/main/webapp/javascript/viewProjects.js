var header = $("meta[name='_csrf_header']").attr("content");
var token = $("meta[name='_csrf']").attr("content");
                	

$(function () {
	
        $("#jsGrid").jsGrid({
        	height: 500,
            width: "60%",
            
            filtering: true,
            inserting: true,
            editing: true,
            sorting: true,
            paging: true,
            autoload: true,
           
            pageSize: 2,
            pageButtonCount: 5,
            pageLoading: true,
            
            deleteConfirm: "Do you really want to delete the project?",   
           
            controller: {           	
            	loadData: function(filter) {  
            		//alert(filter.sortField);
            		//alert(filter.sortOrder);
            		//alert(filter.name);      		
            		
            		var d = $.Deferred();
            		var jsonReq = '{"type":"view","filter":"'+filter.name+'","sortOrder":"'+filter.sortOrder+'","sortField":"'+filter.sortField+'","dummy=":"'+(new Date()).getTime()+'"}';
            	   
            	    $.ajax({
                        type: "GET",
                        url: encodeURI("manageProject.do?jsonstr="+jsonReq),
                        error: function() {
                            //alert("FAILURE !");
                          }
                    }).done(function(response) {
                    	d.resolve({
            	            data: JSON.parse(response.projects),
            	            itemsCount: response.itemCount
            	       });
            	    });
            	   return d.promise();
            	},
            	insertItem: function (item) {
            	    var jsonReq = '{"type":"add","id":"'+item.id+'","name":"'+item.name+'","desc":"'+item.description+'"}';
            	    $.ajax({
                        type: "POST",
                        url: encodeURI("addProject.do"),
                        data: "jsonstr="+jsonReq,
                        beforeSend: function(xhr){
                            xhr.setRequestHeader(header, token);
                        },
                        error: function() {
                            //alert("FAILURE !");
                          }
                    }).done(function(){
                    	$("#jsGrid").jsGrid("loadData");
                    });
                },
                updateItem: function (item) {
                	var jsonReq = '{"type":"update","id":"'+item.id+'","name":"'+item.name+'","desc":"'+item.description+'"}';
                    $.ajax({
                        type: "POST",
                        url: encodeURI("addProject.do"),
                        data: "jsonstr="+jsonReq,
                        beforeSend: function(xhr){
                            xhr.setRequestHeader(header, token);
                        },
                        error: function() {
                            //alert("FAILURE !");
                          }
                    }).done(function(){
                    	$("#jsGrid").jsGrid("loadData");
                    });
                },
                deleteItem: function (item) {
                	var jsonReq = '{"type":"delete","id":"'+item.id+'","dummy=":"'+(new Date()).getTime()+'"}';
                    $.ajax({
                        type: "GET",
                        url: encodeURI("manageProject.do?jsonstr="+jsonReq),
                        error: function() {
                            //alert("FAILURE !");
                          }
                    }).done(function(){
                    	$("#jsGrid").jsGrid("loadData");
                    });
                }
            },
            
            fields: [
                {name: "id", title: "Id", type: "text",visible: false, width: 0},
                {name: "name", title: "Project Name", type: "text", width: 60, order:"asc"},
                {name: "description", title: "Description", type: "text",  width: 50,sorting:false},
                {type: "control"}
            ]
            
        });      
});
var header = $("meta[name='_csrf_header']").attr("content");
var token = $("meta[name='_csrf']").attr("content");
                	

$(function () {
	
	var jsonReqObj = new Object();
	
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
            		jsonReqObj.type="view";
            		jsonReqObj.projName=filter.name;
            		jsonReqObj.sortOrder=filter.sortOrder;
            		jsonReqObj.sortField=filter.sortField;
            		jsonReqObj.projDesc=filter.description;
            		
            	    $.ajax({
                        type: "GET",
                        url: encodeURI("manageProject.do?jsonstr="+JSON.stringify(jsonReqObj)),
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
            	    jsonReqObj = new Object();
            	    jsonReqObj.type="add";
            	    jsonReqObj.id=item.id;
            	    jsonReqObj.name=item.name;
            	    jsonReqObj.desc=item.description;
            	    
            	    $.ajax({
                        type: "POST",
                        url: encodeURI("addProject.do"),
                        data: "jsonstr="+JSON.stringify(jsonReqObj),
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
                	jsonReqObj = new Object();
             	    jsonReqObj.type="update";
             	    jsonReqObj.id=item.id;
             	    jsonReqObj.name=item.name;
             	    jsonReqObj.desc=item.description;
                    $.ajax({
                        type: "POST",
                        url: encodeURI("addProject.do"),
                        data: "jsonstr="+JSON.stringify(jsonReqObj),
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
                	jsonReqObj = new Object();
             	    jsonReqObj.type="delete";
             	    jsonReqObj.id=item.id;
                    $.ajax({
                        type: "POST",
                        url: encodeURI("addProject.do"),
                        data: "jsonstr="+JSON.stringify(jsonReqObj),
                        beforeSend: function(xhr){
                            xhr.setRequestHeader(header, token);
                        },
                        error: function() {
                            //alert("FAILURE !");
                          }
                    }).done(function(response){
                    	if(response.results=='success'){
                    		$("#jsGrid").jsGrid("loadData");
                    	}else{
                    		alert("Error Occured- Cannot delete Project.");
                    	}
                    });
                }
            },
            
            fields: [
                {name: "id", title: "Id", type: "text",visible: false, width: 0},
                {name: "name", title: "Project Name", type: "text", width: 60, order:"asc"},
                {name: "description", title: "Description", type: "text",  width: 50},
                {type: "control"}
            ]
            
        });      
});
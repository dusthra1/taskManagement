var header = $("meta[name='_csrf_header']").attr("content");
var token = $("meta[name='_csrf']").attr("content");
                	

$(function () {
	
	var jsonReq = '{"type":"projects","dummy=":"'+(new Date()).getTime()+'"}';
	
    $.ajax({
        type: "GET",
        url: encodeURI("manageEmployees.do?jsonstr="+jsonReq)
    }).done(function(projList) {

	
        $("#jsGrid").jsGrid({
        	height: 500,
            width: "100%",
            
            filtering: true,
            inserting: true,
            editing: true,
            sorting: true,
            paging: true,
            autoload: true,
           
            pageSize: 2,
            pageButtonCount: 5,
            pageLoading: true,
            
            deleteConfirm: "Do you really want to delete record?",   
           
            controller: {           	
            	loadData: function(filter) {      		
            		
            		var d = $.Deferred();
            		var jsonReq = '{"type":"view","filter":"'+filter.name+'","sortOrder":"'+filter.sortOrder+'","sortField":"'+filter.sortField+'","dummy=":"'+(new Date()).getTime()+'"}';
            	   
            	    $.ajax({
                        type: "GET",
                        url: encodeURI("manageEmployees.do?jsonstr="+jsonReq),
                        error: function() {
                            //alert("FAILURE !");
                          }
                    }).done(function(response) {
                    	d.resolve({
            	            data: JSON.parse(response.employees),
            	            itemsCount: response.itemCount,
            	       });
            	    });
            	   return d.promise();
            	},
            	insertItem: function (item) {
            		var jsonReq = '{"type":"add","mid":"'+item.mid+'","name":"'+item.name+'","joinDate":"'+item.joinDate+'","emailId":"'+item.emailId+'","projId":"'+item.project.id+'"}';
            	    $.ajax({
                        type: "POST",
                        url: encodeURI("addEmployeeAjx.do"),
                        data: "jsonstr="+jsonReq,
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
                    		alert("Error Occured- Could not add employee. Please try again later.");
                    	}
                    	
                    	
                    });
                },
                updateItem: function (item) {
                	var jsonReq = '{"type":"update","mid":"'+item.mid+'","name":"'+item.name+'","joinDate":"'+item.joinDate+'","emailId":"'+item.emailId+'","projId":"'+item.project.id+'"}';
                    $.ajax({
                        type: "POST",
                        url: encodeURI("addEmployeeAjx.do"),
                        data: "jsonstr="+jsonReq,
                        beforeSend: function(xhr){
                            xhr.setRequestHeader(header, token);
                        },
                        error: function() {
                            //alert("FAILURE !");
                          }
                    }).done(function(){
                    	if(response.results=='success'){
                    		$("#jsGrid").jsGrid("loadData");
                    	}else{
                    		alert("Error Occured- Could not update employee. Please try again later.");
                    	}
                    });
                },
                
                deleteItem: function (item) {
                	var jsonReq = '{"type":"delete","mid":"'+item.mid+'"}';
                    $.ajax({
                        type: "POST",
                        url: encodeURI("addEmployeeAjx.do"),
                        data: "jsonstr="+jsonReq,
                        beforeSend: function(xhr){
                            xhr.setRequestHeader(header, token);
                        },
                        error: function () {
                        	 //alert("FAILURE !");
                        }
                    }).done(function(response){
                    	if(response.results=='success'){
                    		$("#jsGrid").jsGrid("loadData");
                    	}else{
                    		alert("Error Occured- Cannot delete employee.");
                    	}
                    });
                }
            },
            
            fields: [
                {name: "mid", title: "Id", type: "text", width: 40},
                {name: "name", title: "Employee Name", type: "text", width: 60, order:"asc"},
                {name: "joinDate", title: "Join Date", type: "text",  width: 50, align: "center", sorting:false},
                {name: "emailId", title: "Email", type: "text",  width: 60,sorting:false},                
                { name: "project.id", title: "Project", width: 60 ,type: "select", items: JSON.parse(projList.projects), valueField: "id", textField: "name" },
                {type: "control"}
            ]
            
        });
        
    });
});
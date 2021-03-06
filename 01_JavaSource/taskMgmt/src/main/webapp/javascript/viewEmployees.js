var header = $("meta[name='_csrf_header']").attr("content");
var token = $("meta[name='_csrf']").attr("content");
                	

$(function () {
	
	var jsonReqObj = new Object();
	jsonReqObj.type = "projects";
	
    $.ajax({
        type: "GET",
        url: encodeURI("manageEmployees.do?jsonstr="+JSON.stringify(jsonReqObj))
    }).done(function(projList) {

	//Date field script
        var MyDateField = function(config) {
            jsGrid.Field.call(this, config);
        };
     
        MyDateField.prototype = new jsGrid.Field({
            sorter: function(date1, date2) {
                return new Date(date1) - new Date(date2);
            },
     
            itemTemplate: function(value) {
            	var date = new Date(value);
            	//console.log($.datepicker.formatDate("dd-mm-yy", date));
            	return ($.datepicker.formatDate("dd-MM-yy", date));
            	
            },
     
            insertTemplate: function(value) {
                return this._insertPicker = $("<input>").datepicker({ defaultDate: new Date()});
            },
     
            editTemplate: function(value) {
                return this._editPicker = $("<input>").datepicker().datepicker("setDate", new Date(value));
            },
     
            insertValue: function() {
                return this._insertPicker.datepicker("getDate");
            },
     
            editValue: function() {
                return this._editPicker.datepicker("getDate");
            }
        });
     
        jsGrid.fields.myDateField = MyDateField;	
    //
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
            		jsonReqObj = new Object();
            		jsonReqObj.type="view";
            		jsonReqObj.mid=filter.mid;
            		jsonReqObj.name=filter.name;
            		jsonReqObj.emailId=filter.emailId;
            		jsonReqObj.projId=filter.project != undefined ? filter.project.id: 0;	
            		
            	    $.ajax({
                        type: "GET",
                        url: encodeURI("manageEmployees.do?jsonstr="+JSON.stringify(jsonReqObj)),
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
            		jsonReqObj = new Object();
            		jsonReqObj.type="add";
            		jsonReqObj.mid=item.mid;
            		jsonReqObj.name=item.name;
            		jsonReqObj.joinDate=($.datepicker.formatDate("dd-mm-yy", item.joinDate));
            		jsonReqObj.emailId=item.emailId;
            		jsonReqObj.projId=item.project != undefined ? item.project.id: 0;
            		
            		$.ajax({
                        type: "POST",
                        url: encodeURI("addEmployeeAjx.do"),
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
                    		alert("Error Occured- Could not add employee. Please try again later.");
                    	}
                    	
                    	
                    });
                },
                
                updateItem: function (item) {
                	jsonReqObj = new Object();
            		jsonReqObj.type="update";
            		jsonReqObj.mid=item.mid;
            		jsonReqObj.name=item.name;
            		jsonReqObj.joinDate=($.datepicker.formatDate("dd-mm-yy", item.joinDate));
            		jsonReqObj.emailId=item.emailId;
            		jsonReqObj.projId=item.project != undefined ? item.project.id: 0;
                    $.ajax({
                        type: "POST",
                        url: encodeURI("addEmployeeAjx.do"),
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
                    		alert("Error Occured- Could not update employee. Please try again later.");
                    	}
                    });
                },
                
                deleteItem: function (item) {
                	jsonReqObj = new Object();
            		jsonReqObj.type="delete";
            		jsonReqObj.mid=item.mid;
                    $.ajax({
                        type: "POST",
                        url: encodeURI("addEmployeeAjx.do"),
                        data: "jsonstr="+JSON.stringify(jsonReqObj),
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
                {name: "mid", title: "Id", type: "text", width: 40, editing: false},
                {name: "name", title: "Employee Name", type: "text", width: 60, order:"asc"},
                {name: "joinDate", title: "Join Date", type: "myDateField",  width: 50, align: "center"},
                {name: "emailId", title: "Email", type: "text",  width: 60,sorting:false},                
                { name: "project.id", title: "Project", width: 60 ,type: "select", items: JSON.parse(projList.projects), valueField: "id", textField: "name" },
                {type: "control"}
            ]
            
        });
        
    });
});
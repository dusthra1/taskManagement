var header = $("meta[name='_csrf_header']").attr("content");
var token = $("meta[name='_csrf']").attr("content");
                	

$(function () {
	
        $("#jsGrid").jsGrid({
        	height: 500,
            width: "50%",
            
            filtering: false,
            inserting: false,
            editing: false,
            sorting: false,
            paging: true,
            autoload: true,
           
            pageSize: 2,
            pageButtonCount: 5,
            pageLoading: true,
            
            deleteConfirm: "File will be deleted from the system. Do you want to continue?",   
           
            controller: {           	
            	loadData: function(filter) {  
            		//alert(filter.sortField);
            		//alert(filter.sortOrder);
            		//alert(filter.name);      		
            		
            		var d = $.Deferred();
            		var jsonReq = '{"type":"view","dummy=":"'+(new Date()).getTime()+'"}';
            	   
            	    $.ajax({
                        type: "GET",
                        url: encodeURI("viewFiles.do?jsonstr="+jsonReq),
                        error: function() {
                            //alert("FAILURE !");
                          }
                    }).done(function(response) {
                    	d.resolve({
            	            data: JSON.parse(response.fileList),
            	            itemsCount: response.itemCount
            	       });
            	    });
            	   return d.promise();
            	},

                deleteItem: function (item) {
                	var jsonReq = '{"type":"delete","id":"'+item.id+'","dummy=":"'+(new Date()).getTime()+'"}';
                    $.ajax({
                        type: "GET",
                        url: encodeURI("viewFiles.do?jsonstr="+jsonReq),
                        error: function() {
                            //alert("FAILURE !");
                          }
                    }).done(function(){
                    	$("#jsGrid").jsGrid("loadData");
                    });
                }
            },
            
            fields: [
                {name: "id", 	   title: "Id", 	   type: "text", width: 20, sorting:false},
                {name: "fileName", title: "File Name", type: "text", width: 60, sorting:false},
                
                {name: "id", itemTemplate: function(itemVal) {
                				return $("<a>").attr("href", "downloadFile.do?fileId="+itemVal).attr("target","_blank").text("Download");
                			}, title: "Download", type: "text", width: 30,sorting:false},
                
                {
                    type: "control",
                    editButton: false,
                    deleteButton: true
                    
                }
           
            ]
            
        });      
});
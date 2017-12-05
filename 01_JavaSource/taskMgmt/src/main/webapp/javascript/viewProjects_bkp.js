$(function () {
	
	var jsonReq = '{"type":"view","dummy=":"'+(new Date()).getTime()+'"}';

    $.ajax({
        type: "GET",
        url: "manageProject.do?jsonstr="+jsonReq
        	
    }).done(function (event) { 
    	
        $("#jsGrid").jsGrid({
            height: "100%",
            width: "80%",
            inserting: true,
            editing: true,
            sorting: true,
            paging: true,
            filtering: true,
            autoload: false,
            pageSize: 10,  
            pageLoading: true,
            data: JSON.parse(event.projects),            
           
            controller: {
            	
            	insertItem: function (item) {
            	    jsonReq = '{"type":"add","id":"'+item.id+'","name":"'+item.name+'","desc":"'+item.description+'","dummy=":"'+(new Date()).getTime()+'"}';
                    return $.ajax({
                        type: "POST",
                        url: "addProject.do?jsonstr="+jsonReq,
                        data:item
                    });
                },
                updateItem: function (item) {
                	jsonReq = '{"type":"update","id":"'+item.id+'","name":"'+item.name+'","desc":"'+item.description+'","dummy=":"'+(new Date()).getTime()+'"}';
                    return $.ajax({
                        type: "POST",
                        url: "addProject.do?jsonstr="+jsonReq,
                        data: item
                    });
                },
                deleteItem: function (item) {
                	jsonReq = '{"type":"delete","id":"'+item.id+'","dummy=":"'+(new Date()).getTime()+'"}';
                     $.ajax({
                        type: "GET",
                        url: "manageProject.do?jsonstr="+jsonReq,
                        data: item
                    });
                }
            },
           
            fields: [
                {name: "id", title: "Id", type: "text",visible: false, width: 0},
                {name: "name", title: "Project Name", type: "text", width: 60,sorting:true},
                {name: "description", title: "Description", type: "text",  width: 50,sorting:true},
                {type: "control"}
            ]
            
        });

    });
});
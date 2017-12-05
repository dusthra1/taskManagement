<!doctype html>
<html lang="en">
<head>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Task Details</title>
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $( function() { 
	  
    var availableTags = ${projectsList};
    
    $( "#project" ).autocomplete({
    	 minLength:3, 
		 noCache: true, //default is false, set to true to disable caching
		 maxHeight:400,
		 width:300,
		 zIndex: 9999,
		 deferRequestBy: 0,
		 
        source: availableTags,
        
        select: function( event, ui ) {
		    var selectedText = ui.item.value;
		    var name="";
		    var prjId="";
		    var textArray = selectedText.split(':');
		    if(textArray.length > 0) {
		    	 name = textArray[0];
				 ui.item.value = name;
				 prjId = textArray[1];
		    }
		    loadTasks(prjId);
		}
        
      });
    
    });
  
  function loadTasks(prjId){
	 
	  jsonReq = '{"projId":"'+prjId+'","dummy=":"'+(new Date()).getTime()+'"}';
	  $.ajax({
          type: "GET",
          url: "getTasksHTML.do?jsonstr="+jsonReq                       
      }).done(function(response){
    	  $('#showContent').empty();
    	  $('#showContent').html(response);
      });
	 
  }
  </script>
</head>
<body>
 
 <div>
  <label>Projects: </label>
  <input id="project">
 </div>
 
 <div id="showContent"></div>
 
 

 
 
</body>
</html>
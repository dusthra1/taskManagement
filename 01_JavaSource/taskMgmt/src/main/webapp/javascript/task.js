function loadProjEmp(projId) {	
	
var jsonReqObj = new Object();
jsonReqObj.projId=projId;
	
$.ajax({
        type: 'GET',
		url: encodeURI("getProjEmployees.do?jsonstr="+JSON.stringify(jsonReqObj)),
		dataType: "json",
		
		success: function(event) {			
			
			 $('#empId').empty()
			  var newOption = $('<option>');
              newOption.attr('value', "0").text("----SELECT-----");
              $('#empId').append(newOption);
              
              var empLst = JSON.parse(event.EMP_LIST);
              $.each(empLst, function(i, emp) {
				            	 
            	// Create New Option.
            	 var newOption = $('<option>');
            	 newOption.attr('value', emp.mid).text(emp.name+" - "+emp.mid);

            	// Append that to the DropDownList.
            	 $('#empId').append(newOption);
            

            })
			
		},
		
        error: function()
        {
         //alert('error occured');
        }
	});	
}
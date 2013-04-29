/**  
author:Adrian Pomilio
purpose: this file is specific to the LOA application.
dependancy: jQuery 1.6.1 library
**/


$(document).ready(function(){
	$("#breadcrumbs li a").after(" > ");
	$("label.mandatory").prepend("<span class='requiredSymbol'>* </span>");

	// mega menu
	rail.base.megaHoverOver();
	rail.base.megaHoverOut();
	//Set custom configurations for mega menu
	rail.base.megaConfig();	
	//Init menu
	rail.base.applicationNavMenu();
	// table config
    //rail.base.selectableTable("sortableTable", false);
    rail.base.tableSorterUtil("sortableTable");
	//accordion init - for advanced search section
	//rail.base.accordion("advanced");
	//check buttons for btn class, add it if it's not there
	rail.base.checkButtons();
	// i had to override the datepicker from standard.js because it had an image location hard coded
	$( ".dpField" ).datepicker({
		showOn: "button",
		buttonImage: contextPath + "/r/images/calendar.gif",
		buttonImageOnly: false
	});

});
$(document).ready(function(){
    $('#filterText')
      .data('timeout', null)
      .keyup(function(){
          clearTimeout(jQuery(this).data('timeout'));
          $(this).data('timeout', setTimeout(refreshFilter, 1000));
      });
 });

function goToPage(page) {
	window.location = contextPath + page;
}


//newFunction
function submitForm(form, forward)
{
	form.action.value = forward;
	form.submit();
}

//function to submit form via ajax
//expects an html snippet as response
function ajaxSubmitForm(form, forward) {
	form.action.value = forward;
	$.post(form.getAttributeNode("action").value, $(form).serialize(), 
	function onResult(data){
		$('#results').empty();
		$('#results').append(data);
		$("input").attr('disabled', '');
		$("#searchMessage").empty();
	});
	$("input").attr('disabled', 'disabled');
	$("#searchMessage").html('<div class="working"><img src="<%=request.getContextPath() %>/r/legacy/images/iconWorking.gif"  />&nbsp;&nbsp;Working</div>');
	
}
// legacy from here downw
function cancelForm(form, forward) {
	form.reset();
	submitForm(form, forward);
}
function submitForSort(sortAttribute) {
	theForm = getContentForm();
	// 	set the sort data
	theForm.sortAttributeName.value = sortAttribute;
	submitForm(theForm, "sortColumn");
}
function getContentForm(){
	return window.document.forms[0];
}
function showMe()
{
	elId = arguments[0] != undefined ? arguments[0].toString() : '';
	opWin = arguments[1] != undefined ? arguments[1].toString() : '';
	var d = (opWin === 'opener') ? opener.document : document;
	$("#" + elId).toggle(true);
}

function hideMe() {
	elId = arguments[0] != undefined ? arguments[0].toString() : '';
	opWin = arguments[1] != undefined ? arguments[1].toString() : '';
	var d = (opWin === 'opener') ? opener.document : document;
	$("#" + elId).toggle(false);
}	
function apply(d, elementId, f) {
	var e = d.getElementById(elementId);
	if (e === null) {
		alert("unable to find " + elementId);
	} else {
		f(e);
	}
}

function showMeInline() {
	elId = arguments[0] != undefined ? arguments[0].toString() : '';
	opWin = arguments[1] != undefined ? arguments[1].toString() : '';
	var d = (opWin === 'opener') ? opener.document : document;
	apply(d, elId, function(e) {
		with(e.style) {
			display='inline';
			visibility = 'visible';
		}
	});
}	

// used by userProfile.jsp -- require certain fields based on country
function setMandatoryFieldsForMenu(selectList) {
	//each Array arg is like {"US", new array("state", "zip")}
	   //clear all mandatory fields for all items in array. 
	   //TODO this code is somewhat inefficient.
	   var i
	   for (i=1; i<setMandatoryFieldsForMenu.arguments.length; i++) 
	   {
	      ary = setMandatoryFieldsForMenu.arguments[i];
	      aryFields = ary[1]; 
	      makeFieldsNonMandatory(aryFields);
	   }
	             
	   for (i=1; i<setMandatoryFieldsForMenu.arguments.length; i++) 
	   {
	      ary = setMandatoryFieldsForMenu.arguments[i];
	      optionValue = ary[0];
	      aryFields = ary[1]; 
	      if (optionValue==selectList.value) 
	      {
	         makeFieldsMandatory(aryFields);   
	      }
	   }
	}

	// For each string in the arrayOfFieldIDAttributes, look up the element by the ID 
	// attribute and set the class property to "" (make it non-mandatory).
	function makeFieldsNonMandatory(arrayOfFieldIDAttributes) 
	{
	   for (var j=0; j<arrayOfFieldIDAttributes.length; j++) 
	   {
	      makeFieldNonMandatory(arrayOfFieldIDAttributes[j])
	   }
	}    

	// Look up the field by its ID attribute and change the
	// class property to "". 
	function makeFieldNonMandatory(fieldIDAttribute) 
	{
	   obj = getObj(fieldIDAttribute);
	   obj.className=""
	}  
	    
	// Look up the field by its ID attribute and change the
	// class property to "mandatoryLabel". 
	function makeFieldsMandatory(arrayOfFields)
	{
	  for (var i=0; i<arrayOfFields.length; i++) 
	  {
	     obj = getObj(arrayOfFields[i]);
	     obj.className="mandatoryLabel"
	  }
	}

	// Return the object with the ID attribute = oid. 
	function getObj(oid) 
	{
	  if (document.all && !document.getElementById) 
	  {
	    return document.all(oid)
	  } 
	  else 
	  {
	    return document.getElementById(oid)
	  }
	}

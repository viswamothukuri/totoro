// main.js

// newFunction
function submitForm(form, forward)
{
	form.action.value = forward;
	form.submit();
}

// function to submit form via ajax
// expects an html snippet as response
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

//Reset the form and submit
function cancelForm(form, forward)
{
	form.reset();
	submitForm(form, forward);
}


/**
 * Sort the form
 */
function submitForSort(sortAttribute)
{
  theForm = getContentForm();
  // set the sort data
  theForm.sortAttributeName.value = sortAttribute;

  submitForm(theForm, "sortColumn");
}
function getContentForm()
{
	return window.document.forms[0];
}

/***************************************************************
 * Trim function to remove whitespace and tabs from the 
 * beginning and end of a string
 ***************************************************************/
function trimString(s)
{
	if ( s != null )
	{
		s = s.replace(/^[\s]+/g,"");
		s = s.replace(/[\s]+$/g,"");
	}
	return s;
}
/******************************************************
 * Function to execute on loading of a page
 ******************************************************/
function onloadExecute(){
	// do nothing. to be implemented in individual jsps as required.
}


function isRadioButtonSelected( form )
{
	var isSelected = false ;
	for ( var i = 0; i < form.elements.length; i++ )
	{
		if ( form.elements[ i ].type == "radio" )
		{
			if ( form.elements[ i ].checked )
			{
				isSelected = true ;
				break ;
			}
		}
	}
	return isSelected ;	
}

function isCheckboxSelected( form )
{
	var isSelected = false ;
	for ( var i = 0; i < form.elements.length; i++ )
	{
		if ( form.elements[ i ].type == "checkbox" )
		{
			if ( form.elements[ i ].checked )
			{
				isSelected = true ;
				break ;
			}
		}
	}
	return isSelected ;	
}
function formatFloat(expr) {
	var inputValue = parseFloat(expr.value);
	if(!isNaN(inputValue)){
		if(expr.value.indexOf(".") == -1){
			expr.value = expr.value +".0";
		}
	}
}


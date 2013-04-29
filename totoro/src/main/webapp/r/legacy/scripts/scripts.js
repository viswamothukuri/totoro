	function showMe()
	{
		elId = arguments[0] != undefined ? arguments[0].toString() : '';
		opWin = arguments[1] != undefined ? arguments[1].toString() : '';
		var d = (opWin === 'opener') ? opener.document : document;
		apply(d, elId, function(e) {
			with(e.style) {
				display='block';
				visibility = 'visible';
			}
		});
	}

	function hideMe()
	{
		elId = arguments[0] != undefined ? arguments[0].toString() : '';
		opWin = arguments[1] != undefined ? arguments[1].toString() : '';
		var d = (opWin === 'opener') ? opener.document : document;
		apply(d, elId, function(e) {
			with(e.style) {
				display='none';
				visibility = 'hidden';
			}
		});
	}	
	function apply(d, elementId, f) {
		var e = d.getElementById(elementId);
		if (e === null) {
			alert("unable to find " + elementId);
		} else {
			f(e);
		}
	}
	
	function showMeInline()
	{
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

	function hideWorkingBar()
	{    
	    if (!document.getElementById)
	            return;
	    
	    document.getElementById("Working").style.display="none";
	}
	
	function showWorkingBar()
	{    
	   if (!document.getElementById)
	        return;
	
	    document.getElementById("Working").style.display="block";
	}

/****************** MANDATORY FIELD MANIPULATION FUNCTIONS ************************************/                    
                    
/*
 * Accepts a menu and a variable list of arrays that describe the
 * mandatory fields required for any menu choice that may have
 * mandatory fields. Each array contains as its first element the
 * string name of the menu's option value followed by another 
 * array of the mandatory fields for that option value. The function
 * first clears all fields referenced in all array arguments passed
 * in, and then sets the fields depending on the current menu choice. 
 *
 * An example call is listed below. Note, that the Denmark array is 
 * not needed if none of the indicated fields will be marked mandatory.
 * 
 * onchange="javascript:setMandatoryFieldsForMenu(this, new Array('US', new Array('state','zip')), new Array('DK', new Array()))"
 *
 * Any field used here must have an ID tag set to the field name used
 * in the Array of fields.
 *
 */                    
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


 
/******************************************************************************
Form Utilities
2004 T. Hill - Railinc Corporation

Dependencies:  n/a
******************************************************************************/

function clearForm() // [, frm ]
{
	var frm = ( arguments[0] != undefined ) ? arguments[0] : document.forms[0];
	var el;
	var type;

	for ( var i = 0; i < frm.length; i++ )
	{
		el = frm[i];

		if ( el.getAttributeNode( "type" ) )
		{
			type = el.getAttributeNode( "type" ).value;
		}

		if ( ( type == "checkbox" ) ||
			  ( type == "radio" ) )
		{
			el.checked = false;
		} else 
		if ( ( type == "select" ) ||
			  ( type == "text" ) ||
			  ( type == "textarea" ) ) 
		{
			el.value = "";
		}
	}
}


//// Useful for checking if inputs have been dirtied -- capture initially
//// and check against saved values before submitting.
//function getFormState() // [, frm ]
//{
//	var frm = ( arguments[0] != undefined ) ? arguments[0] : document.forms[0];
//	var el;
//	var type;
//	var valArr = new Array();
//
//	for ( var i = 0; i < frm.length; i++ )
//	{
//		el = frm[i];
//		
//		if ( el.getAttributeNode( "type" ) )
//		{
//			type = el.getAttributeNode( "type" ).value;
//		}
//
//		if ( ( type == "checkbox" ) ||
//			  ( type == "radio" ) )
//		{
//			valArr.push( i + "=" + el.checked );
//		} else 
//		if ( ( type == "select" ) ||
//			  ( type == "text" ) ||
//			  ( type == "textarea" ) ) 
//		{
//			valArr.push( i + "=" + el.value );
//		}
//	}	
//	
//	return valArr.join( ",");
//}

// Useful for checking if inputs have been dirtied -- capture initially
// and check against saved values before submitting.
// arg1: (Optional) A form.  Defaults to document.forms[0].
// arg2: (Optional, required if arg1) Comma seperated list of form element 
//       names within the form specified by arg1 (or the default) to be excluded
//       from the state.
function getFormState() // [, frm, excludeList ]
{
	var frm = ( arguments[0] != undefined ) ? arguments[0] : document.forms[0];
	var exclArr = ( arguments[1] != undefined ) ? arguments[1].toString().split( "," ) : new Array();	
	var shouldExclude;
	var el;
	var type;
	var valArr = new Array();

	for ( var i = 0; i < frm.length; i++ )
	{
		el = frm[i];
		shouldExclude = false;
		
		// Consider only elements with a name
		if ( el.name )
		{	
			for ( var j = 0; j < exclArr.length; j++ )
			{
				// Check to see if this is among the exclusion list if there are any to exclude.
				if ( ( el.name ) && ( frm[exclArr[j]] ) && ( el.name == frm[exclArr[j]].name ) )
				{		
					shouldExclude = true;
					break;			
				}
			}

			if ( !shouldExclude )
			{
				if ( el.getAttributeNode( "type" ) )
				{
					type = el.getAttributeNode( "type" ).value;
				}

				if ( ( type == "checkbox" ) ||
					  ( type == "radio" ) )
				{
					valArr.push( el.name + "=" + el.checked );
				} else if ( ( type == "hidden" )||
				            ( type == "select" ) ||
								( type == "text" ) ||
								( type == "textarea" ) ) 
				{
					valArr.push( el.name + "=" + el.value );
				}
			}
		}
	}	
	
	return valArr.join( ",");
}

function setDisabled( el, shouldDisable )
{
	var shouldChangeBG = true;	
	
	// don't change background if radio or checkbox
	if ( el.getAttributeNode( "type" ) )
	{
		type = el.getAttributeNode( "type" ).value;		
		shouldChangeBG = ( !( ( type == "checkbox" ) || ( type == "radio" ) ) );
	}

	if ( shouldDisable )
	{
		el.disabled = true;
		if ( shouldChangeBG )
		{
			el.style.backgroundColor = "#eeeeee";
		}
	} else
	{
		el.disabled = false;
		if ( shouldChangeBG )
		{
			el.style.backgroundColor = "#ffffff";
		}
	}
}

// Apply CSS to input elements
// vertical-align: middle for all radio and checkbox
// background-color set for all but radio and checkbox
function formatInputs()
{
	var types = [ "input", "textarea" ];
	var el;
	var type;
	var disabled;

	for ( ti = 0; ti < types.length; ti++ )
	{
		el = document.getElementsByTagName( types[ti] );

		for ( i = 0; i < el.length; i++ )
		{
			type = null
			disabled = null;

			// input type
			if ( el[i].getAttributeNode( "type" ) )
			{
				type = el[i].getAttributeNode( "type" ).value;
			}

			if ( ( type == "checkbox" ) ||
				  ( type == "button" ) ||
				  ( type == "submit" ) ||
				  ( type == "reset" ) )
			{
				; //el[i].style.verticalAlign = "middle";
			} else if ( type == "radio" )
			{
				//el[i].parentNode.style.border = "1px solid #eeeeee";
			} else
			{			
				el[i].style.backgroundColor = "#fff";
				el[i].style.border = "1px solid #666";	
				el[i].style.padding = "1px 2px 1px 2px";
			}

			// disabled inputs
			if ( ( el[i].getAttributeNode( "disabled" ) ) &&
				  ( ( el[i].getAttributeNode( "disabled" ).value == "true" /* ie */ ) ||
					 ( el[i].getAttributeNode( "disabled" ).value == "disabled" /* gecko */ ) ) )
			{
				el[i].style.backgroundColor = "#eee";			
			}

		}
	}
}

// Pass a form and a regex name to match element on.
// Can optionally pass an element type
function getFormElementsByNameArray( frm, regex ) // [, type ]
{
	var elArr = new Array();
	var type = ( arguments[2] != undefined ) ? arguments[2].toString() : undefined;
	
	for ( var i = 0; i < frm.length; i++ )
	{
		var nameAttr = frm[i].getAttributeNode( "name" );
		
		if ( ( nameAttr ) && 
			  ( nameAttr.value.length > 0  ) )
		{
			var re = RegExp( regex );
			
			if ( nameAttr.value.toString().search( re ) != -1 )					
			{				
				if ( type == undefined )
				{
					elArr.push( frm[i] );
				} else
				{
					var typeAttr = frm[i].getAttributeNode( "type" );
					
					if ( ( typeAttr ) &&
					     ( typeAttr.toLowerCase() == type.toLowerCase() ) )
					{
						elArr.push( frm[i] );
					}
				}
			}
		}
	}
	
	return elArr;
}

// Pass the name of the element.  
// Return array of parse index found matching all "[<num>]" occurences.
function getElementIndexesArray( name ) 
{
	var idxArr = new Array();
	var str = name;
	var idx = -1;
	
	do
	{
		var matches = str.match( /(^.*\[)(\d+)\].*$/ );
		if ( ( matches ) && ( matches.length == 3 ) )
		{
			str = matches[1];
			idx = parseInt( matches[2] );
			idxArr.push( idx );		
		}
	} while ( matches );
	
	return idxArr;
}

// Pass the name of the element.
// Can pass the nth occurance to specify which index to return if
// the name is greater than one-dimensional
function getElementIndex( name ) // [, nth_occurance ]
{
	var nth = ( arguments[1] != undefined ) ? arguments[1] : 1;
	var idxArr = getElementIndexesArray( name );
	var idx = -1;
	
	if ( ( idxArr.length > 0 ) && ( idxArr.length <= nth ) )
	{
		idx = idxArr[nth - 1];
	}
	
	return idx;
}

function getLastElementIndex( frm, regex ) // [, nth_occurance ]
{
	// Default nth occurance to 1
	var nth = ( arguments[2] != undefined ) ? arguments[2] : 1;
	var elArr = getFormElementsByNameArray( frm, regex );
	var idx;
	var nextIdx = -1;
	var nameAttr;

	for ( var i = 0; i < elArr.length; i++ )
	{
		nameAttr = elArr[i].getAttributeNode( "name" ).value;
		idx = getElementIndex( nameAttr, nth );	
		nextIdx = ( ( nextIdx == undefined ) || ( idx > nextIdx ) ) ? idx : nextIdx;
	}

	return nextIdx;
}

// Pass a select element; returns an array of selected options
function getSelectedOptionsArray( sel )
{
	var selectedOptsArr = new Array();

	if ( sel.selectedIndex >= 0 )
	{		
		for ( var i = sel.selectedIndex; i < sel.length; i ++ )
		{
			if ( sel.options[i].selected )
			{
				selectedOptsArr.push( sel.options[i].value );
			}
		}
	}

	return selectedOptsArr;
}


// Pass a select element; returns a list of selected options
// Can pass delimeter as arg2 (default is comma)
function getSelectedOptionsList( sel ) // [, delim ]
{
	var selectedOpts = getSelectedOptionsArray( sel );

	if ( selectedOpts.length == 0 )
	{
		return "";
	} else
	{
		// Default delim comma ","
		var delim = ( arguments[1] != undefined ) ? arguments[1].toString() : ",";
		return selectedOpts.join( delim );
	}
}	

// Pass a select element and list of values to set as selected.
// Passing true or false as arg2 will set or unset all options, respectively.
// If arg2 is true, and if arg2 is passed as true, then special values
// will not be selected (special values resolve to numbers less than 0).
// If arg2 is a string expressing a list of values, arg3 will be used as the 
// list delimeter if passed.
function setSelectedOptions( sel ) // [, selList|true|false, delim|true ]
{
	var obj = ( arguments[1] != undefined ) ? ( typeof( arguments[1] ) == "boolean" ) ? arguments[1] : ( typeof( arguments[1] ) == "string" ) ? arguments[1].toString() : "" : "";
	var opts = sel.options;
	var isMultiple = ( sel.getAttribute( "multiple" ) != false && sel.getAttribute( "multiple" ) != null  ); // gecko: false; ie: null	
	var hasEmpty = false;

	// Set all selected (true) - the first if not multiple; all if multiple
	// or - set all unselected (false) 
	if ( typeof( obj ) == "boolean" )
	{
		var excludeSpecial = ( ( obj ) && ( arguments[2] != undefined ) && ( typeof( arguments[2] ) == "boolean" ) ) ? arguments[2] : false;
	
		if ( isMultiple )
		{
			for ( var i = 0; i < opts.length; i++ )
			{		
				// If unselecting all (false) then unselect this option			
				// If selecting all (true) then only select options with non-special values
				// (non-special value being number less than 0)				
				if ( ( !obj ) || ( ( obj ) && ( !excludeSpecial ) )  || ( ( obj ) && ( excludeSpecial ) && ( ( !parseInt( opts[i].value ) ) || ( parseInt( opts[i].value ) > 0 ) ) ) )
				{
					opts[i].selected = obj;
				}
			}
		} else
		{
			opts[0].selected = obj;
		}
	}
	
	if ( typeof( obj ) == "string" )
	{
		// Default delim comma ","
		var delim = ( arguments[2] != undefined ) ? arguments[2].toString() : ",";	
		var selArr = obj.split( delim );

		for ( var i = 0; i < opts.length; i++ )
		{
			opts[i].selected = false;
			hasEmpty = ( ( !hasEmpty ) && ( opts[i].value == "" ) );

			if ( obj != "" )
			{
				for ( var j = 0; j < selArr.length; j++ )
				{
					if ( opts[i].value == selArr[j] )
					{			
						opts[i].selected = true;
						break;
					}
				}
			}
		}
		
		if ( ( obj == "" ) && ( !isMultiple ) && ( !hasEmpty ) )
		{
			opts[0].selected = true;
		}		
	}
	
}	

// Pass a radio group; returns selected value
function getRadioGroupValue( rg )
{
	var val = "";

	// handle single option
	if ( ( rg ) &&
		  ( rg.value ) )
	{
		if( rg.checked == true )
		{
			val = rg.value;
		}
	// handle multiple options
	} else
	{
		for ( var i = 0; i < rg.length; i++ )
		{
			if ( rg[i].checked == true )
			{
				val = rg[i].value;
				break;
			}
		}
	}

	return val;
}

// Pass a radio group, optionally pass a value as arg2
// Will set radio button with val as checked.
// Passing no arg2 will uncheck all radio button in group
function setRadioGroupValue( rg ) // [, val ]
{	
	var val = ( arguments[1] != undefined ) ? arguments[1].toString() : null;

	// null val unchecks all radio buttons
	if ( val == null )
	{
		for ( i = 0; i < rg.length; i++ )
		{			
			rg[i].checked = false;
		}
	} else
	{
		// pass a value for its radio button to be checked

		// handle single
		if ( ( rg ) &&
			  ( rg.value == val ) )
		{
			rg.checked = true;			
		// handle multiple
		} else
		{
			for ( var i = 0; i < rg.length; i++ )
			{		
				if ( rg[i].value == val )
				{
					rg[i].checked = true;
					break;
				}
			}
		}
	}
}

// Pass a radio group; returns selected radio button
function getSelectedRadioButton( rg )
{
	var rb = null;

	// handle single option
	if ( ( rg ) &&
		  ( rg.value ) )
	{
		if( rg.checked == true )
		{
			rb = rg
		}
	// handle multiple options
	} else
	{
		for ( var i = 0; i < rg.length; i++ )
		{
			if ( rg[i].checked == true )
			{
				rb = rg[i];
				break;
			}
		}
	}

	return rb;
}

// Toggles all checkboxes whic have a name matching the passed regex.
// Optional arg3 specifies whether to include disabled checkboxes in 
// selection/unselection (false by default).
function toggleAllCheckboxes( cb, cbgStr ) // [, includeDisabled ]
{				
	var includeDisabled = ( arguments[2] != undefined ) ? arguments[2] : false;
	var cbgArr = getCheckboxesArray( cbgStr );

	for( var i = 0; i < cbgArr.length; i++ )
	{
		var cbi = cbgArr[i];
		
		if ( includeDisabled || ( ( !includeDisabled ) && ( !cbi.disabled ) ) )
		{
			cbi.checked = cb.checked;
		}
	}
}		



// Struts-friendly
// Pass the regex of the checkbox group name to match on. 
// example:  name="foo[2].selected" -> pass "foo" to match
function getCheckboxesArray( cbgStr )
{
	var cbgArr = new Array();
	var inputs = document.getElementsByTagName( "input" );

	// get cbg given the Struts way of handling indexed checkboxes
	for ( var i = 0; i < inputs.length; i++ )
	{
		var typeAttr = inputs[i].getAttributeNode( "type" );

		if ( ( typeAttr ) && 
			  ( typeAttr.value == "checkbox" ) )
		{  
			var nameAttr = inputs[i].getAttributeNode( "name" );

			if ( ( nameAttr ) && 
				  ( nameAttr.value.length > 0  ) )
			{
				var re = RegExp( cbgStr );

				if ( nameAttr.value.toString().search( re ) != -1 )					
				{
					cbgArr.push( inputs[i] );
				}
			}
		}
	}

	return cbgArr;
}	

// Struts-friendly
// Pass the regex of the checkbox group name to match on. 
// example:  name="foo[2].selected" -> pass "foo" to match
function getSelectedCheckboxesArray( cbgStr )
{
	var cbgArr = getCheckboxesArray( cbgStr )
	var selectedArr = new Array();

	if ( cbgArr )
	{
		for ( var i = 0; i < cbgArr.length; i++ )
		{
			if ( cbgArr[i].checked == true )
			{
				selectedArr.push( cbgArr[i] );				
			}
		}
	}

	return selectedArr;
}	

// Struts-friendly
// arg1:  Regex of the checkbox group name to match on. 
// example:  name="foo[2].selected" -> pass "foo" to match
// arg2:  Comparison operator (">", "<=", etc.)
// arg3:  Quantity to compare selected count by using the arg2 operator
// (optional) arg4:  Alert message displayed when comparason fails
// (optional) arg5:  Alert message dispalyed when the there are no selected checkboxes
function validateQuantityCheckboxSelection( cbgStr, operator, qty ) // [, msgInvalid, msgNA ]
{
	var selArr = getSelectedCheckboxesArray( cbgStr );	
	var msgInvalid = ( arguments[3] != undefined ) ? arguments[3].toString() : undefined;
	var msgNA = ( arguments[4] != undefined ) ? arguments[4].toString() : undefined;	

	if ( selArr )
	{
		if ( eval( "( selArr.length " + operator + " qty )" ) )
		{
			return true;		
		} else
		{
			if ( msgInvalid != undefined )
			{	
				alert( msgInvalid );
			}
			return false;
		}
	} else
	{
		if ( msgNA != undefined )
		{
			alert( msgNA );
		}
		return false;
	}
}

// Can be called on a textarea's onchange event to emulate
// a maxlength attribute which the textarea lacks natively.
// Treats newline characters as having length of 2 for portability.
function maxlength( ta, len )
{
	if ( ta.nodeName.toString().toLowerCase() == "textarea" )
	{
		if ( ta.value )
		{
			var reduction = 0;		
			var matches = ta.value.match( /\n/g );
			if ( matches && matches.length )
			{
				reduction = matches.length;
			}
			var adjLen = len - reduction
			if ( ta.value.length > adjLen ) 
			{
				ta.value = ta.value.substring( 0, adjLen - 1 );
			}
		}
	}

}

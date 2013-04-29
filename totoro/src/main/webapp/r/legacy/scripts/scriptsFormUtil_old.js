	/******************************************************************************
	Form Utilities
	2004 T. Hill - Railinc Corporation
	******************************************************************************/

	function clearForm( frm )
	{
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

	function setDisabled( el, shouldDisable )
	{
		if ( shouldDisable )
		{
			el.disabled = true;
			el.style.backgroundColor = "#eeeeee";
		} else
		{
			el.disabled = false;
			el.style.backgroundColor = "#ffffff";
		}
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
			for ( i = 0; i < rg.length; i++ )
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
	

	// Pass a checkbox group
	function getCheckboxGroupValueArray( cbg )
	{
		var checkedValArr = new Array();
	
		// handle single
		if ( ( cbg ) &&
			  ( cbg.value ) )
		{
			if( cbg.checked == true )
			{
				checkedValArr.push( cbg.value );
			}
		// handle multiple
		} else if ( ( cbg ) &&
						( cbg.length != undefined ) )
		{
			for ( var i = 0; i < cbg.length; i++ )
			{
				if ( cbg[i].checked == true )
				{
					checkedValArr.push( cbg[i].value );
				}
			}
		} 
		
		return checkedValArr;
	}

	// Pass a checkbox group; returns a list of selected values
	// Can pass delimeter as arg2 (default is comma)
	function getCheckboxGroupValueList( cbg ) // [, delim ]
	{
		var checkedValArr = getCheckboxGroupValueArray( cbg )
	
		if ( checkedValArr.length == 0 )
		{
			return "";
		} else
		{
			// Default delim comma ","
			var delim = ( arguments[1] != undefined ) ? arguments[1].toString() : ",";		
		
			return checkedValArr.join( delim );
		}
	}

	function setCheckboxes( cbg ) // [, valList ]
	{	
		var valArr = ( arguments[1] != undefined  ) ? arguments[1].toString().split( "," ) : null;		

		// if cbg has only one checkbox
		if ( cbg.type == "checkbox" )
		{
			if ( valArr )
			{
				for ( var i = 0; i < valArr.length; i++ )
				{
					if ( valArr[i] == cbg.value )
					{
						cbg.checked = true;
					}
				}
			} else
			{
				cbg.checked = true;
			}
		// if cbg has more than one checkbox			
		} else 
		{
			for ( var i = 0; i < cbg.length; i++ )
			{
				if ( valArr )
				{
					for ( var j = 0; j < valArr.length; j++ )
					{
						if ( valArr[j] == cbg[i].value )
						{
							cbg[i].checked = true;
						}
					}	
				} else
				{
					cbg.checked = true;
				}
			}
		}
	}
	
	function unsetCheckboxes( cbg ) // [, valList ]
	{	
		var valArr = ( arguments[1] != undefined  ) ? arguments[1].toString().split( "," ) : null;		
		
		// if cbg has only one checkbox
		if ( cbg.type == "checkbox" )
		{
			if ( valArr )
			{
				for ( var i = 0; i < valArr.length; i++ )
				{
					if ( valArr[i] == cbg.value )
					{
						cbg.checked = true;
					}
				}
			} else
			{
				cbg.checked = false;
			}
		// if cbg has more than one checkbox			
		} else 
		{
			for( var i = 0; i < cbg.length; i++ )
			{
				if ( valArr )
				{
					for ( var j = 0; j < valArr.length; j++ )
					{
						if ( valArr[j] == cbg[i].value )
						{
							cbg[i].checked = true;
						}
					}	
				} else
				{
					cbg.checked = false;
				}
			}
		}
	}

	function checkQuantitySelectedCheckboxes( cbg ) // [, qty ]
	{
		var qty = ( arguments[1] != undefined  ) ? arguments[1] : 1; // default to 1
		
		if ( !( cbg ) )
		{
			return false;
		}
		
		selArr = getCheckboxGroupValueArray( cbg );
		
		if ( selArr.length == qty )
		{
			return true;
		} else
		{
			return false;
		}	
	}
	
	function checkMinimumQuantitySelectedCheckboxes( cbg, qty )
	{
		if ( !( cbg ) )
		{
			return false;
		}
		
		selArr = getCheckboxGroupValueArray( cbg );
		
		if ( selArr.length > qty )
		{
			return true;
		} else
		{
			return false;
		}	
	}	

// TH 20050506 - For this app, use the struts-friendly version
//	// Pass a action checkbox and an affected checkbox group; 
//	// Sets all chechboxes in the affected group to the checked status
//	// of the action checkbox
//	function toggleAllCheckboxes( cb, cbg )
//	{				
//		// if cbGrp has only one checkbox
//		if ( cbg.type == "checkbox" )
//		{
//			cbg.checked = cb.checked
//		// if cbGrp has more than one checkbox			
//		} else 
//		{
//			for( var i = 0; i < cbg.length; i++ )
//			{
//				cbg[i].checked = cb.checked;
//			}
//		}
//	}
	
	
	function toggleAllCheckboxes( cb, cbgStr )
	{				
		var cbgArr = getStrutsCheckboxesArray( cbgStr );
	
		for( var i = 0; i < cbgArr.length; i++ )
		{
			cbgArr[i].checked = cb.checked;
		}
	}		
	
	
	
	/*------------------------------------------------------------------------*/
	// REN SPECIFIC
	/*------------------------------------------------------------------------*/	
	
	// Struts-friendly
	// Pass the name of the checkbox group 
	// example:  name="foo[2].selected" -> pass "foo"
	// Will match a the beginning of a checkbox name
	// Can also match on a regular expression
	function getStrutsCheckboxesArray( cbgStr )
	{
		var cbgArr = new Array();

		inputs = document.getElementsByTagName( "input" );

		// get cbg given the Struts way of handling indexed checkboxes
		for ( var i = 0; i < inputs.length; i++ )
		{
			typeAttr = inputs[i].getAttributeNode( "type" );

			if ( ( typeAttr ) && 
				  ( typeAttr.value == "checkbox" ) )
			{  
				nameAttr = inputs[i].getAttributeNode( "name" );

				if ( ( nameAttr ) && 
					  ( nameAttr.value.length > 0  ) )
				{
					if ( nameAttr.value.toString().indexOf( cbgStr ) != -1 )					
					{
						cbgArr.push( inputs[i] );
					} else
					{
						var re = RegExp( cbgStr );
						
						if ( nameAttr.value.toString().search( re ) != -1 )					
						{
							cbgArr.push( inputs[i] );
						}
					}
				}
			}
		}

		return cbgArr;
	}	
	
	// Struts-friendly
	// Pass the name of the checkbox group 
	// example:  name="foo[2].selected" -> pass "foo"
	// Will match a the beginning of a checkbox name
	// Can also match on a regular expression	
	function getStrutsSelectedCheckboxesArray( cbgStr )
	{
		var cbgArr = new Array();

		inputs = document.getElementsByTagName( "input" );

		// get cbg given the Struts way of handling indexed checkboxes
		for ( var i = 0; i < inputs.length; i++ )
		{
			typeAttr = inputs[i].getAttributeNode( "type" );

			if ( ( typeAttr ) && 
				  ( typeAttr.value == "checkbox" ) &&
				  ( inputs[i].checked ) )
			{  
				nameAttr = inputs[i].getAttributeNode( "name" );

				if ( ( nameAttr ) && 
					  ( nameAttr.value.length > 0  ) )
				{
					if ( nameAttr.value.toString().indexOf( cbgStr ) != -1 )					
					{
						cbgArr.push( inputs[i] );
					} else
					{
						var re = RegExp( cbgStr );
						
						if ( nameAttr.value.toString().search( re ) != -1 )					
						{
							cbgArr.push( inputs[i] );
						}
					}
				}
			}
		}
		
		return cbgArr;
	}	
	
	function getStrutsCheckboxGroupValueArray( cbgArr )
	{
		var checkedValArr = new Array();
	
		for ( var i = 0; i < cbgArr.length; i++ )
		{
			if ( cbgArr[i].checked == true )
			{
				checkedValArr.push( cbgArr[i].value );
			}
		} 
	
		return checkedValArr;
	}
	
	function checkStrutsQuantitySelectedCheckboxes( cbgArr ) // [, qty ]
	{
		var qty = ( arguments[1] != undefined  ) ? arguments[1] : 1; // default to 1
		
		if ( !( cbgArr ) )
		{
			return false;
		}
		
		selArr = getStrutsCheckboxGroupValueArray( cbgArr );
		
		if ( selArr.length == qty )
		{
			return true;
		} else
		{
			return false;
		}
			
	}
	
// TH 20050506 - For this application, use the struts-friendly versions below.
//	function validateSingleSelection( cbg ) // [, msg ] 
//	{
//		var msg = ( arguments[1] != undefined  ) ? arguments[1].toString() : "";
//	
//		if ( !( checkQuantitySelectedCheckboxes( cbg ) ) )
//		{
//			msg += "\nOne checkbox must be selected to perform this function.";
//			alert( msg );
//			return false;
//		} else
//		{
//			return true;
//		}
//	}
//	
//	function validateMinimumSelection( cbg )
//	{	
//		var msg = ( arguments[1] != undefined  ) ? arguments[1].toString() : "";	
//
//		if ( !( checkMinimumQuantitySelectedCheckboxes( cbg, 0 ) ) )
//		{
//			msg += "\nAt least one checkbox must be selected to perform this function.";	
//			alert( msg );
//			return false;
//		} else
//		{
//			return true;
//		}
//	}	
	
	// Struts-friendly.
	// Pass and array of checkboxes.
	function checkStrutsMinimumQuantitySelectedCheckboxes( cbgArr, qty )
	{
		if ( !( cbgArr ) )
		{
			return false;
		}
		
		selArr = getStrutsCheckboxGroupValueArray( cbgArr );

		if ( selArr.length > qty )
		{
			return true;
		} else
		{
			return false;
		}	
	}		
	
	// Struts-friendly
	// Pass the name of the checkbox group 
	// example:  name="foo[2].selected" -> pass "foo"
	function validateSingleSelection( cbgStr ) // [, msg ] 
	{
		var msg = ( arguments[1] != undefined  ) ? arguments[1].toString() : "";
		var cbgArr = getStrutsCheckboxesArray( cbgStr );

		if ( !( checkStrutsQuantitySelectedCheckboxes( cbgArr ) ) )
		{
			msg += "\nOne checkbox must be selected to perform this function.";
			alert( msg );
			return false;
		} else
		{
			return true;
		}
	}
	
	// Struts-friendly
	// Pass the name of the checkbox group 
	// example:  name="foo[2].selected" -> pass "foo"	
	function validateMinimumSelection( cbgStr ) // [, msg ]
	{	
		var msg = ( arguments[1] != undefined  ) ? arguments[1].toString() : "";	
		var cbgArr = getStrutsCheckboxesArray( cbgStr );

		if ( !( checkStrutsMinimumQuantitySelectedCheckboxes( cbgArr, 0 ) ) )
		{
			msg += "\nAt least one checkbox must be selected to perform this function.";	
			alert( msg );
			return false;
		} else
		{
			return true;
		}
	}	
	
	
	
	// Struts-related convenience function
	// Gets the index of the specified form. 
	// arg1: The form element
	// arg2: (optional) Regular expression to match on the prefix of the element name
	// return: the first integer in [x] format
	// Example when only arg1 is passed:
	//   name="foo.list[2].bar.list[3].selected" -> pass el -> returns 2
	// Example when arg2 is also passed, can exlcude the first index (or whatever desired):
	//   name="foo.list[2].bar.list[3].selected" -> pass el, "bar\.list" -> returns 3
	function getNameIndex( frmEl ) // [, frmElNamePartRE ]
	{
		var frmElNamePartRE = ( arguments[1] != undefined  ) ? arguments[1].toString() : "";
	
		var out = "";
		
		regex = new RegExp( frmElNamePartRE + "\\[(\\d+)\\]" );

		if ( frmEl.name && regex )
		{
			out = frmEl.name.match( regex )[1];
		}
		
		return out;
	}
	
	
	// Creates a hidden input and appends it a a child to a form.
	function createHiddenInput( frm, name, value )
	{ 
		var inputEl = document.createElement( "input" );
		inputEl.setAttribute( "type", "hidden" );
		inputEl.setAttribute( "name", name );
		inputEl.setAttribute( "value", value );
		frm.appendChild( inputEl );		
	}
	
	// Event key press handler wrapper object to conviently handle common key press task
	function EventKeyPressHandler()
	{
		this.evt = null;

		this.KEY_CODE_ENTER = 13;

		this.getEvent = function() { return this.evt; }

		this.onKeyPress = function( e ) { this.evt = e ? e : ( window.event ) ? window.event : null; }

		this.hasCaptured = function() { return ( this.evt != null ) ? true : false; }

		this.getKeyCode = function() { return ( this.hasCaptured() ) ? this.evt.keyCode : null; }

		this.wasPressed = function( code ) { return ( this.getKeyCode() == code );	}

		this.wasEnterPressed = function() { return this.wasPressed( this.KEY_CODE_ENTER ); }
	}

	
/******************************************************************************
WindowShade
20050821 T. Hill, Railinc Coproration
Object for providing window shade behavior on a container element.  

Dependencies:  n/a
******************************************************************************/

// Set the style of an element either by passing the ID as a string
// or the element itself.
//  arg1: either ID as string or element
//  arg2: class name
function setStyle( elParam, cl )
{
	var el = null;

	if ( typeof( elParam ) == "string" )
	{
		el = document.getElementById( elParam );
	} else if ( typeof( elParam ) == "object" )
	{
		el = elParam;
	}

	if ( el != null )
	{
		el.className = cl;
	}
}


function getElementCssAttribute( elId, name )
{
	var el = document.getElementById( elId );
	var dv = document.defaultView;
	var val = null;	

	// Try inline style
	if ( el.style[name] )
	{
		val = el.style[name];
	} else
	// Try external stylesheet
	if ( el.currentStyle ) // ie
	{
		val = el.currentStyle[name]; 
	} else 
	if ( dv && dv.getComputedStyle ) // gecko
	{
		name = name.replace(/([A-Z])/g,"-$1");
		val = dv.getComputedStyle( el, "" ).getPropertyValue( name.toLowerCase() );
	}	

	return val;
}	

/******************************************************************************
CSS application via script
******************************************************************************/

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


// Adds the generated content to mandatory labels (prepends *)
// Note that the style of the label should be defined in the stylesheet
// for class 'mandatory'.
function formatLabels()
{
	var lbls = document.getElementsByTagName( "label" );
	var lbl, cls, clsNames, shouldAppendColon;

	for ( var i = 0; i < lbls.length; i++ )
	{
		lbl = lbls.item(i);
		cls = lbl.getAttributeNode( "class" );
		shouldAppendColon = true;

		if ( cls )
		{
			clsNames = cls.value.split( /\s+/ );		

			for ( var cn = 0; cn < clsNames.length; cn++ )
			{
				if ( clsNames[cn] == "mandatory" )
				{
					lbl.innerHTML = "*" + lbl.innerHTML;
				}

				if ( clsNames[cn] == "sub" )
				{
					shouldAppendColon = false;
				}					
			}
		}

		if ( shouldAppendColon )
		{
			lbl.innerHTML += ":";			
		}
	}
}



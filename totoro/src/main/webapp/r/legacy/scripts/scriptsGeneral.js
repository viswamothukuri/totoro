/******************************************************************************
General functions
2005 T. Hill, Railinc Coproration

Dependencies:  scriptsBrowser.js
               scriptsStyleUtil.js
******************************************************************************/

// Useful for providing a common alert when ultimate functionality from
// triggered from the client is not available yet during the course
// of development.  Calls should be removed before production.
function notAvailable()
{
	var out = "* This feature is not available. *";

	out = ( arguments[0] != undefined ) ?  arguments[0].toString() : out;

	alert( out );
}


// Called on every page to set the window title with the text of 
// the element holding the screen title.
function setWindowTitle()
{
	var pageTitle = document.title;
	var screenHeader = "";

	if ( document.getElementById( "pageTitle" ) )
	{
		screenHeader = document.getElementById( "pageTitle" ).innerHTML;

		screenHeader = screenHeader.match( /^\s*(\S.*)\s*$/ );

		if ( screenHeader[1] ) 
		{
			pageTitle += " | " + screenHeader[1];
		}

		document.title = pageTitle;			
	}

}


// Call following body element of every page to 
// force IE to emulate the min-height css attribute.
// (as of IE 6, does not support min-height)
function setLayout()
{
	// set minimum height
	var contentDiv = document.getElementById('content');
	var minHeight = getElementCssAttribute( "content", "height" );
	var browser = new Browser();

	if ( browser.isIE() && browser.getVersion() < 7 )
	{
		contentDiv.style.height = minHeight;
	} else // standard
	{		
		contentDiv.style.height = "auto";
		contentDiv.style.minHeight = minHeight;
	}
}


function setExtWinLayout()
{
	// Passing through to setLayout() - reserved for future use.
	setLayout();
}


function getElement( obj )
{
	var el = undefined;
	
	if ( typeof( obj ) == "string" )
	{
		el = document.getElementById( obj );
	} else if ( obj.nodeType && obj.nodeType == 1 )
	{
		el = obj;
	}
	
	return el;
}


function removeActionMessages()
{
	amDiv = document.getElementById( "actionMessages" );

	if ( amDiv )
	{
		amDiv.parentNode.removeChild( amDiv );
	}
}

function removeActionErrors()
{
	aeDiv = document.getElementById( "actionErrors" );

	if ( aeDiv )
	{
		aeDiv.parentNode.removeChild( aeDiv );
	}
}




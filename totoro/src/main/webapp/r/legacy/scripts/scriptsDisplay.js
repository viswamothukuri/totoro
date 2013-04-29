/******************************************************************************
Display Utilities
2004 T. Hill, Railinc Coproration

Dependencies:  n/a
******************************************************************************/

// Can pass and element or an id of an element. 
// If the "opener" flag is passed as arg2, then the 
// context of the element referred to by arg1 will be
// the opener window of the document where the call
// is made.
// arg1: an element reference of the id of an element
// arg12 (optional): "opener" flag
function hide( str ) // [ ,"opener" ]
{
	var el;

	try
	{

		if ( typeof( str ) == "string" )
		{
			el = document.getElementById( str );	
		} else // should pass an element
		{
			el = str;
		}

		var opWin = arguments[1] != undefined ? arguments[1].toString() : '';

		if ( opWin == 'opener' )
		{
			opener.el.style.display = 'none';
			opener.el.style.visibility = 'hidden';
		} else
		{
			el.style.display = 'none';
			el.style.visibility = 'hidden';
		}

	} catch( error )
	{
		alert( error +"\nInfo: hide( \"" + str + "\" ) " );

	}

}	

// Can pass and element or an id of an element. 
// If the "opener" flag is passed as arg2, then the 
// context of the element referred to by arg1 will be
// the opener window of the document where the call
// is made.
// arg1: an element reference of the id of an element
// arg12 (optional): "opener" flag
function show( str )
{
	var el;

	try
	{			

		if ( typeof( str ) == "string" )
		{
			el = document.getElementById( str );	
		} else // should pass an element
		{
			el = str;
		}

		var opWin = arguments[1] != undefined ? arguments[1].toString() : '';

		if ( opWin == 'opener' )
		{
			opener.el.style.display = 'block';
			opener.el.style.visibility = 'visible';
		} else
		{	
			el.style.display = 'block';
			el.style.visibility = 'visible';
		}	

	} catch( error )
	{
		alert( error +"\nInfo: show( \"" + str + "\" ) " );

	}		

}

// Can pass and element or an id of an element. 
// If the "opener" flag is passed as arg2, then the 
// context of the element referred to by arg1 will be
// the opener window of the document where the call
// is made.
// arg1: an element reference of the id of an element
// arg12 (optional): "opener" flag
function showInline( str )
{
	var el;

	if ( typeof( str ) == "string" )
	{
		el = document.getElementById( str );	
	} else // should pass an element
	{
		el = str;
	}	

	var opWin = arguments[1] != undefined ? arguments[1].toString() : '';

	if ( opWin == 'opener' )
	{
		opener.el.style.display = 'inline';
		opener.el.style.visibility = 'visible';
	} else
	{
		el.style.display = 'inline';
		el.style.visibility = 'visible';
	}
}	

function maximizeY( elObj ) // [, minHeightPixels]
{
	var el = getElement( elObj );
	// If minHeightPixels passed, parse for integer value, ignoring the "px" unit
	var minHeight = ( arguments[1] == undefined ) ? 0/*parseInt( el.offsetHeight )*/ : parseInt( arguments[1].toString() );
	var browser = new Browser();
	var availableHeight = browser.getInnerHeight() - ( document.body.offsetHeight - el.offsetHeight ) - browser.Constants.SCROLLER_BUFFER;
	el.style.height = ( ( availableHeight < minHeight ) ? minHeight  : availableHeight ) + "px";
}

function maximizeX( elObj ) // [, minWidthPixels]
{
	var el = getElement( elObj );
	// If minWidthPixels passed, parse for integer value, ignoring the "px" unit	
	var minWidth = ( arguments[1] == undefined ) ? el.offsetWidth : arguments[1];
	var browser = new Browser();
	var availableWidth = browser.getInnerWidth() - ( document.body.offsetWidth - el.offsetWidth ) - browser.Constants.SCROLLER_BUFFER;
	el.style.width = ( ( availableWidth < minWidth ) ? minWidth  : availableWidth ) + "px";
}

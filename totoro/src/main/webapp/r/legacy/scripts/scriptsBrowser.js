/********************************************************************************
Simple Browser Check
2004 T. Hill - Railinc Corporation
Browser object with sinmlpe checks for IE and Gecko-based browsers
Additional functions for manipulating the browser/window.

Dependencies:  n/a
********************************************************************************/

function Browser()
{
	this.ua = navigator.userAgent;
	this.browser = '';
	this.version = '';
	this.isIEFlag = false;
	this.isGeckoFlag = false;
	this.geckoVersion = '';

	this.Constants = 
	{ 
		// Buffer given to default scrollbar width plus gutter
		// specified in pixels -- useful for handling  IE issues
		// where it calculation excludes scrollbar width
		// in block level rendering.
		SCROLLER_BUFFER: 30,
		INTERNET_EXPLORER: "msie",
		FIREFOX: "firefox"
	};

	this.isGeckoFlag = ( this.ua.toLowerCase().search( 'gecko' ) != -1 );
	this.isIEFlag = ( this.ua.toLowerCase().search( 'msie' ) != -1 );

	if ( this.isIEFlag ) 
	{
		this.version = this.ua.toLowerCase().match( /^.*\ msie\ ([0-9,.]+).*/ )[1];
	} else 
	{
		if ( this.isGeckoFlag )
		{
			try {
				this.geckoVersion = this.ua.toLowerCase().match( /^.*\ rv:([0-9,.]+).*/ )[1];
			} catch (e){}
		}
		
		if ( this.ua.toLowerCase().search( 'firefox' ) != -1 ) 
		{
			this.browser = this.Constants.FIREFOX;
			this.version = this.ua.toLowerCase().match( /^.*\ firefox\/([0-9,.]+).*/ )[1];		
		}	
	}

	this.getUserAgent = function() { return this.ua; }
	this.getBrowser = function() { return this.browser; }
	this.isGecko = function() { return this.isGeckoFlag; }	
	this.isIE = function() { return this.isIEFlag; }		
	this.getVersion = function() { return this.version; }	
	this.getGeckoVersion = function() { return this.geckoVersion; }

	this.centerWindow = function() // [ winObj ]
	{
		// If winObj reference passed, use it, otherwise
		// default to self
		var winObj = ( arguments[0] != undefined ) ? arguments[0] : self;
		var w = 0;
		var h = 0;

		if ( this.ieIE() )
		{
			w = winObj.document.body.clientWidth;
			h = winObj.document.body.clientHeight;				
		} else
		{
			w = winObj.window.outerWidth;
			h = winObj.window.outerHeight;
		}				

		x = this.getCenterTopLeftX( w );
		y = this.getCenterTopLeftY( h );

		winObj.moveTo( x, y );		
	}	

	this.getCenterTopLeftX = function( winWidth )
	{
		var x = 0;

		if ( this.isIE() )
		{
			x = ( screen.width - winWidth ) / 2;
		} else
		{
			x = ( screen.availWidth - winWidth ) / 2;
		}

		return x;			
	}

	this.getCenterTopLeftY = function( winHeight )
	{
		var y = 0;

		if ( this.isIE() )
		{
			y = ( screen.height - winHeight ) / 2;			
		} else
		{
			y = ( screen.availHeight - winHeight ) / 2;
		}

		return y;			
	}		

	this.getInnerHeight = function()
	{      
		var h = 0;

		if ( this.isIE() )
		{
			h = document.documentElement.offsetHeight;	
		} else
		{
			h = window.innerHeight;
		}

		return h;
	}

	this.getInnerWidth = function()
	{      
		var w = 0;

		if ( this.isIE() )
		{
			w = document.documentElement.offsetWidth;	
		} else
		{
			w = window.innerWidth;
		}

		return w;
	}		
}
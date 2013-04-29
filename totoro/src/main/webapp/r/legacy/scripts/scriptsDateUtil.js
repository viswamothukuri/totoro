	/******************************************************************************
	Date Utilities
	2004 T. Hill - Railinc Corporation
	******************************************************************************/

	// Takes a date; returns a string in MM-DD-YYYY format
	function formatDate( dt )
	{
		var delim = '/';
		var y = dt.getFullYear();
		var m = dt.getMonth() + 1; ( m < 10 ) ? m = '0' + m : m; // left pad month to 2 digits
		var d = dt.getDate(); ( d < 10 ) ? d = '0' + d : d; // left pad date to 2 digits
		var outDtStr = ( m ) + delim + ( d ) + delim + ( y );
		return outDtStr;
	}

	// Takes date, adds number of  days, months, years; returns resulting date
	function addDate( dt, yrs, mos, days )
	{
		var outDt = new Date( dt.getTime() );
		var y = yrs;
		var m = outDt.getMonth() + mos;
		
		if ( m > 11 )
		{
			y = Math.floor( ( m + 1 ) / 12 );
			m -= 12 * y;
			y += yrs;
		}
		
		outDt.setMonth( m );
		outDt.setFullYear( outDt.getFullYear() + yrs );
		outDt.setTime( outDt.getTime() + 86400000 * days );

		return outDt;
	}
	
	// Takes date, adds number of days; returns resulting date
	function addDay( dt, days )
	{
		return addDate( dt, 0, 0, days );
	}
	
	// Takes date, adds number of months; returns resulting date	
	function addMonth( dt, mos )
	{
		return addDate( dt, 0, mos, 0 );
	}	
	
	// Takes date, adds number of years; returns resulting date
	function addYear( dt, yrs )
	{
		return addDate( dt, yrs, 0, 0 );
	}
	
	// Returns an array of date parts: year, month, date
	// Parses string first for month, then date, then year.
	// Handles 2 digit years > 30 years 20xx; < 30 years 19xx.
	function parseDateString( str )
	{
		var digit2YearThreshold = 30;
		var tokensRaw = str.split( /\D/ ); // get tokens
		var tokens = new Array();
		var tokensOut = new Array();
		var y, m, d;
		
		for ( var i = 0; i < tokensRaw.length; i++ )
		{
			if ( tokensRaw[i].length > 0 ) 
			{
				tokens.push( tokensRaw[i] ); // get only valid tokens
			}
		}

		if ( tokens.length == 1 ) // support single token mmddyyyy
		{
			m = tokens[0].substring( 0, 2 );
			d = tokens[0].substring( 2, 4 );
			y = tokens[0].substring( 4, 8 );
		}
		
		if ( tokens.length == 3 ) 
		{
			m = tokens[0];
			d = tokens[1];
			y = tokens[2];

			tokensOut[0] = ( y.length == 2 ) ? ( y > digit2YearThreshold ) ? "19" + y : "20" + y : y;
			tokensOut[1] = m;
			tokensOut[2] = d;
		}
		
		return tokensOut;
	}
	
	// Auto format dates  Useful with onblur on a textfield.
	// Relies on formatDate() for format
	// [ex. <input type="text" ... onblur="autoFormatDate( this );" /> ]
	function autoFormatDate( obj )
	{
		var formattedDate = "";
		var dtParts = parseDateString( obj.value )
		
		if ( dtParts.length > 0 )
		{
			var y = dtParts[0];
			var m = dtParts[1];
			var d = dtParts[2];

			if ( m && d && y )
			{
				if ( formattedDate.valueOf().toString() != Number.NaN.toString() )
				{				
					formattedDate = formatDate( new Date( y, m - 1, d ) );
				}
			}			
		}	
		
		obj.value = formattedDate;			
	}	


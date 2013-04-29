
	/******************************************************************************
	Calendar
	2006 T. Hill - Railinc Corporation
	Provides a calendar data object and call to render a calendar selector.
	Inspired by calendar picker by TengYong Ng 
	 (http://www.rainforestnet.com/datetimepicker.htm)
	******************************************************************************/

	var calendarSelector;

	function CalendarDtm() // [ dtStr ]
	{  
		this.dtm = new Date(); // default to now

		if ( arguments.length == 1 )
		{
			var dtStr = arguments[0].toString();

			if ( dtStr.length > 0 )
			{
				var dtParts = parseDateString( dtStr );
				this.dtm = new Date( dtParts[0], dtParts[1] - 1, dtParts[2] );
			}
		} 

		this.year = this.dtm.getFullYear();
		this.month = this.dtm.getMonth();
		this.date = this.dtm.getDate();

		this.getYear = function() { return this.year; }
		this.getMonth = function() { return this.month; }
		this.getDate = function() { return this.date; }
		this.getDay = function() { return this.dtm.getDay() + 1; }

		this.months = [ "January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "December" ];
		this.getMonths = function() { return this.months; }		
		this.getMonthName = function() // [ m ]
		{ 
			if ( arguments.length == 1 ) 
			{
				return this.months[arguments[0]];	
			} else
			{
				return this.months[this.dtm.getMonth()];
			}
		}

		this.days = [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ];
		this.getDays = function() { return this.days; }	
		this.getDayName = function() // [ d ]
		{ 
			if ( arguments.length == 1 )
			{
				return this.days[arguments[0]];
			} else 
			{
				return this.days[this.dtm.getDay()];
			}
		}

		this.isLeapYear = function() { return ( this.year % 4 == 0 ) ? ( ( ( this.year % 100 == 0 ) && ( this.year % 400 != 0 ) ) ? false : true ) : false; }
		this.daysInMonth = [ 31, ( this.isLeapYear() ) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
		this.getDaysInMonth = function() // [ m ]
		{ 
			if ( arguments.length == 1 ) 
			{
				return this.daysInMonth[arguments[0]];	
			} else
			{
				return this.daysInMonth[this.dtm.getMonth()];
			}
		}

		this.setYear = function( y ) { this.year = ( y > 9999 ) ? 9999 : ( y < 1 ) ? 1 : y; this.updateDtm(); }
		this.setMonth = function( m ) { this.month = m - 1; this.updateDtm(); }
		this.setDate = function( d ) {this.date = d; this.updateDtm(); }
		this.getDtm = function() { return this.dtm; }
		this.updateDtm = function() { this.dtm = new Date( this.year, this.month, this.date ); }

		this.isEqualDate = function( inCalDtm )
		{	
			if ( this.getYear() != inCalDtm.getYear() ) { return false; }
			if ( this.getMonth() != inCalDtm.getMonth() ) { return false; }
			if ( this.getDate() != inCalDtm.getDate() ) { return false; }

			return true;
		}

		this.debug = function()
		{

			var out = "";

			out += "dtm: " + this.dtm + "\n"
			out += "year: " + this.year + "\n"
			out += "month: " + this.month + "\n"
			out += "date: " + this.date + "\n"

			alert( out );
		}
	}

	function CalendarSelector( inpElId )
	{
		this.inpEl = document.getElementById( inpElId );

		this.calendarDtm = new CalendarDtm( this.inpEl.value );
		this.currentDtm = new CalendarDtm( this.inpEl.value );
		this.todayDtm = new CalendarDtm();

		this.html;

		this.extWin;

		// functions
		this.getCalendarDtm = function() { return this.calendarDtm; }
		this.getFormattedDateString = function() { return formatDate( this.calendarDtm.getDtm() ); }


		this.updateDtm = function() 
		{	
			// If the override onDateChange function was defined,
			// call it instead of the default update to the input value
			if ( this.onDateChange != undefined )
			{
				this.onDateChange();				
			} else
			{
				this.inpEl.value = this.getFormattedDateString(); 
			}
			this.close(); 			
		}

		this.addYear = function()
		{ 
			this.calendarDtm.setYear( this.calendarDtm.getYear() + 1 );	
			this.updateCalendarSelector();		
		}

		this.addMonth = function() 
		{ 
			this.calendarDtm.addMonth(); 
		}
		
		this.addDay = function() 
		{ 
			this.calendarDtm.addDay();
		}

		this.subtractYear = function()
		{ 	
			this.calendarDtm.setYear( this.calendarDtm.getYear() - 1 );		
			this.updateCalendarSelector();		
		}

		this.subtractMonth = function() 
		{ 
			this.calendarDtm.subtractMonth();
		}
		
		this.subtractDay = function()
		{ 
			this.calendarDtm.subtractDay(); ; 
		}	

		this.selectMonth = function( obj )
		{
			this.calendarDtm.setMonth( obj.value );		
			this.updateCalendarSelector();
		}

		this.selectDate = function( d )
		{
			this.calendarDtm.setDate( d );
			this.updateDtm();			
		}

		// Can be overriden to perform other actions upon a date
		// change made in the calendar
		this.onDateChange;

		this.getHtml = function() { return this.html; }

		this.initCalendarSelector = function()
		{			
			browser = new Browser();
			var w = 250;
			var h = 270;
			var x = browser.getCenterTopLeftX( w );
			var y = browser.getCenterTopLeftY( h );		
			var u = "";
			var n = "extWinCalendarApplyClient";

			this.extWin = window.open( u, n, "resizable=1,toolbar=0,menubar=0,location=0,scrollbars=1,status=0,width=" + w + ",height=" + h + ",screenX=" + x + ",screenY=" + y );

			this.updateCalendarSelector();	

			this.extWin.focus();					
		}

		this.updateCalendarSelector = function()
		{
			var html = '<html><head><title>Calendar</title><link type="text/css" rel="stylesheet" media="screen, projection, print" href="' + stylesheetLocation + '" /></head><body>';

			html += '<div>';
			html += '<table class="calendar">';
			html += '<tbody>';
			html += '<tr>';

			// Month selector
			html += '<td align="center"><select onchange="opener.calendarSelector.selectMonth( this );">';

			for( var m = 1; m < this.calendarDtm.getMonths().length + 1; m++ )
			{
				html += '<option value="' + m + '"';

				if ( m - 1 == this.calendarDtm.getMonth() )
				{
					html += ' selected="selected"';
				}

				html += '>' + this.calendarDtm.getMonths()[m - 1] + '</option>';
			}

			html += '</select></td>';  

			// Year shifter
			html += '<td align="center"><a class="calendarText" href="javascript:opener.calendarSelector.subtractYear();">&lt;</a>&nbsp;<span class="calendarText">' + this.calendarDtm.getYear() + '</span>&nbsp;<a class="calendarText" href="javascript:opener.calendarSelector.addYear();">&gt;</a></td>';
			html += '</tr>';
			html += '</tbody>';		
			html += '</table>';

			html += '<table class="calendarTiles">';
			html += '<tbody>';	

			html += '<tr>';
			html += '<td colspan="7" align="center"><span class="calendarText">' + this.calendarDtm.getMonthName() + ' ' + this.calendarDtm.getYear()  + '</span></td>';

			var actDate, tileDtm, isEmpty, isWeekend, isToday, isCurrent;

			// Calendar header
			html += '<tr>';

			for ( var d = 0; d < this.calendarDtm.getDays().length; d++ )
			{
				isWeekend = ( ( d % 7 == 0 ) || ( d % 7 == 6 ) );		
				html += '<th align="center" class="calendarTile">' + this.calendarDtm.getDays()[d].substring( 0, 1 ) + '</th>';
			}

			html += '</tr>';

			// Calendar tiles	
			var firstDayOfMonth = new Date( this.calendarDtm.getYear(), this.calendarDtm.getMonth(), 1 ).getDay();
			var tilesCount = this.calendarDtm.getDaysInMonth() + firstDayOfMonth;

			while ( tilesCount % 7 != 0 ) // need enough tiles to fill the calendar body
			{
				tilesCount++;	
			}

			html += '<tr>';		

			for ( var i = 1; i < tilesCount + 1; i++ )
			{	
				actDate = i - firstDayOfMonth;
				isEmpty = !( ( actDate > 0 ) && ( actDate < this.calendarDtm.getDaysInMonth() + 1 ) ) ;
				isWeekend = ( ( i % 7 == 1 ) || ( i % 7 == 0 ) );	
				tileDtm = new CalendarDtm( ( this.calendarDtm.getMonth() + 1 ) + "-" + actDate + "-" + this.calendarDtm.getYear() );
				isToday = tileDtm.isEqualDate( this.todayDtm );
				isCurrent = tileDtm.isEqualDate( this.currentDtm );

				html += '<td align="center" class="' + ( isEmpty ? 'calendarTileEmpty' : ( isCurrent ? 'calendarTileCurrent' : ( isToday ? 'calendarTileToday' : ( isWeekend ? 'calendarTileWeekend' : 'calendarTile' ) ) ) ) + '">' + ( !isEmpty ? ( '<a class="calendarText" href="javascript:opener.calendarSelector.selectDate( ' + actDate + ' );">' + actDate + '</a>' ) : '<span class="calendarText">&nbsp;</span>' ) + '</td>';		

				if ( i % 7 == 0 )
				{
					html += '<tr></tr>';			
				}
			}

			html += '</tr>';	

			html += '<tr><td colspan="7" align="center" style="padding-top: 10px;"><button onclick="opener.calendarSelector.close();">Cancel</button></td></tr>';

			html += '</tbody>';	
			html += '</table>';		

			html += '</div>';

			html += '</body></html>';

			this.extWin.document.open();
			this.extWin.document.writeln( html );
			this.extWin.document.close();
		}

		this.close = function()
		{
			this.extWin.close();
		}
	}

	function popupCalendarApplyClient( inpElId )
	{
		calendarSelector = new CalendarSelector( inpElId );
		calendarSelector.initCalendarSelector();		
	}
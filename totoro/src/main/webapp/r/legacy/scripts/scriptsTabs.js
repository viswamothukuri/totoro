/******************************************************************************
Tabs
2006 T. Hill - Railinc Corporation
 
Dependencies:  scriptsDisplay.js
******************************************************************************/

// Construct with the title element ID and the body element ID
function Tab( titleId, bodyId )
{
	this.OFF = 0;
	this.ON = 1;
	this.DISABLED = 2;
	this.titleId = titleId;
	this.bodyId = bodyId;
	this.isOn = false;
	this.baseState = this.OFF;
	this.state = this.baseState;
	
	this.getTitleId = function() { return this.titleId; }
	this.setTitleId = function( titleId ) { this.titleId = titleId; }
	
	this.getBodyId = function() { return this.bodyId; }
	this.setBodyId = function( bodyId ) { this.bodyId = bodyId; }	
	
	this.getState = function() { return this.state; }
	this.getBaseState = function() { return this.baseState; }	
	this.setOn = function() { this.state = this.ON; }	
	this.setOff = function() { this.state = this.OFF; this.baseState = this.OFF; }	
	this.setDisabled = function() { this.state = this.DISABLED; this.baseState = this.DISABLED; }
}

// Construct with the name of the tab 'on' and 'off' style.
function TabCollection( styleOn, styleOff ) // [, styleDisabled ]
{
	this.tabs = new Array();
	this.onTab = null;
	this.styleOn = styleOn;
	this.styleOff = styleOff;
	// default disabled style to off if not provided
	this.styleDisabled = ( arguments[2] == undefined ) ? styleOff : arguments[2].toString(); 
	
	this.add = function( tabObj ) { this.tabs.push( tabObj ); }
	this.getOnTitleId = function() { return ( this.onTab == null ) ? null : this.onTab.getTitleId(); }
	this.getOnBodyId = function() { return ( this.onTab == null ) ? null : this.onTab.getBodyId(); }

	this.setEnabledByTitleId = function( titleId )
	{
		for ( var i = 0; i < this.tabs.length; i++ )
		{
			if ( this.tabs[i].getTitleId() == titleId )
			{
				this.tabs[i].setOff();				
				document.getElementById( this.tabs[i].getTitleId() ).className = this.styleOff;			
			}			
		}
	}
	
	this.setDisabledByTitleId = function( titleId )
	{
		for ( var i = 0; i < this.tabs.length; i++ )
		{
			if ( this.tabs[i].getTitleId() == titleId )
			{
				this.tabs[i].setDisabled();
				document.getElementById( this.tabs[i].getTitleId() ).className = this.styleDisabled;	
			}			
		}
	}	
	
	this.reset = function()
	{
		for ( var i = 0; i < this.tabs.length; i++ )
		{
			this.onTab = null;		

			if ( this.tabs[i].getBaseState() == this.tabs[i].DISABLED )
			{
				this.tabs[i].setDisabled();			
				document.getElementById( this.tabs[i].getTitleId() ).className = this.styleDisabled;				
			} else // off
			{
				this.tabs[i].setOff();							
				document.getElementById( this.tabs[i].getTitleId() ).className = this.styleOff;
			}			
			hide( this.tabs[i].getBodyId() );			
		}
	}	
	
	this.setAllOff = function()
	{
		for ( var i = 0; i < this.tabs.length; i++ )
		{
			this.onTab = null;		
			this.tabs[i].setOff();				
			document.getElementById( this.tabs[i].getTitleId() ).className = this.styleOff;
			hide( this.tabs[i].getBodyId() );			
		}
	}
	
	this.setOnByTitleId = function( titleId )
	{	
		this.reset();
		
		for ( var i = 0; i < this.tabs.length; i++ )
		{
			if ( this.tabs[i].getTitleId() == titleId )
			{
				this.onTab = this.tabs[i];
				this.tabs[i].setOn();
				document.getElementById( this.tabs[i].getTitleId() ).className = this.styleOn;			
				show( this.tabs[i].getBodyId() );					
			}
		}
	}	
}
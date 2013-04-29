/********************************************************************************
Page Loader
2005 T. Hill - Railinc Corporation
Simple object to handle page loading behavior	

Dependencies:  scriptsDisplay.js
********************************************************************************/

function PageLoader( contentElId, loadingElId )
{
	this.contentNode = document.getElementById( contentElId );
	this.loadingNode = document.getElementById( loadingElId );

	this.loading = function()
	{	
		hide( this.contentNode );		
		show( this.loadingNode );
	}

	this.done = function()
	{
		hide( this.loadingNode );
		show( this.contentNode );
	}

	this.showWaiting = function()
	{		
		show( this.loadingNode );
	}	

	this.hideWaiting = function()
	{
		hide( this.loadingNode );
	}
}


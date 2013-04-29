		// Dynamic body events: onload, onunload
		var bodyOnloadFunctions = new Array();
		var bodyOnunloadFunctions = new Array();

		var wrapperOnload = function()
		{
			for ( var i = 0; i < bodyOnloadFunctions.length; i++ )
			{
				bodyOnloadFunctions[i]();
			}
		}

		var wrapperOnunload = function()
		{
			for ( var i = 0; i < bodyOnunloadFunctions.length; i++ )
			{
				bodyOnunloadFunctions[i]();
			}
		}
		
	
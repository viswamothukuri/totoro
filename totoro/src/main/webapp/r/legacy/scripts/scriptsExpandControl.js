/********************************************************************************
ExpandControl - expanding/collapsing items
2007 R. Kainz - Railinc Corporation

Dependencies:  scripts.js
********************************************************************************/

function ExpandControl(options)
{
	this.varName = options && options.varName ? options.varName : "expandControl";
	this.itemDivPrefix = options && options.itemDivIdPrefix ? options.itemDivIdPrefix : "itemContainer";
	this.itemLabelDivPrefix = options && options.itemLabelDivIdPrefix ? options.itemLabelDivIdPrefix : "itemLabel";
	this.allId = options && options.allItemsLabelDivIdPrefix ? options.allItemsLabelDivIdPrefix : "allItemsLabel";
	this.isExpanded = options && options.defaultExpanded ? options.defaultExpanded : false;
	this.expandedIconSrc = options && options.expandedIconSrc ? options.expandedIconSrc : "***You must provide the path to the expand icon!!!***";
	this.collapsedIconSrc = options && options.collapsedIconSrc ? options.collapsedIconSrc : "***You must provide the path to the collapse icon!!!***";
	this.expandedTitle = options && options.expandTooltip ? options.expandTooltip : "Expand";
	this.collapsedTitle = options && options.collapseTooltip ? options.collapseTooltip : "Collapse";

	
	this._getExpandedIconSrc = function(expanded)
	{
		return expanded ? this.expandedIconSrc : this.collapsedIconSrc;
	}
	
	this._getTooltip = function(expanded)
	{
		return !expanded ? this.expandedTitle : this.collapsedTitle;
	}
	
	this.toggleAllExpansion = function(expanded)
	{
		this.isExpanded = expanded ? expanded : this.isExpanded;
		var i = 0;
		var ele = document.getElementById(this.allId);
		if (ele)
		{
			ele.innerHTML = '<a href="javascript:' + this.varName + '.toggleExpansionItem(\'' + this.allId + '\');"><img title="' + this._getTooltip(this.isExpanded) + '" src="' + this._getExpandedIconSrc(this.isExpanded) + '"/></a>';
			while (true)
			{
				var ele = document.getElementById(this.itemLabelDivPrefix + i);
				if (ele==null) { break; }
				this._showItems(i, this.isExpanded);
				i++;
			}
		}
	}	
		
	this.toggleExpansionItem = function(id)
	{
		var ele = document.getElementById(id);
		if (ele)
		{
			if (ele.id==this.allId)
			{
				this.isExpanded = !this.isExpanded;	
				this.toggleAllExpansion(this.isExpanded);
			}
			else
			{	
				var index = id.substring(this.itemLabelDivPrefix.length);
				var expanded = !(ele.innerHTML!=null && ele.innerHTML.indexOf(this.expandedIconSrc)>0);
				this._showItems(index, expanded);
			}
		}
	}
	
	this._showItems = function(index, show)
	{
		var ele = document.getElementById(this.itemLabelDivPrefix + index);
		if (ele) 
		{
			ele.innerHTML =  '<a href="javascript:' + this.varName + '.toggleExpansionItem(\'' + this.itemLabelDivPrefix + index + '\');"><img  title="' + this._getTooltip(show) + '" src="' + this._getExpandedIconSrc(show) + '"/></a>';
			if (show)
			{
				showMe(this.itemDivPrefix + index);
			}
			else
			{
				hideMe(this.itemDivPrefix + index);
			}		
		}
	}
	
	this.toggleAllExpansion();
	
}


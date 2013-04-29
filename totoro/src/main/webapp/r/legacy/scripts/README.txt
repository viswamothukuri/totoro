Javascript README

The global js files have been minimized in hte sso project. The gobal files  being
the ones that are included in head.jsp. Minimized mean that all the whitespace and
comments have been removed from the .js file. The purpose of this is to reduce file
size, which makes the aplication faster. For development purposes, replace the 
minify.js include with the individual .js file includes, so that the fully commented 
and properly styled .js file can be used for dev.

replace this in head.jsp :

	<script type="text/javascript" src="<html:rewrite forward='scripts'/>/minify.js"></script>

with this code block:

	<script type="text/javascript" src="<html:rewrite forward='scripts'/>/scriptsBrowser.js"></script>
	<script type="text/javascript" src="<html:rewrite forward='scripts'/>/scriptsDisplay.js"></script>
	<script type="text/javascript" src="<html:rewrite forward='scripts'/>/scriptsStyleUtil.js"></script>
	<script type="text/javascript" src="<html:rewrite forward='scripts'/>/scriptsGeneral.js"></script>
	<script type="text/javascript" src="<html:rewrite forward='scripts'/>/scriptsPageLoader.js"></script>
	<script type="text/javascript" src="<html:rewrite forward='scripts'/>/scriptsTabs.js"></script>
	
To minimize new code, run the code through a minimizer, such as http://closure-compiler.appspot.com/home.
Paste the code as one line into minify.js. Don't forge to revert the inlcudes in head.jsp.
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" href="//code.jquery.com/ui/1.11.2/themes/smoothness/jquery-ui.css">
  	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
  	<script src="//code.jquery.com/ui/1.11.2/jquery-ui.js"></script>
  	<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
    </script>
	<link rel="stylesheet" href=<c:url value="/resources/css/sms.css" /> >
  	<script type="text/javascript">
  	(function () {
  	    var QUEUE = MathJax.Hub.queue;  // shorthand for the queue
  	    var math = null;                // the element jax for the math output.
  	    var input = $("search");
  	    var literals = null;
  	    //var literals = null;

  	    //
  	    //  Get the element jax when MathJax has produced it.
  	    //
  	    QUEUE.Push(function () {
  	      math = MathJax.Hub.getAllJax("math_expression")[0];
  	      
  	    });
  	    
  	    window.onNewText = function() {
    		console.log($("#search_input").val());
    		var jsonInput = { "input" : $("#search_input").val() }
    		
  	    $.ajax({
  	    	type: "post",
  	    	contentType: 'application/json; charset=utf-8',
  	    	dataType:'json',
  	    	mimeType: 'application/json',
  	    	data: JSON.stringify(jsonInput),
  	    	url: 'ajax.html',
  	    	success : function(data) {
  	    		console.log(data);
  	    		$("#literals").html(data.literals);
  	    		literals = data.literalList;
  	    		jQuery.each(data.literalList, function() {
  	    		  $("#radio_" + this).buttonset();
  	    		});
  	    		QUEUE.Push(["Text",math, data.output ]); 	    		
  	    	},
  	    	error:function(data,status,er) { 
  	          alert("error: "+data+" status: "+status+" er:"+er);
  	         }
  	    });
  	    }
  	  })();
  	</script>
</head>
<body>
<!-- <math xmlns="http://www.w3.org/1998/Math/MathML">
  <mi>a</mi><mo>&#x2260;</mo><mn>0</mn>
</math> -->
<table align="center">
<tr>
 <td>
<div id="math_input">
 <form method="get" onkeyup="onNewText()" id="search">
  <input type="text" size="40" placeholder="Search..."  id="search_input" onPaste="" onkeydown="if (event.keyCode == 13) return false"/>
 </form>
</div>
 </td>
 <td  rowspan="2" valign="top">
<div id="literals"></div>
</td>
 
</tr>
<tr>
<td>
<div id="math_expression"><math xmlns="http://www.w3.org/1998/Math/MathML"></math></div>
</td>
</tr>
</table>
<P>  The time on the server is ${serverTime}. </P>
</body>
</html>



var silent=function(){
	$.get("http://10.42.0.253:8080/prism/rest/track/setsilent",
					function(data){});

}

var recordmic=function(){
	$.get("http://10.42.0.253:8080/prism/rest/track/recordmic",
					function(data){});
}

var stoprecordmic=function(){
	$.get("http://10.42.0.253:8080/prism/rest/track/stoprecordmic",
					function(data){});
}


var general=function(){
	$.get("http://10.42.0.253:8080/prism/rest/track/setgeneral",
					function(data){});
}

var showarchives=function(){
	$.get("http://10.42.0.253:8080/prism/rest/track/filenum",
					function(data){
	str=data.split(",");
	recnum=parseInt(str[0]);
	odnum=parseInt(str[1]);
	var table=document.getElementById("recordings");
	table.innerHTML = '';

	for(var i=1; i<=recnum; i++){		
		var tr = document.createElement("tr");
		var td = document.createElement("td");
		var a= document.createElement("a");
		a.innerHTML="Recording"+" "+i; 
		a.href="../audio/recorded"+i+".3gp";
		td.appendChild(a);
		tr.appendChild(td);
		table.appendChild(tr);
	}
	for(var i=1; i<=odnum; i++){		
		var tr = document.createElement("tr");
		var td = document.createElement("td");
		var a= document.createElement("a");
		a.innerHTML="Ondemand Recording"+" "+i;
		a.href="../audio/ondemand"+i+".3gp"; 
		td.appendChild(a);
		tr.appendChild(td);
		table.appendChild(tr);

		//console.log(""+str[i]);
	}
	});
	
}

var main = function(){ 

	showarchives();


	$('#mute').click(function() {
		if(this.checked){
			silent();
		}
		else{
			general();
			showarchives();
		}
	});

	$('#recordmic').click(function() {
		if(this.checked){
			recordmic();
		}
		else{
			stoprecordmic();
			showarchives();
		}
	});		
	      	
	setInterval(
		function(){
			$.get("http://10.42.0.253:8080/prism/rest/track/getcall_logs",
					function(data){		 
				
					showarchives();
			});
		}, 5000); 

}




$( document ).ready(main);

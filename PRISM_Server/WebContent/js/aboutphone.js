

var main = function(){  
		
			$.get("http://10.42.0.253:8080/prism/rest/track/getinfo",
					function(data){		 
				str=data.split("\n");
				var table=document.getElementById("info");
				table.innerHTML = '';

				for(i in str){
					t=str[i].split(":");
					var tr = document.createElement("tr");
					for( j in t){
						var td = document.createElement("td");
						td.innerHTML=t[j];
						tr.appendChild(td);
					}

					table.appendChild(tr);

					//console.log(""+str[i]);
				}

				});
		
	       

}










$( document ).ready(main);

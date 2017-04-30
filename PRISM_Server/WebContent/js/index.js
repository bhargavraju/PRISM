

var main = function(){
 
    $.get("http://10.42.0.253:8080/prism/rest/track/getlatlng",
      function(data){   
      if(data != "1"){
        $('#cntr')[0].innerHTML="1";
        $('#panhead')[0].style.display="";
        $('#panhead')[0].innerHTML="Phone 1";

      } 

     
    
    });
	
}// refresh every 1



$( document ).ready(main);
var center = null;
var map = null;
var currentPopup;
// var bounds = new google.maps.LatLngBounds();
var markLAT,markLNG;
var marker;




function addMarker(lat, lng, info)
 {
  var pinImage = new google.maps.MarkerImage("http://www.googlemapsmarkers.com/v1/P/0df4f4/000000/0df4f4");
        var currcp=new google.maps.LatLng(lat,lng);
        var marker = new google.maps.Marker({
        position: currcp,
          icon: pinImage,
        });
    marker.setPosition(new google.maps.LatLng(lat,lng) );
    marker.setMap(map);  
  /* var pt = new google.maps.LatLng(lat, lng);
   map.setCenter(pt);
   map.setZoom(17);
  bounds.extend(pt);
  marker = new google.maps.Marker(
  {
      position: pt,
      icon: icon,
      map: map
  });

  var popup = new google.maps.InfoWindow(
  {
      content: "Driver: Manu",
      maxWidth: 300
  });

  google.maps.event.addListener(marker, "click", function(){
      if (currentPopup != null)
      {
          currentPopup.close();
          currentPopup = null;
      }
      popup.open(map, marker);
      currentPopup = popup;
     });
      google.maps.event.addListener(popup, "closeclick", function()
     {
      //map.panTo(center);
      //currentPopup = null;
     });*/
   }

var main = function()
{
 map = new google.maps.Map(document.getElementById("map"), {
center: new google.maps.LatLng(28.35649957,75.58642426),
zoom: 18,
mapTypeId: google.maps.MapTypeId.ROADMAP,
mapTypeControl: false,
mapTypeControlOptions:
{
   style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
},
navigationControl: true,
navigationControlOptions:
{
style: google.maps.NavigationControlStyle.SMALL
 }
 });

routePoints = [];
 read();
  }

 
function read(){

    $.get("http://10.42.0.253:8080/prism/rest/track/getlatlng",
      function(data){   
      if(data != "1"){
        str=data.split(",");
        lat=str[0];
        lng=str[1];
        addMarker(lat, lng, "info");

      } 
     
    
    });
	
		
		setInterval(
			function(){		
				
		$.get("http://10.42.0.253:8080/prism/rest/track/getlatlng",
			function(data){		
      if(data != "1"){
        str=data.split(",");
        lat=str[0];
        lng=str[1];
        addMarker(lat, lng, "info");

      } 
      /*else{
        lat=28.35649957;
        lng =75.58642426;
        addMarker(lat, lng, "info");
      }*/
		
		});
		
		}, 5000);
}// refresh every 1



$( document ).ready(main);
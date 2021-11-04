/**
 * 
 */
function addMarker(args,google) {
	let marker = new google.maps.Marker({
		position: new google.maps.LatLng(21.27141, 157.44478)
	});
	 PF('gMap').addOverlay(marker);
}
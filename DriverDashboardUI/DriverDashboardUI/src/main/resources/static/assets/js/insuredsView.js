jQuery(document).ready(function($){

	getDriverByPOlicy = function(policyId) {
    	$( '#insuredPOlicyNumber' ).val( "Searching for policy" );
		$.ajax( {
			url: "/TripData/drivers/search/by-policy?policy=" + policyId, 
			success: function( data ) {
				var driver = data._embedded.drivers[0];
		    	$( '#insuredPOlicyNumber' ).val( driver.policy );
		    	$( '#driverName' ).val( "" + driver.firstName + " " + driver.lastName);
		    	$( '#vehicleMake' ).val( driver.policy );
		    	
		    	var safetyUrl = driver._links.safety.href;
		    	console.log("safetyUrl " + safetyUrl);
				getSafety(safetyUrl);
				
		    	var driverUrl = driver._links.self.href; 
		    	console.log("driver Url: " + driverUrl); 	
		    	var driverId = driverUrl.substring(driverUrl.indexOf('drivers/') + 8);
		    	getVehicleByVehicleId(driverId); 
			}
		});
	};
	
	getVehicleByVehicleId = function(vehicleId) {
		$.get("/TripData/vehicles/" + vehicleId, function( data ) {
			var vehicle = data;
			$( '#vehicleMake' ).val( vehicle.make + " "  + vehicle.model);
		});
	};
	
	getSafety = function(safetyUrl) {
		$.get(safetyUrl, function( data ) {
			var safety = data;
			var safetyUrl = data._links.self.href; 
			console.log("Safety URL: " + safetyUrl);
			var safetyId = safetyUrl.substring(safetyUrl.indexOf('safeties/') + 9);
			$( '#safetyLevel' ).val( safetyId);
		});
	};
	
    console.log( "ready!" );
    getDriverByPOlicy("85-3422");
    
});

var searchForPolicy = function() {
	var policyNumber = $('#policySearchBox').val();
	console.log("Searchnig for policy: " + policyNumber);
	getDriverByPOlicy(policyNumber);
	return false; 
}
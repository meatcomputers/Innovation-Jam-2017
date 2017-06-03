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
			//Speedometer work
			d3.select("#speedometer").html("");
			

	        var svg = d3.select("#speedometer")
	                .append("svg:svg")
	                .attr("width", 400)
	                .attr("height", 400);


	        var gauge = iopctrl.arcslider()
	                .radius(120)
	                .events(false)
	                .indicator(iopctrl.defaultGaugeIndicator);
	        gauge.axis().orient("in")
	                .normalize(true)
	                .ticks(12)
	                .tickSubdivide(3)
	                .tickSize(10, 8, 10)
	                .tickPadding(5)
	                .scale(d3.scale.linear()
	                        .domain([0, 10])
	                        .range([-3*Math.PI/4, 3*Math.PI/4]));

	        var segDisplay = iopctrl.segdisplay()
	                .width(80)
	                .digitCount(6)
	                .negative(false)
	                .decimals(0);

	        svg.append("g")
	                .attr("class", "gauge")
	                .call(gauge);
	                
	        gauge.value(safetyId);
	    	
	        //Driverless work 
	        var safetyPercent = safety.driverlessPct; 
	        var automatic = 100 * safetyPercent; 
	        var manual = 100 - automatic; 
	    	createPieChart("#driverlessPieChart", "manual", "automatic", manual, automatic); 
	    	var rideShare = manual / 2;
	    	var privateDriving = 100 - rideShare; 
	    	createPieChart("#rideShareChart", "private", "ride share", privateDriving, rideShare); 
	    	var maxPremium = 600; 
	    	var discount = maxPremium * safetyPercent;
	    	discount = discount - (discount * (rideShare / 100));
	    	var premium = maxPremium - discount; 
	    	createPieChart("#premiumChart", "premium $" + premium, "credit $" + discount, premium, discount); 
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
};

var createPieChart = function(
		targetDivName, 
		label1, label2,
		value1, value2) {

    var w = 300,                        //width
    h = 300,                            //height
    r = 150,                            //radius
    color = d3.scale.category20c();     //builtin range of colors

    data = [{"label":label1, "value":value1}, 
            {"label":label2, "value":value2}];
    
    d3.select(targetDivName).html("");
    var vis = d3.select(targetDivName)
        .append("svg:svg")              //create the SVG element inside the <body>
        .data([data])                   //associate our data with the document
            .attr("width", w)           //set the width and height of our visualization (these will be attributes of the <svg> tag
            .attr("height", h)
        .append("svg:g")                //make a group to hold our pie chart
            .attr("transform", "translate(" + r + "," + r + ")")    //move the center of the pie chart from 0, 0 to radius, radius

    var arc = d3.svg.arc()              //this will create <path> elements for us using arc data
        .outerRadius(r);

    var pie = d3.layout.pie()           //this will create arc data for us given a list of values
        .value(function(d) { return d.value; });    //we must tell it out to access the value of each element in our data array

    var arcs = vis.selectAll("g.slice")     //this selects all <g> elements with class slice (there aren't any yet)
        .data(pie)                          //associate the generated pie data (an array of arcs, each having startAngle, endAngle and value properties) 
        .enter()                            //this will create <g> elements for every "extra" data element that should be associated with a selection. The result is creating a <g> for every object in the data array
            .append("svg:g")                //create a group to hold each slice (we will have a <path> and a <text> element associated with each slice)
                .attr("class", "slice");    //allow us to style things in the slices (like text)

        arcs.append("svg:path")
                .attr("fill", function(d, i) { return color(i); } ) //set the color for each slice to be chosen from the color function defined above
                .attr("d", arc);                                    //this creates the actual SVG path using the associated data (pie) with the arc drawing function

        arcs.append("svg:text")                                     //add a label to each slice
                .attr("transform", function(d) {                    //set the label's origin to the center of the arc
                //we have to make sure to set these before calling arc.centroid
                d.innerRadius = 0;
                d.outerRadius = r;
                return "translate(" + arc.centroid(d) + ")";        //this gives us a pair of coordinates like [50, 50]
            })
            .attr("text-anchor", "middle")                          //center the text on it's origin
            .text(function(d, i) { return data[i].label; });        //get the label from our original data array
        
};
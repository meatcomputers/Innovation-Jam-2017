jQuery(document).ready(function($){

	getDriverByPOlicy = function(policyId) {
    	$( '#insuredPOlicyNumber' ).val( "Searching for policy" );
		$.ajax( {
			url: "/TripData/drivers/search/by-policy?policy=" + policyId, 
			success: function( data ) {
				var driver = data._embedded.drivers[0];
		    	$( '#insuredPOlicyNumber' ).html( driver.policy );
		    	$( '#driverName' ).html( "" + driver.firstName + " " + driver.lastName);
		    	getVehicleByVehicleId();
		    	
		    	var safetyUrl = driver._links.safety.href;
		    	console.log("safetyUrl " + safetyUrl);
				getSafety(safetyUrl);
				
		    	var driverUrl = driver._links.self.href; 
		    	console.log("driver Url: " + driverUrl); 	
		    	var driverId = driverUrl.substring(driverUrl.indexOf('drivers/') + 8);
		    	$( '#driverId' ).val(driverId);
		    	getVehicleByVehicleId(driverId); 
		    	getCollision(driverId);
			}
		});
	};
	
	getVehicleByVehicleId = function(vehicleId) {
		var vehiclesUrl = "/TripData/vehicles/1";// + vehicleId;
		console.log("What did we call to get a vehicle? " + vehiclesUrl);
		$.get(vehiclesUrl, function( data ) {
			var vehicle = data;
			var makeModel = vehicle.make + " "  + vehicle.model;
			$( '#vehicleMake' ).html( makeModel);
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

	var timeFormat = function(date) {
		return "" + date;
	}
	
	getCollision = function(collisionId) {
		$.get("/TripData/collisions/" + collisionId, function( data ) {
			var collision = data;
			$('#CollisionReportedOn').html("Collision Detected on: " + timeFormat(collision.timestamp));
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

var goToCollision = function() {
	var driverId = $( '#driverId' ).val(); 
	console.log("We are opening a new page: " + driverId);
	window.location.href = "DrivingDataForCollision.html?collisionId=" + driverId; 
}

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

/*Go to the page. */
var goToLastHourOfDrivingData = function() {
	var driverId = $( '#driverId' ).val(); 
	console.log("We are opening a new page: " + driverId);
	window.location.href = "DrivingDataForDriver.html?driverId=" + driverId; 
}

var displayVelocityChartForUser = function(userId) {

	var svg = d3.select("svg#userChart"),
	    margin = {top: 55, right: 20, bottom: 200, left: 50},
	    width = +svg.attr("width") - margin.left - margin.right,
	    height = +svg.attr("height") - margin.top - margin.bottom,
	    g = svg.append("g").attr("transform", "translate(" + margin.left + "," + margin.top + ")");

	var parseTime = d3.timeParse("%m/%d/%Y %H:%M:%S");

	var x = d3.scaleTime()
	    .rangeRound([0, width]);

	var y = d3.scaleLinear()
	    .rangeRound([height, 0]);

	var line = d3.line()
	    .x(function(d) { return x(d.timeStamp); })
	    .y(function(d) { return y(d.speed); });
	    
	var area = d3.area()
	    .x(function(d) { return x(d.timeStamp); })
	    .y0(height)
	    .y1(function(d) { return y(d.speed); });

	d3.json('http://localhost:9090/TripData//tripDatas/search/by-driverIdLastHour?driverId=' + userId, function(error, data) {
	  if (error) throw error;
	  data._embedded.tripDatas.forEach(function(d) {
	    d.timeStamp = parseTime(d.timeStamp);
	    d.speed = +d.speed;
	    d.x = d.timeStamp;
	    d.y = d.speed;
	    return d;
	  });

	  x.domain(d3.extent(data._embedded.tripDatas, function(d) { return d.timeStamp; }));
	  y.domain(d3.extent(data._embedded.tripDatas, function(d) { return d.speed; }));

	  g.append("g")
	      .attr("transform", "translate(0," + height + ")")
	      .call(d3.axisBottom(x))
	      .append("text")
	        .attr("fill", "#000")
	        .attr("x", 21)
	        .attr("y", 21)
	        .attr("dy", "0.71em")
	        .attr("text-anchor", "end")
	        .text("Time");

	  g.append("g")
	      .call(d3.axisLeft(y))
	    .append("text")
	      .attr("fill", "#000")
	      .attr("transform", "rotate(-90)")
	      .attr("y", -35)
	      .attr("dy", "0.71em")
	      .attr("text-anchor", "end")
	      .text("Speed (mph)");

	  g.append("path")
	      .datum(data._embedded.tripDatas.slice(0))
	      .attr("fill", "#58D68D")
	      .attr("stroke-width", 0)
	      .attr("d", area);
	      
	    g.append("path")
	      .datum(data._embedded.tripDatas.slice(4))
	      .attr("fill", "green")
	      .attr("stroke-width", 0)
	      .attr("d", area);
	      
	    g.append("path")
	      .datum(data._embedded.tripDatas.slice(8))
	      .attr("fill", "steelblue")
	      .attr("stroke-width", 0)
	      .attr("d", area);
	      
	    g.append("path")
	      .datum(data._embedded.tripDatas.slice(10))
	      .attr("fill", "#85C1E9")
	      .attr("stroke-width", 0)
	      .attr("d", area);
	      
	    g.append("path")
	      .datum(data._embedded.tripDatas.slice(12))
	      .attr("fill", "green")
	      .attr("stroke-width", 0)
	      .attr("d", area);

	  g.append("path")
	      .datum(data._embedded.tripDatas)
	      .attr("fill", "none")
	      .attr("stroke", "steelblue")
	      .attr("stroke-linejoin", "round")
	      .attr("stroke-linecap", "round")
	      .attr("stroke-width", 1.5)
	      .attr("d", line);
	  
	  	// Add title	  
		svg.append("svg:text")
			 .attr("class", "title")
		   .attr("x", (svg.attr("width") - margin.left - margin.right)/2)
		   .attr("y", 50)
		   .text("Driving Data");
		   
	 var w = 125;
	 
	 var color_hash = {  0 : ["rideshare automated", "#58D68D"],
	    1 : ["personal automated", "green"],
	    2 : ["rideshare manual", "#85C1E9"],
	    3 : ["personal manual", "steelblue"]
	  }       
	  
		// add legend   
		var legend = svg.append("g")
		  .attr("class", "legend")
		  .attr("x", w - 65)
		  .attr("y", 25)
		  .attr("height", 100)
		  .attr("width", 100);

		legend.selectAll('g').data(data._embedded.tripDatas.slice( 0,4))
	      .enter()
	      .append('g')
	      .each(function(d, i) {
	        var g = d3.select(this);
	        g.append("rect")
	          .attr("x", w - 65)
	          .attr("y", 450 + i*25)
	          .attr("width", 10)
	          .attr("height", 10)
	          .style("fill", color_hash[String(i)][1]);
	        
	        g.append("text")
	          .attr("class", "legendText")
	          .attr("x", w - 50)
	          .attr("y", 450 + i * 25 + 8)
	          .attr("height",30)
	          .attr("width",100)
	          .style("fill", color_hash[String(i)][1])
	          .text(color_hash[String(i)][0]);

	      });

	});
};
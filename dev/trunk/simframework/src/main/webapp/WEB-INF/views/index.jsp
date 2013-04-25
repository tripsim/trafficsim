<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>TRIPS</title>
<link rel="stylesheet" href="<c:url value="/resources/styles/style.css"/>"></link>

<script src="http://maps.google.com/maps/api/js?v=3.6&sensor=false"></script>
<script src="<c:url value="/resources/scripts/proj4js-compressed.js"/>"></script>
<script
	src="<c:url value="/resources/scripts/openlayers/lib/OpenLayers.js"/>"></script>
<script src="<c:url value="/resources/scripts/simulation.js"/>"></script>
</head>

<body onload="simulation.initMap();">
	<div id="map"></div>
	<div id ="control">
		<button onclick="simulation.load();">run</button>
		<button onclick="simulation.animate();">animate</button>
	</div>
	<div id="dev">
		<div id="proj-900913"></div>
		<div id="proj-4326"></div>
		<div id="Text"></div>
	</div>
</body>
</html>

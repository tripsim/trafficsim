<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>TRIPS</title>
<style type="text/css">
html,body,#map {
	margin: 0;
	width: 100%;
	height: 100%;
}

#text {
	position: absolute;
	bottom: 1em;
	left: 1em;
	width: 800px;
	z-index: 20000;
	background-color: white;
	padding: 0 0.5em 0.5em 0.5em;
}
</style>

<script src="http://maps.google.com/maps/api/js?v=3.6&sensor=false"></script>
<script src="<c:url value="/resources/scripts/proj4js-compressed.js"/>"></script>
<script
	src="<c:url value="/resources/scripts/openlayers/lib/OpenLayers.js"/>"></script>
<script src="<c:url value="/resources/scripts/simulation.js"/>"></script>
</head>

<body onload="init();">
	<div id="map"></div>
	<div id="text">
		<div id="proj-1"></div>
		<div id="proj-2"></div>
		<div id="proj-3"></div>
		<div id="proj-4"></div>
		<div>
			<button onclick="rerun();">rerun</button>
		</div>
	</div>
</body>
</html>

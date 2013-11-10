<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>TRIPS</title>
<link rel="stylesheet"
	href="<c:url value="/resources/styles/style.css"/>"></link>
<link rel="stylesheet"
	href="<c:url value="/resources/styles/style-dev.css"/>"></link>

<script src="http://maps.google.com/maps/api/js?v=3.6&sensor=false"></script>
<script
	src="<c:url value="/resources/scripts/lib/proj4js-compressed.js"/>"></script>
<script
	src="<c:url value="/resources/scripts/lib/openlayers/OpenLayers.js"/>"></script>
<script
	src="<c:url value="/resources/scripts/lib/jquery-2.0.3.min.js"/>"></script>
<script src="<c:url value="/resources/scripts/simulation.js"/>"></script>
<script src="<c:url value="/resources/scripts/ui.js"/>"></script>
</head>

<body onload="simulation.initMap();">
	<div id="map"></div>

	<div id="user-feedback"></div>
	
	<%-- quick demo user interface --%>
	<div id="user-interface">
		<div id="user-interface-network">Network Panel</div>
		<div id="user-interface-links" class="sub">Links</div>
		<div id="user-interface-nodes" class="sub">Nodes</div>
		<div class="sub">ODs</div>
		<div>Vehicle Panel</div>
		<div class="sub">Vehicle Type</div>
		<div class="sub">Vehicle Composition</div>
		<div>Simulator Panel</div>
		<div>Online Coding</div>
		<div class="buttons">
			<span onclick="simulation.createNew();">Create New Scenario</span>
		</div>
	</div>

	<div id="user-configuration"></div>
	<div id="ajax-indicator"></div>
</body>
</html>

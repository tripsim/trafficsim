<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>TRIPS</title>
<link rel="stylesheet"
	href="<c:url value="/resources/styles/style.css"/>"></link>

<script src="http://maps.google.com/maps/api/js?v=3.6&sensor=false"></script>
<script src="<c:url value="/resources/scripts/proj4js-compressed.js"/>"></script>
<script
	src="<c:url value="/resources/scripts/openlayers/lib/OpenLayers.js"/>"></script>
<script src="<c:url value="/resources/scripts/simulation.js"/>"></script>
</head>

<body onload="simulation.initMap();">
	<div id="map"></div>

	<%-- quick demo user interface --%>
	<div id="user-interface">
		<div>Network Panel</div>
		<div class="sub">Links</div>
		<div class="sub">Nodes</div>
		<div class="sub">ODs</div>
		<div>Vehicle Panel</div>
		<div class="sub">Vehicle Type</div>
		<div class="sub">Vehicle Composition</div>
		<div>Simulator Panel</div>
		<div>Online Coding</div>
		<div class="buttons">
			<span onclick="simulation.load();">Run</span>
			<span onclick="simulation.animate();">Animate</span>
		</div>
		
	</div>
	<div id="user-configuration">
		<h2>Online Coding and Compilation Panel</h2>
		<p>
			Model: <select>
				<option value="0">My Car-Following Model</option>
				<option value="1">My Lane-Changing Model</option>
			</select>
		</p>
		<p>
			Interface: <select>
				<option value="0">Car-Following Model</option>
				<option value="1">Lane-Changing Model</option>
			</select>
		</p>
		<textarea wrap="off">
		Codes!		
		</textarea>
	</div>

	<%-- DEV mouse position indicator --%>
	<div id="dev">
		<div id="proj-900913"></div>
		<div id="proj-4326"></div>
		<div id="Text"></div>
	</div>
</body>
</html>

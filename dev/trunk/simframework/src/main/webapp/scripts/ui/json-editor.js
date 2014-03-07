var jsonEditor = {
	open : function() {
		simwebhelper.getJson('scenario/export', function(json) {
			jQuery('#user-editor').show();
			var container = document.getElementById("user-json-editor");
			var editor = new jsoneditor.JSONEditor(container);
			editor.set(json);
		});
	},
	close : function() {
		jQuery('#user-json-editor').empty().parent().hide();
	}
};
jQuery(document).ready(function() {
	/* close panel */
	jQuery('#user-editor-close').on('click', function() {
		jsonEditor.close();
	});
});
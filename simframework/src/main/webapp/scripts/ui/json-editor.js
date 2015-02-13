/*
 * Copyright (C) 2014 Xuan Shi
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
var jsonEditor = {
	open : function() {
		simwebhelper.getJson('project/export', function(json) {
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
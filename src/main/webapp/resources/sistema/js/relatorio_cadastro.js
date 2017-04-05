function hideToolTextEdit(){
	var quill = new Quill('#textEditorWidget', {
		 modules: {
		            toolbar : false
		          },
		  theme: 'snow' // or 'bubble'
		});
		quill.enable(false);
}
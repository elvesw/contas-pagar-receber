
if (typeof CKEDITOR != "undefined") {
	CKEDITOR.editorConfig = function(config) {
		/*console.log(CKEDITOR.basePath);
		console.log(CKEDITOR_GETURL);*/
		
		var contextPath = window.location.pathname.substring(0,window.location.pathname.indexOf("/", 2));
		config.filebrowserImageBrowseUrl = contextPath + '/erro/esse.jsf';
		config.filebrowserImageUploadUrl = contextPath + '/upload';
		config.filebrowserBrowseUrl = contextPath + '/imagem';
		config.toolbar = [['Bold','Italic','Underline','StrikeThrough','RemoveFormat','-','Undo','Redo','-','Cut','Copy','Paste','Find','Replace','-','Outdent','Indent','-'],
		                  ['Styles','Format','Font','FontSize'],
		                  ['NumberedList','BulletedList','CreateDiv','ShowBlocks','-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','Image','Table','-','TextColor','BGColor','Source']
						];
		/*Css customizado*/
		config.contentsCss = contextPath+'/resources/sistema/css/ckeditorConfig.css';
		
		/*Retitando o border das tabelas*/
		config.startupShowBorders = false;
		//config.removePlugins = 'image';
		config.extraPlugins='tableresize';
		
		/*Removendo o title com espaço, Editor de Rich Text...*/
		config.title = " ";


		
		
	
		
		
		var myinstances = [];

		//Loop para instancias
		for(var i in CKEDITOR.instances) {
			
			var nomeEditor = CKEDITOR.instances[i].name;
			
			/*Alterando cor do layouto somente no documento modelo*/
			if(nomeEditor=='formPrincipal:editorDocModelo'){
				config.uiColor = '#313B3F';
			}
			
			
		   /* Isso retorna cada instância como objeto. print para eu ver e acompanhar*/
		    CKEDITOR.instances[i];
		    console.log( CKEDITOR.instances[i] );
		   
		    /* Este retorna os nomes dos textareas / id das instâncias.*/
		    //CKEDITOR.instances[i].name;
		    //console.log( CKEDITOR.instances[i].name );

		    /* Retorna o valor inicial da textarea */
		   // CKEDITOR.instances[i].value;
		 
		   /* Isso atualiza o valor do textarea das instâncias do CK.*/
		  // CKEDITOR.instances[i].updateElement();

		   /* Isso recupera os dados de cada instância e armazena-os em uma matriz associativa com
		    * os nomes das áreas de texto como chaves ...*/
		   //myinstances[CKEDITOR.instances[i].name] = CKEDITOR.instances[i].getData(); 
		   
		   /*Recupera o texto*/
		  /* CKEDITOR.instances[CKEDITOR.instances[i].name].getData();
		   console.log(CKEDITOR.instances[CKEDITOR.instances[i].name].getData());*/
		   
		
		}
	};
	
	
}

function ckePrint(editor) {
    var i = CKEDITOR.instances[CKEDITOR.instances[editor].name];
    i.execCommand( 'print' );
}

function ckeNewPage(editor) {
    var i = CKEDITOR.instances[CKEDITOR.instances[editor].name];
    i.execCommand( 'newpage' );
}


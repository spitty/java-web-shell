function execute(){
	var query = $("#query").val();
	switch(query){
		case "":
			echo();
			break;
		case "clear":
			echo();
			$("#results").html("");
			break;
		default:
			$.ajax({
				type : 'POST',
				url : '/ShellPostProcessor',
				cache : false,
				data : {
					query : query
				},
				error : function () {
					echo("Connection error", query);
				},
				success : function (data) {
					echo (data);
				}
			});
	}
}
function echo(text, query){
	if (!text) text="";
	if (!query) query="";	
	$("#results").append("<div class='result'>>"+text+"</div>");
	$("#query").val(query);
}
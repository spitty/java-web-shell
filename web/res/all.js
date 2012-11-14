var lastQuery = 0;
if (!store.get("last"))
	store.set("last", 1);
function binding(){
	/*$(document).bind('keydown', 'tab',function (evt){
		alert(evt);
		tab();
		return false;
	});*/
	$(document).bind('keyup', 'up',function (evt){
		last_queries(1);
		return false;
	});
	$(document).bind('keydown', 'down',function (evt){
		last_queries(0);
		return false;
	});
}
$(document).ready(binding);
/*
jQuery(document).bind('keyup', 'up',function (evt){
	
	return false;
});
jQuery(document).bind('keyup', 'down',function (evt){
	last_queries(0);
	return false;
});*/
function execute(){
	var query = $("#query").val();
	if (query){
		storing(query);
	}
	
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
				url : 'ShellPostProcessor',
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
	$("#results").append("<div class='result'>> "+text+"</div>");
	$("#query").val(query);
}
function tab(){
	
}
function last_queries(updown){
	if (!lastQuery){
		lastQuery = Number(store.get("last"));
	}
	if (updown==1){
		if (lastQuery==1) return;
		lastQuery--;
		$("#query").val(store.get("q["+lastQuery+"]"));
	}else{
		if (lastQuery >= Number(store.get("last"))) return;
		lastQuery++;
		if (lastQuery == Number(store.get("last")))
			$("#query").val("");
		else
			$("#query").val(store.get("q["+lastQuery+"]"));
	}
}
function storing(query){
	var last = store.get("last");
	last = Number(last);
	store.set("q["+last+"]", query);
	last++;
	store.set("last", last);
}
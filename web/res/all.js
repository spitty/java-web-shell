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
var ws;
function webSocketConnect() {
	ws = new WebSocket("ws://localhost:8080/java-web-shell/ShellIORegistrator/");
	ws.onopen = function(event) {
	};
	ws.onmessage = function(event) {
		echo (event.data);
		window.scrollTo(0, document.body.scrollHeight);
	};
	ws.onclose = function(event) {
		
	};
	ws.onerror = function(event) {
		alert("Error!");
	};
}


$(document).ready(binding);
$(document).ready(webSocketConnect);
/*
jQuery(document).bind('keyup', 'up',function (evt){
	
	return false;
});
jQuery(document).bind('keyup', 'down',function (evt){
	last_queries(0);
	return false;
});*/

function execute() {
	var query = $("#query").val();
	
	switch(query){
	case "":
		echo(null, null, ">");
		break;
	case "clear":
		storing(query);
		echo();
		$("#results").html("");
		break;
	default:
		storing(query);
		echo (query, null, '>');
		ws.send(query);
	};
}

function execute_old(){
	var query = $("#query").val();
	if (query){
		storing(query);
	}
	
	switch(query){
		case "":
			echo(null, null, ">");
			break;
		case "clear":
			echo();
			$("#results").html("");
			break;
		default:
			$.ajax({
				type : 'POST',
				url : 'PostRequestManager',
				cache : false,
				data : {
					query : query
				},
				error : function () {
					echo (query, null, '>');
					echo("Connection error", query);
				},
				success : function (data) {
					echo (query, null, '>');
					echo (data);
				}
			});
	}
}
function echo(text, query, delim){
	if (!text) text="";
	if (!query) query="";
	if (!delim) 
		delim = "";
	else
		delim = delim+" ";
	if (text != "") {
		$("#results").append("<div class='result'>"+(delim+text).split("<").join("&lt;").split(">").join("&gt;")+"</div>");
	}; 
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
	lastQuery = last;
}
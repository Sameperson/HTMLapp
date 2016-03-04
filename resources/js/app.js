var showed = false;


var item = document.getElementById("guess0");
item.onclick = function () {
    item.innerHTML = "Button pressed!";
    item.write("TEST");
}

$(".spoiler span").hide();
$(".spoiler").append("<br><button id=\"button0\">Reveal Spoiler</button>");
$(".spoiler button").click(function(){
    if(!showed) {
        $(this).prev().prev().show();
        //$(".spoiler span").show();
        document.getElementById("button0").innerHTML="Hide spoiler";
    }
    else {
        $(".spoiler span").hide();
        document.getElementById("button0").innerHTML="Show spoiler";
        //$(this).remove();
    }
    showed = !showed;


})
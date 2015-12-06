$(document).ready( function() {
    
    $("#go-button").on("click", searchResults);

});

function goButtonOnClick() {
    var query = $("#search-box").val();
    if (query) {
        $.ajax({
            url: "/API/"+ query,
            //data: data,
            async: false,
            //cache: false,
            //contentType: false,
            //processData: false,
            type: 'post',
            datatype: "json",
            success: searchResultsOnSuccess,
            error: searchResultsOnError,
            complete: searchResultsOnComplete
        })
    }
}

function searchResults() {
    $.ajax({
        dataType: "json",
        url: "../json/searchResults.json",
        //data: data,
        success: searchResultsOnSuccess
    });
}
function searchResultsOnSuccess(items){
    $.each(items, function(index, value) {
        var result = JSON.parse(value);
        $(".page-name").text(result["pageName"]);
        $(".page-url").href(result["pageUrl"]);
        $(".page-snippet").text(result["pageSnippet"]);
    });
}

function searchResultsOnError(error) {
    var errorSpan = "<span>" + error + "</span>";
    $("#search-form").append(errorSpan);
}

function searchResultsOnComplete() {
    // DO nothing
}


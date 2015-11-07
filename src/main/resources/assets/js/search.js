$(document).ready( function() {
  $("#search").click(function () {

    var query = $("#search").val();
    $.ajax({
      url: "/API/"+title,
      data: data,
      async: false,
      cache: false,
      contentType: false,
      processData: false,
      type: 'POST'
    })
  });
});




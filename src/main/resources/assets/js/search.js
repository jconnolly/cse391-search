$(document).ready( function() {
    $('#autocomplete').autocomplete({
        serviceUrl: '/API/autocomplete',
        onSelect: function (suggestion) {
            alert('You selected: ' + suggestion.value + ', ' + suggestion.data);
        }
    });
});

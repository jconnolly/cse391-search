$(document).ready( function() {
    $('#autocomplete').autocomplete({
        serviceUrl: '/API/autocomplete',
        onSelect: function (suggestion) {
            console.log(suggestion.value);
            window.location = '/API/results?q=' + suggestion.value;
        }
    });
});

$.ajax({
    url: "/test/gcs/download",
    type: "POST",
    data: {},
    dataType: "json",
    success: function (result) {
        switch (result) {
            case true:
                processResponse(result);
                break;
            default:
                resultDiv.html(result);
        }
    },
    error: function (xhr, ajaxOptions, thrownError) {
        alert(xhr.status);
        alert(thrownError);
    }
});
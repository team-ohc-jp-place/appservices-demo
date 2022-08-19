if (!!window.EventSource) {
    var eventSource = new EventSource("/stream");
    eventSource.onmessage = function(event) {
        data = JSON.parse(event.data);

        //console.log(data);

        var row = '<tr><td>' + data.orderId + '</td><td>' + data.orderType + '</td><td>' + data.orderItemName + '</td><td>' + data.quantity + '</td><td>' + data.price + '</td><td>' + data.shipmentAddress + '</td><td>' + data.zipCode + '</td></tr>';
        $('#tbody').append(row);
    };
} else {
    window.alert("EventSource not available on this browser.")
}



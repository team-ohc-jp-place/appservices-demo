if (!!window.EventSource) {
    var eventSource = new EventSource("/stream");
    eventSource.onmessage = function(event) {
        data = JSON.parse(event.data);

        console.log(data);

        //var row = '<tr><td>' + data.accountId + '</td><td>' + data.orderId + '</td><td>' + data.orderType + '</td><td>' + data.orderItemName + '</td><td>' + data.openDate + '</td><td>' + data.symbol + '</td><td style="text-align: right;">' + data.price + '</td><td style="text-align: right;">' + data.orderFee + '</td></tr>';
        var row = '<tr><td>' + data.orderId + '</td><td>' + data.orderType + '</td><td>' + data.orderItemName + '</td><td>' + data.quantity + '</td><td>' + data.price + '</td><td>' + data.shipmentAddress + '</td><td>' + data.zipCode + '</td><td>' + data.totalAmount + '</td><td>' + data.deliveryFee + '</td><td>' + data.stateCode + '</td><td>' + data.stateName + '</td></tr>';
        $('#tbody').append(row);
    };
} else {
    window.alert("EventSource not available on this browser.")
}



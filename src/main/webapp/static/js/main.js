(function () {
    $('#addUserButton').click(function () {
        document.location.href = "/adminPanel/addUser"
    });
    $('#addContractButton').click(function () {
        document.location.href = "/adminPanel/addContract"
    });
    $('#addTariffButton').click(function () {
        document.location.href = "/adminPanel/addTariff"
    });
    function addTariffTableBehavior(){
        $("#addTariffAddedOptions").on("click","tr.move-row", function () {
            if ( $(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });
        $("#addTariffAvailableOptions").on("click","tr.move-row", function () {
            if ( $(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });

        $('#addTariffAddOption').on('click', function () {
            var tr = $("#addTariffAvailableOptions tr.add-tariff-table-selected").remove().clone();
            tr.removeClass('add-tariff-table-selected');
            $("#addTariffAddedOptions").append(tr);
        });
        $('#addTariffDelOption').on('click', function () {
            var tr = $("#addTariffAddedOptions tr.add-tariff-table-selected").remove().clone();
            tr.removeClass('add-tariff-table-selected');
            $("#addTariffAvailableOptions").append(tr);
        });
    }


    $('#addTariff').on('click', function () {
        addOptions();
    });

    <!--- How to catch suceess? always fail -->
    function addOptions(){
        var part1 = $('#addTariffAddedOptions').tableToJSON() ;
        var part2 = {name: $('#name').val(), price: $('#price').val()};
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");
        $.ajax({
            beforeSend:function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'text/html; charset=utf-8'
            },
            type: "POST",
            url: "/adminPanel/addTariff",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify({getOptionsAsJsonDtoList:part1, tariffDto:part2}) ,
            contentType:  "application/json; charset=utf-8",
            dataType: "json"
        }).fail(function (xhr,a,error) {
            alert(error);
        })
    }

    $("#usersTable").on("click","tr.user-row", function () {
        document.location.href = "/adminPanel/user/"+$(this).find("td:first").html();
    });
    $(".contracts-table").on("click","tr.contract-row", function () {
        document.location.href = "/adminPanel/contract/"+$(this).find("td:first").html();
    });
    $(".tariffs-table").on("click","tr.tariff-row", function () {
        document.location.href = "/adminPanel/tariff/"+$(this).find("td:first").html();
    });

    function tariffTableBehavior(){
        $("#tariffAddedOptions").on("click","tr.move-row", function () {
            if ( $(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });
        $("#tariffAvailableOptions").on("click","tr.move-row", function () {
            if ( $(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });

        $('#tariffAddOption').on('click', function () {
            // OPTIONS RULES
            tr = $("#tariffAvailableOptions tr.add-tariff-table-selected");
            var rows = $("#tariffAvailableOptions").tableToJSON().slice(tr[0].rowIndex-1,tr[tr.length-1].rowIndex);
            console.log(JSON.stringify(rows));
            //console.log($('#tariffAvailableOptions').tableToJSON({onlyColumns:columns}));
            //$('#tariffAvailableOptions').tableToJSON({onlyColumns:[3, 4]})


            // $.ajax({
            //     beforeSend:function (xhr) {
            //         xhr.setRequestHeader(header, token);
            //     },
            //     headers: {
            //         'Content-Type': 'application/json',
            //         'Accept': 'text/html; charset=utf-8'
            //     },
            //     type: "POST",
            //     url: "/adminPanel/tariff/addOptions",
            //     // The key needs to match your method's input parameter (case-sensitive).
            //     data: JSON.stringify({getOptionsAsJsonDtoList:part1, tariffDto:part2}) ,
            //     contentType:  "application/json; charset=utf-8",
            //     dataType: "json"
            // }).fail(function (xhr,a,error) {
            //     alert(error);
            //
            // var tr = $("#tariffAvailableOptions tr.add-tariff-table-selected").remove().clone();
            // tr.removeClass('add-tariff-table-selected');
            // $("#tariffAddedOptions").append(tr);
        });

        $('#tariffDelOption').on('click', function () {
            // OPTIONS RULES

            // var tr = $("#tariffAddedOptions tr.add-tariff-table-selected").remove().clone();
            // tr.removeClass('add-tariff-table-selected');
            // $("#tarifffAvailableOptions").append(tr);
        });
    }

    addTariffTableBehavior();
    tariffTableBehavior();



})();
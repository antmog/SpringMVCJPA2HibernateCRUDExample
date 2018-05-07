(function () {

    $(".users-table").on("click","tr.user-row", function () {
        document.location.href = "/adminPanel/user/"+$(this).find("td:first").html();
    });
    $(".contracts-table").on("click","tr.contract-row", function () {
        document.location.href = "/adminPanel/contract/"+$(this).find("td:first").html();
    });
    $(".tariffs-table").on("click","tr.tariff-row", function () {
        document.location.href = "/adminPanel/tariff/"+$(this).find("td:first").html();
    });
    $(".options-table").on("click","tr.option-row", function () {
        document.location.href = "/adminPanel/option/"+$(this).find("td:first").html();
    });


    function adminPanel(){

        $(".searchUserByNumber").on("click", function () {
            if($('.searchUserByNumberInput').val() === ""){
                alert("its null");
            }else{
                searchUserByNumber();
            }
        });

        function searchUserByNumber() {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            var phoneNumber =$('.searchUserByNumberInput').val();
            $.ajax({
                beforeSend:function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'application/json'
                },
                type: "POST",
                url: "/adminPanel/user/searchUserByNumber",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({ phoneNumber : phoneNumber }),
                dataType: "json"
            }).done(function( msg ) {
                updateUserTable(msg);
            }).fail(function( jqXHR, textStatus ) {
                alert( "Request failed: " + textStatus );
            });
        }
        function updateUserTable(msg){
            document.location.href = "/adminPanel/user/"+msg.id;
        }


        $('#addUserButton').click(function () {
            document.location.href = "/adminPanel/addUser"
        });
        $('#addContractButton').click(function () {
            document.location.href = "/adminPanel/addContract"
        });
        $('#addTariffButton').click(function () {
            document.location.href = "/adminPanel/addTariff"
        });
        $('#addOptionButton').click(function () {
            document.location.href = "/adminPanel/addOption"
        });
    }


    function userPanel(){
        $("#deleteUser").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'text/html; charset=utf-8',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/adminPanel/user/deleteUser",
                data: user_id.toString()
            }).done(function (msg) {
                if(msg==="ok"){
                    document.location.href = "/adminPanel"
                }else{
                    alert("Can't delete this user. Try to delete his contracts.");
                }
            }).fail(function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            });
        });
        var newStatus;
        $('#addContractToUserButton').click(function () {
            document.location.href = "/adminPanel/addContractToUser/"+user_id
        });

        $('#blockUserButton').click(function () {
            newStatus = 'BLOCKED';
            globalSetNewStatus("user",newStatus,user_id);
        });
        $('#unBlockUserButton').click(function () {
            newStatus = 'ACTIVE';
            globalSetNewStatus("user",newStatus,user_id);
        });
        $('#deactivateUserButton').click(function () {
            newStatus = 'INACTIVE';
            globalSetNewStatus("user",newStatus,user_id);
        });

        function updateUserInfo() {
            var input,td,button,editing,canselButton;
            var oldRow, oldValue = "";
            $(".userEditableTable").on("click","tbody tr", function () {
                if ( $(this).hasClass('editable')) {
                    oldValue = $(this).find("td:eq(1)").remove().clone();
                    input = document.createElement('input');
                    button = document.createElement('button');
                    canselButton = document.createElement('button');
                    input.type = 'text';
                    input.value = oldValue.html();
                    input.size = 10;
                    button.innerHTML = 'OK';
                    canselButton.innerHTML = 'X'
                    $(this).append(input,button,canselButton);
                    input.focus();
                    input.selectionStart = input.value.length;
                    $(this).removeClass('editable');
                    $(this).addClass('editing');
                }
            });
            $(document).on("focusout",".userEditableTable tr.editing", function(event) {
                if(button === event.relatedTarget){
                    var token = $("meta[name='_csrf']").attr("content");
                    var header = $("meta[name='_csrf_header']").attr("content");
                    var editing = $(".userEditableTable tr.editing");
                    var value = editing.find("input").val();
                    $.ajax({
                        beforeSend:function (xhr) {
                            xhr.setRequestHeader(header, token);
                        },
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'text/html; charset=utf-8'
                        },
                        type: "POST",
                        url: "/user/editUser",
                        // The key needs to match your method's input parameter (case-sensitive).
                        data: JSON.stringify({ dataInstance:editing.find("td:first").html() , value : value, userId: user_id })
                    }).done(function( msg ) {
                        if (msg === "ok") {
                            oldValue.html(value);
                        }
                    }).fail(function( jqXHR, textStatus ) {
                        alert( "Request failed: " + textStatus );
                    });
                }
                $(this).removeClass('editing').addClass('editable');
                $(this).find("input").remove();
                $(this).find("button").remove();
                $(this).append(oldValue);
            });

        }

        updateUserInfo();
    }

    function contractPanel(){
        $("#deleteContract").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'text/html; charset=utf-8',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/adminPanel/contract/deleteContract",
                data: contract_id.toString()
            }).done(function (msg) {
                document.location.href = "/adminPanel"
            }).fail(function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            });
        });
        $("#contractCurrentOptions").on("click","tr.move-row", function () {
            if ( $(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });
        $("#contractAvailableOptions").on("click","tr.move-row", function () {
            if ( $(this).hasClass('add-tariff-table-selected')) {
                $(this).removeClass('add-tariff-table-selected');
            } else {
                $(this).addClass('add-tariff-table-selected');
            }
        });

        var newStatus;
        $('#blockContractButton').click(function () {
            newStatus = 'BLOCKED';
            globalSetNewStatus("contract",newStatus,contract_id);
        });
        $('#unBlockContractButton').click(function () {
            newStatus = 'ACTIVE';
            globalSetNewStatus("contract",newStatus,contract_id);
        });
        $('#deactivateContractButton').click(function () {
            newStatus = 'INACTIVE';
            globalSetNewStatus("contract",newStatus,contract_id);
        });

        // + logic in addContract page - same class items
        $('#switchTariff').on('click',function () {
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
                url: "/adminPanel/contract/switchTariff",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({tariffId:$('#addContractTariffs tr.add-tariff-table-selected').find('td:first').html() , contractId : contract_id})
            }).done(function( msg ) {
                if (msg === "ok") {
                    alert(msg);
                }
            }).fail(function( jqXHR, textStatus ) {
                alert( "Request failed: " + textStatus );
            });
        });

        $('#contractAddOption').on('click', function () {
            // OPTIONS RULES
            var tr = $("#contractAvailableOptions tr.add-tariff-table-selected").clone();
            var table = $('#parseTable');
            for (var i = 0; i < tr.length; i++) {
                table.append(tr[i]);
            }
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
                url: "/adminPanel/contract/addOptions",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({getOptionsAsJsonDtoList:table.tableToJSON(), contractId : contract_id})
            }).done(function( msg ) {
                if (msg === "ok") {
                    var tr = $("#contractAvailableOptions tr.add-tariff-table-selected").clone();
                    tr.removeClass('add-tariff-table-selected');
                    $("#contractCurrentOptions").append(tr);
                }
            }).fail(function( jqXHR, textStatus ) {
                alert( "Request failed: " + textStatus );
            });
            $('#parseTable tr.move-row').remove();
        });

        $('#contractDelOption').on('click', function () {
            // OPTIONS RULES

            var tr = $("#contractCurrentOptions tr.add-tariff-table-selected").clone();
            var table = $('#parseTable');
            for (var i = 0; i < tr.length; i++) {
                table.append(tr[i]);
            }
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
                url: "/adminPanel/contract/delOptions",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({getOptionsAsJsonDtoList:table.tableToJSON(), contractId : contract_id})
            }).done(function( msg ) {
                if (msg === "ok") {
                    var tr = $("#contractCurrentOptions tr.add-tariff-table-selected").remove().clone();
                    tr.removeClass('add-tariff-table-selected');
                    $("#contractAvailableOptions").append(tr);
                }
            }).fail(function( jqXHR, textStatus ) {
                alert( "Request failed: " + textStatus );
            });
            $('#parseTable tr.move-row').remove();
        });
    }

    function globalSetNewStatus(entity,status,entity_id){
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
            url: "/" +entity+ "/setStatus",
            // The key needs to match your method's input parameter (case-sensitive).
            data: JSON.stringify({ entityId : entity_id, entityStatus : status})
        }).done(function( msg ) {
            if (msg === "ok") {
                location.reload();
            }
        }).fail(function( jqXHR, textStatus ) {
            alert( "Request failed: " + textStatus );
        });
    }


    function addTariffPanel() {
        $('#addTariff').on('click', function () {
            addOptions();
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
            }).done(function(msg) {
                if(msg==="ok"){
                    document.location.href = "/adminPanel"
                }else{
                    alert(msg);
                }
            }).fail(function (xhr,a,error) {
                alert(error);
            })
        }
        addTariffTableBehavior();
    }





    function addContractPanel() {
        $('#addContract').on('click', function () {
            addContract();
        });

        function addContract() {
            var part1 = $('#addContractAddedOptions').tableToJSON() ;
            var tariffId =  $("#addContractTariffs tr.add-tariff-table-selected").find('td:first').html();
            console.log(tariffId);
            var part2 = {userId: $('#user_id').val(), phoneNumber: $('#phoneNumber').val(), tariffId: tariffId};
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend:function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'application/json'
                   // 'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/adminPanel/addContract",
                // The key needs to match your method's input parameter (case-sensitive).
                data: JSON.stringify({getOptionsAsJsonDtoList:part1, contractDto:part2}) ,
            }).done(function(msg) {
                if(msg==="ok"){
                    document.location.href = "/adminPanel"
                }else{
                    alert(msg);
                }
            }).fail(function (xhr,a,error) {
                alert(error);
            })
        }

        function addContractTableBehavior(){
            $("#addContractAddedOptions").on("click","tbody tr", function () {
                if ( $(this).hasClass('add-tariff-table-selected')) {
                    $(this).removeClass('add-tariff-table-selected');
                } else {
                    $(this).addClass('add-tariff-table-selected');
                }
            });
            $("#addContractAvailableOptions").on("click","tbody tr", function () {
                if ( $(this).hasClass('add-tariff-table-selected')) {
                    $(this).removeClass('add-tariff-table-selected');
                } else {
                    $(this).addClass('add-tariff-table-selected');
                }
            });
            $("#addContractTariffs").on("click","tr.t-row", function () {
                if ( !$(this).hasClass('add-tariff-table-selected')) {
                    $(this).addClass('add-tariff-table-selected').siblings().removeClass('add-tariff-table-selected');
                }
                var token = $("meta[name='_csrf']").attr("content");
                var header = $("meta[name='_csrf_header']").attr("content");
                $.ajax({
                    beforeSend:function (xhr) {
                        xhr.setRequestHeader(header, token);
                    },
                    headers: {
                        'Content-Type': 'text/html; charset=utf-8'
                    },
                    type: "POST",
                    url: "/tariffOptions",
                    // The key needs to match your method's input parameter (case-sensitive).
                    data: $(this).find('td:first').html(),
                    dataType: "json"
                }).done(function( json ) {
                    $("#addContractAvailableOptions tbody tr").remove();
                    var tbl_body = document.createElement("tbody");
                    var odd_even = false;
                    $.each(json, function() {
                        var tbl_row = tbl_body.insertRow();
                        tbl_row.className = odd_even ? "odd" : "even";
                        $.each(this, function(k , v) {
                            var cell = tbl_row.insertCell();
                            cell.appendChild(document.createTextNode(v.toString()));
                        });
                        odd_even = !odd_even;
                    });
                    $("#addContractAvailableOptions").append(tbl_body);
                }).fail(function( jqXHR, textStatus ) {
                    alert( "Request failed: " + textStatus );
                });
            });

            $('#addContractAddOption').on('click', function () {
                var tr = $("#addContractAvailableOptions tr.add-tariff-table-selected").clone();
                tr.removeClass('add-tariff-table-selected');
                $("#addContractAddedOptions").append(tr);
            });
            $('#addContractDelOption').on('click', function () {
                $("#addContractAddedOptions tr.add-tariff-table-selected").remove();
            });
        }

        addContractTableBehavior();
    }

    function tariffPanel() {
        $("#archiveTariff").on("click", function () {
            newStatus = 'INACTIVE';
            globalSetNewStatus("tariff",newStatus,tariff_id);
        });
        $("#unArchiveTariff").on("click", function () {
            newStatus = 'ACTIVE';
            globalSetNewStatus("tariff",newStatus,tariff_id);
        });
        $("#deleteTariff").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'text/html; charset=utf-8',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/adminPanel/user/deleteTariff",
                data: tariff_id.toString()
            }).done(function (msg) {
                if(msg==="ok"){
                    document.location.href = "/adminPanel"
                }else{
                    alert("Can't delete tariff. It's still used in some contracts.");
                }
            }).fail(function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            });
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
                var tr = $("#tariffAvailableOptions tr.add-tariff-table-selected").clone();
                var table = $('#parseTable');
                for (var i = 0; i < tr.length; i++) {
                    table.append(tr[i]);
                }
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
                    url: "/adminPanel/tariff/addOptions",
                    // The key needs to match your method's input parameter (case-sensitive).
                    data: JSON.stringify({getOptionsAsJsonDtoList:table.tableToJSON(), tariffId : tariff_id})
                }).done(function( msg ) {
                    if (msg === "ok") {
                        var tr = $("#tariffAvailableOptions tr.add-tariff-table-selected").remove().clone();
                        tr.removeClass('add-tariff-table-selected');
                        $("#tariffAddedOptions").append(tr);
                    }
                }).fail(function( jqXHR, textStatus ) {
                    alert( "Request failed: " + textStatus );
                });
                $('#parseTable tr.move-row').remove();
            });

            $('#tariffDelOption').on('click', function () {
                // OPTIONS RULES

                var tr = $("#tariffAddedOptions tr.add-tariff-table-selected").clone();
                var table = $('#parseTable');
                for (var i = 0; i < tr.length; i++) {
                    table.append(tr[i]);
                }
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
                    url: "/adminPanel/tariff/delOptions",
                    // The key needs to match your method's input parameter (case-sensitive).
                    data: JSON.stringify({getOptionsAsJsonDtoList:table.tableToJSON(), tariffId : tariff_id})
                }).done(function( msg ) {
                    if (msg === "ok") {
                        var tr = $("#tariffAddedOptions tr.add-tariff-table-selected").remove().clone();
                        tr.removeClass('add-tariff-table-selected');
                        $("#tariffAvailableOptions").append(tr);
                    }
                }).fail(function( jqXHR, textStatus ) {
                    alert( "Request failed: " + textStatus );
                });
                $('#parseTable tr.move-row').remove();
            });
        }
        tariffTableBehavior();
    }

    function optionPanel(){
        $("#deleteOption").on("click", function () {
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                beforeSend: function (xhr) {
                    xhr.setRequestHeader(header, token);
                },
                headers: {
                    'Content-Type': 'text/html; charset=utf-8',
                    'Accept': 'text/html; charset=utf-8'
                },
                type: "POST",
                url: "/adminPanel/option/deleteOption",
                data: option_id.toString()
            }).done(function (msg) {
                if(msg==="ok"){
                    document.location.href = "/adminPanel"
                }else{
                    alert("Can't delete tariff. It's still used in some contracts.");
                }
            }).fail(function (jqXHR, textStatus) {
                alert("Request failed: " + textStatus);
            });
        });
    }

    optionPanel();
    adminPanel();
    userPanel();
    contractPanel();
    tariffPanel();
    addTariffPanel();
    addContractPanel();

})();
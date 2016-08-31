/**
 * Created by JooLiu on 2016/8/26.
 */
function initializeUploader() {
    var container = document.getElementById('container');
    var url = "/applicant/uploadFile";
    var filters = { max_file_size : '10mb',
                    mime_types: [
                        {title : "Image files", extensions : "jpg,gif,png"},
                        {title : "Zip files", extensions : "zip"}]};
    var init = { PostInit: onPostInit,
                  FilesAdded: onFilesAdded,
                  FilesRemoved: onFilesRemoved,
                  UploadProgress: onUploadProgress,
                  FileUploaded: onFileUploaded,
                  Error: onError};

    uploader = new plupload.Uploader({
        runtimes : 'html5,html4',
        browse_button : 'pickFiles', // you can pass in id...
        container: container, // ... or DOM Element itself
        url : url,
        filters : filters,
        unique_names: true,
        init:init
    });
    uploader.init();

    function onPostInit() {

    }

    function onFilesAdded(up, files) {
        plupload.each(files, function(file) {
            if (page_type == "purchase") {
                uploader.settings.url = "/applicant/uploadFile?file_id=" + file.id +
                    "&purchase_id=" + document.getElementById("purchase_id").value;
                document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/common/downloadFile?file_id=' + file.id + '&purchase_id=' +
                    document.getElementById("purchase_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size) + ')</a> <b></b>&nbsp;' +
                    '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                    'data-options="iconCls:\'icon-remove\',plain:true" ' +
                    'onclick=removeAttachFile(' + file.id + ')> 删除</a><br/></div>';
            } else {
                uploader.settings.url = "/applicant/uploadFile?file_id=" + file.id +
                    "&package_id=" + document.getElementById("packet_id").value;
                document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/common/downloadFile?file_id=' + file.id + '&packet_id=' +
                    document.getElementById("packet_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size) + ')</a> <b></b>&nbsp;' +
                    '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                    'data-options="iconCls:\'icon-remove\',plain:true" ' +
                    'onclick=removeAttachFile(' + file.id + ')> 删除</a><br/></div>';
            }
        });
        uploader.start();
    }

    function onFilesRemoved(up, files) {

    }

    function onUploadProgress(up, file) {
        document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent
            + "%</span>";
    }

    function onFileUploaded(up, file, resp) {

    }

    function onError() {
        document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
    }
}

var CHARS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
Math.uuid = function (len, radix) {
    var chars = CHARS, uuid = [], i;
    radix = radix || chars.length;
    if (len) {
        // Compact form
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random() * radix];
    } else {
        // rfc4122, version 4 form
        var r;
        // rfc4122 requires these characters
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';
        // Fill in random data.  At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random() * 16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }
    return uuid.join('');
}

function getReadableFileSizeString(fileSizeInBytes) {
    var i = -1;
    var byteUnits = [' KB', ' MB', ' GB', ' TB', 'PB', 'EB', 'ZB', 'YB'];
    do {
        fileSizeInBytes = fileSizeInBytes / 1024;
        i++;
    } while (fileSizeInBytes > 1024);

    return Math.max(fileSizeInBytes, 0.1).toFixed(1) + byteUnits[i];
};

function showNewPurchasePage() {
    baseData = null;
    $('#contentDiv').panel('setTitle','新建采购过程');
    $('#contentDiv').panel('refresh','../jsp/pages/new_purchase.jsp');
}

function showNewPackagePage() {
    baseData = null;
    $('#contentDiv').panel('setTitle', '新建分包');
    $('#contentDiv').panel('refresh', '../jsp/pages/new_package.jsp');
}

function onLeftMenuRightClick(e,node) {
    baseData = null;
    curNode = node;
    e.preventDefault();
    if (node.type == 'create') {
        $(this).tree('select', node.target);
        $('#menuPurchase').menu('show', {
            left: e.clientX,
            top: e.clientY
        });
    } else if (node.type == 'to_divide') {
        document.getElementById('purchase_id').value = node.id;
        $(this).tree('select', node.target);
        $('#menuPacket').menu('show', {
            left: e.clientX,
            top: e.clientY
        });
    }
}

function onLeftMenuLeftClick(node) {
    baseData = null;
    rootNode = $('#menuTree').tree('getRoot');
    curNode = node;
    parentNode = $('#menuTree').tree('getParent', node.target);
    if (rootNode.type == 'applicant') {
        if (node.type == 'create' || node.type == 'submitted' || node.type == 'executed' ||
            node.type == 'implemented') {
            url = '/common/getBaseData';
            data = {purchase_id:node.id};
        } else {
            url = '/common/getPacketBase';
            data = {packet_id:node.id};
        }
    } else if (rootNode.type == 'purchase') {
        if (node.type == 'to_divide' || node.type == 'divided' || node.type == 'finished' ||
            node.type == 'interrupt' || node.type == 'failed') {
            document.getElementById('purchase_id').value = node.id;
            url = '/common/getBaseData';
            data = {purchase_id:node.id};
        } else {
            url = '/common/getPacketBase';
            data = {packet_id:node.id};
        }
    } else if (rootNode.type == 'payment') {
        if (node.type == 'to_pay' || node.type == 'paid') {
            document.getElementById('purchase_id').value = node.id;
            url = '/common/getBaseData';
            data = {purchase_id:node.id};
        } else {
            url = '/common/getPacketBase';
            data = {packet_id:node.id};
        }
    } else {
        url = '/common/getBaseData';
        data = {purchase_id:node.id};
    }

    $.ajax({
        type: 'post',
        url:url,
        data: data,
        dataType: 'json',
        success: onSuccess,
        error: onError
    });

    function onSuccess(r) {
        if (r.result == 'success') {
            baseData = r.base;
            if (rootNode.type == 'applicant') {
                showApplicantPage();
            } else if (rootNode.type == 'purchase') {
                showPurchasePage();
            } else if (rootNode.type == 'payment') {
                showPaymentPage();
            } else {
                showAprovalPage();
            }
        } else {
            baseData = null;
        }
    }

    function onError(x, e) {

    }

    function showApplicantPage() {
        if (node.type == 'create') {
            $('#contentDiv').panel('setTitle','新建采购过程');
            $('#contentDiv').panel('refresh','../jsp/pages/new_purchase.jsp');
        } else {
            $('#contentDiv').panel('setTitle',node.text);
            if (node.type == 'packet') {
                $('#contentDiv').panel('refresh', '../jsp/pages/acceptance.jsp');
            } else {
                $('#contentDiv').panel('refresh','../jsp/pages/view_purchase.jsp');
            }
        }
    }

    function showAprovalPage() {
        if (node.type == 'to_approve') {
            $('#contentDiv').panel('setTitle','待审批项目');
            if (rootNode.type == "accounting") {
                $('#contentDiv').panel('refresh', '../jsp/pages/accounting_unapproved.jsp');
            } else if (rootNode.type == "director") {
                $('#contentDiv').panel('refresh', '../jsp/pages/director_unapproved.jsp');
            } else if (rootNode.type == "regulatory") {
                $('#contentDiv').panel('refresh', '../jsp/pages/regulatory_unapproved.jsp');
            }else if (rootNode.type == "bureau") {
                $('#contentDiv').panel('refresh', '../jsp/pages/unapproved.jsp');
            }
        } else if (node.type == 'approved') {
            $('#contentDiv').panel('setTitle','已审批项目');
            if (rootNode.type == "regulatory") {
                $('#contentDiv').panel('refresh', '../jsp/pages/regulatory_approved.jsp');
            } else {
                $('#contentDiv').panel('refresh', '../jsp/pages/view_purchase.jsp');
            }
        } else if (node.type == 'rejected') {
            $('#contentDiv').panel('setTitle','审批未通过项目');
            $('#contentDiv').panel('refresh', '../jsp/pages/view_purchase.jsp');
        }
    }

    function showPurchasePage() {
        if (node.type == 'to_divide' || node.type == 'divided' || node.type == 'finished' ||
            node.type == 'interrupt' || node.type == 'failed') {
            $('#contentDiv').panel('setTitle', node.text);
            $('#contentDiv').panel('refresh', '../jsp/pages/view_purchase.jsp');
        } else {
            $('#contentDiv').panel('setTitle', node.text);
            var parentNode = $('#menuTree').tree('getParent', node.target)
            if (parentNode.type == 'to_divide') {
                $('#contentDiv').panel('refresh', '../jsp/pages/un_packaged.jsp');
            } else {
                $('#contentDiv').panel('refresh', '../jsp/pages/view_package.jsp');
            }
        }
    }

    function showPaymentPage() {
        if (node.type == 'to_pay' || node.type == 'paid') {
            $('#contentDiv').panel('setTitle', node.text);
            $('#contentDiv').panel('refresh', '../jsp/pages/view_purchase.jsp');
        } else {
            $('#contentDiv').panel('setTitle', node.text);
            var parentNode = $('#menuTree').tree('getParent', node.target)
            if (parentNode.type == 'to_pay') {
                $('#contentDiv').panel('refresh', '../jsp/pages/unpaid.jsp');
            } else {
                $('#contentDiv').panel('refresh', '../jsp/pages/paid.jsp');
            }
        }
    }
}

function onLoadPurchase() {
    if (baseData != null) {
        document.getElementById("purchase_id").value = baseData['purchase_id'];
        $('#pur_code').textbox('setText', baseData['pur_code']);
        $('#funds_src').textbox('setText', baseData['funds_src']);
        $('#contacts').textbox('setText', baseData['contacts']);
        $('#phone_num').textbox('setText', baseData['phone_num']);
        $('#funds_nature').combobox('setValue', baseData['funds_nature']);
        $('#commodity_pre_price').textbox('setText', baseData['commodity_pre_price']);
        $('#service_pre_price').textbox('setText', baseData['service_pre_price']);
        $('#engineering_pre_price').textbox('setText', baseData['engineering_pre_price']);
        $('#commodity_total_price').textbox('setText', baseData['commodity_total_price']);
        $('#service_total_price').textbox('setText', baseData['service_total_price']);
        $('#engineering_total_price').textbox('setText', baseData['engineering_total_price']);
        if (curNode.type == 'create') {
            $('#pur_code').textbox('disable');
        } else {
            $('#pur_code').textbox('disable');
            $('#funds_src').textbox('disable');
            $('#contacts').textbox('disable');
            $('#phone_num').textbox('disable');
            $('#funds_nature').combobox('disable');
            $('#commodity_pre_price').textbox('disable');
            $('#service_pre_price').textbox('disable');
            $('#engineering_pre_price').textbox('disable');
            $('#commodity_total_price').textbox('disable');
            $('#service_total_price').textbox('disable');
            $('#engineering_total_price').textbox('disable');
        }
    } else {
        document.getElementById("purchase_id").value = Math.uuid(36, 62);
    }
}

function onLoadPackage() {
    if (baseData != null) {
        document.getElementById("packet_id").value = baseData['packet_id'];
        $('#pack_code').textbox('setText', baseData['pack_code']);
        $('#pack_code').textbox('disable');
        $('#pur_address').textbox('setText', baseData['pur_address']);
        $('#expert_count').textbox('setText', baseData['expert_count']);
        $('#pur_date').datebox('setValue', baseData['pur_date']);
        $('#pur_method').combobox('setValue', baseData['pur_method']);
        $('#pur_publicity').switchbutton('setValue', baseData['pur_publicity']);
        $('#pur_supplier').textbox('setText', baseData['pur_supplier']);
        $('#pur_amount').textbox('setText', baseData['pur_amount']);
    } else {
        document.getElementById("packet_id").value = Math.uuid(36, 62);
    }
}

function getProductByType(type) {
    if (type == "commodity") {
        productObj = $('#dgCommodity');
    } else if (type == "service") {
        productObj = $('#dgService');
    } else if (type == "engineering") {
        productObj = $('#dgEngineering');
    }
}

function addPurchaseProductItem(type){
    $('#dlgPurchaseProduct').dialog('open').dialog('center').dialog('setTitle','新增项目');
    $('#prj_name').textbox('clear');
    $('#prj_count').textbox('clear');
    $('#prj_price').textbox('clear');
    $('#prj_spec').textbox('clear');
    $('#prj_param').textbox('clear');
    $('#prj_pre_price').textbox('clear');
    product_dlg_name = 'new';
    getProductByType(type);
}

function editPurchaseProductItem(type){
    getProductByType(type);
    var row = productObj.datagrid('getSelected');
    if (row){
        $('#dlgPurchaseProduct').dialog('open').dialog('center').dialog('setTitle','编辑项目');
        $('#prj_name').textbox('setText', row["prj_name"]);
        $('#prj_count').textbox('setText',row["prj_count"]);
        $('#prj_price').textbox('setText',row["prj_price"]);
        $('#prj_spec').textbox('setText', row["prj_spec"]);
        $('#prj_param').textbox('setText', row["prj_param"]);
        $('#prj_pre_price').textbox('setText', row["prj_pre_price"]);
        product_dlg_name = 'edit';
        product_edit_index = productObj.datagrid('getRowIndex', row);
    }
}

function viewPurchaseProductItem(type){
    getProductByType(type);
    var row = productObj.datagrid('getSelected');
    if (row){
        $('#dlgPurchaseProductView').dialog('open').dialog('center').dialog('setTitle','浏览项目');
        $('#prj_name').textbox('setText', row["prj_name"]);
        $('#prj_count').textbox('setText',row["prj_count"]);
        $('#prj_price').textbox('setText',row["prj_price"]);
        $('#prj_spec').textbox('setText', row["prj_spec"]);
        $('#prj_param').textbox('setText', row["prj_param"]);
        $('#prj_pre_price').textbox('setText', row["prj_pre_price"]);

        $('#prj_name').textbox('disable');
        $('#prj_count').textbox('disable');
        $('#prj_price').textbox('disable');
        $('#prj_spec').textbox('disable');
        $('#prj_param').textbox('disable');
        $('#prj_pre_price').textbox('disable');
    }
}

function removePurchaseProductItem(type){
    getProductByType(type);
    var row = productObj.datagrid('getSelected');
    if (row){
        $.messager.confirm('操作提示','是否确认删除此项目?',function(r){
            if (r){
                var index =  productObj.datagrid('getRowIndex', row);
                productObj.datagrid('deleteRow', index);
            }
        });
    }
}

function savePurchaseProductItem(){
    var data = {};
    //data["project_id"] = $('#project_id').textbox('getText');
    data["prj_name"] = $('#prj_name').textbox('getText');
    data["prj_count"] = $('#prj_count').textbox('getText');
    data["prj_price"] = $('#prj_price').textbox('getText');
    data["prj_spec"] = $('#prj_spec').textbox('getText');
    data["prj_param"] = $('#prj_param').textbox('getText');
    data["prj_pre_price"] = $('#prj_pre_price').textbox('getText');

    if (product_dlg_name == 'edit') {
        var updateData = {};
        updateData['index'] = product_edit_index;
        updateData['row'] = data;
        productObj.datagrid('updateRow', updateData);
    } else {
        data["project_id"] = Math.uuid(36, 62);
        productObj.datagrid('appendRow', data);
    }
    $('#dlgPurchaseProduct').dialog('close');
}

function agreePurchase() {
    if (rootNode.type == 'accounting') {
        if ($('#approve_person').combobox('getValue') == 'default') {
            $.messager.alert("警告", "请选择上级审核人");
            return;
        } else {
            approve_content = '确认需要' + $('#approve_person').combobox('getText') + '审核?';
        }
    } else if (rootNode.type == 'director') {
        if ($('#approve_department').combobox('getValue') == 'default') {
            $.messager.alert("警告", "请选择财政局资金分管股室");
            return;
        } else {
            approve_content = '确认需要提交财政局' + $('#approve_department').combobox('getText') + '进行资金审核?';
        }
    } else if (rootNode.type == 'regulatory') {
        if ($('#purchasing_nature').combobox('getValue') == 'default') {
            $.messager.alert("警告", "请选择采购性质");
            return;
        } else {
            approve_content = '确认提交审核?';
        }
    } else {
        approve_content = "确认同意";
    }

    var url = '/common/approve';
    var data = {purchase_id:document.getElementById("purchase_id").value,
                content:$('#opinion').textbox('getText'),
                opinion:'agree'}
    $.messager.confirm('操作提示', approve_content, function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:url,
                data:data ,
                dataType: 'json',
                success: onSuccess,
                error: onError
            });
        }
    });

    function onSuccess(r) {
        if(r.result == "success") {
            $('#menuTree').tree('reload', $('#approved_prj').target);
            var cur_node = {};
            cur_node.id = document.getElementById("purchase_id").value;
            cur_node.type = "approved";
            //showContent(cur_node);
        } else {
        }
    }

    function onError(x, e) {
        alert("error cancelPurchasing");
    }
}

function disagreePurchase() {
    var url = '/common/approve';
    var data = {purchase_id:document.getElementById("purchase_id").value,
                content:$('#opinion').textbox('getText'),
                opinion:'disagree'}
    $.messager.confirm('操作提示','确认退回上一级?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:url,
                data: data,
                dataType: 'json',
                success: onSuccess,
                error: onError
            });
        }
    });

    function onSuccess(r) {
        if(data.result == "success") {
            $('#menuTree').tree('reload', $('#approved_prj').target);
        } else {
        }
    }

    function onError(x, e) {
        alert("error cancelPurchasing");
    }
}

function complaintsPurchase () {
    $('#dlgComplaintPurchase').dialog('open').dialog('center').dialog('setTitle','投诉处理');
}

function submitComplaintsOpinion () {
    $('#dlgComplaintPurchase').dialog('close');
}

function savePackage() {
    $.messager.confirm('操作提示','确认保存此分包?',function(r){
        if (r){
            var baseData = {};
            baseData['purchase_id'] = document.getElementById("purchase_id").value;
            baseData['packet_id'] = document.getElementById("packet_id").value;
            baseData['pack_code'] = $('#pack_code').textbox('getText');
            baseData['pur_address'] = $('#pur_address').textbox('getText');
            baseData['expert_count'] = $('#expert_count').textbox('getText');
            baseData['pur_date'] = $('#pur_date').datebox('getValue');
            baseData['pur_method'] = $('#pur_method').combobox('getValue');
            baseData['pur_publicity'] = $('#pur_publicity').switchbutton('options').checked;
            baseData['pur_supplier'] = $('#pur_supplier').textbox('getText') ;
            baseData['pur_amount'] = $('#pur_amount').textbox('getText');

            var items = $('#tbToDivide').datagrid('getChecked');
            var checkedData = {};
            checkedData['total'] = items.length;
            checkedData['rows'] = items;

            var url = '/purchase/save';
            var data = {base:JSON.stringify(baseData),products:JSON.stringify(checkedData)};
            $.ajax({
                type: 'post',
                url:url,
                data: data,
                dataType: 'json',
                success: onSuccess,
                error: onError
            });
        }
    });

    function onSuccess(r) {
        if(r.result == "success") {
            $('#menuTree').tree('reload', $('#to_divide').target);
            var cur_node = {};
            cur_node.id = document.getElementById("purchase_id").value;
            cur_node.type = "to_divide";
        } else {
        }
    }

    function onError(x, e) {
        alert("error savePurchasing");
    }
}

function cancelPackage() {
    $.messager.confirm('操作提示','确认取消此分包?',function(r) {
        if (r) {
            showNewPacketPage();
        }
    });
}

function submitPackages() {
    var url = '/purchase/submit';
    var data = {purchase_id:document.getElementById("purchase_id").value};
    $.messager.confirm('操作提示','确认提交分包?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:url,
                data: data,
                dataType: 'json',
                success: onSuccess,
                error: onError
            });
        }
    });

    function onSuccess(r) {
        if(r.result == "success") {
            $('#menuTree').tree('reload', $('#to_divide').target);
            var cur_node = {};
            cur_node.id = document.getElementById("purchase_id").value;
            cur_node.type = "to_divide";
        } else {
        }
    }

    function onError(x, e) {
        alert("error savePurchasing");
    }
}

function rePackage() {
    var url = '/purchase/repacket';
    var data = {packet_id:document.getElementById("packet_id").value};
    $.messager.confirm('操作提示','确认重新分包?',function(r){
        if (r){
            $.ajax({
                type: 'post',
                url:url,
                data: data,
                dataType: 'json',
                success: onSuccess,
                error: onError
            });
        }
    });

    function onSuccess(r) {
        if(r.result == "success") {
            $('#menuTree').tree('reload', $('#to_divide').target);
            var cur_node = {};
            cur_node.id = document.getElementById("purchase_id").value;
            cur_node.type = "to_divide";
        } else {
        }
    }

    function onError(x, e) {
        alert("error savePurchasing");
    }
}

function savePurchase() {
    $.messager.confirm('操作提示','确认保存此项目?',function(r){
        if (r){
            var url = '/applicant/save';
            var data = {base:JSON.stringify(getData()),
                        commodity:JSON.stringify($('#dgCommodity').datagrid('getData')),
                        service:JSON.stringify($('#dgService').datagrid('getData')),
                        engineering:JSON.stringify($('#dgEngineering').datagrid('getData'))}
            $.ajax({
                type: 'post',
                url:url,
                data: data,
                dataType: 'json',
                success: onSuccess,
                error: onError
            });
        }
    });

    function getData() {
        var baseData = {};
        baseData['purchase_id'] = document.getElementById("purchase_id").value;
        baseData['pur_code'] = $('#pur_code').textbox('getText');
        baseData['funds_src'] = $('#funds_src').textbox('getText');
        baseData['contacts'] = $('#contacts').textbox('getText');
        baseData['phone_num'] = $('#phone_num').textbox('getText');
        baseData['funds_nature'] = $('#funds_nature').combobox('getValue');
        baseData['commodity_pre_price'] = $('#commodity_pre_price').textbox('getText') == ''
            ? '0' : $('#commodity_pre_price').textbox('getText');
        baseData['service_pre_price'] = $('#service_pre_price').textbox('getText') == ''
            ? '0' : $('#service_pre_price').textbox('getText');
        baseData['engineering_pre_price'] = $('#engineering_pre_price').textbox('getText') == ''
            ? '0' : $('#engineering_pre_price').textbox('getText');

        return baseData;
    }

    function onSuccess(r) {
        if(r.result == "success") {
            $('#menuTree').tree('reload', $('#new_prj').target);
            var cur_node = {};
            cur_node.id = document.getElementById("purchase_id").value;
            cur_node.type = "create";
        } else {
        }
    }

    function onError(x, e) {
        alert("error savePurchasing");
    }
}

function submitPurchase() {
    $.messager.confirm('操作提示','确认提交审核?',function(r){
        if (r){
            var url = '/applicant/submit';
            var data = {purchase_id:document.getElementById("purchase_id").value};
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                dataType: 'json',
                success: onSuccess,
                error: onError
            });
        }
    });

    function onSuccess(r) {
        if(r.result == "success") {
            $('#menuTree').tree('reload', $('#new_proj').target);
            showNewPurchasePage();
        } else {
        }
    }

    function onError(x, e) {
        alert("error submitPurchasing");
    }
}

function cancelPurchase() {
    $.messager.confirm('操作提示','确认撤销此项目?',function(r){
        if (r){
            var url = '/applicant/cancel';
            var data = {purchase_id:document.getElementById("purchase_id").value};
            $.ajax({
                type: 'post',
                url: url,
                data: data,
                dataType: 'json',
                success: onSuccess,
                error: onError
            });
        }
    });

    function onSuccess(r) {
        if(r.result == "success") {
            $('#menuTree').tree('reload', $('#new_proj').target);
            showNewPurchasePage();
        } else {
        }
    }

    function onError(x, e) {
        alert("error cancelPurchasing");
    }
}

function viewPurchaseOpinion() {

}

function viePurchaseComplaints() {

}

function onLoadAttachFiles() {
    // 初始化长传插件
    page_type = document.getElementById('page_type').value;
    initializeUploader();

    var url = '/common/getAttachFiles';
    var data = null;
    if (page_type == "purchase") {
        data = {purchase_id: document.getElementById("purchase_id").value};
    } else {
        data = {package_id: document.getElementById("packet_id").value};
    }
    $.ajax({
        type: 'post',
        url: url,
        data: data,
        dataType: 'json',
        success: onSuccess,
        error: onError
    });

    function onSuccess(r) {
        var parentNode = $('#menuTree').tree('getParent', curNode.target)
        for(var key in r.files) {
            var file = r.files[key];
            if (curNode.type == 'create' || (curNode.type == 'packet' && parentNode.type == 'to_divide')) {
                if (page_type == "purchase") {
                    document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                        '<a href=/common/downloadFile?file_id=' + file.id + '&purchase_id=' +
                        document.getElementById("purchase_id").value + '> ' + file.name +
                        '(' + getReadableFileSizeString(file.size) + ')</a> <b></b>&nbsp;' +
                        '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                        'data-options="iconCls:\'icon-remove\',plain:false" ' +
                        'onclick=removeAttachFile(' + file.id + ')> 删除</a><br/></div>';
                } else {
                    document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                        '<a href=/common/downloadFile?file_id=' + file.id + '&package_id=' +
                        document.getElementById("packet_id").value + '> ' + file.name +
                        '(' + getReadableFileSizeString(file.size) + ')</a> <b></b>&nbsp;' +
                        '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                        'data-options="iconCls:\'icon-remove\',plain:false" ' +
                        'onclick=removeAttachFile(' + file.id + ')> 删除</a><br/></div>';
                }
            } else {
                if (page_type == "purchase") {
                    document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                        '<a href=/common/downloadFile?file_id=' + file.id + '&purchase_id=' +
                        document.getElementById("purchase_id").value + '> ' + file.name +
                        '(' + getReadableFileSizeString(file.size)  + ')</a> <b></b><br/></div>';
                } else {
                    document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                        '<a href=/common/downloadFile?file_id=' + file.id + '&package_id=' +
                        document.getElementById("packet_id").value + '> ' + file.name +
                        '(' + getReadableFileSizeString(file.size)  + ')</a> <b></b><br/></div>';
                }
            }
        }
    }

    function onError(x, e) {
        alert("onLoadAttachFiles");
    }
}

function removeAttachFile(file_id) {
    var url = '/common/removeFile';
    var data = null;
    if (page_type == "purchase") {
        data = {file_id: file_id.id, purchase_id: document.getElementById("purchase_id").value};
    } else {
        data = {file_id: file_id.id, package_id: document.getElementById("packet_id").value};
    }

    $.ajax({
        type: 'post',
        url: url,
        data: data,
        dataType: 'json',
        success: onSuccess,
        error: onError
    });

    function onSuccess(r) {
        if(r.result == "success") {
            file_id.parentNode.removeChild(file_id);
        }
    }

    function onError(x, e) {
        alert("removeAttachFile");
    }
}

function submitForPay() {

}

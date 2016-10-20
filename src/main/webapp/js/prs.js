/**
 * Created by JooLiu on 2016/8/26.
 */
function initializeUploader() {
    var container = document.getElementById('container');

    var url;
    if (page_type == "purchase") {
        url = "/purchase/upload_file";
    } else {
        url = "/package/upload_evaluation_file";
    }

    var filters = { max_file_size : '10mb',
                    mime_types: [
                        {title : "Image files", extensions : "jpg,gif,png"},
                        {title : "Document files", extensions : "doc,pdf,xls"}]};
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
                uploader.settings.url = "/purchase/upload_file?file_id=" + file.id +
                    "&purchase_id=" + document.getElementById("purchase_id").value;
                document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/purchase/download_file?file_id=' + file.id + '&purchase_id=' +
                    document.getElementById("purchase_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size) + ')</a> <b></b>&nbsp;' +
                    '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                    'data-options="iconCls:\'icon-remove\',plain:true" ' +
                    'onclick=removeAttachFile(' + file.id + ')> 删除</a><br/></div>';
            } else {
                uploader.settings.url = "/package/upload_evaluation_file?file_id=" + file.id +
                    "&package_id=" + document.getElementById("package_id").value +
                    "&purchase_id=" + document.getElementById("purchase_id").value;
                document.getElementById('evaluationFileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/package/download_file?file_id=' + file.id + '&package_id=' +
                    document.getElementById("package_id").value + '> ' + file.name +
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

function initializeAcceptanceUploader() {
    var container = document.getElementById('acceptance_uploader');

    var url =  "/package/upload_acceptance_file";
    var filters = { max_file_size : '10mb',
        mime_types: [
            {title : "Image files", extensions : "jpg,gif,png"},
            {title : "Document files", extensions : "doc,pdf,xls"}]};
    var init = { PostInit: onPostInit,
        FilesAdded: onFilesAdded,
        FilesRemoved: onFilesRemoved,
        UploadProgress: onUploadProgress,
        FileUploaded: onFileUploaded,
        Error: onError};

    acceptance_uploader = new plupload.Uploader({
        runtimes : 'html5,html4',
        browse_button : 'acceptanceFiles', // you can pass in id...
        container: container, // ... or DOM Element itself
        url : url,
        filters : filters,
        unique_names: true,
        init:init
    });
    acceptance_uploader.init();

    function onPostInit() {

    }

    function onFilesAdded(up, files) {
        plupload.each(files, function(file) {
            alert(document.getElementById("package_id").value);
            alert(document.getElementById("purchase_id").value);
            acceptance_uploader.settings.url = "/package/upload_acceptance_file?file_id=" + file.id +
                "&package_id=" + document.getElementById("package_id").value +
                "&purchase_id=" + document.getElementById("purchase_id").value;
            document.getElementById('acceptanceFileList').innerHTML += '<div id="' + file.id + '">' +
                '<a href=/package/download_file?file_id=' + file.id + '&package_id=' +
                document.getElementById("package_id").value + '> ' + file.name +
                '(' + getReadableFileSizeString(file.size) + ')</a> <b></b>&nbsp;' +
                '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                'data-options="iconCls:\'icon-remove\',plain:true" ' +
                'onclick=removeAttachFile(' + file.id + ')> 删除</a><br/></div>';
        });
        acceptance_uploader.start();
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
    url = '/package/base_info';
    data = {purchase_id:curNode.id};

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
            $('#contentDiv').panel('setTitle', '新建分包');
            $('#contentDiv').panel('refresh', '../jsp/pages/new_package.jsp');
        } else {
            baseData = null;
        }
    }

    function onError(x, e) {

    }
}

function showNewPaymentPage() {
    $('#contentDiv').panel('setTitle', '申请支付');
    $('#contentDiv').panel('refresh', '../jsp/pages/new_payment.jsp');
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
        $('#menuPackage').menu('show', {
            left: e.clientX,
            top: e.clientY
        });
    } else if (node.type == 'packet') {
        $(this).tree('select', node.target);
        $('#menuPayment').menu('show', {
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
            url = '/purchase/base_info';
            data = {purchase_id:node.id};
        } else {
            url = '/package/base_info';
            data = {package_id:node.id};
        }
    } else if (rootNode.type == 'purchase') {
        if (node.type == 'to_divide' || node.type == 'divided' || node.type == 'finished' ||
            node.type == 'interrupt' || node.type == 'failed') {
            document.getElementById('purchase_id').value = node.id;
            url = '/purchase/base_info';
            data = {purchase_id:node.id};
        } else {
            url = '/package/base_info';
            data = {package_id:node.id};
        }
    } else if (rootNode.type == 'accounting') {
        if (node.type == 'to_approve' || node.type == 'approved') {
            document.getElementById('purchase_id').value = node.id;
            url = '/purchase/base_info';
            data = {purchase_id:node.id};
        } else {
            url = '/package/base_info';
            data = {package_id:node.id};
        }
    } else {
        url = '/purchase/base_info';
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
            } else if (rootNode.type == 'accounting') {
                showAccountingPage();
            } else {
                showApprovalPage();
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
            if (node.type == 'package') {
                $('#contentDiv').panel('refresh', '../jsp/pages/acceptance.jsp');
            } else {
                $('#contentDiv').panel('refresh','../jsp/pages/view_purchase.jsp');
            }
        }
    }

    function showApprovalPage() {
        if (node.type == 'to_approve') {
            $('#contentDiv').panel('setTitle','待审批项目');
            if (rootNode.type == "leader") {
                if (node.level == "1") {
                    $('#contentDiv').panel('refresh', '../jsp/pages/director_unapproved.jsp');
                } else {
                    $('#contentDiv').panel('refresh', '../jsp/pages/leader_unapproved.jsp');
                }
            } else if (rootNode.type == "director") {
                $('#contentDiv').panel('refresh', '../jsp/pages/director_unapproved.jsp');
            } else if (rootNode.type == "sector") {
                $('#contentDiv').panel('refresh', '../jsp/pages/sector_unapproved.jsp');
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

    function showAccountingPage() {
        if (node.type == 'to_approve') {
            $('#contentDiv').panel('refresh', '../jsp/pages/accounting_unapproved.jsp');
        } else if (node.type == 'approved') {
            $('#contentDiv').panel('refresh', '../jsp/pages/view_purchase.jsp');
        } else {
            $('#contentDiv').panel('setTitle', node.text);
            var parentNode = $('#menuTree').tree('getParent', node.target)
            if (parentNode.type == 'to_pay') {
                $('#contentDiv').panel('refresh', '../jsp/pages/paid.jsp');
            } else {
                $('#contentDiv').panel('refresh', '../jsp/pages/unpaid.jsp');
            }
        }
    }
}

function onLoadPurchase() {
    if (baseData != null) {
        document.getElementById("purchase_id").value = baseData['purchase_id'];
        $('#pur_name').textbox('setText', baseData['pur_name']);
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
            $('#pur_name').textbox('disable');
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

function onLoadPreviewPurchase() {
    document.getElementById("preview_title").textContent = 'XXX县采购' + baseData['pur_name'] + '函件';
}

function onLoadPackage() {
    if (baseData != null) {
        if (baseData['pack_type'] == 'new_package') {
            document.getElementById("package_id").value = Math.uuid(36, 62);
            $('#pur_name').textbox('setText', baseData['pur_name']);
            $('#pur_code').textbox('setText', baseData['pur_code']);
        } else {
            document.getElementById("package_id").value = baseData['package_id'];
            $('#pur_name').textbox('setText', baseData['pur_name']);
            $('#pur_code').textbox('setText', baseData['pur_code']);
            $('#pack_code').textbox('setText', baseData['pack_code']);
            $('#pack_code').textbox('disable');
            $('#pur_address').textbox('setText', baseData['pur_address']);
            $('#expert_count').textbox('setText', baseData['expert_count']);
            $('#pur_date').datebox('setValue', baseData['pur_date']);
            $('#pur_method').combobox('setValue', baseData['pur_method']);
            $('#pur_publicity').switchbutton('setValue', baseData['pur_publicity']);
            $('#pur_supplier').textbox('setText', baseData['pur_supplier']);
            $('#pur_amount').textbox('setText', baseData['pur_amount']);
        }
        $('#pur_name').textbox('disable');
        $('#pur_code').textbox('disable');
    } else {
        document.getElementById("package_id").value = Math.uuid(36, 62);
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

function addPaymentApplicantItem() {
    $('#dlgPaymentApplicant').dialog('open').dialog('center').dialog('setTitle','新增支付');
}

function savePaymentApplicantItem() {
    $('#dlgPaymentApplicant').dialog('close');
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
    var next_role_id = 0;
    var purchase_nature_id = 0;
    if (rootNode.type == 'accounting') {
        if ($('#approve_person').combobox('getText') == 'default') {
            $.messager.alert("警告", "请选择上级审核人");
            return;
        } else {
            approve_content = '确认需要' + $('#approve_person').combobox('getText') + '审核?';
        }
        next_role_id = $('#approve_person').combobox('getValue');
    } else if ((rootNode.type == 'director') || (rootNode.type == "leader" && curNode.level == "1")) {
        if ($('#approve_department').combobox('getText') == 'default') {
            $.messager.alert("警告", "请选择财政局资金分管股室");
            return;
        } else {
            approve_content = '确认需要提交财政局' + $('#approve_department').combobox('getText')
                + '进行资金审核?';
        }
        next_role_id = $('#approve_department').combobox('getValue');
    } else if (rootNode.type == 'regulatory') {
        if ($('#purchase_nature').combobox('getValue') == 'default') {
            $.messager.alert("警告", "请选择采购性质");
            return;
        } else {
            approve_content = '确认提交审核?';
        }
        purchase_nature_id = $('#purchase_nature').combobox('getValue');
    } else {
        approve_content = "确认同意";
    }

    var url = '/purchase/approve';
    var data = {purchase_id:document.getElementById("purchase_id").value,
                content:$('#opinion').textbox('getText'),
                opinion:'agree',next_role_id:next_role_id,purchase_nature_id:purchase_nature_id}
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
    var url = '/purchase/approve';
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

function printPreviewPurchase() {
    $('#dlgPreviewPurchase').dialog('open').dialog('center').dialog('setTitle','');
}

function printPurchase() {
    $("#print_area").jqprint();
    $('#dlgPreviewPurchase').dialog('close');
}

function complaintsPurchase () {
    $('#dlgComplaintPurchase').dialog('open').dialog('center').dialog('setTitle','投诉处理');
}

function submitComplaintsOpinion () {
    $('#dlgComplaintPurchase').dialog('close');
}

function savePackage() {
    $('#tbToDivide').datagrid("acceptChanges");
    $.messager.confirm('操作提示','确认保存此分包?',function(r){
        if (r){
            var baseData = {};
            baseData['purchase_id'] = document.getElementById("purchase_id").value;
            baseData['package_id'] = document.getElementById("package_id").value;
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

            var url = '/package/save';
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
            showNewPackagePage();
        }
    });
}

function rePackage() {
    var url = '/package/cancel';
    var data = {package_id:document.getElementById("package_id").value};
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
            var url = '/purchase/save';
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
        baseData['pur_name'] = $('#pur_name').textbox('getText');
        baseData['pur_code'] = $('#pur_code').textbox('getText');
        baseData['funds_src'] = $('#funds_src').textbox('getText');
        baseData['contacts'] = $('#contacts').textbox('getText');
        baseData['phone_num'] = $('#phone_num').textbox('getText');
        baseData['funds_nature'] = $('#funds_nature').combobox('getValue');
        baseData['purchase_type'] = $('#purchase_type').combobox('getValue');
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
            var url = '/purchase/submit';
            var data = {purchase_id:document.getElementById("purchase_id").value,
                purchase_type:$('#purchase_type').combobox("getValue")};
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
            var url = '/purchase/cancel';
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

function onLoadAcceptanceFiles() {
    // 初始化长传插件
    page_type = document.getElementById('page_type').value;
    initializeAcceptanceUploader();

    var url = '/package/attach_acceptance_files';
    var data = {package_id: document.getElementById("package_id").value};
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
            if (curNode.type == 'create' || (curNode.type == 'package' && parentNode.type == 'to_divide')) {
                document.getElementById('acceptanceFileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/package/download_file?file_id=' + file.id + '&package_id=' +
                    document.getElementById("package_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size) + ')</a> <b></b>&nbsp;' +
                    '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                    'data-options="iconCls:\'icon-remove\',plain:false" ' +
                    'onclick=removeAttachFile(' + file.id + ')> 删除</a><br/></div>';
            } else {
                document.getElementById('acceptanceFileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/package/download_file?file_id=' + file.id + '&package_id=' +
                    document.getElementById("package_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size)  + ')</a> <b></b><br/></div>';
            }
        }
    }

    function onError(x, e) {
        alert("onLoadAcceptanceFiles Error");
    }
}

function onLoadEveluationFilse() {
    // 初始化长传插件
    page_type = document.getElementById('page_type').value;
    initializeUploader('evaluation');

    var url = '/package/attach_evaluation_files';
    var data = {package_id: document.getElementById("package_id").value};
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
            if (curNode.type == 'create' || (curNode.type == 'package' && parentNode.type == 'to_divide')) {
                document.getElementById('evaluationFileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/package/download_file?file_id=' + file.id + '&package_id=' +
                    document.getElementById("package_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size) + ')</a> <b></b>&nbsp;' +
                    '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                    'data-options="iconCls:\'icon-remove\',plain:false" ' +
                    'onclick=removeAttachFile(' + file.id + ')> 删除</a><br/></div>';
            } else {
                document.getElementById('evaluationFileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/package/download_file?file_id=' + file.id + '&package_id=' +
                    document.getElementById("package_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size)  + ')</a> <b></b><br/></div>';
            }
        }
    }

    function onError(x, e) {
        alert("onLoadEveluationFilse Error");
    }
}

function onLoadAttachFiles() {
    // 初始化长传插件
    page_type = document.getElementById('page_type').value;
    initializeUploader('purchase');

    var url = '/purchase/attach_files';
    var data = {purchase_id: document.getElementById("purchase_id").value};
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
            if (curNode.type == 'create' || (curNode.type == 'package' && parentNode.type == 'to_divide')) {
                document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/purchase/download_file?file_id=' + file.id + '&purchase_id=' +
                    document.getElementById("purchase_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size) + ')</a> <b></b>&nbsp;' +
                    '<a href="javascript:void(0)" class="easyui-linkbutton" ' +
                    'data-options="iconCls:\'icon-remove\',plain:false" ' +
                    'onclick=removeAttachFile(' + file.id + ')> 删除</a><br/></div>';
            } else {
                document.getElementById('fileList').innerHTML += '<div id="' + file.id + '">' +
                    '<a href=/purchase/download_file?file_id=' + file.id + '&purchase_id=' +
                    document.getElementById("purchase_id").value + '> ' + file.name +
                    '(' + getReadableFileSizeString(file.size)  + ')</a> <b></b><br/></div>';
            }
        }
    }

    function onError(x, e) {
        alert("onLoadAttachFiles Error");
    }
}

function removeAttachFile(file_id) {
    var url = null;
    var data = null;
    if (page_type == "purchase") {
        url = '/purchase/remove_file';
        data = {file_id: file_id.id, purchase_id: document.getElementById("purchase_id").value};
    } else {
        url = '/package/remove_file';
        data = {file_id: file_id.id, package_id: document.getElementById("package_id").value};
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

function submitPackage() {
    $.messager.confirm('操作提示','是否确认提交分包?',function(r){
        if (r){
            var url = '/package/submit';
            var data = {package_id:document.getElementById("package_id").value};
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
    }

    function onError(x, e) {
    }
}

function approvePackage() {
    $.messager.confirm('操作提示','是否确认提交审核?',function(r){
        if (r){
            var url = '/package/approve';
            var data = {package_id:document.getElementById("package_id").value,content:'',opinion:'agree'};
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
    }

    function onError(x, e) {
    }
}

function onClickToDivide(index, field) {
    $('#tbToDivide').datagrid('beginEdit', index);
}

function onEndEditToDivide(index, row) {
    $('#tbToDivide').datagrid('endEdit', index);
}

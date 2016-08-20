<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/8/19
  Time: 22:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>已提交采购过程</title>
</head>
<body>
<div>
    <div class="easyui-panel" title="基础信息" style="width:100%" data-options="onLoad:onLoadSubmitted()">
        <table>
            <input id="purchasing_id" type="hidden" value=""/></td>
            <td align="left">
            <td style="width:15%">采购函编号</td>
            <td style="width:35%;"><input id="pur_code" class="easyui-textbox" data-options="readonly:true"/></td>
            <td style="width:15%;">资金来源</td>
            <td style="width:35%;"><input id="funds_src" class="easyui-textbox" data-options="readonly:true"/></td>
            </td>
            <tr/>
            <td align="left">
            <td style="width:15%;">联系人</td>
            <td style="width:35%;"><input id="contacts" class="easyui-textbox" data-options="readonly:true"/></td>
            <td style="width:15%;">联系电话</td>
            <td style="width:35%;"><input id="phone_num" class="easyui-textbox" data-options="readonly:true"/></td>
            </td>
            <tr/>
            <td align="left">
            <td style="width:15%;">资金性质</td>
            <td style="width:35%;">
                <select id="funds_nature" class="easyui-combobox" style="width:50%;" data-options="readonly:true">
                    <option value="ncys" selected>年初预算</option>
                    <option value="zxzj">专项资金</option>
                    <option value="zczj">自筹资金</option>
                    <option value="jzzj">捐赠资金</option>
                </select>
            </td>
            </td>
        </table>
    </div>
</div><br/>
<div region="center">
    <table id="dgCommodity" class="easyui-datagrid" title="商品类" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/applicant/commodityList',method:'post',
               queryParams:{id:document.getElementById('purchasing_id').value},toolbar:tbCommodity">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80">项目名称</th>
            <th data-options="field:'prj_count',width:80">数量</th>
            <th data-options="field:'prj_price',width:80">单价</th>
            <th data-options="field:'prj_spec',width:80">规格型号</th>
            <th data-options="field:'prj_pre_price',width:80">预算总价</th>
            <th data-options="field:'prj_param',width:160">技术参数及售后</th>
        </tr>
        </thead>
    </table>
    <table id="dgService" class="easyui-datagrid" title="服务类" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/applicant/serviceList',method:'post',
               queryParams:{id:document.getElementById('purchasing_id').value},toolbar:tbService">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80">项目名称</th>
            <th data-options="field:'prj_count',width:80">数量</th>
            <th data-options="field:'prj_price',width:80">单价</th>
            <th data-options="field:'prj_spec',width:80">规格型号</th>
            <th data-options="field:'prj_pre_price',width:80">预算总价</th>
            <th data-options="field:'prj_param',width:160">技术参数及售后</th>
        </tr>
        </thead>
    </table>
    <table id="dgEngineering" class="easyui-datagrid" title="工程类" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/applicant/engineeringList',method:'post',
               queryParams:{id:document.getElementById('purchasing_id').value},toolbar:tbEngineering">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80">项目名称</th>
            <th data-options="field:'prj_count',width:80">数量</th>
            <th data-options="field:'prj_price',width:80">单价</th>
            <th data-options="field:'prj_spec',width:80">规格型号</th>
            <th data-options="field:'prj_pre_price',width:80">预算总价</th>
            <th data-options="field:'prj_param',width:160">技术参数及售后</th>
        </tr>
        </thead>
    </table>
</div><br/>
<div style="padding:0px 0px 0px 0px">
    <div class="easyui-panel" title="附件" style="width:100%;height:225px;"
         data-options="fit:false,border:true,onLoad:loadAttachFile2()">
        <jsp:include page="view_attach.jsp"/>
    </div>
</div><br/>
<div>
    <table id="dgOpinionLst" class="easyui-datagrid" title="已经审批单位意见" style="width:100%;height:225px"
           data-options="rownumbers:true,singleSelect:true,url:'/new/serviceList',method:'get',
                   toolbar:tbOpinion">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80">单位</th>
            <th data-options="field:'prj_count',width:80">意见</th>
            <th data-options="field:'prj_price',width:80">审批人</th>
            <th data-options="field:'prj_spec',width:80">审批日期</th>
            <th data-options="field:'prj_total_price',width:80">备注</th>
        </tr>
        </thead>
    </table>
</div>
<div id="tbCommodity" style="height:auto">
    <td align="left">
    <td>商品类预算总价</td>
    <td><input id="commodity_pre_price" class="easyui-textbox" /></td>
    <td>商品类实际总价</td>
    <td><input id="commodity_total_price" class="easyui-textbox" /></td>
    </td>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
       onclick="editProjectItem('commodity')">查看</a>
</div>
<div id="tbService" style="height:auto">
    <td align="left">
    <td>服务类预算总价</td>
    <td><input id="service_pre_price" class="easyui-textbox" /></td>
    <td>服务类实际总价</td>
    <td><input id="service_total_price" class="easyui-textbox" /></td>
    </td>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
       onclick="editProjectItem('service')">查看</a>
</div>
<div id="tbEngineering" style="height:auto">
    <td align="left">
    <td>工程类预算总价</td>
    <td><input id="engineering_pre_price" class="easyui-textbox"/></td>
    <td>工程类实际总价</td>
    <td><input id="engineering_total_price" class="easyui-textbox" /></td>
    </td>
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true"
       onclick="editProjectItem('engineering')">查看</a>
</div>
<div id="tbOpinion" style="height:auto">
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true"
       onclick="viewOpinion()">查看</a>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px"
     closed="true" buttons="#dlg-buttons">
    <div style="margin:0;padding:20px 50px">
        <div style="margin-bottom:10px">
            <td>项目名称</td>
            <input id="prj_name" class="easyui-textbox" required="true" style="width:100%"
                   >
        </div>
        <div style="margin-bottom:10px">
            <td>数量</td>
            <input id="prj_count" class="easyui-textbox" required="true" style="width:100%"
                   >
        </div>
        <div style="margin-bottom:10px">
            <td>单价</td>
            <input id="prj_price" class="easyui-textbox" required="true" style="width:100%"
                   >
        </div>
        <div style="margin-bottom:10px">
            <td>预算总价</td>
            <input id="prj_pre_price" class="easyui-textbox" required="true" style="width:100%"
                   >
        </div>
        <div style="margin-bottom:10px">
            <td>规格型号</td>
            <input id="prj_spec" class="easyui-textbox" required="true" multiline="true"
                   style="width:100%;height:40px" >
        </div>
        <div style="margin-bottom:10px">
            <td>技术参数及售后</td>
            <input id="prj_param" class="easyui-textbox" required="true" multiline="true" label="Email:"
                   style="width:100%;height:120px" >
        </div>
    </div>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')" style="width:90px">取消</a>
</div>
</body>
</html>

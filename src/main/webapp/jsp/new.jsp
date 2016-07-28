<%--
  Created by IntelliJ IDEA.
  User: JooLiu
  Date: 2016/7/28
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="easyui_header.jsp"/>
    <script type="text/javascript" src="../js/new/commodity.js"></script>
    <script type="text/javascript" src="../js/new/service.js"></script>
    <script type="text/javascript" src="../js/new/engineering.js"></script>
    <title>新建采购过程</title>
</head>
<body>
<div class="easyui-panel" title="新建采购过程" style="width:1340px;padding:30px 70px 50px 70px">
    <form  action="/new" method="post">
        <table>
            <td align="center">
                <td>采购函编号：</td>
                <td><input name="uName" class="easyui-textbox" /></td>
                <td>资金来源：</td>
                <td><input type="password"  name="uPass" class="easyui-textbox" /></td>
                <td>资金性质：</td>
                <td>
                    <select id="cc" class="easyui-combobox" name="dept" style="width:200px;">
                        <option value="ncys">年初预算</option>
                        <option value="zxzj">专项资金</option>
                        <option value="zczj">自筹资金</option>
                        <option value="jzzj">捐赠资金</option>
                    </select>
                </td>
            </td>
            <tr/>
            <td align="center">
                <td>联系人：</td>
                <td><input type="password"  name="uPass" class="easyui-textbox"/></td>
                <td>联系电话：</td>
                <td><input type="password"  name="uPass" class="easyui-textbox"/></td>
            </td>
            <tr/>
            <td align="center">
                <td>采购函扫描件：</td>
                <td><input id="cgh" class="easyui-filebox" data-options="prompt:'选择文件.'" style="width:300px"></td>
                <td><input type="submit" value=" 上 传 "  /></td>
            </td>
            <tr/>
            <td align="center">
                <td>资金来源文件：</td>
                <td><input id="zjly" class="easyui-filebox" data-options="prompt:'选择文件.'" style="width:300px"></td>
                <td><input type="submit" value=" 上 传 "  /></td>
            </td>
            <tr/>
            <td align="center">
                <td>相关附件：</td>
                <td><input id="qt" class="easyui-filebox" data-options="prompt:'选择文件.'" style="width:300px"></td>
                <td><input type="submit" value=" 上 传 "  /></td>
            </td>
        </table>
    </form>
    <td align="left">
        <td>商品类预算总价：</td>
        <td><input name="uName" class="easyui-textbox" /></td>
    </td>
    <table id="dgCommodity" class="easyui-datagrid" title="商品类" style="width:1200px;height:auto"
           data-options="
            iconCls: 'icon-edit',
            singleSelect: true,
            toolbar: '#tbCommodity',
            url: 'datagrid_data1.json',
            method: 'get',
            onClickCell: onClickCommodityCell,
            onEndEdit: onEndCommodityEdit ">
        <thead>
            <tr>
                <th data-options="field:'prj_name',width:80,editor:'textbox'" >项目名称</th>
                <th data-options="field:'prj_count',width:80,align:'right',editor:'numberbox'">数量</th>
                <th data-options="field:'prj_price',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">单价</th>
                <th data-options="field:'prj_spec',width:80,editor:'textbox'">规格型号</th>
                <th data-options="field:'prj_total_price',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">预算总价</th>
                <th data-options="field:'prj_param',width:160,editor:'textbox'">技术参数及售后</th>
                <th data-options="field:'prj_attach',width:80,editor:'textbox'">相关附件</th>
            </tr>
        </thead>
    </table>
    <div id="tbCommodity" style="height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendCommodity()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeCommodity()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="acceptCommodity()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejectCommodity()">撤消</a>
    </div>
    <br/>
    <td align="left">
        <td>服务类预算总价：</td>
        <td><input name="uName" class="easyui-textbox" /></td>
    </td>
    <table id="dgService" class="easyui-datagrid" title="服务类" style="width:1200px;height:auto"
           data-options="
            iconCls: 'icon-edit',
            singleSelect: true,
            toolbar: '#tbService',
            url: 'datagrid_data1.json',
            method: 'get',
            onClickCell: onClickServiceCell,
            onEndEdit: onEndServiceEdit ">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80,editor:'textbox'" >项目名称</th>
            <th data-options="field:'prj_count',width:80,align:'right',editor:'numberbox'">数量</th>
            <th data-options="field:'prj_price',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">单价</th>
            <th data-options="field:'prj_spec',width:80,editor:'textbox'">规格型号</th>
            <th data-options="field:'prj_total_price',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">预算总价</th>
            <th data-options="field:'prj_param',width:160,editor:'textbox',multiline:true">技术参数及售后</th>
            <th data-options="field:'prj_attach',width:80,editor:'textbox'">相关附件</th>
        </tr>
        </thead>
    </table>
    <div id="tbService" style="height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendService()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeService()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="acceptService()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejectService()">撤消</a>
    </div>
    <br/>
    <td align="left">
        <td>工程类预算总价：</td>
        <td><input name="uName" class="easyui-textbox" /></td>
    </td>
    <table id="dgEngineering" class="easyui-datagrid" title="工程类" style="width:1200px;height:auto"
           data-options="
            iconCls: 'icon-edit',
            singleSelect: true,
            toolbar: '#tbEngineering',
            url: 'datagrid_data1.json',
            method: 'get',
            onClickCell: onClickEngineeringCell,
            onEndEdit: onEndEngineeringEdit ">
        <thead>
        <tr>
            <th data-options="field:'prj_name',width:80,editor:'textbox'" >项目名称</th>
            <th data-options="field:'prj_count',width:80,align:'right',editor:'numberbox'">数量</th>
            <th data-options="field:'prj_price',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">单价</th>
            <th data-options="field:'prj_spec',width:80,editor:'textbox'">规格型号</th>
            <th data-options="field:'prj_total_price',width:80,align:'right',editor:{type:'numberbox',options:{precision:1}}">预算总价</th>
            <th data-options="field:'prj_param',width:160,editor:'textbox'">技术参数及售后</th>
            <th data-options="field:'prj_attach',width:80,editor:'textbox'">相关附件</th>
        </tr>
        </thead>
    </table>
    <div id="tbEnginneering" style="height:auto">
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true" onclick="appendEngineering()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true" onclick="removeEngineering()">删除</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save',plain:true" onclick="acceptEngineering()">保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-undo',plain:true" onclick="rejectEngineering()">撤消</a>
    </div>
    <br/>
    <table align="right">
        <td align="right">
            <td><input type="submit" value=" 保 存 "  /></td>
            <td><input type="submit" value=" 提交审核 "  /></td>
            <td><input type="submit" value=" 重 置 "  /></td>
        </td>
    </table>
</div>
</body>
</html>

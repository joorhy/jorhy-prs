<%--
  Created by IntelliJ IDEA.
  User: Joo
  Date: 2016/7/1
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE HTML>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>待审批项目(2)</title>
    <script type="text/javascript">
        $(function(){
            $('#toApproveData').datagrid({
                onLoadSuccess : function(data){
                    $('#toApproveData').datagrid('selectRow',0)
                },
                onSelect: function (index, row) {

                    var itemName = row["itemName"];
                    var itemNo = row["itemNo"];
                    $("#detailTitle").html(itemName+"_"+itemNo);
                }
            });


        });
    </script>
</head>
<body>
     <div region="center" border="false">
        <table id="toApproveData" class="easyui-datagrid" title="待审批项目" style="width:100%;height:250px"
              data-options="singleSelect:true,
                            collapsible:true,
                            url:'/toApprove',
                            method:'get'">
           <thead>
           <tr>
               <th data-options="field:'itemName',width:180">项目名称</th>
               <th data-options="field:'itemNo',width:120">采购函号</th>
               <th data-options="field:'person',width:100">审批人</th>
               <th data-options="field:'advice',width:200">意见</th>
               <th data-options="field:'approveDate',width:100">日期</th>
               <th data-options="field:'remark',width:200,align:'center'">备注</th>
           </tr>
           </thead>
       </table>
    </div>
    <br/>
    <div id="detailDiv" class="easyui-panel" title="项目详细信息"  collapsible="true" style="padding-top:10px;height:200px;">
        <h3 id="detailTitle" style="color:#0099FF;text-align: center"></h3>
        <table style="width:95%; align:center;">
            <tr>
                <td style="width:25%;text-align: left;">申请人</td>
                <td id="applyPerson" style="width:25%;text-align: left;">张三</td>
                <td style="width:25%;text-align: left;">申请日期</td>
                <td id="applyDate" style="width:25%;text-align: left;"></td>
            </tr>
            <tr>
                <td style="width:25%;text-align: left;">联系电话</td>
                <td id="linkTel" style="width:25%;text-align: left;"></td>
                <td style="width:25%;text-align: left;">交货日期</td>
                <td id="deliveryDate" style="width:25%;text-align: left;"></td>
            </tr>
            <tr>
                <td style="width:25%;text-align: left;">项目采购应达到的技术参数及售后服务等方面的要求</td>
                <td id="remark" style="column-span: 3;text-align: left;"></td>
            </tr>
        </table>
        <table id="purchaseDetail" class="easyui-datagrid" title="采购明细" style="width:96%;padding:5px 5px 5px 5px;"
               data-options="singleSelect:true,
                            collapsible:false,
                            url:null,
                            method:'get'">
            <thead>
            <tr>
                <th data-options="field:'detailName',width:180">采购项目名称</th>
                <th data-options="field:'detailCount',width:120">数量</th>
                <th data-options="field:'detailPrice',width:100">单价人</th>
                <th data-options="field:'detailStyle',width:200">规格型号</th>
                <th data-options="field:'amount',width:100">预算总价</th>
            </tr>
            </thead>
        </table>
    </div>
     <br/>
    <div class="easyui-panel" title="审批信息"  collapsible="true" selected="true" style="padding:10px;height:200px;">
     审批信息
    </div>

</body>
</html>

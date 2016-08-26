/**
 * Created by JooLiu on 2016/8/25.
 */
function onLoadCreatePacket() {
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
    }
}

package controller;

import com.jfinal.core.Controller;

/**
 * Created by Joo on 2016/10/12.
 */
public class PurchasePreviewController extends Controller {
    public void index() {
        renderJsp("/jsp/prs_print_preview.jsp");
    }
}

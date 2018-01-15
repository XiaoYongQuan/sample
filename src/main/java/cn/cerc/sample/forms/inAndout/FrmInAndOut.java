package cn.cerc.sample.forms.inAndout;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.form.IPage;
import cn.cerc.jmis.form.AbstractForm;
import cn.cerc.jmis.page.JspPage;

public class FrmInAndOut extends AbstractForm {

	@Override
	public IPage execute() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmInAndOut.jsp");
		String code = getRequest().getParameter("code");
		String searchText = getRequest().getParameter("searchText");
		String dateStart = getRequest().getParameter("dateStart");
		String dateEnd = getRequest().getParameter("dateEnd");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.getDataIn().getHead().setField("SearchText_", searchText);
		svr.getDataIn().getHead().setField("dateStart", dateStart);
		svr.getDataIn().getHead().setField("dateEnd", dateEnd);
		svr.setService("SvrInAndOut.queryDetailed");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
		}
		jspPage.add("dataSet", svr.getDataOut().getRecords());
		jspPage.add("code", code);
		jspPage.add("searchText", searchText);
		jspPage.add("dateStart", dateStart);
		jspPage.add("dateEnd", dateEnd);
		jspPage.add("msg", svr.getMessage());
		return jspPage;
	}

	@Override
	public boolean logon() {
		return true;
	}

}

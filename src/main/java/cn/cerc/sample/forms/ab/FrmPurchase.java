package cn.cerc.sample.forms.ab;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.form.IPage;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jmis.form.AbstractForm;
import cn.cerc.jmis.page.JspPage;
import cn.cerc.jmis.page.RedirectPage;

public class FrmPurchase extends AbstractForm {

	@Override
	public IPage execute() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranH.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String text = getRequest().getParameter("searchText");
		String dateStart = getRequest().getParameter("dateStart");
		String dateEnd = getRequest().getParameter("dateEnd");
		String tb = "AB";
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TB_", tb);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("SearchText_", text);
		svr.getDataIn().getHead().setField("dateStart", dateStart);
		svr.getDataIn().getHead().setField("dateEnd", dateEnd);
		svr.setService("SvrPurchase.queryPurchase");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		}
		jspPage.add("tbNo", tbNo);
		jspPage.add("dateStart", dateStart);
		jspPage.add("dateEnd", dateEnd);
		jspPage.add("searchText", text);
		jspPage.add("dataSet", svr.getDataOut().getRecords());
		return jspPage;
	}

	public IPage addTranH() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranH_add.jsp");
		jspPage.add("tbDate", TDateTime.Now().getDate());
		return jspPage;
	}

	public IPage saveTranH() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranH_add.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String tbDate = getRequest().getParameter("tbDate");
		String supName = getRequest().getParameter("supName");
		String appUser = getRequest().getParameter("appUser");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("TBDate_", tbDate);
		svr.getDataIn().getHead().setField("SupName_", supName);
		svr.getDataIn().getHead().setField("AppUser_", appUser);
		svr.setService("SvrPurchase.savePurchase");
		if (!svr.exec()) {
			jspPage.add("tbNo", tbNo);
			jspPage.add("tbDate", tbDate);
			jspPage.add("supName", supName);
			jspPage.add("appUser", appUser);
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmPurchase.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage saveUpDateTranH() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranB.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String tbDate = getRequest().getParameter("tbDate");
		String supName = getRequest().getParameter("supName");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("TBDate_", tbDate);
		svr.getDataIn().getHead().setField("SupName_", supName);
		svr.setService("SvrPurchase.saveUpDateTranH");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmPurchase.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage addTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranB_add.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		jspPage.add("tbNo", tbNo);
		return jspPage;
	}

	public IPage saveTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranB_add.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		String num = getRequest().getParameter("num");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.getDataIn().getHead().setField("Num_", num);
		svr.setService("SvrPurchase.saveTranB");
		if (!svr.exec()) {
			jspPage.add("tbNo", tbNo);
			jspPage.add("code", code);
			jspPage.add("num", num);
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmPurchase.ShowTranB?tbNo=" + tbNo);
		}

	}

	public IPage modifyTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranB_edit.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.setService("SvrPurchase.findTranB");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		}
		jspPage.add("tbNo", tbNo);
		jspPage.add("code", code);
		jspPage.add("desc", svr.getDataOut().getHead().getField("Desc_"));
		jspPage.add("spec", svr.getDataOut().getHead().getField("Spec_"));
		jspPage.add("unit", svr.getDataOut().getHead().getField("Unit_"));
		jspPage.add("num", svr.getDataOut().getHead().getField("Num_"));
		return jspPage;
	}

	public IPage saveUpDateTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranB_edit.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		String desc = getRequest().getParameter("desc");
		String spec = getRequest().getParameter("spec");
		String unit = getRequest().getParameter("unit");
		String num = getRequest().getParameter("num");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.getDataIn().getHead().setField("Num_", num);
		svr.setService("SvrPurchase.saveUpDateTranB");
		if (!svr.exec()) {
			jspPage.add("tbNo", tbNo);
			jspPage.add("code", code);
			jspPage.add("desc", desc);
			jspPage.add("spec", spec);
			jspPage.add("unit", unit);
			jspPage.add("num", num);
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmPurchase.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage deleteTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranB.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.setService("SvrPurchase.deleteTranB");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmPurchase.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage ShowTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmPurchaseTranB.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.setService("SvrPurchase.queryTranbAndH");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		}
		jspPage.add("tbNo", tbNo);
		jspPage.add("appUser", svr.getDataOut().getHead().getField("AppUser_"));
		jspPage.add("tbDate", svr.getDataOut().getHead().getField("TBDate_"));
		jspPage.add("supName", svr.getDataOut().getHead().getField("SupName_"));
		jspPage.add("dataSet", svr.getDataOut().getRecords());
		return jspPage;
	}

	@Override
	public boolean logon() {
		return true;
	}
}

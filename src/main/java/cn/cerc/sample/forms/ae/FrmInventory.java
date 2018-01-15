package cn.cerc.sample.forms.ae;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.form.IPage;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jmis.form.AbstractForm;
import cn.cerc.jmis.page.JspPage;
import cn.cerc.jmis.page.RedirectPage;

public class FrmInventory extends AbstractForm {

	@Override
	public IPage execute() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmInventoryTranH.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String appUser = getRequest().getParameter("appUser");
		String dateStart = getRequest().getParameter("dateStart");
		String dateEnd = getRequest().getParameter("dateEnd");
		String tb = "AE";
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TB_", tb);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("AppUser_", appUser);
		svr.getDataIn().getHead().setField("dateStart", dateStart);
		svr.getDataIn().getHead().setField("dateEnd", dateEnd);
		svr.setService("SvrInventory.queryInventory");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		}
		jspPage.add("tbNo", tbNo);
		jspPage.add("dateStart", dateStart);
		jspPage.add("dateEnd", dateEnd);
		jspPage.add("appUser", appUser);
		jspPage.add("dataSet", svr.getDataOut().getRecords());

		return jspPage;
	}

	public IPage addTranH() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmInventoryTranH_add.jsp");
		jspPage.add("tbDate", TDateTime.Now().getDate());
		return jspPage;
	}

	public IPage saveTranH() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmInventoryTranH_add.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String tbDate = getRequest().getParameter("tbDate");
		String appUser = getRequest().getParameter("appUser");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("TBDate_", tbDate);
		svr.getDataIn().getHead().setField("AppUser_", appUser);
		svr.setService("SvrInventory.saveInventory");
		if (!svr.exec()) {
			jspPage.add("tbNo", tbNo);
			jspPage.add("tbDate", tbDate);
			jspPage.add("appUser", appUser);
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmInventory.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage addTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmInventoryTranB_add.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		jspPage.add("tbNo", tbNo);
		return jspPage;
	}

	public IPage saveTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmInventoryTranB_add.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		String num = getRequest().getParameter("num");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.getDataIn().getHead().setField("Num_", num);
		svr.setService("SvrInventory.saveTranB");
		if (!svr.exec()) {
			jspPage.add("tbNo", tbNo);
			jspPage.add("code", code);
			jspPage.add("num", num);
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmInventory.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage deleteTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmInventoryTranB.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.setService("SvrInventory.deleteTranB");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmInventory.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage ShowTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmInventoryTranB.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.setService("SvrInventory.queryTranbAndH");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		}
		jspPage.add("tbNo", tbNo);
		jspPage.add("appUser", svr.getDataOut().getHead().getField("AppUser_"));
		jspPage.add("tbDate", svr.getDataOut().getHead().getField("TBDate_"));
		jspPage.add("dataSet", svr.getDataOut().getRecords());
		return jspPage;
	}

	@Override
	public boolean logon() {
		return true;
	}

}

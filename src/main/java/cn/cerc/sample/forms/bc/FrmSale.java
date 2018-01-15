package cn.cerc.sample.forms.bc;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.form.IPage;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jmis.form.AbstractForm;
import cn.cerc.jmis.page.JspPage;
import cn.cerc.jmis.page.RedirectPage;

public class FrmSale extends AbstractForm {

	@Override
	public IPage execute() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranH.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String text = getRequest().getParameter("searchText");
		String dateStart = getRequest().getParameter("dateStart");
		String dateEnd = getRequest().getParameter("dateEnd");
		String tb = "BC";
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TB_", tb);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("SearchText_", text);
		svr.getDataIn().getHead().setField("dateStart", dateStart);
		svr.getDataIn().getHead().setField("dateEnd", dateEnd);
		svr.setService("SvrSale.querySale");
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
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranH_add.jsp");
		jspPage.add("tbDate", TDateTime.Now().getDate());
		return jspPage;
	}

	public IPage saveTranH() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranH_add.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String tbDate = getRequest().getParameter("tbDate");
		String cusName = getRequest().getParameter("cusName");
		String appUser = getRequest().getParameter("appUser");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("TBDate_", tbDate);
		svr.getDataIn().getHead().setField("CusName_", cusName);
		svr.getDataIn().getHead().setField("AppUser_", appUser);
		svr.setService("SvrSale.saveSale");
		if (!svr.exec()) {
			jspPage.add("tbNo", tbNo);
			jspPage.add("tbDate", tbDate);
			jspPage.add("cusName", cusName);
			jspPage.add("appUser", appUser);
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmSale.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage saveUpDateTranH() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranB.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String tbDate = getRequest().getParameter("tbDate");
		String cusName = getRequest().getParameter("cusName");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("TBDate_", tbDate);
		svr.getDataIn().getHead().setField("CusName_", cusName);
		svr.setService("SvrSale.saveUpDateTranH");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmSale.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage addTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranB_add.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		jspPage.add("tbNo", tbNo);
		return jspPage;
	}

	public IPage saveTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranB_add.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		String num = getRequest().getParameter("num");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.getDataIn().getHead().setField("Num_", num);
		svr.setService("SvrSale.saveTranB");
		if (!svr.exec()) {
			jspPage.add("tbNo", tbNo);
			jspPage.add("code", code);
			jspPage.add("num", num);
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmSale.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage modifyTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranB_edit.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.setService("SvrSale.findTranB");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		}
		jspPage.add("tbNo", tbNo);
		jspPage.add("code", code);
		jspPage.add("desc", svr.getDataOut().getHead().getString("Desc_"));
		jspPage.add("spec", svr.getDataOut().getHead().getString("Spec_"));
		jspPage.add("unit", svr.getDataOut().getHead().getString("Unit_"));
		jspPage.add("num", svr.getDataOut().getHead().getString("Num_"));
		return jspPage;
	}

	public IPage saveUpDateTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranB_edit.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		String num = getRequest().getParameter("num");
		String submit = getRequest().getParameter("submit");
		if (submit != null && !"".equals(submit)) {
			LocalService svr = new LocalService(this);
			svr.setService("SvrSale.saveUpDateTranB");
			svr.getDataIn().getHead().setField("TBNo_", tbNo);
			svr.getDataIn().getHead().setField("Code_", code);
			svr.getDataIn().getHead().setField("Num_", num);
			if (!svr.exec()) {
				jspPage.add("msg", svr.getMessage());
			} else {
				return new RedirectPage(this, "FrmSale.ShowTranB?tbNo=" + tbNo);
			}
		}
		LocalService svr1 = new LocalService(this);
		svr1.getDataIn().getHead().setField("TBNo_", tbNo);
		svr1.getDataIn().getHead().setField("Code_", code);
		svr1.setService("SvrSale.findTranB");
		if (!svr1.exec()) {
			jspPage.add("msg", svr1.getMessage());
			return jspPage;
		}
		jspPage.add("tbNo", tbNo);
		jspPage.add("code", code);
		jspPage.add("desc", svr1.getDataOut().getHead().getString("Desc_"));
		jspPage.add("spec", svr1.getDataOut().getHead().getString("Spec_"));
		jspPage.add("unit", svr1.getDataOut().getHead().getString("Unit_"));
		jspPage.add("cusName", svr1.getDataOut().getHead()
				.getString("CusName_"));
		jspPage.add("num", num);
		return jspPage;
	}

	public IPage deleteTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranB.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		String code = getRequest().getParameter("code");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.setService("SvrSale.deleteTranB");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmSale.ShowTranB?tbNo=" + tbNo);
		}
	}

	public IPage ShowTranB() throws Exception {
		JspPage jspPage = new JspPage(this, "common/FrmSaleTranB.jsp");
		String tbNo = getRequest().getParameter("tbNo");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("TBNo_", tbNo);
		svr.setService("SvrSale.queryTranbAndH");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		}
		jspPage.add("tbNo", tbNo);
		jspPage.add("appUser", svr.getDataOut().getHead().getField("AppUser_"));
		jspPage.add("tbDate", svr.getDataOut().getHead().getField("TBDate_"));
		jspPage.add("cusName", svr.getDataOut().getHead().getField("CusName_"));
		jspPage.add("dataSet", svr.getDataOut().getRecords());
		return jspPage;
	}

	@Override
	public boolean logon() {
		return true;
	}

}

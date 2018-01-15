package cn.cerc.sample.forms.part;

import cn.cerc.jbean.client.LocalService;
import cn.cerc.jbean.form.IPage;
import cn.cerc.jmis.form.AbstractForm;
import cn.cerc.jmis.page.JspPage;
import cn.cerc.jmis.page.RedirectPage;

public class FrmProductShow extends AbstractForm {

	@Override
	public IPage execute() throws Exception {
		JspPage jspPage = new JspPage(this, "common/productShow.jsp");
		String text = getRequest().getParameter("searchText");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("SearchText_", text);
		svr.setService("SvrProduct.queryProduct");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		}
		jspPage.add("searchText", text);
		jspPage.add("dataSet", svr.getDataOut().getRecords());
		return jspPage;
	}

	public IPage deleteProduct() throws Exception {
		JspPage jspPage = new JspPage(this, "common/productShow.jsp");
		String uid = getRequest().getParameter("uid");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("UID_", uid);
		svr.setService("SvrProduct.deleteProduct");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmProductShow");
		}
	}

	public IPage modify() throws Exception {
		JspPage jspPage = new JspPage(this, "common/productShow_edit.jsp");
		String uid = getRequest().getParameter("uid");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("UID_", uid);
		svr.setService("SvrProduct.findProductByUid");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		}
		jspPage.add("uid", svr.getDataOut().getHead().getField("UID_"));
		jspPage.add("code", svr.getDataOut().getHead().getField("Code_"));
		jspPage.add("desc", svr.getDataOut().getHead().getField("Desc_"));
		jspPage.add("spec", svr.getDataOut().getHead().getField("Spec_"));
		jspPage.add("unit", svr.getDataOut().getHead().getField("Unit_"));
		return jspPage;

	}

	public IPage updateProduct() throws Exception {
		JspPage jspPage = new JspPage(this, "common/productShow_edit.jsp");
		String uid = getRequest().getParameter("uid");
		String code = getRequest().getParameter("code");
		String desc = getRequest().getParameter("desc");
		String spec = getRequest().getParameter("spec");
		String unit = getRequest().getParameter("unit");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("UID_", uid);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.getDataIn().getHead().setField("Desc_", desc);
		svr.getDataIn().getHead().setField("Spec_", spec);
		svr.getDataIn().getHead().setField("Unit_", unit);
		svr.setService("SvrProduct.updateProduct");
		if (!svr.exec()) {
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmProductShow");
		}
	}

	public IPage addProduct() throws Exception {
		JspPage jspPage = new JspPage(this, "common/productShow_add.jsp");
		return jspPage;
	}

	public IPage saveProduct() throws Exception {
		JspPage jspPage = new JspPage(this, "common/productShow_add.jsp");
		String code = getRequest().getParameter("code");
		String desc = getRequest().getParameter("desc");
		String spec = getRequest().getParameter("spec");
		String unit = getRequest().getParameter("unit");
		LocalService svr = new LocalService(this);
		svr.getDataIn().getHead().setField("Code_", code);
		svr.getDataIn().getHead().setField("Desc_", desc);
		svr.getDataIn().getHead().setField("Spec_", spec);
		svr.getDataIn().getHead().setField("Unit_", unit);
		svr.setService("SvrProduct.saveProduct");
		if (!svr.exec()) {
			jspPage.add("code", code);
			jspPage.add("desc", desc);
			jspPage.add("spec", spec);
			jspPage.add("unit", unit);
			jspPage.add("msg", svr.getMessage());
			return jspPage;
		} else {
			return new RedirectPage(this, "FrmProductShow");
		}
	}

	@Override
	public boolean logon() {
		return true;
	}

}

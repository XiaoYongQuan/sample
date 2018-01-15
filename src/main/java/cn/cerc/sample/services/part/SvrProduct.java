package cn.cerc.sample.services.part;

import cn.cerc.jbean.core.CustomService;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.mysql.BuildQuery;
import cn.cerc.jdb.mysql.SqlQuery;

public class SvrProduct extends CustomService {

	public boolean queryProduct() {
		Record headIn = getDataIn().getHead();
		BuildQuery f = new BuildQuery(this);
		if (headIn.hasValue("SearchText_")) {
			f.byLink(new String[] { "p.Code_", "p.Desc_", "p.Spec_" },
					headIn.getString("SearchText_"));
		}
		f.add("select * from %s p ", "product");
		SqlQuery ds = f.open();
		if (ds.eof()) {
			throw new RuntimeException(String.format("查询结果为空，请重新确认查询条件！"));
		}
		getDataOut().appendDataSet(ds);
		return true;
	}

	public boolean deleteProduct() {
		Record headIn = getDataIn().getHead();
		String uid = headIn.getString("UID_");

		SqlQuery ds = new SqlQuery(this);
		ds.add("select * from %s where UID_='%s' ", "product", uid);
		ds.open();
		if (ds.eof()) {
			// FIXME 
			throw new RuntimeException("");
		}
		ds.delete();
		return true;
	}

	public boolean findProductByUid() {
		Record headIn = getDataIn().getHead();
		String uid = headIn.getString("UID_");
		SqlQuery ds = new SqlQuery(this);
		ds.add("select * from %s where UID_='%s' ", "product", uid);
		ds.open();
		if (!ds.eof()) {
			getDataOut().getHead().copyValues(ds.getCurrent());
		}
		return true;
	}

	public boolean updateProduct() {
		Record headIn = getDataIn().getHead();
		String uid = headIn.getString("UID_");
		String desc = headIn.getString("Desc_");
		String spec = headIn.getString("Spec_");
		String unit = headIn.getString("Unit_");
		SqlQuery ds = new SqlQuery(this);
		ds.add("select * from %s where UID_='%s' ", "product", uid);
		ds.open();
		if (!ds.eof()) {
			ds.edit();
			ds.setField("Desc_", desc);
			ds.setField("Spec_", spec);
			ds.setField("Unit_", unit);
			ds.post();
		} else {
			throw new RuntimeException("修改商品信息失败！");
		}
		return true;
	}

	public boolean saveProduct() {
		Record headIn = getDataIn().getHead();
		String code = headIn.getString("Code_");
		String desc = headIn.getString("Desc_");
		String spec = headIn.getString("Spec_");
		String unit = headIn.getString("Unit_");
		if ("".equals(code)) {
			throw new RuntimeException("商品编号不能为空！");
		}
		SqlQuery ds = new SqlQuery(this);
		ds.add("select * from %s where Code_='%s' ", "product", code);
		ds.open();
		if (ds.eof()) {
			ds.append();
			ds.setField("Code_", code);
			ds.setField("Desc_", desc);
			ds.setField("Spec_", spec);
			ds.setField("Unit_", unit);
			ds.setField("Stock_", 0);
			ds.post();
		} else {
			throw new RuntimeException("商品编号已存在，不可重复添加！");
		}
		return true;
	}

}

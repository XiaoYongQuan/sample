package cn.cerc.sample.services.ae;

import cn.cerc.jbean.core.CustomService;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jdb.mysql.BuildQuery;
import cn.cerc.jdb.mysql.SqlQuery;

public class SvrInventory extends CustomService {

	public boolean queryInventory() {
		Record headIn = getDataIn().getHead();
		BuildQuery f = new BuildQuery(this);
		if (headIn.hasValue("TB_")) {
			f.byField("h.TB_", headIn.getString("TB_"));
		}
		if (headIn.hasValue("TBNo_")) {
			f.byField("h.TBNo_", headIn.getString("TBNo_"));
		}
		if (headIn.hasValue("AppUser_")) {
			f.byField("h.AppUser_", headIn.getString("AppUser_"));
		}
		if (headIn.hasValue("dateStart") && !headIn.hasValue("dateEnd")) {
			f.byParam(String.format("h.TBDate_ >= '%s'",
					headIn.getString("dateStart")));
		}
		if (!headIn.hasValue("dateStart") && headIn.hasValue("dateEnd")) {
			f.byParam(String.format("h.TBDate_ <= '%s'",
					headIn.getString("dateEnd")));
		}
		if (headIn.hasValue("dateStart") && headIn.hasValue("dateEnd")) {
			f.byBetween("h.TBDate_", headIn.getString("dateStart"),
					headIn.getString("dateEnd"));
		}
		f.add("select * from %s h ", "tranh");
		SqlQuery ds = f.open();
		if (ds.eof()) {
			throw new RuntimeException(String.format("查询结果为空，请重新确认查询条件！"));
		}
		getDataOut().appendDataSet(ds);
		return true;
	}

	public boolean saveInventory() {
		Record headIn = getDataIn().getHead();
		String tbNo = headIn.getString("TBNo_");
		String tbDate = headIn.getString("TBDate_");
		String appUser = headIn.getString("AppUser_");
		if(tbNo==null||"".equals(tbNo)){
			throw new RuntimeException("单号不能为空！");
		}
		String tb = tbNo.substring(0, 2);
		if (!tb.equals("AE")) {
			throw new RuntimeException("你输入的不是盘点单号！");
		} else {
			SqlQuery ds = new SqlQuery(this);
			ds.add("select * from %s where TBNo_='%s'", "tranh", tbNo);
			ds.open();
			if (ds.eof()) {
				ds.append();
				ds.setField("TB_", tb);
				ds.setField("TBNo_", tbNo);
				ds.setField("TBDate_", tbDate);
				ds.setField("AppUser_", appUser);
				ds.setField("AppDate_", TDateTime.Now());
				ds.post();
			} else {
				throw new RuntimeException("单据编号已存在，不可重复添加！");
			}
		}
		return true;
	}

	public boolean saveTranB() {
		Record headIn = getDataIn().getHead();
		String tbNo = headIn.getString("TBNo_");
		String code = headIn.getString("Code_");
		double num = headIn.getDouble("Num_");
		SqlQuery dsPart = new SqlQuery(this);
		dsPart.add("select * from %s where Code_='%s' ", "product", code);
		dsPart.open();
		double stock = 0;
		if (num < 0) {
			throw new RuntimeException(String.format("盘点数量不能为负！"));
		} else {
			if (dsPart.eof()) {
				throw new RuntimeException(String.format("商品编号 %s 不存在！", code));
			} else {
				stock = dsPart.getDouble("Stock_");
				// 更新商品库存
				dsPart.edit();
				dsPart.setField("Stock_", num);
				dsPart.post();
			}
			SqlQuery dsB = new SqlQuery(this);
			dsB.add("select * from %s where TBNo_='%s' and Code_='%s'",
					"tranb", tbNo, code);
			dsB.open();
			if (dsB.eof()) {
				dsB.append();
				dsB.setField("It_", getMaxIt(tbNo));
				dsB.setField("TBNo_", tbNo);
				dsB.setField("Code_", code);
				dsB.setField("Desc_", dsPart.getString("Desc_"));
				dsB.setField("Spec_", dsPart.getString("Spec_"));
				dsB.setField("Unit_", dsPart.getString("Unit_"));
				dsB.setField("Num_", num - stock);
			} else {
				dsB.edit();
				dsB.setField("Num_", num - stock + dsB.getDouble("Num_"));
			}
			dsB.post();
		}
		return true;
	}

	public boolean deleteTranB() {
		Record headIn = getDataIn().getHead();
		String tbNo = headIn.getString("TBNo_");
		String code = headIn.getString("Code_");
		SqlQuery dsPart = new SqlQuery(this);
		dsPart.add("select * from %s where Code_='%s' ", "product", code);
		dsPart.open();
		SqlQuery ds = new SqlQuery(this);
		ds.add("select * from %s where TBNo_='%s' and Code_='%s'  ", "tranb",
				tbNo, code);
		ds.open();
		if (!ds.eof()) {
			// 更新商品库存
			dsPart.edit();
			dsPart.setField("Stock_",
					dsPart.getDouble("Stock_") - ds.getDouble("Num_"));
			dsPart.post();

			ds.delete();
		}
		return true;
	}

	public boolean queryTranbAndH() {
		Record headIn = getDataIn().getHead();
		String tbNo = headIn.getString("TBNo_");
		SqlQuery dsh = new SqlQuery(this);
		dsh.add("select * from %s where TBNo_='%s' ", "tranh", tbNo);
		dsh.open();
		if (!dsh.eof()) {
			getDataOut().getHead().copyValues(dsh.getCurrent());
		}
		SqlQuery dsb = new SqlQuery(this);
		dsb.add("select b.* ,p.Stock_ stock from %s b", "tranb");
		dsb.add("inner join %s p on b.Code_=p.Code_", "product");
		dsb.add("where b.TBNo_='%s'", tbNo);
		dsb.open();
		if (!dsb.eof()) {
			getDataOut().appendDataSet(dsb);
		}
		return true;
	}

	private int getMaxIt(String tbno) {
		SqlQuery dsIt = new SqlQuery(this);
		dsIt.add("select max(It_) as It_ from %s where TBNo_='%s' ", "tranb",
				tbno);
		dsIt.open();
		return dsIt.getInt("It_") + 1;
	}

}

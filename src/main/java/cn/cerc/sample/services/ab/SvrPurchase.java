package cn.cerc.sample.services.ab;

import cn.cerc.jbean.core.CustomService;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.core.TDateTime;
import cn.cerc.jdb.mysql.BuildQuery;
import cn.cerc.jdb.mysql.SqlQuery;

public class SvrPurchase extends CustomService {

	public boolean queryPurchase() {
		Record headIn = getDataIn().getHead();
		BuildQuery f = new BuildQuery(this);
		if (headIn.hasValue("TB_")) {
			f.byField("h.TB_", headIn.getString("TB_"));
		}
		if (headIn.hasValue("TBNo_")) {
			f.byField("h.TBNo_", headIn.getString("TBNo_"));
		}
		if (headIn.hasValue("SearchText_")) {
			f.byLink(new String[] { "h.SupName_", "h.AppUser_" },
					headIn.getString("SearchText_"));
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
		dsb.add("select b.*,p.Stock_ from %s b", "tranb");
		dsb.add("inner join %s p on b.Code_=p.Code_", "product");
		dsb.add("where b.TBNo_='%s'", tbNo);
		dsb.open();
		if (!dsb.eof()) {
			getDataOut().appendDataSet(dsb);
		}
		return true;
	}

	public boolean savePurchase() {
		Record headIn = getDataIn().getHead();
		String tbNo = headIn.getString("TBNo_");
		String tbDate = headIn.getString("TBDate_");
		String supName = headIn.getString("SupName_");
		String appUser = headIn.getString("AppUser_");
		if(tbNo==null||"".equals(tbNo)){
			throw new RuntimeException("单号不能为空！");
		}
		String tb = tbNo.substring(0, 2);
		if (!tb.equals("AB")) {
			throw new RuntimeException("你输入的不是进货单号！");
		} else {
			SqlQuery ds = new SqlQuery(this);
			ds.add("select * from %s where TBNo_='%s' ", "tranh", tbNo);
			ds.open();
			if (ds.eof()) {
				ds.append();
				ds.setField("TB_", tb);
				ds.setField("TBNo_", tbNo);
				ds.setField("TBDate_", tbDate);
				ds.setField("SupName_", supName);
				ds.setField("AppUser_", appUser);
				ds.setField("AppDate_", TDateTime.Now());
				ds.post();
			} else {
				throw new RuntimeException("单据编号已存在，不可重复添加！");
			}
		}
		return true;
	}

	public boolean saveUpDateTranH() {
		Record headIn = getDataIn().getHead();
		String tbNo = headIn.getString("TBNo_");
		String tbDate = headIn.getString("TBDate_");
		String supName = headIn.getString("SupName_");
		SqlQuery ds = new SqlQuery(this);
		ds.add("select * from %s where TBNo_='%s' ", "tranh", tbNo);
		ds.open();
		if (!ds.eof()) {
			ds.edit();
			ds.setField("TBDate_", tbDate);
			ds.setField("SupName_", supName);
			ds.setField("AppDate_", TDateTime.Now());
			ds.post();
		} else {
			throw new RuntimeException("保存失败！");
		}
		return true;
	}

	public boolean saveTranB() {
		Record headIn = getDataIn().getHead();
		String tbNo = headIn.getString("TBNo_");
		String code = headIn.getString("Code_");
		double num = headIn.getDouble("Num_");
		if (num < 0) {
			throw new RuntimeException("进货数量不能为负数！");
		} else {
			SqlQuery dsPart = new SqlQuery(this);
			dsPart.add("select * from %s where Code_='%s' ", "product", code);
			dsPart.open();
			if (dsPart.eof()) {
				throw new RuntimeException(String.format("商品编号 %s 不存在！", code));
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
				dsB.setField("Num_", num);
			} else {
				dsB.edit();
				dsB.setField("Num_", dsB.getDouble("Num_") + num);
			}
			dsB.post();
			// 更新商品库存
			dsPart.edit();
			dsPart.setField("Stock_", dsPart.getDouble("Stock_") + num);
			dsPart.post();
		}
		return true;
	}

	public boolean findTranB() {
		Record headIn = getDataIn().getHead();
		String tbNo = headIn.getString("TBNo_");
		String code = headIn.getString("Code_");
		SqlQuery ds = new SqlQuery(this);
		ds.add("select * from %s where TBNo_='%s' and Code_='%s' ", "tranb",
				tbNo, code);
		ds.open();
		if (!ds.eof()) {
			getDataOut().getHead().copyValues(ds.getCurrent());
		}

		return true;
	}

	public boolean saveUpDateTranB() {
		Record headIn = getDataIn().getHead();
		String tbNo = headIn.getString("TBNo_");
		String code = headIn.getString("Code_");
		double num = headIn.getDouble("Num_");
		if (num < 0) {
			throw new RuntimeException("进货数量不能为负数！");
		} else {
			SqlQuery dsPart = new SqlQuery(this);
			dsPart.add("select * from %s where Code_='%s' ", "product", code);
			dsPart.open();
			SqlQuery ds = new SqlQuery(this);
			ds.add("select * from %s where TBNo_='%s' and Code_='%s'  ",
					"tranb", tbNo, code);
			ds.open();
			if (!ds.eof()) {
				// 更新商品库存
				dsPart.edit();
				dsPart.setField("Stock_",
						dsPart.getDouble("Stock_") - ds.getDouble("Num_") + num);
				dsPart.post();
				ds.edit();
				ds.setField("Num_", num);
				ds.post();
			}
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

	private int getMaxIt(String tbNo) {
		SqlQuery dsIt = new SqlQuery(this);
		dsIt.add("select max(It_) as It_ from %s where TBNo_='%s' ", "tranb",
				tbNo);
		dsIt.open();
		return dsIt.getInt("It_") + 1;
	}

}

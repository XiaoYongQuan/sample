package cn.cerc.sample.services.inAndout;

import cn.cerc.jbean.core.CustomService;
import cn.cerc.jdb.core.Record;
import cn.cerc.jdb.mysql.BuildQuery;
import cn.cerc.jdb.mysql.SqlQuery;

public class SvrInAndOut extends CustomService {

	public boolean queryDetailed() {
		Record headIn = getDataIn().getHead();
		BuildQuery f = new BuildQuery(this);
		if (headIn.hasValue("Code_")) {
			f.byField("b.Code_", headIn.getString("Code_"));
		}
		if (headIn.hasValue("SearchText_")) {
			f.byLink(new String[] {"h.TB_","h.TBNo_","b.Desc_","h.AppUser_", "b.Spec_", "b.Unit_"}, headIn.getString("SearchText_")); 
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
		f.add("select * from %s b ", "tranb");
		f.add("inner join %s h on h.TBNo_=b.TBNo_", "tranh");
		SqlQuery ds = f.open();
		if (ds.eof()) {
			throw new RuntimeException(String.format("查询结果为空，请重新确认查询条件！"));
		}
		double abNum = 0;
		double bcNum = 0;
		double aeNum = 0;
		ds.first();
		while (ds.fetch()) {
			getDataOut().append().copyRecord(ds.getCurrent());
			if (ds.getString("TB_").equals("AB")) {
				abNum += ds.getDouble("Num_");
			}
			if (ds.getString("TB_").equals("BC")) {
				bcNum += ds.getDouble("Num_");
			}
			if (ds.getString("TB_").equals("AE")) {
				aeNum += ds.getDouble("Num_");
			}
		}
		getDataOut().last();
		getDataOut().append();
		getDataOut().setField("Code_", "合计");
		getDataOut().setField("TB_", "入库数量：");
		getDataOut().setField("TBNo_", abNum);
		getDataOut().setField("TBDate_", "出库数量：");
		getDataOut().setField("AppUser_", bcNum);
		getDataOut().setField("AppDate_", "差异量：");
		getDataOut().setField("Desc_", aeNum);
		return true;
	}
}

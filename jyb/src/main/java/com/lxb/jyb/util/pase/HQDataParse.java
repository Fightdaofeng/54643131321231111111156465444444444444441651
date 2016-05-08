package com.lxb.jyb.util.pase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lxb.jyb.bean.HQData;

import android.text.TextUtils;

public class HQDataParse {

	// 解析行情数据
	public static HQData parseHQData(String name, String json)
			throws JSONException {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		// 解析Json
		JSONArray array = new JSONArray(json);
		JSONObject jsonRoot = array.getJSONObject(0);
		HQData data = new HQData();
		data.setChange(jsonRoot.getDouble("change"));
		data.setChangePercent(jsonRoot.getDouble("changePercent"));
		data.setClose(jsonRoot.getDouble("close"));
		data.setCode(jsonRoot.getString("code"));
		data.setHight(jsonRoot.getDouble("high"));
		data.setLow(jsonRoot.getDouble("low"));
		data.setName(name);
		data.setNewPrice(jsonRoot.getDouble("newPrice"));
		data.setOpen(jsonRoot.getDouble("open"));
		data.setTime(jsonRoot.getLong("time"));
		data.setBaoliu(jsonRoot.getInt("baoliu"));
		return data;
	}
}

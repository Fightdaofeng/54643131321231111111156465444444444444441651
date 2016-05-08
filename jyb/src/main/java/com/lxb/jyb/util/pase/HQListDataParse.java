package com.lxb.jyb.util.pase;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.lxb.jyb.bean.HQData;
import com.lxb.jyb.bean.HQentity;

public class HQListDataParse {

	// 解析行情数据
	public static HashMap<String, Object> parseHQData(String json,
			ArrayList<HQentity> hqentity) throws JSONException {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		// 解析Json
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<HQData> list = new ArrayList<HQData>();
		JSONArray array = new JSONArray(json);
		for (int i = 0; i < array.length(); i++) {
			HQData data;
			if (array.get(i).toString().length() > 4) {
				JSONObject jsonRoot = array.getJSONObject(i);
				data = new HQData();
				data.setChange(jsonRoot.getDouble("change"));
				data.setChangePercent(jsonRoot.getDouble("changePercent"));
				data.setClose(jsonRoot.getDouble("close"));
				data.setCode(jsonRoot.getString("code"));
				data.setHight(jsonRoot.getDouble("high"));
				data.setLow(jsonRoot.getDouble("low"));
				data.setName(hqentity.get(i).getName());
				data.setNewPrice(jsonRoot.getDouble("newPrice"));
				data.setOpen(jsonRoot.getDouble("open"));
				data.setTime(jsonRoot.getLong("time"));
				data.setBaoliu(hqentity.get(i).getBaoliu());
			} else {
				data = new HQData(0, 0, 0, "", 0, 0, hqentity.get(i).getName(),
						0, 0, 0, 0);
			}
			list.add(data);

		}
		map.put("hqlist", list);
		return map;
	}
}

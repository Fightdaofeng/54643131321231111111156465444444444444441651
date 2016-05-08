package com.lxb.jyb.push;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

public class TipPase {
	public static Tip paseTip(String json) throws JSONException {
		if (TextUtils.isEmpty(json)) {
			return null;
		}
		// 解析Json
//		JSONObject jsonObject = new JSONObject(json);
		JSONArray array = new JSONArray(json);
		JSONObject jsonRoot = array.getJSONObject(0);
		Tip tip = new Tip();
		tip.setTipName(jsonRoot.optString("tipName"));
		tip.setId(jsonRoot.optString("id"));
		tip.setValue(jsonRoot.optString("value"));
		tip.setTime(jsonRoot.optString("time"));
		tip.setPeriod(jsonRoot.optString("period"));
		tip.setCountry(jsonRoot.optString("country"));
		tip.setName(jsonRoot.optString("name"));
		tip.setPredictValue(jsonRoot.optString("predictValue"));
		tip.setLastValue(jsonRoot.optString("lastValue"));
		return tip;
	}

}

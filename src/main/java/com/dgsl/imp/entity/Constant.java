package com.dgsl.imp.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Constant {

	private static JSONObject data = null;

	static {
		data = new JSONObject();

		try {
			JSONArray lArray = new JSONArray();

			JSONObject data1 = new JSONObject();
			data1.put("ID", "data1");
			data1.put("HEADER", "INTEREST RATES ON DEPOSITS");
			data1.put("BODY",
					"The current interest rate of term deposits of the Bank along with the proposed changes in light of changing market environment is provided in this note. The sections of the note are defined as follows:,@"
							+ "		A.	Summary of proposed Changes,@" + "		B.	Market updates,@"
							+ "		C.	Callable deposits current rates and proposed rates,@"
							+ "		D.	Non-callable deposits current rates and proposed rates,@"
							+ "		E.	Pre-mature closure penal interest application,@"
							+ "		F.	Interest rate codes to be changed,@");
			lArray.put(data1);

			JSONObject data2 = new JSONObject();
			data2.put("ID", "data2");
			data2.put("HEADER", "A.	Summary of proposed changes");
			data2.put("BODY",
					"In this note, changes in interest rates on term deposits are proposed wef 10th November 2021 as follows:,@,@"
							+ "#tableData,@,@" + "Rationale for change,@"
							+ "We had reduced our short term bulk deposit rates on 14th August 2021, 9th September 2021 and 23rd September 2021. These reductions were because of short term inflows, surplus liquidity and comfortable LCR (~120% to ~130%)."
							+ "Short term rates in the market have started rising guided by higher VRRR cut offs since last one month. (Last 15 day VRRR cut off was 3.99% with WAR of 3.95% on 3rd November 2021)"
							+ "T-Bill rates have also started rising, with last auction cut offs: 3M – 3.65%, 6M – 3.88% and 1Y 4.09%."
							+ "We have seen NRTD inflows slowing down since last two weeks due to our reduced bulk deposit rates. Our LCR has also come down and is hovering around 110%. To fund the expected balance sheet growth in last two quarters of the FY and to maintain LCR, we need to focus on bulk deposits. As such we are proposing increase in callable short term deposits rates upto 1 year by 10-25 bps to match our peers. We are not increasing rates &gt;=1Y as we are already competitive here."
							+ "To increase LCR and garner LCR accretive deposits we are proposing increase in non-callable deposits spread to 25 bps (35 bps in 1Y &lt; 18M) in tenors &gt;6M. We are changing the spread to 35 bps  in 1Y &lt; 18M tenors between callable and non-callable deposits as ~70% of our non-callable deposits are in these tenor brackets. Non-callable deposits are considered for outflow only in last month of maturity. We are increasing non-callable spreads in tenors &gt; 6M so that these deposits are not considered in calculations of outflow for LCR in current FY."
							+ "We will continue to review the deposit rates and inflows on a regular basis and put up proposed changes as and when appropriate.");
			lArray.put(data2);

			JSONObject data3 = new JSONObject();
			data3.put("ID", "data3");
			data3.put("HEADER", "B.	Market updates");
			data3.put("BODY",
					"RBI in its 4th bi-monthly policy FY22, had maintained the key policy rate unchanged and retained the accommodative stance. "
							+ "The MPC had maintained the GDP growth forecast for FY22 to 9.5%, and CPI for the current fiscal was projected at 5.3% vs 5.1%."
							+ "VRRR cut offs have been coming higher and short-term rates are seen going upwards."
							+ "T-Bill rates last auction cut offs: 3M – 3.65%, 6M – 3.88% and 1Y 4.09%."
							+ "Deposit growth of the banking system decreased to 9.9% YoY (prev: 10.2%) in fortnight ended 22nd  October’21, while credit growth increased to 6.9% YoY (prev: 6.5%) for the same period."
							+ "Deposit growth is higher than the credit growth on account of RBI accommodation through OMOs under G-SAP, augmented by weaker currency leakages this year so far.");
			lArray.put(data3);

			JSONObject data4 = new JSONObject();
			data4.put("ID", "data4");
			data4.put("HEADER", "C.	Callable deposits current rates and proposed rates");
			data4.put("BODY",
					"The interest rates structure with effect from 01st November 2021 on Domestic, NRO and NRE deposits of our Bank is as follows:");
			lArray.put(data4);

			JSONObject data5 = new JSONObject();
			data5.put("ID", "data5");
			data5.put("HEADER", "D.	Non-callable deposits current rates and proposed rates");
			data5.put("BODY",
					"The interest rates structure with effect from 01st November 2021on Domestic and NRE deposits of Non-callable of our Bank is as follows: ");
			lArray.put(data5);

			JSONObject data6 = new JSONObject();
			data6.put("ID", "data6");
			data6.put("HEADER", "E.	Pre-mature closure penal interest application");
			data6.put("BODY", "Pre-mature closure penal interest applicability is mentioned in the table below:");
			lArray.put(data6);

			JSONObject data7 = new JSONObject();
			data7.put("ID", "data7");
			data7.put("HEADER", "F.	Interest Rate Codes to be changed");
			data7.put("BODY",
					"The Interest rate codes to undergo change are highlighted in bold on account of proposed rate revision.");
			lArray.put(data7);

			data.put("data", lArray);
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public static JSONObject getData() {
		return data;
	}

	public static void setData(JSONObject pData) {
		data = pData;
	}

	public static Map<String, List<CardEntity>> createTestData() throws JSONException {
		Map<String, List<CardEntity>> data = new HashMap<>();

		List<CardEntity> cardItems = new ArrayList<>();

		JSONArray lArray = Constant.getData().getJSONArray("data");

		for (int i = 0; i < lArray.length(); i++) {
			JSONObject item = lArray.getJSONObject(i);

			CardEntity CardEntityItem = new CardEntity();
			CardEntityItem.setID(item.getString("ID"));
			CardEntityItem.setHEADER(item.getString("HEADER"));
			CardEntityItem.setBODY(item.getString("BODY"));

			cardItems.add(CardEntityItem);
		}

		data.put("data", cardItems);
		return data;
	}

	public static EmailDetails getEmailData(EmailDetails EmailDetails) {

		EmailDetails lEmailDetails = new EmailDetails("aniket.pednekar01@gmail.com", "Dear Sir/Madam,\r\n" + "\r\n"
				+ "You are requested to urgently take action on approval process for Deposit Rate change in application.\r\n"
				+ "\r\n" + "Table:\r\n" + "Request No. ${requestNo} \r\n"
				+ "Subject : Deposit Rate Change - Callable/Non-callable deposit w.e.f. ${depositRate}\r\n"
				+ "Initiator: ${empID} (emp ID)\r\n" + "Department:${department}.\r\n" + "\r\n"
				+ "Click on the following link to view details:\r\n" + "https://${link}\r\n" + "\r\n" + "\r\n"
				+ "This is system generated mail please do not reply to this mail.",
				"Change in Rate of Interest on Term Depsosit");

		return lEmailDetails;
	}

	public static TestEntity getBodyEnity() {
		TestEntity lEntity = new TestEntity();

		lEntity.setDepartment("IMP");
		lEntity.setDepositRate("12358");
		lEntity.setEmpID("145258");
		lEntity.setLink("www.google.com");
		lEntity.setRequestNo("DGSL123456");

		return lEntity;

	}

}

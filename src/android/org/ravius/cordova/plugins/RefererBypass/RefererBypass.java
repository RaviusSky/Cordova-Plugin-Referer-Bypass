package org.ravius.cordova.plugins;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RefererBypass extends CordovaPlugin
{
	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException
	{
		if (action.equals("get"))
		{
			String url = args.getString(0);
			String referer = args.getString(1);
			this.get(url, referer, callbackContext);
			return true;
		}
	}

	private void get(String url, String referer, CallbackContext callbackContext)
	{
		if (url != null && url.length() > 0 && referer != null && referer.length() > 0)
		{
			URL u = new URL(url);
			HttpURLConnection con = (HttpURLConnection) u.openConnection();
			con.setRequestMethod("GET");

			con.setRequestProperty("referer", referer);
			con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
			con.setRequestProperty("Accept", "*/*");
			con.setRequestProperty("Cache-Control", "no-cache");
			con.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
			con.setRequestProperty("Connection", "keep-alive");

			int code = con.getResponseCode();
			System.out.println(code);

			try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8")))
			{
				StringBuilder res = new StringBuilder();
				String resLine = null;

				while ((resLine = br.readLine()) != null)
				{
					res.append(resLine.trim());
				}

				callbackContext.success(res.toString());
			}
		}
		else
		{
			callbackContext.error("Expected 2 non-empty string arguments.");
		}
	}
}

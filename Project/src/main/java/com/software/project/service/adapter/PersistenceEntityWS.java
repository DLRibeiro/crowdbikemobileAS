package com.software.project.service.adapter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.software.project.entities.Attributes;
import com.software.project.entities.Entity;

@Service("PersistenceEntity")
@Transactional(propagation=Propagation.REQUIRED)
public class PersistenceEntityWS implements PersistenceEntity {
    
	private Gson gson;
	private String uri = "http://148.6.80.19:1026/v1/contextEntities";
	private int responseCode = 0;
	private String result = "";  
	private String line = "";
	private HttpClient client;
	private HttpResponse response;
	private BufferedReader rd;

	@Override
	public void createNew(Entity entity) throws Exception {
		// TODO Auto-generated method stub	
		try {
			uri += "/" +entity.getId();
			client = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(uri);
		    httppost.setHeader("Accept", "application/json");
			gson = new Gson();
			StringEntity entityPost = new StringEntity(gson.toJson(entity));
			entityPost.setContentType("application/json");
			httppost.setEntity(entityPost);
			int executeCount = 0;
			do {
				executeCount++;
				response = client.execute(httppost);
				responseCode = response.getStatusLine().getStatusCode();						
			} while (executeCount < 5 && responseCode == 408);
			      rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = rd.readLine()) != null){
				result += line.trim();
			}	      
		} catch (Exception e) {
			responseCode = 408;
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Entity> getAll() throws Exception {
	
		try {
			client = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(uri);
		    httpget.setHeader("Accept", "application/json");		
		    httpget.setHeader("Content-type", "application/json");

			int executeCount = 0;
			do {
				executeCount++;
				response = client.execute(httpget);
				responseCode = response.getStatusLine().getStatusCode();						
			} while (executeCount < 5 && responseCode == 408);
			      rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = rd.readLine()) != null){
				result += line.trim();
			}	      
		} catch (Exception e) {
			responseCode = 408;
			e.printStackTrace();
		}
	
		List<Entity> contextElement = AdapterOcurrence.parseListEntity(result);
		
		// TODO Auto-generated method stub
		return contextElement;
	}

	@Override
	public List<Entity> getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getByLatLng(double lat, double lng) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long countEntity() throws Exception {
		// TODO Auto-generated method stub
		int count = getAll().size();
		return Long.parseLong(String.valueOf(count));
	}

	@Override
	public void deleteById(Long id) throws Exception {
		// TODO Auto-generated method stub
		try {
			uri += "/" +String.valueOf(id);
			client = new DefaultHttpClient();
			HttpDelete httpdelete = new HttpDelete(uri);
			httpdelete.setHeader("Accept", "application/json");
			httpdelete.setHeader("Content-type", "application/json");
			gson = new Gson();
			int executeCount = 0;
			do {
				executeCount++;
				response = client.execute(httpdelete);
				responseCode = response.getStatusLine().getStatusCode();						
			} while (executeCount < 5 && responseCode == 408);
			      rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			while ((line = rd.readLine()) != null){
				result += line.trim();
			}	      
		} catch (Exception e) {
			responseCode = 408;
			e.printStackTrace();
		}
	}

	@Override
	public Long countEntityByType(String title) throws Exception {
		// TODO Auto-generated method stub
		int count = 0;
		List<Entity> l = getAll();
		for (Entity entity : l) {
			for (Attributes att : entity.getAttributes()) {
				if (title.equalsIgnoreCase(att.getValue()) && att.getName().equalsIgnoreCase("title")) {
					count++;
				}
			}
		}
		return null;
	}
	
	

}

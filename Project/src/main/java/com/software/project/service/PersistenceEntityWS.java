package com.software.project.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
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
	private HttpPost httppost;
	private StringEntity entityPost;
	private HttpResponse response;
	private BufferedReader rd;

	@Override
	public void createNew(Entity entity) throws Exception {
		// TODO Auto-generated method stub	
		try {
			client = new DefaultHttpClient();
			httppost = new HttpPost(uri);
		    httppost.setHeader("Accept", "application/json");
			gson = new Gson();
			entityPost = new StringEntity(gson.toJson(entity));
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
		// TODO Auto-generated method stub
		return null;
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
	public Long countEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long countEntityByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
